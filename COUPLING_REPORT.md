# ARCHITECTURE COUPLING REPORT

This report identifies hardcoded dependencies and coupling points within the Evolutionary OS (ECOS) codebase as of Phase J.

## 1. LLM Providers

| Subsystem | Location | Instantiation | Direct References | Injectable? |
|-----------|----------|---------------|-------------------|-------------|
| OpenAI | `LlmRouter.java` | `private final ILlmProvider openAiProvider = new OpenAIProvider();` | `sendRemoteRequest`, `testConnection` | No (Static) |
| Ollama | `LlmRouter.java` | `private ILlmProvider ollamaProvider = new OllamaProvider();` | `sendLocalRequest`, `testConnection`, `buildContextLocally`, `verifyResponseLocally` | Partially (`setLocalProvider`) |
| Gemini | `LlmRouter.java` | `private final ILlmProvider geminiProvider = new GeminiProvider();` | `sendRemoteRequest`, `testConnection` | No (Static) |

## 2. Git Operations

| Subsystem | Location | Instantiation | Direct References | Injectable? |
|-----------|----------|---------------|-------------------|-------------|
| VCS Provider | `PeerReviewService.java` | `this.vcsProvider = new GitVersionControlProvider();` | `checkImpact`, `verifySafeState` | No (Hardcoded in constructor) |
| Git Tool | `ToolFactory.java` | `registerTool(EvolutionConstants.TOOL_GIT, new GitTool());` | `ToolFactory.getTool("git")` | No (Static Registry) |
| Git Tool (Agent) | `GitAgent.java`, `CppDevAgent.java` | `addTool(new GitTool());` | `tools` list in `BaseAiAgent` | No (Hardcoded in constructor) |
| Git Tool (Direct) | `WorkspaceDeltaAnalyzer.java`, `EvolutionServer.java`, `DarwinFlow.java` | `new GitTool()` | Direct method calls | No (Hardcoded) |

## 3. Agent System

| Subsystem | Location | Instantiation | Direct References | Injectable? |
|-----------|----------|---------------|-------------------|-------------|
| Agent Suite | `AgentFactory.java` | `isolated.add(new AnalyticAgent(container));` (19 agents total) | `IterationManager` | No (Hardcoded factory methods) |
| Agent Discovery | `IterationManager.java` | `getInternalAgent(EvolutionConstants.AGENT_ANALYTIC)` (Casts to concrete classes) | `analyticAgent`, `strategicPlanner`, `criticAgent`, etc. | No (Concrete class coupling) |

## 4. Tool System

| Subsystem | Location | Instantiation | Direct References | Injectable? |
|-----------|----------|---------------|-------------------|-------------|
| Tool Registry | `ToolFactory.java` | Static initializer: `registerTool(..., new FileTool());` | `ToolFactory.getTool(...)` | No (Static Registry) |
| File Tool | `ContextBuilder.java` | `FileTool fileTool = new FileTool();` | `fileTool.execute(...)` | No (Hardcoded) |
| Maven Tool | `Evaluator.java`, `TesterAgent.java`, `MavenAgent.java`, `JavaDevAgent.java`, `QualityAgent.java` | `new MavenTool()` | `mavenTool.execute(...)` | No (Hardcoded) |

## 5. Memory/Storage Systems

| Subsystem | Location | Instantiation | Direct References | Injectable? |
|-----------|----------|---------------|-------------------|-------------|
| Memory Service | `EvolutionKernelContext.java` | `new IterationMemoryService(projectRoot)` | `this.memoryService` | Partially (Constructor allows null fallback) |
| Session Memory | `SessionContext.java` | `memoryService = new IterationMemoryService(projectRoot);` | `session.getMemoryService(...)` | No (Hardcoded in constructor) |

## 6. Workflow Orchestration Logic

| Subsystem | Location | Instantiation | Direct References | Injectable? |
|-----------|----------|---------------|-------------------|-------------|
| Kernel State Machine | `KernelFactory.java` | `return new IterationManager(...)` | `KernelFacade.handle()` | No (Hardcoded factory) |
| Evolution Engines | `IterationManager.java` | `this.phaseEngine = new DefaultPhaseEngine();`<br>`this.branchManager = new DefaultBranchManager();`<br>`this.mutationEngine = new DefaultMutationEngine(...);`<br>`this.fitnessEngine = new DefaultFitnessEngine(...);`<br>`this.realityEngine = new DefaultRealityEngine(...);`<br>`this.authorityEngine = new DefaultAuthorityEngine(...);`<br>`this.trajectoryEngine = new DefaultTrajectoryEngine(...);`<br>`this.gitAdapter = new DefaultGitEvolutionAdapter();` | Internal state transition, mutation, and evaluation logic | No (Hardcoded in constructor) |
| Trajectory Engine | `IterationManager.java` | `private final EvolutionaryTrajectoryEngine evolutionaryTrajectoryEngine = new EvolutionaryTrajectoryEngine();` | `evolve()` | No (Hardcoded field) |
| Intent Engines | `IterationManager.java` | `this.intentExpansionEngine = new IntentExpansionEngine(sessionContainer);`<br>`this.dimensionInferenceEngine = new DefaultDimensionInferenceEngine(...);`<br>`this.clarificationManager = new ClarificationManager();`<br>`this.clarificationPlanner = new ClarificationPlanner();` | `runDarwinIteration()` | No (Hardcoded in constructor/fields) |
| Mediation Components | `IterationManager.java` | `new TargetScanner()`, `new ContextCurator()`, `new SemanticExtractor()`, `new PromptSynthesizer()`, `new MediatedExportManager()` | `performMediatedExportConvergence()` | No (Hardcoded in method) |
