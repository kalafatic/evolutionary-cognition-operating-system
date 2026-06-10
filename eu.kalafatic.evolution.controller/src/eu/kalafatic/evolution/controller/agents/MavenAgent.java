package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.MavenTool;

/**
 * Agent specialized in Maven build operations.
 */
public class MavenAgent extends BaseAiAgent {
    public MavenAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("Maven", "Maven", container);
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_MAVEN));
    }

    @Override
    protected String getAgentInstructions() {
        return "You are a specialized Maven Build Agent. You handle build execution, dependency management, and test runs.";
    }
}
