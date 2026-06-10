# AI OS INTERFACES

This document defines the core interfaces for the pluggable AI OS architecture.

## 1. ILlmProvider
**Responsibility:** Token generation and reasoning interface.
- `String sendRequest(Orchestrator orchestrator, String prompt, float temperature, String proxyUrl, TaskContext context)`
- `String testConnection(Orchestrator orchestrator, float temperature, String proxyUrl, TaskContext context)`

## 2. IAgent
**Responsibility:** Specialized cognitive worker.
- `String getId()`
- `String getType()`
- `List<ITool> getTools()`
- `OrchestratorResponse execute(String request, TaskContext context)`
- `void setSessionContainer(SessionContainer container)`

## 3. ITool
**Responsibility:** Physical effector for environment interaction.
- `String execute(String command, File projectRoot, TaskContext context)`
- `String getName()`

## 4. IMemoryProvider
**Responsibility:** Persistence and retrieval of evolutionary artifacts.
- `void saveRecord(IterationRecord record)`
- `Checkpoint loadCheckpoint(String sessionId)`
- `void saveCheckpoint(Checkpoint checkpoint)`
- `TrajectoryMemory getTrajectoryMemory()`

## 5. IEvolutionEngine
**Responsibility:** Evolutionary logic coordination (proposal, mutation, selection).
- `List<BranchVariant> generateProposals(TaskContext context, String goal)`
- `EvaluationResult executeWinner(TaskContext context, EvolutionDecision decision, List<BranchVariant> variants, String goal)`

## 6. IRepositoryProvider
**Responsibility:** Version control and workspace abstraction.
- `void commit(File root, String message)`
- `String diff(File root, String branch1, String branch2)`
- `void createBranch(File root, String name)`
- `List<String> getBranches(File root)`
