package eu.kalafatic.evolution.controller.orchestration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.kalafatic.evolution.controller.agents.AgentFactory;
import eu.kalafatic.evolution.controller.agents.AnalyticAgent;
import eu.kalafatic.evolution.controller.agents.BaseAiAgent;
import eu.kalafatic.evolution.controller.agents.IAgent;
import eu.kalafatic.evolution.controller.agents.ProposalConsolidatorAgent;
import eu.kalafatic.evolution.controller.agents.RepairAgent;
import eu.kalafatic.evolution.controller.agents.ValidatorAgent;
import eu.kalafatic.evolution.controller.orchestration.util.CodeExtractor;
import eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants;
import eu.kalafatic.evolution.controller.tools.ToolFactory;
import eu.kalafatic.evolution.model.orchestration.Task;


/**
 * Core Orchestrator implementation. Blind executor of tasks.
 * Strictly gated by SystemState.
 */
public class EvolutionOrchestrator implements IOrchestrator {

    private static final int MAX_RETRIES = EvolutionConstants.MAX_TASK_RETRIES;
    private AnalyticAgent analyticAgent;
    private ValidatorAgent validator;
    private RepairAgent repairAgent;
    private ProposalConsolidatorAgent consolidator;
    private final List<IAgent> availableAgents = new ArrayList<>();
    private AiService aiService = new AiService();
    private final SessionContainer sessionContainer;

    public EvolutionOrchestrator() {
        this(null);
    }

    public EvolutionOrchestrator(SessionContainer container) {
        this.sessionContainer = container;
        if (container != null) {
            Map<String, IAgent> registry = (container instanceof SessionContext) ?
                    ((SessionContext)container).getAgentRegistry() : new java.util.HashMap<>();

            if (registry.isEmpty()) {
                List<IAgent> isolated = AgentFactory.createIsolatedAgents(container);
                isolated.forEach(a -> registry.put(a.getType(), a));
            }

            availableAgents.addAll(registry.values());
            analyticAgent = (AnalyticAgent) registry.get(EvolutionConstants.AGENT_ANALYTIC);
            validator = (ValidatorAgent) registry.get(EvolutionConstants.AGENT_VALIDATOR);
            repairAgent = (RepairAgent) registry.get(EvolutionConstants.AGENT_REPAIR);
            consolidator = (ProposalConsolidatorAgent) registry.get(EvolutionConstants.AGENT_PROPOSAL_CONSOLIDATOR);
        } else {
            // Orchestrator without a container is now discouraged but kept for some legacy paths
            // We should ideally throw an exception here if we want STRICT isolation
            analyticAgent = null;
            validator = null;
            repairAgent = null;
            consolidator = null;
        }
    }

    public void setAiService(AiService aiService) {
        this.aiService = aiService;
        if (analyticAgent != null) analyticAgent.setAiService(aiService);
        if (validator != null) validator.setAiService(aiService);
        if (repairAgent != null) repairAgent.setAiService(aiService);
        if (consolidator != null) consolidator.setAiService(aiService);
    }

    @Override
    public OrchestratorResponse handle(TaskRequest taskRequest, TaskContext context) throws Exception {
        SessionContainer session = sessionContainer != null ? sessionContainer : SessionManager.getInstance().getOrCreateSession(context.getSessionId());

        IterationManager kernel = session.getIterationManager();
        if (kernel == null) {
            kernel = KernelFactory.create(context, session, aiService);
            session.setIterationManager(kernel);
        }

        return kernel.handle(taskRequest);
    }

