# ECOS: Evolution Kernel Model Analysis

This document analyzes the ECOS architectural primitives introduced in Phase C and evaluates their ability to support true evolutionary cognition.

---

## 1. Abstraction Analysis

### Artifact
- **Responsibilities**: Encapsulates the state of an evolved entity (e.g., code snippet, prompt, plan). It is the "genotype" of the system.
- **Ownership**: Owned by a `Lineage` as either a candidate or the current survivor.
- **Persistence**: Persisted as a first-class entity within the EMF model.
- **Relationships**: Contains `Property` objects for metadata. Referenced by `EvolutionStep` and `Lineage`.

### Lineage
- **Responsibilities**: Maintains evolutionary continuity. It tracks the progression from ancestor to descendant and holds the set of candidates currently under selection.
- **Ownership**: Owned by `Orchestrator` or `EvoProject`.
- **Persistence**: Primary persistent cognitive structure.
- **Relationships**: Contains `Artifact` (candidates), References `Artifact` (survivor), Contains `EvolutionStep` (history).

### Mutation
- **Responsibilities**: Defines the delta or transformation logic applied to an ancestor to produce a descendant.
- **Ownership**: Owned by an `EvolutionStep`.
- **Persistence**: Persisted as part of the lineage history.
- **Relationships**: Contained in `EvolutionStep`.

### Evaluation
- **Responsibilities**: Records the "fitness" of an artifact against a specific pressure.
- **Ownership**: Owned by an `EvolutionStep`.
- **Persistence**: Persisted as part of the lineage history.
- **Relationships**: References a `Pressure`.

### Pressure
- **Responsibilities**: Acts as the driver of evolution. It defines the constraints and objectives (e.g., "Reduce Complexity", "Fix Build").
- **Ownership**: Global registry in `Orchestrator`.
- **Persistence**: Persisted as configuration.
- **Relationships**: Referenced by `Evaluation`.

### Kernel (`IEvolutionKernel`)
- **Responsibilities**: The central cognition engine. Implements the workflow: Analyze → Identify Pressure → Mutate → Evaluate → Select.
- **Ownership**: Architectural service layer (stateless implementation).
- **Persistence**: N/A (logic only).
- **Relationships**: Manipulates all above entities to drive evolution.

---

## 2. Identified Risks and Remnants

### Procedural Remnants
- **Execution Loop**: `EvolutionOrchestrator` still operates on a sequential list of `Task` objects. The kernel is currently invoked *within* this loop, making evolution a sub-step of a procedure rather than the primary driver.
- **Task-Centric UI**: The view layer is still optimized for displaying a task list, which may obscure the lineage progression.

### Type-Switch Risks
- **Adapter Logic**: `TaskArtifactAdapter` relies on `task.getType()` (String). This is fragile and lacks the semantic richness of a typed ECOS Artifact system.
- **Agent Selection**: Finding an agent for a mutation currently depends on string matching within the task name/type.

### Hidden Dependencies
- **Session-Iteration Dependency**: `IterationLineageAdapter` assumes that a legacy `Iteration` maps 1:1 to a `Lineage` attempt. This ties evolution to the specific Git-branching lifecycle of the Self-Dev mode.
- **Project Root**: Tools (File, Maven) still depend on the `TaskContext` providing a physical project root, which may limit the evolution of virtual or non-file-based artifacts.

### Missing Evolutionary Concepts
- **Crossover**: The model supports Mutation but lacks a native concept for merging multiple successful lineages (Sexual Reproduction/Crossover).
- **Branching Lineages**: `Lineage` is currently modeled as a single history chain. It lacks support for explicit branching where multiple "survivors" might be tracked in parallel before a later selection step.

---

## 3. Proof of True Lineage Evolution

The ECOS model supports **true lineage evolution** rather than generic document storage because it enforces **Decision Traceability**.

In generic document storage, the system would simply store `Artifact_V1`, `Artifact_V2`, etc.

In the ECOS model:
1.  Every transition is governed by `Pressure` (The "Why").
2.  Every transition records a `Mutation` (The "How").
3.  Every transition is validated by `Evaluation` (The "Proof").
4.  The `Lineage` preserves the **Cognitive Path**, allowing the `EvolutionKernel` to:
    -   Analyze which `Pressures` are most difficult to resolve.
    -   Determine which `Mutation` strategies have the highest success rate.
    -   Rollback to a known-fit `Survivor` based on multi-dimensional `Evaluation` scores.

By separating the **Artifact** (State) from the **Lineage** (Process/History) and **Pressure** (Driver), ECOS creates a closed-loop system capable of autonomous improvement through informed selection.
