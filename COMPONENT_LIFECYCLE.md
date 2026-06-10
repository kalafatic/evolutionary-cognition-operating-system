# COMPONENT LIFECYCLE

All pluggable components follow a standardized lifecycle managed by the Kernel.

## 1. register
The component is added to the `ComponentRegistry`. Metadata is verified.

## 2. initialize
The component's `init()` method is called. This is for one-time setup (loading configs, preparing internal state).

## 3. activate
The component is prepared for execution within a specific `TaskContext`.

## 4. execute
The component performs its primary duty (e.g., `sendRequest`, `executeCommand`).

## 5. deactivate
Clean up resources used during execution.

## 6. unload
The component is removed from the registry. Final resource cleanup occurs.
