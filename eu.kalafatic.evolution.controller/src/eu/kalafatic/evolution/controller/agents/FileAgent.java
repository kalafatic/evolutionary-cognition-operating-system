package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.FileTool;

/**
 * Agent specialized in file system operations.
 */
public class FileAgent extends BaseAiAgent {
    public FileAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("File", "File", container);
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_FILE));
    }

    @Override
    protected String getAgentInstructions() {
        return "You are an AI File Agent. You specialize in reading, writing, and managing files.";
    }
}
