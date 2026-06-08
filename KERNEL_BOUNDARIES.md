# ECOS KERNEL VS. PLUGIN BOUNDARIES

This document defines the separation of concerns between the ECOS core OS runtime (Kernel) and external implementations (Plugins).

## 1. Kernel (Core OS Runtime)
The Kernel is responsible for the universal evolutionary logic and system orchestration. It remains artifact-agnostic and provider-agnostic.

### Responsibilities
- **Evolutionary Orchestration:** The abstract Darwin loop (Analyze, Mutate, Evaluate, Select).
- **Lineage Management:** Maintaining the integrity and history of surviving artifacts.
- **Session & Isolation:** Managing concurrent evolutionary contexts and resource gating.
- **Signal Bus:** Propagation of pressure signals and system-wide events.
- **Interface Definitions:** Defining the contracts that plugins must implement.
- **System State Gating:** Ensuring all operations are permitted by the current `SystemState`.
- **Capability Registry:** Discovery and routing of plugin-provided capabilities.

---

## 2. Plugins (External Implementations)
Plugins provide concrete implementations for specific models, tools, and environments.

### Responsibilities
- **LLM Connectivity:** Concrete adapters for OpenAI, Anthropic, Ollama, etc.
- **Tool Implementation:** Concrete logic for Git, Maven, Filesystem, or Shell operations.
- **Domain-Specific Agents:** Specialized cognitive logic (e.g., CppDevAgent, JavaDevAgent).
- **Persistence Adapters:** Implementations for local filesystem, SQL databases, or vector stores.
- **Evaluation Logic:** Specific fitness functions and pressure analysis for different domains (e.g., Code Coverage, Linting).
- **Mutation Strategies:** Domain-specific variation logic (e.g., Refactoring patterns, Prompt perturbation).
- **Repository Adapters:** Adapters for Git, SVN, or virtual workspaces.

---

## 3. Boundary Invariants
- The **Kernel** must never depend on a concrete **Plugin** class.
- **Plugins** must communicate with each other exclusively through **Kernel** interfaces or the Signal Bus.
- All cognitive decisions (Selection, Finalization) belong to the **Kernel**.
- All physical execution (Process spawning, File writing) belongs to **Plugins** (Tools).
- Lineage persistence logic is owned by the **Kernel**, but the storage backend is a **Plugin** (MemoryProvider).
