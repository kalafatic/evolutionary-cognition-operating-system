package eu.kalafatic.evolution.controller.kernel;

import java.util.List;
import eu.kalafatic.evolution.controller.orchestration.selfdev.BranchVariant;
import eu.kalafatic.evolution.controller.orchestration.selfdev.StateSnapshot;
import eu.kalafatic.evolution.controller.orchestration.selfdev.FailureMemory;
import eu.kalafatic.evolution.controller.trajectory.Trajectory;
import eu.kalafatic.evolution.controller.orchestration.selfdev.EvolutionaryPressureVector;

/**
 * Interface for the evolution engine responsible for proposal generation and variation.
 */
public interface IEvolutionEngine {
    List<BranchVariant> generateVariants(String goal, StateSnapshot snapshot, FailureMemory failureMemory, Trajectory trajectory, EvolutionaryPressureVector pressure) throws Exception;
}
