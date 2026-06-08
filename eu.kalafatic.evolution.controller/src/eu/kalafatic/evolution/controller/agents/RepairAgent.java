package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.ToolFactory;
import eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants;

import eu.kalafatic.evolution.controller.tools.FileTool;

/**
 * Specialized agent for fixing code and configuration errors.
 */
public class RepairAgent extends BaseAiAgent {
    public RepairAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("Repair", "Repair", container);
        addTool(ToolFactory.getTool(EvolutionConstants.TOOL_FILE));
    }

    @Override
    protected String getAgentInstructions() {
        return "You are a specialized Code Repair Agent. Your goal is to identify and fix bugs, compilation errors, and configuration issues.";
    }
}
