# ECOS Phase D0 Update: Cognitive Ownership Analysis

This document provides a post-Phase D1 Step 2 analysis of cognitive ownership in the platform.

---

## 1. Ownership Mapping

| Component | Cognition | Infrastructure | Persistence | UI | Execution |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **EvolutionKernel** | ✅ (Primary) | | | | |
| **EvolutionOrchestrator** | ❌ (Delegates) | ✅ (Coordinator) | | | |
| **SelfDevSupervisor** | ❌ (Remnant) | ✅ (Scheduler) | | | |
| **PlannerAgent** | ✅ (In Service) | | | | |
| **IterationManager** | | ✅ (Workflow) | | | ✅ |
| **Git / Maven Tools** | | ✅ | | | ✅ |
| **EMF Model (Lineage/Artifact)** | | | ✅ | | |
| **EMF Model (Task/Iteration)** | ❌ (Passive) | | ✅ | | |
| **MultiPageEditor / Pages** | | | | ✅ | |

---

## 2. Evolution of Authority

Since the initiation of Phase D1, cognitive authority has materially shifted:

### 1. Retry Decision
- **Old Owner**: `EvolutionOrchestrator` (Procedural `for` loop with hardcoded `MAX_RETRIES`).
- **Current Owner**: `EvolutionKernel` (Pressure-aware `analyze()` method).
- **Status**: ✅ TRANSFERRED.

### 2. Looping / Backtracking
- **Old Owner**: `Task` (Embedded `loopToTaskId` attribute).
- **Current Owner**: `EvolutionKernel` (`selectTarget()` based on lineage state).
- **Status**: ✅ TRANSFERRED.

---

## 3. Remaining Violations (Phase D2 Targets)

### 1. Supervisor owns Rollback Decision
- **Status**: ❌ VIOLATION
- **Evidence**: `SelfDevSupervisor` checks `!result.isSuccess()` to increment failure counts and decide to stop the session.
- **Conflict**: The decision that an iteration has failed so significantly that it should be "rolled back" is a cognitive evaluation of lineage fitness, not a simple build status check. This decision should move to the `Kernel`.

### 2. Orchestrator owns Agent Routing
- **Status**: ❌ VIOLATION
- **Evidence**: `EvolutionOrchestrator.findAgentForTask()` contains hardcoded logic mapping types to implementation classes.
- **Conflict**: Mapping a mutation requirement to a specialized agent is a cognitive function of the `EvolutionKernel` based on the `Artifact` type and `Pressure`.

### 3. IterationManager owns Workflow Sequencing
- **Status**: ❌ VIOLATION
- **Evidence**: `IterationManager` executes a hardcoded sequence of Planning -> Execution -> Evaluation.
- **Conflict**: While most evolution follows this path, the `Kernel` should be able to deviate (e.g., skip planning if the pressure is a simple retry, or perform multi-stage evaluation).

---

## 4. Flow Verification

### Pressure → Kernel → Lineage → Artifact

The flow is now **Dominant** in the following ways:
1.  **Pressure**: Input via `IEvolutionKernel.analyze`.
2.  **Kernel**: Successfully established as the decision-maker for retry and jump-back strategy.
3.  **Lineage**: Effectively utilized via adapters to provide history to the kernel.
4.  **Artifact**: The target of every kernel decision.

### Conclusion
Cognitive ownership is approximately **60% aligned** with the ECOS vision. The structural foundation is 100% complete, and the tactical control (retries/loops) is transferred. The remaining 40% (Strategic rollback, agent selection, and workflow flexibility) will be the focus of Phase D2.
