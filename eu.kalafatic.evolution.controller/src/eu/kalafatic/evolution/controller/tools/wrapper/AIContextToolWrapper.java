package eu.kalafatic.evolution.controller.tools.wrapper;

import java.io.File;
import eu.kalafatic.evolution.controller.tools.ITool;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;
import eu.kalafatic.utils.semantic.AIContextTool;

/**
 * Wrapper for AIContextTool to implement ITool interface.
 */
public class AIContextToolWrapper implements ITool {
    private final AIContextTool delegate = new AIContextTool();

    @Override
    public String getName() {
        return "AIContextTool";
    }

    @Override
    public String execute(String command, File workingDir, TaskContext context) throws Exception {
        // AIContextTool doesn't have a generic execute, but we provide it for the interface
        return "AIContextTool: generic execution not implemented. Use specific methods if needed.";
    }

    public AIContextTool getDelegate() {
        return delegate;
    }
}
