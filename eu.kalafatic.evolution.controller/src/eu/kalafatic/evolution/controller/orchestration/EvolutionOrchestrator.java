package eu.kalafatic.evolution.controller.orchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import eu.kalafatic.evolution.model.orchestration.*;
import eu.kalafatic.evolution.controller.manager.OrchestrationStatusManager;

/**
 * Core Orchestrator implementation that manages the task lifecycle and execution.
 */
public class EvolutionOrchestrator implements IOrchestrator {

    private final IEvolutionKernel kernel = new BaseEvolutionKernel();
    private final PlannerAgent planner = new PlannerAgent();
    private final List<IAgent> availableAgents = new ArrayList<>();
    private final ReviewerAgent reviewer = new ReviewerAgent();

    public EvolutionOrchestrator() {
        // Initialize default agents
        availableAgents.add(new ArchitectAgent());
        availableAgents.add(new JavaDevAgent());
        availableAgents.add(new TesterAgent());
        availableAgents.add(new ReviewerAgent());
        availableAgents.add(new GeneralAgent());
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

            updateStatus(context, 1.0, "Completed");
            return lastResult != null && !lastResult.isEmpty() ? lastResult : "Orchestration successful.";
        } catch (Exception e) {
            context.log("Orchestrator Error: " + e.getMessage());
            throw e;
        } finally {
            OrchestrationStatusManager.getInstance().updateAgentStatus("Planner", "Idle");
            for (IAgent agent : availableAgents) {
                OrchestrationStatusManager.getInstance().updateAgentStatus(agent.getType(), "Idle");
            }
        }
    }

    private boolean executeTaskWithRetries(Task task, TaskContext context) throws Exception {
        IAgent agent = findAgentForTask(task, context);
        String lastFeedback = null;
        OrchestrationStatusManager.getInstance().updateAgentStatus(agent.getType(), "Executing: " + task.getName());

        int attempt = 0;
        int maxAttempts = 5; // Hard safety backstop
        while (attempt < maxAttempts) {
            attempt++;
            context.log("Orchestrator: Executing " + task.getName() + " (Attempt " + attempt + " of " + maxAttempts + ")");

            try {
                String result = performAction(task, agent, context, lastFeedback);
                task.setResponse(result);

                JSONObject evalJson = reviewer.evaluate(result, task.getName(), context);

                Artifact artifact = new TaskArtifactAdapter(task);
                Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
                evaluation.setScore(evalJson.optBoolean("success", false) ? 1.0 : 0.0);
                evaluation.setComment(evalJson.optString("feedback", ""));

                // In Phase F/G, Orchestrator handles per-task evolution via Kernel
                Lineage lineage = null;
                if (context.getOrchestrator().getSelfDevSession() != null && !context.getOrchestrator().getSelfDevSession().getIterations().isEmpty()) {
                    lineage = new IterationLineageAdapter(context.getOrchestrator().getSelfDevSession().getIterations().get(0));
                }

                EvolutionDecision decision = kernel.analyze(artifact, evaluation, lineage, context);

                if (decision == EvolutionDecision.STABILIZE) {
                    task.setFeedback("Success: " + evalJson.optString("comment", "Pressure resolved."));
                    return true;
                } else if (decision == EvolutionDecision.ABORT) {
                    task.setFeedback("Kernel ABORT: " + evaluation.getComment());
                    return false;
                }

                lastFeedback = evaluation.getComment();
                task.setFeedback("Attempt " + attempt + " (" + decision + "): " + lastFeedback);

            } catch (Exception e) {
                lastFeedback = "Exception: " + e.getMessage();
                task.setFeedback("Attempt " + attempt + " exception: " + e.getMessage());
                return false;
            }
        }
    }

    private String performAction(Task task, IAgent agent, TaskContext context, String lastFeedback) throws Exception {
        String taskType = task.getType();
        String taskName = task.getName();
        if ("file".equalsIgnoreCase(taskType)) {
            FileTool fileTool = new FileTool();
            String content = agent.process(taskName, context, lastFeedback);
            String path = taskName.replaceFirst("(?i)Write ", "").trim();
            path = path.replaceFirst("^([a-zA-Z]:)?(/|\\\\)+", "");
            path = path.replace("\\", "/");
            return fileTool.execute("WRITE " + path + "\n" + content, context.getProjectRoot(), context);
        } else if ("maven".equalsIgnoreCase(taskType)) {
            MavenTool mavenTool = new MavenTool();
            return mavenTool.execute(taskName, context.getProjectRoot(), context);
        } else if ("git".equalsIgnoreCase(taskType)) {
            GitTool gitTool = new GitTool();
            return gitTool.execute(taskName, context.getProjectRoot(), context);
        } else if ("shell".equalsIgnoreCase(taskType)) {
            ShellTool shellTool = new ShellTool();
            return shellTool.execute(taskName, context.getProjectRoot(), context);
        }
        return agent.process(taskName, context, lastFeedback);
    }

    private IAgent findAgentForTask(Task task, TaskContext context) {
        String name = task.getName().toLowerCase();
        String type = task.getType().toLowerCase();
        for (IAgent agent : availableAgents) {
            if (name.contains(agent.getType().toLowerCase())) return agent;
        }
        if (type.contains("maven") || type.contains("test")) return availableAgents.stream().filter(a -> a instanceof TesterAgent).findFirst().orElse(availableAgents.get(2));
        if (type.contains("file") || type.contains("java")) return availableAgents.stream().filter(a -> a instanceof JavaDevAgent).findFirst().orElse(availableAgents.get(1));
        if (type.contains("arch") || type.contains("design")) return availableAgents.stream().filter(a -> a instanceof ArchitectAgent).findFirst().orElse(availableAgents.get(0));
        return availableAgents.stream().filter(a -> a instanceof GeneralAgent).findFirst().orElse(availableAgents.get(availableAgents.size() - 1));
    }

    private void updateStatus(TaskContext context, double progress, String message) {
        String id = context.getOrchestrator().getId();
        if (id != null) OrchestrationStatusManager.getInstance().updateStatus(id, progress, message);
    }
}
