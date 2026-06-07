package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Specialized ECOS Environment for Workflow Planning evolution.
 */
public class PlannerEnvironment extends BaseEvolutionEnvironment {

    @Override
    public Evaluation reportFacts(Artifact artifact, TaskContext context) throws Exception {
        if (context != null) context.log("PlannerEnvironment: Evaluating plan: " + artifact.getId());

        Evaluation evaluation = OrchestrationFactory.eINSTANCE.createEvaluation();
        String content = artifact.getContent();

        double score = 0.4;
        StringBuilder feedback = new StringBuilder();

        if (content == null || content.isEmpty()) {
            evaluation.setScore(0.0);
            evaluation.setComment("Plan is empty.");
            return evaluation;
        }

        try {
            // Check for valid JSON array
            int start = content.indexOf("[");
            int end = content.lastIndexOf("]");
            if (start != -1 && end != -1 && end > start) {
                new JSONArray(content.substring(start, end + 1));
                score += 0.3;
            } else {
                feedback.append("Plan is not a valid JSON array. ");
            }
        } catch (JSONException e) {
            feedback.append("Invalid JSON format: ").append(e.getMessage()).append(". ");
        }

        // Structural heuristics
        if (content.contains("taskType")) score += 0.1;
        if (content.contains("approval")) score += 0.1;
        if (content.contains("maven") || content.contains("file")) score += 0.1;

        double finalScore = Math.min(1.0, score);
        evaluation.setScore(finalScore);
        evaluation.setComment(feedback.length() > 0 ? feedback.toString().trim() : "Plan structure is valid.");

        if (context != null) {
            context.log("[ECOS] PlannerEnvironment Score: " + finalScore);
        }

        return evaluation;
    }
}