    @Override
    public String execute(String request, TaskContext context) throws Exception {
        try {
            context.log("Orchestrator: Starting request - " + request);

            // 1. Planning
            OrchestrationStatusManager.getInstance().updateAgentStatus("Planner", "Planning...");
            List<Task> originalPlannedTasks = planner.plan(request, context);
            OrchestrationStatusManager.getInstance().updateAgentStatus("Planner", "Finished");
            context.getOrchestrator().getTasks().clear();
            context.getOrchestrator().getTasks().addAll(originalPlannedTasks);

            // Pause for Plan Approval
            context.log("Orchestrator: Plan generated. Waiting for user review and approval...");
            Boolean planApproved = context.requestApproval(TaskContext.PLAN_APPROVAL_MESSAGE).get();
            if (planApproved == null || !planApproved) {
                context.log("Orchestrator: Plan rejected by user.");
                throw new Exception("Orchestration plan rejected by user.");
            }
            context.log("Orchestrator: Plan approved. Starting execution...");

            // Reload tasks from model in case the user modified them during approval
            List<Task> tasks = new ArrayList<>(context.getOrchestrator().getTasks());

            // 2. Execution Loop
            int taskCount = tasks.size();
            String lastResult = "";
            for (int i = 0; i < taskCount; i++) {
                Task task = tasks.get(i);

                // Check for User Approval
                if (task.isApprovalRequired() || "approval".equalsIgnoreCase(task.getType())) {
                    task.setStatus(TaskStatus.WAITING_FOR_APPROVAL);
                    context.log("Orchestrator: Waiting for user approval for task: " + task.getName());
                    Boolean approved = context.requestApproval("Approve task: " + task.getName() + "?").get();
                    if (approved == null || !approved) {
                        task.setStatus(TaskStatus.FAILED);
                        task.setFeedback("Rejected by user.");
                        throw new Exception("Task rejected by user: " + task.getName());
                    }
                }

                task.setStatus(TaskStatus.RUNNING);
                double progress = (double) i / taskCount;
                updateStatus(context, progress, "Executing: " + task.getName());

                boolean success = executeTaskWithRetries(task, context);

                if (!success) {
                    task.setStatus(TaskStatus.FAILED);
                    throw new Exception("Task failed after maximum retries: " + task.getName());
                }

                task.setStatus(TaskStatus.DONE);
                lastResult = task.getResponse();
                context.appendSharedMemory("Task [" + task.getName() + "] completed. Result: " + lastResult);

                // Transfer Loop Authority (Phase D1 Step 2)
                // In ECOS, 'Loop' is replaced by Kernel-driven BACKTRACK or recursive EVOLVE
                if (context.getOrchestrator().getSelfDevSession() != null && !context.getOrchestrator().getSelfDevSession().getIterations().isEmpty()) {
                    Iteration iteration = context.getOrchestrator().getSelfDevSession().getIterations().get(0);
                    Lineage lineage = new IterationLineageAdapter(iteration);

                    // Consult Kernel for post-task strategy
                    EvolutionDecision decision = kernel.analyze(new TaskArtifactAdapter(task), null, lineage, context);
                    if (decision == EvolutionDecision.BACKTRACK) {
                        Artifact target = kernel.selectTarget(lineage, decision, context);
                        int targetIndex = -1;
                        for (int j = 0; j < tasks.size(); j++) {
                            if (tasks.get(j).getId().equals(target.getId())) {
                                targetIndex = j;
                                break;
                            }
                        }
                        if (targetIndex != -1) {
                            context.log("Orchestrator: Kernel requested BACKTRACK to: " + target.getId());
                            i = targetIndex - 1;
                            continue;
                        }
                    }
                }

                // Legacy fallback for non-ECOS tasks (to be removed in Phase F)
                String loopToId = task.getLoopToTaskId();
                if (loopToId != null && !loopToId.isEmpty() && !"none".equalsIgnoreCase(loopToId)) {
                    int loopTargetIndex = -1;
                    for (int j = 0; j < tasks.size(); j++) {
                        if (loopToId.equals(tasks.get(j).getId())) {
                            loopTargetIndex = j;
                            break;
                        }
                    }
                    if (loopTargetIndex != -1) {
                        context.log("Orchestrator: Legacy Looping back to task ID: " + loopToId);
                        i = loopTargetIndex - 1;
                    }
                }
            }

    @Override
    public String executeTask(Task task, TaskContext context) throws Exception {
        SystemState kernelState = context.getStateHolder().getState();
        if (kernelState != SystemState.EXECUTING && kernelState != SystemState.INIT && kernelState != SystemState.ANALYZING) {
             throw new IllegalStateException("Kernel violation: Orchestrator executeTask() must be gated by the Control Plane. State: " + kernelState);
        }
        return executeTaskInternal(task, context);
    }

    private String executeTaskInternal(Task task, TaskContext context) throws Exception {
        IAgent agent = findAgentForTask(task, context);
        if (agent instanceof BaseAiAgent) {
            ((BaseAiAgent)agent).setAiService(aiService);
        }

        context.checkPause();

        ContextPackage contextPkg = ContextBuilder.build(task, context, 1, task.getFeedback());
        String contextPrompt = ContextBuilder.buildPrompt(contextPkg);

        String patch = generatePatch(task, agent, context, task.getFeedback(), task.getPlan(), contextPrompt);
        String result = applyPatch(task, agent, context, task.getFeedback(), patch);
        task.setResponse(result);

        return result;
    }

    private String generatePatch(Task task, IAgent agent, TaskContext context, String lastFeedback, String localPlan, String contextPrompt) throws Exception {
        if ("file".equalsIgnoreCase(task.getType())) return agent.process(task.getDescription(), context, lastFeedback);
        return contextPrompt;
    }

    private String applyPatch(Task task, IAgent agent, TaskContext context, String lastFeedback, String patch) throws Exception {
        String taskType = task.getType();
        String taskName = task.getName();

        if ("file".equalsIgnoreCase(taskType)) {
            String path = taskName.replaceFirst("(?i)^(Write|Create|Update|MKDIR|DELETE)\\s+", "").trim().split(" ")[0];
            path = path.replaceFirst("^([a-zA-Z]:)?[/\\\\]+", "").replace('\\', '/');

            if (path == null || path.isEmpty() || "null".equals(path)) {
                throw new Exception("Kernel Violation: Attempted to write to a null or empty path: " + taskName);
            }

            String extractedCode = CodeExtractor.extractCode(patch);
            return ToolFactory.getTool(EvolutionConstants.TOOL_FILE).execute("WRITE " + path + "\n" + extractedCode, context.getProjectRoot(), context);
        } else if (EvolutionConstants.TASK_MAVEN.equalsIgnoreCase(taskType)) {
            return ToolFactory.getTool(EvolutionConstants.TOOL_MAVEN).execute(taskName, context.getProjectRoot(), context);
        } else if (EvolutionConstants.TASK_GIT.equalsIgnoreCase(taskType)) {
            return ToolFactory.getTool(EvolutionConstants.TOOL_GIT).execute(taskName, context.getProjectRoot(), context);
        } else if (EvolutionConstants.TASK_SHELL.equalsIgnoreCase(taskType)) {
            return ToolFactory.getTool(EvolutionConstants.TOOL_SHELL).execute(taskName, context.getProjectRoot(), context);
        }
        return agent.process(taskName, context, lastFeedback);
    }

    private IAgent findAgentForTask(Task task, TaskContext context) {
        String type = task.getType().toLowerCase();
        SessionContainer session = sessionContainer != null ? sessionContainer : SessionManager.getInstance().getSession(context.getSessionId());

        if (session != null) {
            Map<String, IAgent> registry = (session instanceof SessionContext) ?
                    ((SessionContext)session).getAgentRegistry() : new java.util.HashMap<>();
            if (type.equals(EvolutionConstants.TASK_FILE)) return registry.get(EvolutionConstants.AGENT_FILE);
            if (type.equals(EvolutionConstants.TASK_MAVEN)) return registry.get(EvolutionConstants.AGENT_MAVEN);
            return registry.get(EvolutionConstants.AGENT_GENERAL);
        }

        return null;
    }
}
