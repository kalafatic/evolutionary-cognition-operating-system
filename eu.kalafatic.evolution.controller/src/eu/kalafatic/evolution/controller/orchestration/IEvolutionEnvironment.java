package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.Evaluation;
import eu.kalafatic.evolution.model.orchestration.Mutation;

/**
 * Encapsulates mode-specific execution infrastructure.
 * Infrastructure reports facts; Kernel decides meaning.
 */
public interface IEvolutionEnvironment {

    /**
     * Materializes a mutation into a concrete change in the environment.
     */
    boolean materialize(Mutation mutation, TaskContext context) throws Exception;

    /**
     * Evaluates the current state of an artifact and reports factual findings.
     */
    Evaluation reportFacts(Artifact artifact, TaskContext context) throws Exception;

    /**
     * Performs environment-specific terminal actions (e.g., Commit or Rollback).
     */
    void finalize(boolean success, TaskContext context) throws Exception;
}
