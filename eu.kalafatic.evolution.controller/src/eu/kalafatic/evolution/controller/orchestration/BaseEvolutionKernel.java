package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;

/**
 * Skeletal implementation of the ECOS Evolution Kernel.
 */
public class BaseEvolutionKernel implements IEvolutionKernel {

    @Override
    public Artifact evolve(Lineage lineage, Pressure pressure, IEvolutionEnvironment environment, TaskContext context) throws Exception {
        context.log("Kernel: Evolving lineage: " + lineage.getId());

        // 1. Identify Ancestor
        Artifact ancestor = lineage.getSurvivor();
        if (ancestor == null && !lineage.getCandidates().isEmpty()) {
            ancestor = lineage.getCandidates().get(0);
        }

        // 2. Report Facts from Environment (Evaluation)
        Evaluation evaluation = environment.reportFacts(ancestor, context);

        // 3. Analyze Strategy
        EvolutionDecision strategy = analyze(ancestor, evaluation, context);

        // 4. Record the Step in History
        EvolutionStep step = OrchestrationFactory.eINSTANCE.createEvolutionStep();
        step.setTimestamp(System.currentTimeMillis());
        step.getEvaluations().add(evaluation);
        lineage.getHistory().add(step);

        if (strategy == EvolutionDecision.STABILIZE) {
            lineage.setSurvivor(ancestor);
            environment.finalize(true, context);
            context.log("Kernel: Lineage stabilized at artifact: " + ancestor.getId());
        } else if (strategy == EvolutionDecision.MUTATE) {
            // In a full implementation, this would trigger mutation generation
            context.log("Kernel: Mutation required for lineage: " + lineage.getId());
        } else if (strategy == EvolutionDecision.BACKTRACK || strategy == EvolutionDecision.ABORT) {
            environment.finalize(false, context);
        }

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

    @Override
    public SelfDevDecision decideStrategicAction(Lineage lineage, Evaluation evaluation, TaskContext context) {
        context.log("Kernel: Strategic assessment for lineage: " + lineage.getId());

        if (evaluation == null) {
            return SelfDevDecision.CONTINUE;
        }

        if (evaluation.getScore() >= 1.0) {
            context.log("Kernel: Fitness acceptable. Decision: CONTINUE (Commit)");
            return SelfDevDecision.CONTINUE;
        }

        // Logic for terminal failure
        context.log("Kernel: Fitness unacceptable. Decision: ROLLBACK");
        return SelfDevDecision.ROLLBACK;
    }
}
