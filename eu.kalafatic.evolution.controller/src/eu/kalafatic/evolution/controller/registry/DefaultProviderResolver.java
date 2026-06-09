package eu.kalafatic.evolution.controller.registry;

import java.util.Map;

/**
 * Resolves the appropriate implementation for a core subsystem based on rules and defaults.
 */
public class DefaultProviderResolver {

    /**
     * Resolves an implementation of the specified interface, matching capabilities if provided.
     */
    public static <T> T resolve(Class<T> interfaceType, Map<String, String> capabilities) {
        ComponentRegistry registry = ComponentRegistry.getInstance();
        T match = registry.getBestMatch(interfaceType, capabilities);

        if (match == null) {
            // Fallback: search without specific capability requirements but same type
            match = registry.findPlugins(interfaceType).stream().findFirst().orElse(null);
        }

        return match;
    }

    /**
     * Resolves by type with no specific capabilities.
     */
    public static <T> T resolve(Class<T> interfaceType) {
        return resolve(interfaceType, null);
    }
}
