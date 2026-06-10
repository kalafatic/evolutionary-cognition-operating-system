# PLUGIN REGISTRY DESIGN

The AI OS uses a centralized registry for dynamic component resolution.

## 1. Registration
Components register themselves with the `ComponentRegistry` using a `PluginDescriptor`.
- **PluginDescriptor**: Contains metadata (id, version, type, priority, capabilities).
- **Mechanism**: Static registration during bootstrap or dynamic discovery from plugin bundles.

## 2. Discovery
Components are discovered by their interface type and optional metadata filters.
- `registry.getProvider(ILlmProvider.class, Map.of("provider", "ollama"))`

## 3. Selection
Selection is based on:
- **Priority**: Higher priority plugins are preferred.
- **Capability Match**: Plugins must satisfy requested metadata (e.g., "model-format: openai").
- **Fallback**: The registry returns the highest-priority "default" if no specific match is found.

## 4. Default Resolution
The Kernel maintains a set of "Default Implementation" mappings for each core interface to ensure a functional system out-of-the-box.
- `DefaultProviderResolver.resolve(ILlmProvider.class)` -> Returns configured primary LLM.
