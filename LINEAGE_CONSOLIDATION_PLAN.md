# ECOS Phase F: Lineage Consolidation Plan

Objective: Strengthen lineage-centric evolution and reduce dependence on legacy variant collections and branch-centric iteration logic.

---

## 1. The Shift to Surviving Lineage

Currently, evolution is tracked through a list of `Iteration` objects, where each iteration corresponds to a Git branch. While functional, this is **Procedural Tracking** rather than **Evolutionary Lineage**.

**ECOS Vision**: The primary persistent cognitive structure is the `SurvivingLineage`.
- Candidates (temporary variants) exist to determine fitness.
- The Lineage (survivor + history) is what persists across sessions.

---

## 2. Migration Steps

### Step 1: Kernel-Owned Persistence (Current Iteration)
- Refactor the `EvolutionKernel` to be responsible for updating the `Lineage` model.
- Every `evolve()` step must produce an `EvolutionStep` recording the `Mutation`, `Evaluations`, and the `SelectedSurvivor`.

### Step 2: Lineage Promotion
- Move the primary evolutionary state from `SelfDevSession.iterations` to a top-level `Lineage` within the orchestrator or session.
- Iterations become "Execution Snapshots" of a lineage step.

### Step 3: Branch Deprecation
- Decouple the concept of "Branching" from the concept of "Mutation".
- A mutation might happen on the same branch or a different one; the `Lineage` tracks the relationship regardless of the infrastructure's storage mechanism.

---

## 3. Success Criteria

- The EMF model correctly captures the ancestor-descendant relationships.
- The `survivor` attribute of the `Lineage` always points to the most fit artifact found so far.
- Legacy `Iteration` objects are relegated to infrastructure logs rather than driving selection logic.
