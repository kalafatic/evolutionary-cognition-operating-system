package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;

/**
 * Specialized ECOS Environment for Mediated Intelligence (Prompt/Context Evolution).
 */
public class MediationEnvironment extends BaseEvolutionEnvironment {

    @Override
    public Evaluation reportFacts(Artifact artifact, TaskContext context) throws Exception {
        if (context != null) context.log("MediationEnvironment: Evaluating artifact: " + artifact.getId());

        Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
        String content = artifact.getContent();

        // Simple heuristic evaluation for prompt quality
        double score = 0.5;
        StringBuilder feedback = new StringBuilder();

        if (content != null) {
            String lowerContent = content.toLowerCase();
            if (content.length() > 50) score += 0.2;
            if (lowerContent.contains("context")) score += 0.1;
            if (lowerContent.contains("intent")) score += 0.1;
            if (content.contains("ONLY")) score += 0.1; // "ONLY" is often a specific instruction keyword
            if (content.length() > 500) {
                score -= 0.1;
                feedback.append("Prompt is too verbose. ");
            }
        }

        double finalScore = Math.min(1.0, score);
        evaluation.setScore(finalScore);
        evaluation.setComment(feedback.length() > 0 ? feedback.toString().trim() : "Fitness assessed via structural heuristics.");

        System.out.println("[ECOS-DEBUG] MediationEnvironment: Content length: " + (content != null ? content.length() : 0));
        System.out.println("[ECOS-DEBUG] MediationEnvironment: Final Score: " + finalScore);

        return evaluation;
    }
}
