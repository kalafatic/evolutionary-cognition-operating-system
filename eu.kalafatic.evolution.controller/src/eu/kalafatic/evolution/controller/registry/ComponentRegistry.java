package eu.kalafatic.evolution.controller.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Central registry for pluggable ECOS components.
 */
public class ComponentRegistry {
    private static final ComponentRegistry INSTANCE = new ComponentRegistry();

    public static ComponentRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<PluginDescriptor, Object> plugins = new ConcurrentHashMap<>();

    private ComponentRegistry() {}

    /**
     * Registers a plugin implementation.
     */
    public void register(PluginDescriptor descriptor, Object implementation) {
        if (implementation instanceof Class) {
             // Store class for instantiation
        } else if (!descriptor.interfaceType().isInstance(implementation)) {
            throw new IllegalArgumentException("Implementation does not match interface type: " + descriptor.interfaceType().getName());
        }
        plugins.put(descriptor, implementation);
    }

    /**
     * Finds all plugins implementing the specified interface.
     */
    public <T> List<T> findPlugins(Class<T> interfaceType) {
        return plugins.entrySet().stream()
            .filter(e -> descriptorMatches(e.getKey(), interfaceType, null))
            .sorted(Comparator.comparingInt((Map.Entry<PluginDescriptor, Object> e) -> e.getKey().priority()).reversed())
            .map(e -> interfaceType.cast(e.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Finds plugins matching interface and required capabilities.
     */
    public <T> List<T> findPlugins(Class<T> interfaceType, Map<String, String> requiredCapabilities) {
        return plugins.entrySet().stream()
            .filter(e -> descriptorMatches(e.getKey(), interfaceType, requiredCapabilities))
            .sorted(Comparator.comparingInt((Map.Entry<PluginDescriptor, Object> e) -> e.getKey().priority()).reversed())
            .map(e -> interfaceType.cast(e.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Gets the best matching plugin or null.
     */
    public <T> T getBestMatch(Class<T> interfaceType, Map<String, String> capabilities) {
        PluginDescriptor descriptor = plugins.keySet().stream()
            .filter(d -> descriptorMatches(d, interfaceType, capabilities))
            .sorted(Comparator.comparingInt(PluginDescriptor::priority).reversed())
            .findFirst()
            .orElse(null);

        if (descriptor == null) return null;
        Object impl = plugins.get(descriptor);

        if (impl instanceof Class) {
            try {
                return interfaceType.cast(((Class<?>) impl).getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                return null;
            }
        }
        return interfaceType.cast(impl);
    }

    /**
     * Gets a plugin by its unique ID.
     */
    public <T> T getById(String id, Class<T> interfaceType) {
        return plugins.entrySet().stream()
            .filter(e -> e.getKey().id().equals(id) && interfaceType.isAssignableFrom(e.getKey().interfaceType()))
            .map(e -> interfaceType.cast(e.getValue()))
            .findFirst()
            .orElse(null);
    }

    private boolean descriptorMatches(PluginDescriptor descriptor, Class<?> type, Map<String, String> reqs) {
        if (!type.isAssignableFrom(descriptor.interfaceType())) return false;
        if (reqs == null || reqs.isEmpty()) return true;

        for (Map.Entry<String, String> entry : reqs.entrySet()) {
            String val = descriptor.capabilities().get(entry.getKey());
            if (val == null || !val.equalsIgnoreCase(entry.getValue())) return false;
        }
        return true;
    }
}
