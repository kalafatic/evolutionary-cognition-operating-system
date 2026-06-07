# ECOS Phase E: Mode Alignment Plan

Objective: Treat modes (Darwin, Mediated, Self-Dev) as specialized execution environments sharing a unified evolutionary core.

---

## 1. Current State of Specialization

Currently, "modes" are implemented through procedural silos:
- **Self-Dev**: Managed by `SelfDevSupervisor` and `IterationManager` with hardcoded Git/Maven dependencies.
- **Darwin**: Managed via specific orchestration flags in `EvolutionOrchestrator`.
- **Mediated**: Managed via prompt-routing logic in `LlmRouter`.

This violates **Constraint 8** (NO MODE-SPECIFIC SHORTCUTS) and **Constraint 2** (ONE EVOLUTION KERNEL).

---

## 2. The Environment Abstraction

Introduce `IEvolutionEnvironment` to encapsulate infrastructure facts and artifact-specific execution:

### Responsibilities:
- **Materialization**: Turning a mutation plan into a concrete change in the workspace.
- **Fact Reporting**: Running builds/tests and providing the raw data for kernel evaluation.
- **Context Providing**: Fetching relevant files/summaries for the specific artifact type.

---

## 3. Migration Roadmap

### Step 1: Environment Definition (Current Iteration)
- Create `IEvolutionEnvironment` and its base implementation.
- Implement `SelfDevEnvironment` to subsume Git/Maven logic from managers.

### Step 2: Kernel Injection
- Refactor the `EvolutionKernel` to accept an environment.
- The kernel becomes the "Brain"; the environment becomes the "Hands".

### Step 3: Unified Orchestration
- Align all entry points to use the same `Kernel.evolve()` loop with different environment configurations.

---

## 4. Architectural Impact

- **Generality**: The kernel no longer knows about "Git" or "Java". It only knows about "Mutations" and "Evaluations" provided by the environment.
- **Reusability**: New modes (e.g., "Documentation Evolution") can be added by implementing a new environment without touching the kernel logic.
