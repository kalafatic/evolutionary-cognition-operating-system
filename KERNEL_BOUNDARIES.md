# KERNEL BOUNDARIES

This document defines the boundaries between the core Kernel and its pluggable extensions.

## Kernel (Core OS Runtime)
The Kernel is the invariant foundation of the system. It manages state transitions, security, and component orchestration.

- **IterationManager**: The primary state machine and controller.
- **SessionManager**: Handles multi-session isolation and lifecycle.
- **ComponentRegistry**: Discovery and resolution of pluggable providers.
- **TaskContext**: Execution state and shared metadata.
- **EvolutionModel**: The EMF-based structural definition of tasks and orchestrators.
- **Security/Policy**: Enforcement of constraints and resource limits.

## Plugins (External Implementations)
Plugins provide the concrete logic for specific technical domains. They are swappable and extensible.

- **LLM Connectors**: Implementations for OpenAI, Ollama, Anthropic, etc.
- **VCS Adapters**: Connectors for Git, SVN, or virtual file systems.
- **Domain Agents**: Specialized agents for Java, C++, DevOps, or Security.
- **Toolkits**: Collections of tools for specific build systems (Maven, Gradle) or environments.
- **Storage Backends**: Implementations for File-based, DB-based, or Vector-based memory.
