# ECOS PROVIDER MAP

This document maps current implementations to conceptual categories as per the ECOS architectural vision.

## 1. LLM Provider
Current implementations responsible for communicating with large language models.

- `OpenAIProvider`: Implements OpenAI Chat Completions API.
- `GeminiProvider`: Implements Google Gemini API.
- `OllamaProvider`: Implements local Ollama API.
- `LlmRouter`: Orchestrates provider selection and fallback logic.
- `AiProviders`: Static configuration registry for LLM endpoints.

## 2. Agent System
Modular cognitive units designed for specific tasks.

- `AgentFactory`: Centralized instantiation of agents.
- `AnalyticAgent`: Analysis and reasoning.
- `ArchitectAgent`: Design and architectural planning.
- `JavaDevAgent` / `CppDevAgent`: Language-specific development agents.
- `TesterAgent` / `ValidatorAgent`: Quality assurance and verification.
- `FileAgent` / `GitAgent` / `MavenAgent`: Domain-specific tool wrappers.
- `PlannerAgent`: Task planning and decomposition.
- `CriticAgent` / `ReviewerAgent`: Peer review and critique.

## 3. Tool System
Execution units for interacting with the physical environment (filesystem, shell, build systems).

- `ToolFactory`: Registry for tool discovery and access.
- `FileTool`: Filesystem operations (Read, Write, Delete, Mkdir).
- `GitTool`: Version control operations via shell.
- `MavenTool`: Build and test execution via Maven shell commands.
- `ShellTool`: Generic process execution with security policy enforcement.
- `EclipseTool`: Interaction with the Eclipse IDE workspace.
- `CppTool` / `DatabaseTool`: Specialized domain tools.

## 4. Memory System
Systems responsible for persistence and retrieval of evolutionary state and knowledge.

- `IterationMemoryService`: File-based persistence for iteration records and checkpoints.
- `TrajectoryMemory`: Tracking of evolutionary paths and fitness history.
- `EvolutionMemoryGraph`: Graph-based representation of evolutionary territory and causal links.
- `FailureMemory`: Persistent tracking of failed strategies and error fingerprints.
- `SessionManager` / `SessionContext`: In-memory isolation of state for concurrent sessions.

## 5. Evolution System
Core cognitive engines and logic for evolutionary processes.

- `IterationManager`: The primary controller for evolutionary iterations.
- `DarwinEngine`: Implements proposal generation, mutation, and selection logic.
- `DarwinFlow`: The core orchestration loop for Darwinian evolution.
- `EvolutionKernel` (and implementations): The abstract core of ECOS cognition.
- `FitnessEngine` / `Evaluator`: Logic for scoring and validating evolutionary candidates.
- `PressureEngine`: Analysis of evolutionary pressures (correctness, stability, etc.).

## 6. Repository System
Systems for managing the source code repository and workspace state.

- `GitVersionControlProvider`: Interface for Git-based workspace management.
- `GitManager` (Supervisor): Standalone Git orchestration for self-development.
- `WorkspaceDeltaAnalyzer`: Logic for detecting and analyzing physical changes in the workspace.
- `ContextBuilder`: Deterministic assembly of repository context for LLM prompts.
- `TargetScanner`: Repository traversal and metadata extraction.
