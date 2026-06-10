package eu.kalafatic.evolution.controller.registry;

import java.util.Map;

/**
 * Metadata descriptor for a plugin component.
 */
public record PluginDescriptor(
    String id,
    String version,
    Class<?> interfaceType,
    Map<String, Object> metadata,
    int priority
) {}
