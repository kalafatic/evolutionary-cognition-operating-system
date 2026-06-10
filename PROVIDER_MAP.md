# PROVIDER MAP

This document maps current implementations to conceptual Evolutionary OS (ECOS) categories.

## LLM Provider
*Conceptual Role: Abstract interface for token generation and reasoning requests.*

*   **OpenAIProvider**: Remote LLM via OpenAI API (deepseek, gpt-4, etc.)
*   **OllamaProvider**: Local LLM via Ollama API (llama3, etc.)
*   **GeminiProvider**: Remote LLM via Google Gemini API

## Agent System
*Conceptual Role: Specialized cognitive workers with distinct personas and toolsets.*

*   **AnalyticAgent**: Problem diagnosis and root cause analysis
*   **ArchitectAgent**: Structural design and architectural review
*   **JavaDevAgent / CppDevAgent**: Implementation and code generation
*   **TesterAgent**: Test generation and execution
*   **ValidatorAgent**: Verification of changes against goals
*   **PlannerAgent**: Strategic trajectory and task planning
*   **CriticAgent**: Peer review and strategy refinement
*   **MetadataAgent**: Repository cognition and structural analysis

## Tool System
*Conceptual Role: Physical effectors for interacting with the environment (FS, Shell, Build).*

*   **FileTool**: File system CRUD operations
*   **MavenTool**: Java build and test execution
*   **GitTool**: Version control operations (commit, diff, branch)
*   **ShellTool**: Arbitrary command execution
*   **EclipseTool**: Integration with Eclipse IDE features
*   **CppTool**: C++ build environment interaction
*   **DatabaseTool**: SQL and data schema operations

## Memory System
*Conceptual Role: Persistence of trajectories, checkpoints, and semantic workspace.*

*   **IterationMemoryService**: Storage for iteration records and checkpoints
*   **SemanticWorkspace**: Vector-like storage for artifacts and trajectory memory
*   **EvolutionMemoryGraph**: Entropy and lineage tracking
*   **ConversationState**: Persistence of chat history and requirements

## Evolution System
*Conceptual Role: Evolutionary cognitive transition logic (Phase, Mutation, Selection).*

*   **DarwinEngine**: Variant proposal generation and survival analysis
*   **EvolutionaryTrajectoryEngine**: Recursive mutation and pressure adaptation
*   **DefaultPhaseEngine**: Evolution phase state transitions
*   **DefaultMutationEngine**: Proposal mutation logic
*   **DefaultFitnessEngine**: Variant scoring and ranking
*   **DefaultAuthorityEngine**: Winner selection and lifecycle management

## Repository System
*Conceptual Role: Abstraction for version control and workspace management.*

*   **GitVersionControlProvider**: Implementation of VCS via Git
*   **TargetScanner**: Structural repository scanning
*   **WorkspaceArtifact**: Semantic representation of repository entities
