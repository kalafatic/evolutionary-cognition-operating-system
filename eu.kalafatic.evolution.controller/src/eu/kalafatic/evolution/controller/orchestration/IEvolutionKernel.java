package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.Lineage;
import eu.kalafatic.evolution.model.orchestration.Pressure;

/**
 * The core ECOS Evolution Kernel interface.
 * Everything evolves through the kernel via Artifacts, Mutations, and Pressures.
 */
public interface IEvolutionKernel {

    /**
     * Evolves a lineage based on the current system pressure.
     */
    Artifact evolve(Lineage lineage, Pressure pressure, TaskContext context) throws Exception;

    /**
     * Propagates pressure through the system to drive mutation selection.
     */
    void applyPressure(Pressure pressure, TaskContext context);

    /**
     * Decides whether a failed evolutionary step should be retried.
     * This transfers cognitive authority from the procedural loop to the kernel.
     */
    boolean shouldRetry(Artifact artifact, String failureFeedback, int attemptCount, TaskContext context);
}
