package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;

/**
 * Base implementation of an ECOS Evolution Environment.
 */
public class BaseEvolutionEnvironment implements IEvolutionEnvironment {

    @Override
    public boolean materialize(Mutation mutation, TaskContext context) throws Exception {
        context.log("Environment: Materializing mutation type: " + mutation.getType());
        return true;
    }

    @Override
    public Evaluation reportFacts(Artifact artifact, TaskContext context) throws Exception {
        Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
        evaluation.setScore(1.0);
        evaluation.setComment("Base environment: No factual evaluation performed.");
        return evaluation;
    }

    @Override
    public void finalize(boolean success, TaskContext context) throws Exception {
        context.log("Environment: Finalizing state. Success: " + success);
    }
}
