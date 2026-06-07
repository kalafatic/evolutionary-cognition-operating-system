package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;
import eu.kalafatic.evolution.controller.orchestration.selfdev.GitManager;
import eu.kalafatic.evolution.controller.orchestration.selfdev.Evaluator;
import java.io.File;

/**
 * Specialized ECOS Environment for Self-Development.
 * Encapsulates Git-branching and Maven-based build/test evaluation.
 */
public class SelfDevEnvironment extends BaseEvolutionEnvironment {
    private final GitManager gitManager;
    private final Evaluator evaluator;
    private final String iterationId;

    public SelfDevEnvironment(String iterationId, File projectRoot, TaskContext context) {
        this.iterationId = iterationId;
        this.gitManager = new GitManager(projectRoot, context);
        this.evaluator = new Evaluator(projectRoot, context);
    }

    @Override
    public boolean materialize(Mutation mutation, TaskContext context) throws Exception {
        context.log("SelfDevEnvironment: Materializing mutation via TaskExecutor...");
        return super.materialize(mutation, context);
    }

    @Override
    public Evaluation reportFacts(Artifact artifact, TaskContext context) throws Exception {
        context.log("SelfDevEnvironment: Running Maven evaluation for artifact [" + artifact.getId() + "]");
        EvaluationResult result = evaluator.evaluate();

        Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
        evaluation.setScore(result.isSuccess() ? 1.0 : 0.0);
        evaluation.setComment("Build " + (result.isSuccess() ? "SUCCESS" : "FAILURE") +
                               " | Pass Rate: " + result.getTestPassRate());
        return evaluation;
    }

    @Override
    public void finalize(boolean success, TaskContext context) throws Exception {
        if (success) {
            context.log("SelfDevEnvironment: Committing successful iteration: " + iterationId);
            gitManager.commit("Self-Development Iteration " + iterationId + " success.");
        } else {
            context.log("SelfDevEnvironment: Rolling back failed iteration: " + iterationId);
            gitManager.rollback();
        }
    }
}
