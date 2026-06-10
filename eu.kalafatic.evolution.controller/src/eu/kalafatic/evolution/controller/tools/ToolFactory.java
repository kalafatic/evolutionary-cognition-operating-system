package eu.kalafatic.evolution.controller.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import eu.kalafatic.evolution.controller.orchestration.util.EvolutionConstants;

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
        // Now handled by PluginLoader and ComponentRegistry
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
        ITool tool = tools.get(name);
        if (tool == null) {
            tool = eu.kalafatic.evolution.controller.registry.DefaultProviderResolver.resolve(ITool.class, java.util.Map.of("name", name));
        }
        return tool;
    }
}
