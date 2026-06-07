# ECOS Phase D0: Cognitive Ownership Analysis

This document maps the responsibilities of every major component in the platform to determine if cognitive ownership is correctly aligned with the ECOS vision.

---

## 1. Ownership Mapping

| Component | Cognition | Infrastructure | Persistence | UI | Execution |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **EvolutionKernel** | ✅ | | | | |
| **EvolutionOrchestrator** | ❌ (Legacy) | ✅ | | | |
| **PlannerAgent** | ✅ | | | | |
| **Specialized Agents** | ✅ | | | | |
| **ReviewerAgent** | ✅ | | | | |
| **SelfDevSupervisor** | | ✅ | | | |
| **Git / Maven Tools** | | ✅ | | | ✅ |
| **File / Shell Tools** | | ✅ | | | ✅ |
| **EMF Model (Lineage/Artifact)** | | | ✅ | | |
| **EMF Model (Task/Iteration)** | ❌ (Remnant) | | ✅ | | |
| **MultiPageEditor / Pages** | | | | ✅ | |

---

## 2. Identified Violations

### 1. Task Owns Cognition
- **Status**: ❌ VIOLATION
- **Evidence**: `Task` objects contain `loopToTaskId` and `priority`.
- **Conflict**: Control flow and selection logic are embedded in a procedural persistence object. In ECOS, the `Kernel` should determine recursion based on `Lineage` history and `Pressure`.

### 2. EvolutionOrchestrator Owns Evolution Strategy
- **Status**: ❌ VIOLATION
- **Evidence**: `EvolutionOrchestrator.executeTaskWithRetries()` handles retry logic and error-to-feedback mapping.
- **Conflict**: The strategy for dealing with failure (Mutate vs. Repair vs. Retry) is a cognitive decision that must be owned by the `Kernel`. The `Orchestrator` should only coordinate the resources.

### 3. Git owns Selection (Rollback Logic)
- **Status**: ❌ VIOLATION
- **Evidence**: `SelfDevSupervisor` uses Git rollback as the primary recovery mechanism.
- **Conflict**: While the *action* of rollback is infrastructure, the *decision* that a state is "unfit" and requires reversion is cognitive. Currently, this decision is often tied directly to Maven build failure (Infrastructure) rather than an ECOS `Evaluation` against `Pressure`.

### 4. UI Drives Orchestration Sequence
- **Status**: ❌ VIOLATION
- **Evidence**: `ApprovalPage` allows users to re-order tasks, which directly modifies the execution path.
- **Conflict**: While user preference is a valid `Pressure`, allowing the UI to directly manipulate the procedural queue bypasses the `Kernel's` ability to maintain lineage consistency.

---

## 3. Flow Verification

### The Dominant Flow:
**Pressure → Kernel → Lineage → Artifact**

- **Pressure**: Currently emerging as a configuration entity. Needs to be promoted to the primary trigger for `AnalyticAgent`.
- **Kernel**: `IEvolutionKernel` is introduced but currently acts as a passive observer within the `EvolutionOrchestrator` loop.
- **Lineage**: Successfully introduced via `IterationLineageAdapter`. Continuity is preserved through the adapter, but the underlying model is still branch-centric.
- **Artifact**: `TaskArtifactAdapter` allows the system to treat legacy results as evolved artifacts.

### Conclusion
The system is in a transitional state. The ECOS primitives are present and have assumed responsibility for **Structure**, but **Control** still resides in legacy procedural components (`EvolutionOrchestrator` and `Task`).

**Phase D1 Requirement**: Subsume retry and loop logic into `BaseEvolutionKernel` to transfer cognitive authority.
