package eu.kalafatic.evolution.controller.kernel;

import eu.kalafatic.evolution.controller.trajectory.Trajectory;
import eu.kalafatic.evolution.controller.trajectory.TrajectoryAnalysisRecord;

/**
 * Interface for tracking evolutionary trends and history.
 */
public interface ITrajectoryEngine {
    void recordTrajectory(Trajectory trajectory);
    void saveTrajectoryAnalysis(TrajectoryAnalysisRecord record);
    void flush();
}
