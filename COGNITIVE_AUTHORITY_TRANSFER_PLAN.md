# ECOS Phase D1: Cognitive Authority Transfer Plan

Objective: Move evolutionary strategy decisions from procedural infrastructure into the `EvolutionKernel`.

---

## 1. Authority Mapping

| Decision | Current Owner | Target Owner |
| :--- | :--- | :--- |
| **Whether to Retry** | `EvolutionOrchestrator` | `EvolutionKernel` |
| **Whether to Mutate** | `EvolutionOrchestrator` | `EvolutionKernel` |
| **Whether to Loop** | `Task.loopToTaskId` | `EvolutionKernel` |
| **Whether to Rollback** | `SelfDevSupervisor` | `EvolutionKernel` |
| **Whether to Finalize** | `SelfDevSupervisor` | `EvolutionKernel` |

---

## 2. Migration Strategy

The transfer will occur incrementally to preserve existing behavior while shifting ownership.

### Step 1: Retry Authority (Current Iteration)
- **Action**: Introduce `shouldRetry` to `IEvolutionKernel`.
- **Delegation**: `EvolutionOrchestrator` calls `kernel.shouldRetry()` instead of hardcoding `retry <= MAX_RETRIES`.

### Step 2: Loop Authority
- **Action**: Introduce `shouldContinue` to `IEvolutionKernel`.
- **Delegation**: `EvolutionOrchestrator` consults the kernel after each task execution to determine if it should jump back to a previous artifact state.

### Step 3: Rollback & Selection Authority
- **Action**: Introduce `evaluateFitness` to `IEvolutionKernel`.
- **Delegation**: `SelfDevSupervisor` delegates the "Success/Failure" decision to the kernel's evaluation of the lineage survivor.

---

## 3. Transitional Adapters

- **TaskArtifactAdapter**: Already introduced. Presenting `Task` as `Artifact` allows the kernel to reason about task results.
- **IterationLineageAdapter**: Already introduced. Presenting `Iteration` as `Lineage` allows the kernel to reason about evolutionary history.

---

## 4. Architectural Risks

1.  **Circular Dependencies**: Ensuring the `Kernel` (Cognition) and `Orchestrator` (Execution) remain decoupled.
2.  **Performance Overheard**: Incremental calls to the kernel (which may involve AI reasoning) must be balanced against the need for deterministic infrastructure speed.
3.  **State Synchronization**: Keeping the legacy `Task` status in sync with the kernel's `Evaluation` of the corresponding `Artifact`.
