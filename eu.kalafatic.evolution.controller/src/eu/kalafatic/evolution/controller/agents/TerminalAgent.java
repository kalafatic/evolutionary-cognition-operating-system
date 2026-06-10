package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.ShellTool;

/**
 * Agent for general shell and terminal tasks.
 */
public class TerminalAgent extends BaseAiAgent {
    public TerminalAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("Terminal", "Terminal", container);
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_SHELL));
    }

    @Override
    protected String getAgentInstructions() {
        return "You are an AI Terminal Agent. You perform general shell commands and investigative tasks via the terminal.";
    }
}
