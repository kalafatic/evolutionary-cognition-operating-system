package eu.kalafatic.evolution.controller.orchestration.selfdev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.kalafatic.evolution.controller.orchestration.IOrchestrator;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;
import eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants;
import eu.kalafatic.evolution.model.orchestration.OrchestrationFactory;
import eu.kalafatic.evolution.model.orchestration.Task;
import eu.kalafatic.evolution.model.orchestration.Orchestrator;

public class TaskExecutor {
    private final Orchestrator model;
    private final IOrchestrator orchestrator;

    public TaskExecutor(Orchestrator model, IOrchestrator orchestrator) {
        this.model = model;
        this.orchestrator = orchestrator;
    }

    public IOrchestrator getOrchestrator() {
        return orchestrator;
    }

    public boolean executeBuild(File dir, TaskContext context) throws Exception {
        Task task = OrchestrationFactory.eINSTANCE.createTask();
        task.setType(EvolutionConstants.TASK_MAVEN);
        task.setName("clean package -DskipTests");
        String result = orchestrator.executeTask(task, context);
        return result != null && !result.toLowerCase().contains("fail");
    }

    public boolean executeTests(File dir, TaskContext context) throws Exception {
        Task task = OrchestrationFactory.eINSTANCE.createTask();
        task.setType(EvolutionConstants.TASK_MAVEN);
        task.setName("test");
        String result = orchestrator.executeTask(task, context);
        return result != null && !result.toLowerCase().contains("fail");
    }
}
