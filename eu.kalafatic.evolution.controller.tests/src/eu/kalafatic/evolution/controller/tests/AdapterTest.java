package eu.kalafatic.evolution.controller.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import eu.kalafatic.evolution.model.orchestration.*;
import eu.kalafatic.evolution.controller.orchestration.*;

public class AdapterTest {

    @Test
    public void testTaskArtifactAdapter() {
        Task task = OrchestrationFactory.eINSTANCE.createTask();
        task.setId("task1");
        task.setType("code");
        task.setDescription("Test content");

        Artifact adapter = new TaskArtifactAdapter(task);
        assertEquals("task1", adapter.getId());
        assertEquals("code", adapter.getType());
        assertEquals("Test content", adapter.getContent());
    }

    @Test
    public void testIterationLineageAdapter() {
        Iteration iteration = OrchestrationFactory.eINSTANCE.createIteration();
        iteration.setId("iter1");

        Task task = OrchestrationFactory.eINSTANCE.createTask();
        task.setId("task1");
        iteration.getTasks().add(task);

        Lineage adapter = new IterationLineageAdapter(iteration);
        assertEquals("iter1", adapter.getId());
        assertEquals(1, adapter.getCandidates().size());
        assertEquals("task1", adapter.getSurvivor().getId());
    }
}
