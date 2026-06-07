# CHANGELOG: Evolution Project

## [2.6.1] - 2026-06-06
### Added
- ECOS Core Primitives in EMF Model: Artifact, Lineage, Pressure, Mutation, Evaluation.
- IEvolutionKernel and BaseEvolutionKernel in Controller.
- Initial project structure for ECOS architectural transition.

### Changed
- Updated PROJECT_SPECIFICATION.md to reflect ECOS direction.

## [2.6.2] - 2026-06-06
### Added
- Phase C Adapters: TaskArtifactAdapter and IterationLineageAdapter.
- Bridged procedural EvolutionOrchestrator with IEvolutionKernel.

## [2.6.3] - 2026-06-07
### Changed
- Phase D1: Transferred retry authority from EvolutionOrchestrator to EvolutionKernel.
- Updated IEvolutionKernel with shouldRetry decision method.
