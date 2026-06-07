package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.Evaluation;
import eu.kalafatic.evolution.model.orchestration.EvolutionDecision;
import eu.kalafatic.evolution.model.orchestration.Lineage;
import eu.kalafatic.evolution.model.orchestration.Pressure;

/**
 * The core ECOS Evolution Kernel interface.
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
     * Analyzes an evaluation result against a pressure and decides the next evolutionary step.
     * This is the primary cognitive authority method.
     */
    EvolutionDecision analyze(Artifact artifact, Evaluation evaluation, TaskContext context);
}
