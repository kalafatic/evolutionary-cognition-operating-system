package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.FileTool;
import eu.kalafatic.evolution.controller.tools.MavenTool;

/**
 * Specialized agent for Java development tasks.
 */
public class JavaDevAgent extends BaseAiAgent {
    public JavaDevAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("JavaDev", "JavaDev", container);
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_FILE));
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_MAVEN));
    }

    @Override
    protected String getAgentInstructions() {
        return "You are a specialized Java Development Agent. Your goal is to write high-quality, maintainable Java code.";
    }
}
