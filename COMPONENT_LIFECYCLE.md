# ECOS COMPONENT LIFECYCLE

This document defines the standard lifecycle for pluggable components within the ECOS AI OS. Every plugin must adhere to this state machine to ensure predictable resource management and system stability.

## Lifecycle Stages

### 1. Register
- **Action:** The plugin descriptor is added to the `RegistryManager`.
- **State:** `REGISTERED`
- **Condition:** No code from the plugin is executed yet. Only metadata is available to the system.

### 2. Initialize
- **Action:** `plugin.init(KernelContext context)` is called.
- **State:** `INITIALIZED`
- **Responsibility:** The plugin sets up its internal state, validates its environment (e.g., checks for local Ollama availability), and prepares non-expensive resources.

### 3. Activate
- **Action:** `plugin.activate()` is called just before the first execution.
- **State:** `ACTIVE`
- **Responsibility:** The plugin allocates expensive resources (e.g., opening database connections, starting background threads). Activation is lazy and only occurs if the plugin is selected for execution.

### 4. Execute
- **Action:** The core interface methods (e.g., `llm.sendRequest()`, `tool.execute()`) are invoked.
- **State:** `ACTIVE`
- **Responsibility:** The plugin performs its primary task according to its interface contract.

### 5. Deactivate
- **Action:** `plugin.deactivate()` is called when the session ends or a better plugin replaces it.
- **State:** `INITIALIZED`
- **Responsibility:** The plugin releases expensive resources but retains its configuration for potential re-activation.

### 6. Unload
- **Action:** The plugin is removed from the registry.
- **State:** `UNLOADED`
- **Responsibility:** The plugin performs final cleanup. The classloader may be disposed of.

---

## State Transition Rules
- A component cannot move to `ACTIVE` without being `INITIALIZED`.
- If `Execute` fails with a fatal error, the component is automatically moved to `DEACTIVATED`.
- The `Kernel` owns the lifecycle and is responsible for triggering transitions based on session demands.
