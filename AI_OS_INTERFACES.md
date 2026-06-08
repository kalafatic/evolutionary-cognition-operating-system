# ECOS AI OS CORE INTERFACES

This document defines the clean interface layer for ECOS, abstracting core subsystems from the OS runtime.

## 1. ILlmProvider
**Responsibility:** Stateless communication with Large Language Models.

### Method Signatures
```java
String sendRequest(String prompt, LlmOptions options) throws LlmException;
LlmCapabilities getCapabilities();
boolean testConnection();
```

### Input/Output Contracts
- **Input:** `prompt` (String), `options` (LlmOptions containing temperature, model, maxTokens, stopSequences).
- **Output:** `String` (The raw textual response from the model).
- **Exceptions:** `LlmException` on connectivity or provider-side failures.

---

## 2. IAgent
**Responsibility:** High-level cognitive processing and task execution within a specific domain.

### Method Signatures
```java
AgentResponse execute(AgentRequest request, SessionContext context) throws AgentException;
String getType();
Set<String> getRequiredTools();
```

### Input/Output Contracts
- **Input:** `request` (Goal and context), `context` (Current session state and allowed tools).
- **Output:** `AgentResponse` (Outcome, reasoning, and proposed actions/changes).

---

## 3. ITool
**Responsibility:** Side-effectful interaction with the physical environment (filesystem, shell, build systems).

### Method Signatures
```java
ToolResult execute(String command, File workingDir, TaskContext context) throws ToolException;
String getName();
ToolDefinition getDefinition(); // Metadata about parameters and capabilities
```

### Input/Output Contracts
- **Input:** `command` (Operation-specific instruction), `workingDir` (Root directory for execution).
- **Output:** `ToolResult` (Captured stdout/stderr, exit code, and change metadata).

---

## 4. IMemoryProvider
**Responsibility:** Persistence and retrieval of evolutionary artifacts, lineages, and cognitive history.

### Method Signatures
```java
void store(Artifact artifact) throws MemoryException;
Artifact retrieve(String artifactId) throws MemoryException;
List<Artifact> query(MemoryQuery query);
void checkpoint(String sessionId, StateSnapshot snapshot);
```

### Input/Output Contracts
- **Input:** `Artifact` (Generic evolutionary unit), `MemoryQuery` (Filter criteria for retrieval).
- **Output:** `Artifact` or `List<Artifact>`.

---

## 5. IEvolutionEngine
**Responsibility:** Orchestration of the Darwinian iterative loop (Analyze → Mutate → Evaluate → Select).

### Method Signatures
```java
EvolutionResult evolve(Artifact parent, PressureVector pressure, Environment env) throws EvolutionException;
List<Artifact> generateVariants(Artifact parent, MutationStrategy strategy);
SelectionResult select(List<Artifact> candidates, Evaluator evaluator);
```

### Input/Output Contracts
- **Input:** `parent` (Surviving artifact from last generation), `pressure` (Target optimization goals).
- **Output:** `EvolutionResult` (The winning artifact and lineage metadata).

---

## 6. IRepositoryProvider
**Responsibility:** Abstraction of workspace state, file operations, and version control.

### Method Signatures
```java
WorkspaceSnapshot getSnapshot() throws RepositoryException;
void applyPatch(String patch) throws RepositoryException;
String commit(String message) throws RepositoryException;
String getDiff(String baseRef, String targetRef) throws RepositoryException;
void rollback() throws RepositoryException;
```

### Input/Output Contracts
- **Input:** `patch` (Diff or change description), `message` (Commit log).
- **Output:** `WorkspaceSnapshot` (Current file tree and metadata), `String` (Commit ID or Diff).
