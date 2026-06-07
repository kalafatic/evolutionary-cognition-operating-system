package eu.kalafatic.evolution.controller.orchestration.selfdev;

import eu.kalafatic.evolution.controller.orchestration.*;
import eu.kalafatic.evolution.model.orchestration.*;

public class SelfDevSupervisor {
    private final SelfDevSession session;
    private final TaskContext context;
    private final IEvolutionKernel kernel = new BaseEvolutionKernel();
    private static final int MAX_FAILURES = 3;

    public SelfDevSupervisor(SelfDevSession session, TaskContext context) {
        this.session = session;
        this.context = context;
    }

    public void startSession() {
        context.log("[SUPERVISOR] Starting Self-Development Session: " + session.getId());
        session.setStatus(SelfDevStatus.RUNNING);
        session.setStartTime(System.currentTimeMillis());

        // Initialize Lineage for the session if it doesn't exist
        if (context.getOrchestrator().getLineages().isEmpty()) {
            Lineage sessionLineage = OrchestrationFactory.eINSTANCE.createLineage();
            sessionLineage.setId(session.getId());
            context.getOrchestrator().getLineages().add(sessionLineage);
        }

        int failureCount = 0;
        RestartManager restartManager = new RestartManager(context);

        try {
            for (int i = 1; i <= session.getMaxIterations(); i++) {
                if (session.getStatus() != SelfDevStatus.RUNNING) {
                    context.log("[SUPERVISOR] Session status changed. Stopping.");
                    break;
                }

                context.log("[SUPERVISOR] Starting Iteration " + i + " of " + session.getMaxIterations());
                Iteration iteration = OrchestrationFactory.eINSTANCE.createIteration();
                iteration.setId("iteration-" + i);
                iteration.setBranchName("selfdev/" + session.getId() + "/" + iteration.getId());
                session.getIterations().add(iteration);

                IterationManager iterationManager = new IterationManager(iteration, context);
                EvaluationResult result = iterationManager.run();

                // Phase D2 Fitness Authority Transfer
                // Delegate terminal decision to Kernel based on cumulative session fitness
                Lineage lineage = context.getOrchestrator().getLineages().get(0);
                Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
                evaluation.setScore(result.isSuccess() ? 1.0 : 0.0);
                evaluation.setComment("Iteration " + i + " complete. Success: " + result.isSuccess());

                SelfDevDecision decision = kernel.decideStrategicAction(lineage, evaluation, context);

                if (decision == SelfDevDecision.STOP) {
                    context.log("[SUPERVISOR] Kernel requested STOP. Terminating session.");
                    session.setStatus(SelfDevStatus.COMPLETED);
                    break;
                }

                if (!result.isSuccess()) {
                    failureCount++;
                    context.log("[SUPERVISOR] Iteration " + i + " failed. Total failures: " + failureCount);
                    if (failureCount >= MAX_FAILURES) {
                        context.log("[SUPERVISOR] Max failures (" + MAX_FAILURES + ") reached. Stopping session.");
                        session.setStatus(SelfDevStatus.FAILED);
                        break;
                    }
                }

                restartManager.persistAndPrepareForRestart();
                restartManager.restartIfNeeded();
            }

            if (session.getStatus() == SelfDevStatus.RUNNING) {
                session.setStatus(SelfDevStatus.COMPLETED);
            }
            context.log("[SUPERVISOR] Session completed. Status: " + session.getStatus());

        } catch (Exception e) {
            context.log("[SUPERVISOR] Critical failure in session: " + e.getMessage());
            session.setStatus(SelfDevStatus.FAILED);
        }
    }

    public void stopSession() {
        context.log("[SUPERVISOR] Stopping Session: " + session.getId());
        session.setStatus(SelfDevStatus.STOPPED);
    }
}
