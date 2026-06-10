package eu.kalafatic.evolution.controller.orchestration.intent;

import java.util.List;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;

/**
 * Interface for Clarification Planner.
 */
public interface IClarificationPlanner {

    public enum Strategy {
        AUTO_INFER,
        BRANCH_PARALLEL,
        CLARIFY_USER
    }

    Strategy determineStrategy(IntentExpansionResult result, TaskContext context);
    List<String> generateQuestions(IntentExpansionResult result);
    String formatClarificationRequest(IntentExpansionResult result);
}
