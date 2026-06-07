package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;

/**
 * Skeletal implementation of the ECOS Evolution Kernel.
 */
public class BaseEvolutionKernel implements IEvolutionKernel {

    @Override
    public Artifact evolve(Lineage lineage, Pressure pressure, TaskContext context) throws Exception {
        context.log("Kernel: Starting evolution for lineage: " + lineage.getId());
        return lineage.getSurvivor();
    }

    @Override
    public void applyPressure(Pressure pressure, TaskContext context) {
        context.log("Kernel: Applying pressure: " + pressure.getName());
    }

    private int attemptCounter = 0; // Temporary procedural remnant for stability

    @Override
    public EvolutionDecision analyze(Artifact artifact, Evaluation evaluation, TaskContext context) {
        context.log("Kernel: Analyzing evaluation for artifact [" + artifact.getId() + "]");

        if (evaluation.getScore() >= 1.0) {
            attemptCounter = 0;
            context.log("Kernel: Pressure resolved. Decision: STABILIZE");
            return EvolutionDecision.STABILIZE;
        }

        attemptCounter++;
        if (attemptCounter >= 3) {
            attemptCounter = 0;
            context.log("Kernel: Exhausted attempts. Decision: ABORT");
            return EvolutionDecision.ABORT;
        }

        context.log("Kernel: Pressure remaining. Decision: MUTATE");
        return EvolutionDecision.MUTATE;
    }
}
