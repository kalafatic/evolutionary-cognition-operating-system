package eu.kalafatic.evolution.tests.iterative;

import java.util.Random;

/**
 * Implementation of the Iterative Development lifecycle test.
 * Updated to use standard phase names and implement ISimulationTest.
 */
public class IterativeDevelopmentTest implements ISimulationTest {

    private final ITestListener listener;
    private final Random random = new Random();
    private boolean stop = false;

    public IterativeDevelopmentTest(ITestListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        stop = false;
        listener.reset();
        executeStep("observe", 0);
    }

    @Override
    public void stop() {
        this.stop = true;
    }

    private void executeStep(String step, int iterationCount) {
        if (stop) return;

        listener.stepStarted(step);

        // Simulate work
        sleep(800 + random.nextInt(400));

        if (stop) return;

        String nextStep = null;
        String edgeId = null;

        boolean success = true;

        // Custom logic for each step
        switch (step) {
            case "observe":
                nextStep = "analyze"; edgeId = "observe_analyze"; break;
            case "analyze":
                nextStep = "plan"; edgeId = "analyze_plan"; break;
            case "plan":
                nextStep = "validate"; edgeId = "plan_validate"; break;
            case "validate":
                nextStep = "execute"; edgeId = "validate_execute"; break;
            case "execute":
                nextStep = "test"; edgeId = "execute_test"; break;
            case "test":
                if (random.nextDouble() < 0.1) success = false;
                else { nextStep = "evaluate"; edgeId = "test_evaluate"; }
                break;
            case "evaluate":
                if (random.nextDouble() < 0.15) success = false;
                else { nextStep = "commit"; edgeId = "evaluate_commit"; }
                break;
            case "commit":
                nextStep = "PR"; edgeId = "commit_PR"; break;
            case "PR":
                nextStep = "feedback"; edgeId = "PR_feedback"; break;
            case "feedback":
                nextStep = "refine"; edgeId = "feedback_refine"; break;
            case "refine":
                if (iterationCount < 1 && random.nextDouble() < 0.4) {
                    listener.stepSuccess("refine");
                    listener.transitionActive("refine_plan");
                    sleep(500);
                    executeStep("plan", iterationCount + 1);
                    return;
                } else {
                    nextStep = "learn"; edgeId = "refine_learn";
                }
                break;
            case "learn":
                listener.stepSuccess("learn");
                return;
        }

        if (success) {
            listener.stepSuccess(step);
            if (nextStep != null && edgeId != null) {
                listener.transitionActive(edgeId);
                sleep(500);
                executeStep(nextStep, iterationCount);
            }
        } else {
            listener.stepFailed(step);
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
