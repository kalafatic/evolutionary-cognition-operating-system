package eu.kalafatic.evolution.controller.orchestration;

/**
 * Deterministic atomic execution flow for simple, high-confidence tasks.
 */
public class AtomicFlow implements IOrchestrationFlow {
    private final AiService aiService;
    private final IterationManager manager;

    public AtomicFlow(AiService aiService, IterationManager manager) {
        this.aiService = aiService;
        this.manager = manager;
    }

    @Override
    public OrchestratorResponse execute(String request, TaskContext context) throws Exception {
        context.log("[KERNEL] Executing Atomic Flow (Delegating to DarwinFlow).");
        return new DarwinFlow(aiService, manager).execute(request, context);
    }
}
