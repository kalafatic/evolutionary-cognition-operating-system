package eu.kalafatic.evolution.controller.orchestration.selfdev;

import eu.kalafatic.evolution.controller.orchestration.Checkpoint;

/**
 * Interface for memory providers.
 */
public interface IMemoryProvider {
    void saveRecord(IterationRecord record);
    Checkpoint loadCheckpoint(String sessionId);
    void saveCheckpoint(Checkpoint checkpoint);
    java.util.List<IterationRecord> getRecords();
    eu.kalafatic.evolution.controller.orchestration.workspace.TrajectoryMemory getTrajectoryMemory();
    java.util.List<IterationRecord> getActiveLineage();
    java.util.Map<String, Integer> getArchitectureHotspots();
    FailureMemory getFailureMemory();
    EvolutionMemoryGraph getEvolutionGraph();
    String getHistoryAnalysis();
    void saveTrajectoryAnalysis(eu.kalafatic.evolution.controller.trajectory.TrajectoryAnalysisRecord record);
    void flush();
}
