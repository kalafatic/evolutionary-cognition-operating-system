# ARCHITECTURE COUPLING REPORT

This report documents the hardcoded dependencies and coupling found within the ECOS codebase as of the current analysis.

## 1. LLM Provider Subsystem

| Aspect | Findings |
| --- | --- |
| **Where it is hardcoded** | `AiProviders.java` (Static map of URLs and API keys for OpenAI, Anthropic, Gemini, DeepSeek, Groq, Ollama). `GeminiProvider.java` (Default URL). `EvolutionServer.java` (Default Ollama URL `http://localhost:11434`). `OrchestratorServiceImpl.java` (Default Ollama URL). |
| **How it is instantiated** | `LlmRouter.java` instantiates `OpenAIProvider`, `GeminiProvider`, and `OllamaProvider` as private final fields. |
| **Where it is directly referenced** | `AiService`, `LlmRouter`, `AiProviders`, `OpenAIProvider`, `GeminiProvider`, `OllamaProvider`. |
| **Injectable or Static** | **Static/Hardcoded**. `LlmRouter` uses fixed instances for remote providers. `AiProviders` is a static registry. `AiService` allows setting an `LlmRouter` instance, but `LlmRouter` itself is a singleton with hardcoded providers. |

## 2. Git Subsystem

| Aspect | Findings |
| --- | --- |
| **Where it is hardcoded** | `GitVersionControlProvider.java`, `GitTool.java`, and `GitManager.java` (Supervisor) all contain hardcoded string literals for Git commands (e.g., `"git log"`, `"git diff"`, `"git commit"`). `GitTool.java` has hardcoded logic for metadata injection. |
| **How it is instantiated** | `DarwinFlow.java`, `EvolutionServer.java`, and `WorkspaceDeltaAnalyzer.java` instantiate `new GitTool()` directly. `GitVersionControlProvider` instantiates `new ShellTool()`. `SelfDevSupervisor` instantiates `new GitManager()`. |
| **Where it is directly referenced** | `DarwinFlow`, `EvolutionOrchestrator` (via `ToolFactory`), `GitAgent`, `SelfDevSupervisor`, `GitEmfReconciler`. |
| **Injectable or Static** | **Mixed/Hardcoded**. While `EvolutionOrchestrator` uses `ToolFactory` (static registry), many core components like `DarwinFlow` bypass the factory and use `new GitTool()`. Supervisor's `GitManager` is strictly hardcoded. |

## 3. Agent Subsystem

| Aspect | Findings |
| --- | --- |
| **Where it is hardcoded** | `AgentFactory.java` contains a hardcoded list of all agent classes (`AnalyticAgent`, `ArchitectAgent`, `JavaDevAgent`, etc.). |
| **How it is instantiated** | `AgentFactory.createIsolatedAgents(SessionContainer)` instantiates each agent class using `new`. |
| **Where it is directly referenced** | `EvolutionOrchestrator`, `SessionContext`, `KernelFactory`. |
| **Injectable or Static** | **Injectable**. Agents are registered in a `SessionContainer`'s registry, allowing for per-session isolation and potential overriding, though the default set is hardcoded in the factory. |

## 4. Tool Subsystem

| Aspect | Findings |
| --- | --- |
| **Where it is hardcoded** | `ToolFactory.java` contains a static registry mapping names to tool instances. Many tools (e.g., `MavenTool`, `GitTool`, `CppTool`) have hardcoded paths to executables or use `new ShellTool()` internally. |
| **How it is instantiated** | `ToolFactory` static block instantiates default tools (`new FileTool()`, `new MavenTool()`, etc.). |
| **Where it is directly referenced** | `EvolutionOrchestrator` (via `ToolFactory`), `GitTool`, `MavenTool`, `CppTool`, `EclipseTool`, `ContextSelectionEngine`. |
| **Injectable or Static** | **Static Registry**. Tools are accessed via `ToolFactory.getTool(name)`. Most tools are singletons within the factory but often instantiate other tools (like `ShellTool`) directly. |

## 5. Memory / Storage System

| Aspect | Findings |
| --- | --- |
| **Where it is hardcoded** | `IterationMemoryService.java` has hardcoded relative paths: `orchestrator/memory`, `orchestrator/audit_trail.jsonl`, and `iterations`. |
| **How it is instantiated** | `KernelFactory.create` instantiates `new IterationMemoryService(projectRoot)`. |
| **Where it is directly referenced** | `DarwinFlow`, `IterationManager`, `DarwinEngine`, `KernelFactory`, `SessionContext`. |
| **Injectable or Static** | **Injectable but Hardcoded Paths**. The service is passed via constructors (injectable), but its internal persistence logic is tied to hardcoded filesystem structures. |

## 6. Workflow / Orchestration Logic

| Aspect | Findings |
| --- | --- |
| **Where it is hardcoded** | `ModeRouter.java` contains hardcoded switch-case and if-else logic mapping `PlatformMode` and prompt keywords to specific flows (primarily `DarwinFlow`). |
| **How it is instantiated** | `ModeRouter.resolveFlow` instantiates `new DarwinFlow(...)` directly. |
| **Where it is directly referenced** | `EvolutionOrchestrator`, `IterationManager`, `KernelFactory`. |
| **Injectable or Static** | **Hardcoded Logic**. The choice of orchestration flow is not extensible without modifying `ModeRouter`. `DarwinFlow` is the hardcoded default for most evolutionary modes. |
