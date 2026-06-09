package eu.kalafatic.evolution.controller.kernel;

import java.util.List;
import eu.kalafatic.evolution.controller.orchestration.selfdev.IterationRecord;
import eu.kalafatic.evolution.controller.orchestration.Checkpoint;
import eu.kalafatic.evolution.controller.orchestration.selfdev.StateSnapshot;

/**
 * Interface for memory and persistence of evolutionary state.
 */
public interface IMemoryProvider {
    void saveRecord(IterationRecord record);
    void saveCheckpoint(Checkpoint checkpoint);
    Checkpoint loadCheckpoint(String sessionId);
    List<IterationRecord> getActiveLineage();
    void flush();
}
