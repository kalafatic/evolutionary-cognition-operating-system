# ECOS PLUGIN REGISTRY DESIGN

This document describes the runtime registry system for pluggable components in the ECOS AI OS. The goal is to move away from static registries and hardcoded factories toward a dynamic, interface-driven discovery mechanism.

## 1. Registration Mechanism
Components register themselves with the Kernel's central `RegistryManager` using a `PluginDescriptor`.

### Plugin Descriptor
```java
public record PluginDescriptor(
    String id,
    String version,
    Class<?> interfaceType,
    Map<String, String> capabilities, // e.g., "model": "gpt-4o", "vcs": "git"
    int priority // Higher number = preferred
) {}
```

### Methods
- **Manual Registration:** `Registry.register(PluginDescriptor descriptor, Object implementation)`
- **Declarative Registration:** Via OSGi Service Registry or an `ECOS-INF/plugins.json` manifest scanned at boot.

## 2. Discovery Mechanism
The Kernel discovers plugins by querying the registry for specific interface types and optional capability filters.

### Query API
```java
<T> List<T> findPlugins(Class<T> interfaceType);
<T> List<T> findPlugins(Class<T> interfaceType, Map<String, String> requiredCapabilities);
```

## 3. Runtime Selection
Selection logic determines which plugin to use when multiple implementations for the same interface exist.

### Selection Strategy
1. **Explicit Selection:** If the `Orchestrator` model or `PlatformMode` specifies a specific plugin ID, it is used.
2. **Capability Match:** The registry filters plugins that match the required capabilities (e.g., matching a specific LLM model).
3. **Priority Ranking:** If multiple matches exist, the one with the highest `priority` is selected.
4. **Contextual Selection:** In some cases, the `EvolutionEngine` may select a plugin based on historical fitness (e.g., selecting the most successful `MutationStrategy`).

## 4. Default Implementation Resolution
The Kernel ensures system stability by providing or identifying default fallbacks.

- **Embedded Defaults:** The Kernel provides minimal, built-in implementations (e.g., `DefaultFileTool`) registered with the lowest priority (`priority = 0`).
- **Configuration Defaults:** A system-wide `ecos.properties` file can define the default ID for each core interface.
- **Fail-safe Fallback:** If no plugin matches a query, the registry returns the implementation with the highest priority, or a `NullImplementation` if required by the contract.
