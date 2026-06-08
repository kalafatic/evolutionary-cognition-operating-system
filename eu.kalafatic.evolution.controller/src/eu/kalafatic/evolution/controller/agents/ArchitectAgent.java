package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.ToolFactory;
import eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants;

import eu.kalafatic.evolution.controller.tools.FileTool;
import eu.kalafatic.evolution.controller.tools.ShellTool;

/**
 * Specialized agent for project structure and design.
 */
public class ArchitectAgent extends BaseAiAgent {
    public ArchitectAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("Architect", "Architect", container);
        addTool(ToolFactory.getTool(EvolutionConstants.TOOL_FILE));
        addTool(ToolFactory.getTool(EvolutionConstants.TOOL_SHELL));
    }

    public void updateDesignModel(eu.kalafatic.evolution.controller.orchestration.TaskContext context, String designJson) {
        context.getOrchestrator().setSharedMemory(designJson);
    }

    @Override
    protected String getAgentInstructions() {
        return "You are acting as an Architect Agent. Analyze technical requirements and provide a detailed architecture design.";
    }
}
