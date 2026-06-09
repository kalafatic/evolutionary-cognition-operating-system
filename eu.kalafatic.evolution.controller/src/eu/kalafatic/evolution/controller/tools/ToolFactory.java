package eu.kalafatic.evolution.controller.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants;
import eu.kalafatic.evolution.controller.registry.ComponentRegistry;

/**
 * Factory and Registry for Tools.
 * Manages tool instances and ensures singletons for stateless tools.
 *
 * @evo:1:1 reason=introduce-tool-factory
 */
public class ToolFactory {

    private static final Map<String, ITool> tools = new ConcurrentHashMap<>();

    static {
        registerDefaultTools();
    }

    private static void registerDefaultTools() {
        // Tools now come from ComponentRegistry
        ComponentRegistry registry = ComponentRegistry.getInstance();
        registry.findPlugins(ITool.class).forEach(t -> registerTool(t.getName().toLowerCase(), t));

        // Manual registration for tools not yet in registry
        if (getTool(EvolutionConstants.TOOL_ECLIPSE) == null) registerTool(EvolutionConstants.TOOL_ECLIPSE, new EclipseTool());
        if (getTool(EvolutionConstants.TOOL_CPP) == null) registerTool(EvolutionConstants.TOOL_CPP, new CppTool());
        if (getTool(EvolutionConstants.TOOL_DATABASE) == null) registerTool(EvolutionConstants.TOOL_DATABASE, new DatabaseTool());
    }

    /**
     * Registers a tool.
     * @param name Name of the tool.
     * @param tool ITool instance.
     */
    public static void registerTool(String name, ITool tool) {
        tools.put(name, tool);
    }

    /**
     * Gets a tool by name.
     * @param name Name of the tool.
     * @return ITool instance or null if not found.
     */
    public static ITool getTool(String name) {
        return tools.get(name);
    }
}
