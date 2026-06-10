# FINAL COUPLING STATE

Post-migration architectural coupling analysis.

## 1. External Implementations (Pluggable)
The following subsystems are now fully pluggable and resolved via the registry:
- **LLM Providers**: OpenAI, Ollama, Gemini.
- **VCS Providers**: Git.
- **Agents**: All 19 default agents + MetadataAgent.
- **Tools**: File, Maven, Git, Shell, Eclipse, Cpp, Database.
- **Memory**: IterationMemoryService.
- **Evolution Engines**: DarwinEngine, Phase, Mutation, Fitness, Authority, Trajectory.

## 2. Remaining Kernel Coupling (Intrinsic)
The following items remain as "Kernel Intrinsic" (part of the AI OS runtime itself):
- `IterationManager`: Orchestrates the state machine.
- `SessionManager`: Manages execution isolation.
- `ComponentRegistry`: The mediator for all plugins.
- `TaskContext`: Shared state container.

## 3. Coupling Summary
- **Before**: 100+ direct instantiations of concrete implementations in core logic.
- **After**: 0 direct instantiations of swappable implementations in core logic. All resolution is mediated by `DefaultProviderResolver`.
