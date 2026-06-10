package eu.kalafatic.evolution.controller.agents;

import eu.kalafatic.evolution.controller.tools.CppTool;
import eu.kalafatic.evolution.controller.tools.FileTool;
import eu.kalafatic.evolution.controller.tools.GitTool;
import eu.kalafatic.evolution.controller.tools.ShellTool;

/**
 * Specialized agent for C and C++ development tasks.
 */
public class CppDevAgent extends BaseAiAgent {
    public CppDevAgent(eu.kalafatic.evolution.controller.orchestration.SessionContainer container) {
        super("CppDev", "CppDev", container);
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_FILE));
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_CPP));
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_GIT));
        addTool(eu.kalafatic.evolution.controller.tools.ToolFactory.getTool(eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants.TOOL_SHELL));
    }

    @Override
    protected String getAgentInstructions() {
        return "You are acting as a Senior C/C++ Developer Agent.\n" +
               "Generate C/C++ source code, headers, Makefiles or CMakeLists.txt content as requested.";
    }

    @Override
    protected String getFooterInstructions() {
        return "Provide ONLY the code content for files, without any conversational preamble or markdown backticks unless specifically required for a file's format.";
    }
}
