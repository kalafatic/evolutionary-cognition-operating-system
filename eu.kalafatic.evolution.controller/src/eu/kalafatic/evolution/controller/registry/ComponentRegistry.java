package eu.kalafatic.evolution.controller.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Central registry for all swappable components.
 */
public class ComponentRegistry {
    private static final ComponentRegistry INSTANCE = new ComponentRegistry();
    private final Map<PluginDescriptor, Object> registrations = new ConcurrentHashMap<>();

    private ComponentRegistry() {}

    public static ComponentRegistry getInstance() {
        return INSTANCE;
    }

    public <T> void register(PluginDescriptor descriptor, T implementation) {
        registrations.put(descriptor, implementation);
    }

    public <T> void register(PluginDescriptor descriptor, Class<? extends T> implementationClass) {
        registrations.put(descriptor, implementationClass);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getProviders(Class<T> interfaceType) {
        return registrations.entrySet().stream()
            .filter(e -> e.getKey().interfaceType().equals(interfaceType))
            .sorted((e1, e2) -> Integer.compare(e2.getKey().priority(), e1.getKey().priority()))
            .map(e -> {
                Object impl = e.getValue();
                if (impl instanceof Class) {
                    try {
                        return (T) ((Class<?>)impl).getDeclaredConstructor().newInstance();
                    } catch (Exception ex) {
                        return null;
                    }
                }
                return (T) impl;
            })
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getProviders(Class<T> interfaceType, Map<String, Object> criteria) {
        return registrations.entrySet().stream()
            .filter(e -> e.getKey().interfaceType().equals(interfaceType))
            .filter(e -> matches(e.getKey().metadata(), criteria))
            .sorted((e1, e2) -> Integer.compare(e2.getKey().priority(), e1.getKey().priority()))
            .map(e -> {
                Object impl = e.getValue();
                if (impl instanceof Class) {
                    try {
                        return (T) ((Class<?>)impl).getDeclaredConstructor().newInstance();
                    } catch (Exception ex) {
                        return null;
                    }
                }
                return (T) impl;
            })
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toList());
    }

    private boolean matches(Map<String, Object> metadata, Map<String, Object> criteria) {
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            if (!metadata.containsKey(entry.getKey()) || !metadata.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
