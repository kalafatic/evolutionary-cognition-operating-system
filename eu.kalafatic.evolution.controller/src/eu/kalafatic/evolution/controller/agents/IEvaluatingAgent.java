package eu.kalafatic.evolution.controller.agents;

import org.json.JSONObject;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;

/**
 * Interface for agents that can evaluate task output.
 */
public interface IEvaluatingAgent extends IAgent {
    JSONObject evaluate(String taskOutput, String taskDescription, TaskContext context) throws Exception;
}
