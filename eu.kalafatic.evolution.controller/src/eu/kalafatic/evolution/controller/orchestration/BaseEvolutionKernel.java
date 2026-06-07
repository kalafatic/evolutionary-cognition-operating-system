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
        if (evaluation == null) {
            // Post-execution analysis without immediate failure
            context.log("Kernel: Post-task sequence analysis for artifact [" + artifact.getId() + "]");
            return EvolutionDecision.MUTATE; // Continue the sequence
        }

        context.log("Kernel: Analyzing evaluation for artifact [" + artifact.getId() + "]");

        if (evaluation.getScore() >= 1.0) {
            attemptCounter = 0;
            context.log("Kernel: Pressure resolved. Decision: STABILIZE");
            return EvolutionDecision.STABILIZE;
        }

        attemptCounter++;
        if (attemptCounter >= 3) {
            attemptCounter = 0;
            // Instead of just ABORT, ECOS prefers BACKTRACK if a survivor exists
            context.log("Kernel: Exhausted attempts. Decision: BACKTRACK");
            return EvolutionDecision.BACKTRACK;
        }

        context.log("Kernel: Pressure remaining. Decision: MUTATE");
        return EvolutionDecision.MUTATE;
    }

    @Override
    public Artifact selectTarget(Lineage lineage, EvolutionDecision decision, TaskContext context) {
        if (decision == EvolutionDecision.BACKTRACK) {
            context.log("Kernel: Backtracking to survivor artifact: " + lineage.getSurvivor().getId());
            return lineage.getSurvivor();
        }

        // For MUTATE or STABILIZE, the target is usually the last candidate (current state)
        if (!lineage.getCandidates().isEmpty()) {
            return lineage.getCandidates().get(lineage.getCandidates().size() - 1);
        }

        return lineage.getSurvivor();
    }
}
