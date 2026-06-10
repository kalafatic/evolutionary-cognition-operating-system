package eu.kalafatic.evolution.controller.orchestration.intent;

import eu.kalafatic.evolution.controller.orchestration.TaskContext;

/**
 * Interface for Dimension Inference Engine.
 */
public interface IDimensionInferenceEngine {
    EvolutionAssessment analyze(String request, TaskContext context) throws Exception;
}
