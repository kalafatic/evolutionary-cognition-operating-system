package eu.kalafatic.evolution.controller.orchestration.selfdev;

import java.util.List;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;
import eu.kalafatic.evolution.controller.supervision.EvolutionDecision;
import eu.kalafatic.evolution.model.orchestration.EvaluationResult;

/**
 * Interface for evolution engines.
 */
public interface IEvolutionEngine {
    List<BranchVariant> generateProposals(TaskContext context, String goal);
    EvaluationResult executeWinner(TaskContext context, EvolutionDecision decision, List<BranchVariant> variants, String goal);
}
