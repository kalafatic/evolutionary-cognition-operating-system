package eu.kalafatic.evolution.controller.orchestration.selfdev;

import java.util.List;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;
import eu.kalafatic.evolution.model.orchestration.*;
import eu.kalafatic.evolution.controller.orchestration.*;

public class IterationManager {
    private final Iteration iteration;
    private final TaskContext context;
    private final GitManager gitManager;
    private final TaskPlanner planner;
    private final TaskExecutor executor;
    private final Evaluator evaluator;
    private final IEvolutionKernel kernel = new BaseEvolutionKernel();

    public IterationManager(Iteration iteration, TaskContext context) {
        this.iteration = iteration;
        this.context = context;
        this.gitManager = new GitManager(context.getProjectRoot(), context);
        this.planner = new TaskPlanner();
        this.executor = new TaskExecutor(context);
        this.evaluator = new Evaluator(context.getProjectRoot(), context);
    }

    public EvaluationResult run() throws Exception {
        context.log("[ITERATION] Starting iteration: " + iteration.getId());
        iteration.setStatus(IterationStatus.RUNNING);

        try {
            // 1. Create Git Branch
            gitManager.createBranch(iteration.getBranchName());

            // 2. Plan Tasks
            List<Task> tasks = planner.generateTasks(context);
            if (tasks.isEmpty()) {
                context.log("[ITERATION] No tasks generated. Skipping.");
                iteration.setStatus(IterationStatus.DONE);
                EvaluationResult skipResult = OrchestrationFactory.eINSTANCE.createEvaluationResult();
                skipResult.setSuccess(true);
                skipResult.setDecision(SelfDevDecision.CONTINUE);
                return skipResult;
            }
            iteration.getTasks().addAll(tasks);

            // 3. Execute Tasks
            boolean executionSuccess = executor.executeTasks(tasks);
            if (!executionSuccess) {
                context.log("[ITERATION] Execution failed. Rolling back.");
                gitManager.rollback();
                iteration.setStatus(IterationStatus.FAILED);
                EvaluationResult failResult = OrchestrationFactory.eINSTANCE.createEvaluationResult();
                failResult.setSuccess(false);
                failResult.setDecision(SelfDevDecision.ROLLBACK);
                return failResult;
            }

            // 4. Evaluate
            EvaluationResult result = evaluator.evaluate();
            iteration.setEvaluationResult(result);

            // 5. Decision (Phase D2 Fitness Authority Transfer)
            // Report facts (EvaluationResult) to Kernel and delegate decision
            Lineage lineage = context.getOrchestrator().getLineages().get(0);
            Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
            evaluation.setScore(result.isSuccess() ? 1.0 : 0.0);
            evaluation.setComment("Build/Test result: " + (result.isSuccess() ? "SUCCESS" : "FAILURE") +
                                   " | Pass Rate: " + result.getTestPassRate());

            // Phase F: Strengthen Lineage Persistence
            // Record every major evolution step in the lineage history
            EvolutionStep step = OrchestrationFactory.eINSTANCE.createEvolutionStep();
            step.setTimestamp(System.currentTimeMillis());
            step.getEvaluations().add(evaluation);
            lineage.getHistory().add(step);

            SelfDevDecision decision = kernel.decideStrategicAction(lineage, evaluation, context);
            result.setDecision(decision);

            if (decision == SelfDevDecision.CONTINUE) {
                context.log("[ITERATION] Kernel approved fitness. Committing.");
                gitManager.commit("Self-Development Iteration " + iteration.getId() + " success.");
                iteration.setStatus(IterationStatus.DONE);
            } else {
                context.log("[ITERATION] Kernel rejected fitness. Decision: " + decision);
                gitManager.rollback();
                iteration.setStatus(IterationStatus.FAILED);
            }

            return result;

        } catch (Exception e) {
            context.log("[ITERATION] Error in iteration: " + e.getMessage());
            gitManager.rollback();
            iteration.setStatus(IterationStatus.FAILED);
            throw e;
        }
    }
}
