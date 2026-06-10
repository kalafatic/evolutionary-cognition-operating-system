package eu.kalafatic.evolution.controller.registry;

import java.util.Map;

/**
 * Helper to resolve the best provider for a given interface.
 */
public class DefaultProviderResolver {

    public static <T> T resolve(Class<T> interfaceType) {
        return resolve(interfaceType, Map.of());
    }

    public static <T> T resolve(Class<T> interfaceType, Map<String, Object> criteria) {
        return ComponentRegistry.getInstance().getProviders(interfaceType, criteria).stream()
            .findFirst()
            .orElse(null);
    }
}
