package eu.kalafatic.evolution.controller.orchestration;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import eu.kalafatic.evolution.model.orchestration.*;

/**
 * Specialized agent for planning tasks from natural language.
 * Evolutionary Planning (Phase J): Plans are evolved artifacts refined through iterative selection.
 */
public class PlannerAgent extends BaseAiAgent implements IPlanner {

    private final IEvolutionKernel kernel = new BaseEvolutionKernel();

    public PlannerAgent() {
        super("Planner", "Planner");
    }

    @Override
    public List<Task> plan(String request, TaskContext context) throws Exception {
        context.log("Planner: Starting evolutionary planning for request - " + request);

        // Phase J: Evolutionary Planning
        Lineage planLineage = new SimpleLineageAdapter("plan-evolution-" + System.currentTimeMillis());
        context.getOrchestrator().getLineages().add(planLineage);

        IEvolutionEnvironment env = new PlannerEnvironment();
        Pressure completenessPressure = OrchestrationFactory.eINSTANCE.createPressure();
        completenessPressure.setName("Plan Completeness");
        completenessPressure.setDescription("The plan must be atomic, complete, and logically sound.");

        String initialResponse = generatePlan(request, null, context);
        Artifact currentPlan = new PlanArtifactAdapter("initial-plan", initialResponse);
        planLineage.getCandidates().add(currentPlan);

        int iterations = 0;
        int maxIterations = 3;

        while (iterations < maxIterations) {
            iterations++;
            context.log("Planner: Plan Optimization Iteration " + iterations);

            Artifact survivor = kernel.evolve(planLineage, completenessPressure, env, context);

            if (planLineage.getSurvivor() != null) {
                currentPlan = planLineage.getSurvivor();
                context.log("Planner: Plan stabilized via ECOS selection.");
                break;
            }

            // Mutation: Refine plan based on last evaluation (implied by lack of survivor)
            EvolutionStep lastStep = planLineage.getHistory().isEmpty() ? null :
                planLineage.getHistory().get(planLineage.getHistory().size() - 1);
            String feedback = (lastStep != null && !lastStep.getEvaluations().isEmpty()) ?
                lastStep.getEvaluations().get(0).getComment() : "";

            String mutatedResponse = generatePlan(request, currentPlan.getContent() + "\nFeedback: " + feedback, context);
            currentPlan = new PlanArtifactAdapter("plan-v" + iterations, mutatedResponse);
            planLineage.getCandidates().add(currentPlan);
        }

        if (planLineage.getSurvivor() == null) {
            planLineage.setSurvivor(currentPlan);
        }

        String finalPlanJson = currentPlan.getContent();
        return parseTasks(finalPlanJson, request, context);
    }

    private String generatePlan(String request, String lastPlanAndFeedback, TaskContext context) throws Exception {
        String plannerPrompt = "You are a workflow planner for an agentic system. " +
                "Decompose the user request into a sequence of atomic, specialized tasks.\n" +
                "If the request is a simple greeting or a general question, just create one 'llm' task to respond.\n" +
                "Available task types:\n" +
                "- 'llm': For reasoning, planning, or general text generation.\n" +
                "- 'file': For writing or creating files (e.g., Java source code, POM, README). Task name should be 'Write <path/to/file>'. File paths MUST be relative to the project root and MUST NOT start with a slash or drive letter.\n" +
                "- 'git': For version control actions (add, commit, push).\n" +
                "- 'maven': For building, testing, or packaging the project.\n" +
                "- 'approval': A specialized task that pauses the workflow and waits for the user to click 'Approve' or 'Reject'. Use this for critical steps like code application or final delivery.\n" +
                "- 'train_nn': For local project neural network training.\n" +
                "- 'train_llm': For local project LLM fine-tuning.\n" +
                "- 'train_agent': For local project agent behavior training.\n\n" +
                "Looping and Iteration:\n" +
                "- Any task can have a 'loopToTaskId' property. If present and not 'none', the orchestrator will jump back to the task with that ID after the current task completes.\n" +
                "Output MUST be a valid JSON array of objects. Schema:\n" +
                "[ { \"id\": \"unique_id\", \"name\": \"Clear task description\", \"taskType\": \"llm\"|\"file\"|\"git\"|\"maven\"|\"approval\"|\"train_nn\"|\"train_llm\"|\"train_agent\", \"approvalRequired\": boolean, \"loopToTaskId\": \"id_to_jump_to\"|\"none\" } ]\n\n";

        if (lastPlanAndFeedback != null) {
            plannerPrompt += "Refine the following previous plan based on feedback:\n" + lastPlanAndFeedback + "\n\n";
        }

        plannerPrompt += "Request: " + request;

        return aiService.sendRequest(context.getOrchestrator(), plannerPrompt, context);
    }

    private List<Task> parseTasks(String response, String request, TaskContext context) {
        int start = response.indexOf("[");
        int end = response.lastIndexOf("]");

        JSONArray jsonArray;
        if (start == -1 || end == -1 || end <= start) {
            context.log("Planner: Warning - AI response is not a JSON array. Using fallback llm task.");
            jsonArray = new JSONArray();
            JSONObject fallbackTask = new JSONObject();
            fallbackTask.put("id", "task0");
            fallbackTask.put("name", request);
            fallbackTask.put("taskType", "llm");
            jsonArray.put(fallbackTask);
        } else {
            try {
                jsonArray = new JSONArray(response.substring(start, end + 1));
            } catch (org.json.JSONException e) {
                context.log("Planner: Warning - Failed to parse AI response as JSON array. Using fallback llm task.");
                jsonArray = new JSONArray();
                JSONObject fallbackTask = new JSONObject();
                fallbackTask.put("id", "task0");
                fallbackTask.put("name", request);
                fallbackTask.put("taskType", "llm");
                jsonArray.put(fallbackTask);
            }
        }

        List<Task> tasks = new ArrayList<>();
        OrchestrationFactory factory = OrchestrationFactory.eINSTANCE;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Task task = factory.createTask();
            task.setId(obj.optString("id", "task" + i));
            task.setName(obj.optString("name", "Task " + i));
            task.setType(obj.optString("taskType", "llm"));
            task.setApprovalRequired(obj.optBoolean("approvalRequired", false));
            task.setLoopToTaskId(obj.optString("loopToTaskId", "none"));
            tasks.add(task);
        }
        context.log("Planner: Final Generated " + tasks.size() + " tasks.");
        return tasks;
    }
}
