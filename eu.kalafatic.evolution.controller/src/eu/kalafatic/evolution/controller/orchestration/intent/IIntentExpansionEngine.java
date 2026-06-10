package eu.kalafatic.evolution.controller.orchestration.intent;

import eu.kalafatic.evolution.controller.orchestration.TaskContext;

/**
 * Interface for Intent Expansion Engine.
 */
public interface IIntentExpansionEngine {
    IntentExpansionResult expand(String goal, TaskContext context) throws Exception;
    void setAiService(eu.kalafatic.evolution.controller.orchestration.AiService aiService);
}
