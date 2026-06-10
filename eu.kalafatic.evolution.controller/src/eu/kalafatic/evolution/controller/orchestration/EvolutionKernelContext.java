package eu.kalafatic.evolution.controller.orchestration;

import java.io.File;

import eu.kalafatic.evolution.controller.orchestration.selfdev.IMemoryProvider;
import eu.kalafatic.evolution.controller.supervision.AuthorityController;
import eu.kalafatic.evolution.controller.trajectory.SignalBus;
import eu.kalafatic.evolution.controller.workflow.RuntimeEventBus;

/**
 * Shared container for kernel-scoped services.
 * Ensures that all components in a single evolution cycle share the same
 * authority, memory, and audit trail instances.
 */
public class EvolutionKernelContext {
    private final AuthorityController authority;
    private IMemoryProvider memoryService;
    private final File projectRoot;
    private final RuntimeEventBus eventBus;
    private final SignalBus signalBus;

    public EvolutionKernelContext(File projectRoot, RuntimeEventBus eventBus, SignalBus signalBus, IMemoryProvider memoryService) {
        this.projectRoot = projectRoot;
        this.eventBus = eventBus;
        this.signalBus = signalBus;
        this.memoryService = memoryService != null ? memoryService : new eu.kalafatic.evolution.controller.orchestration.selfdev.IterationMemoryService(projectRoot);
        this.authority = new AuthorityController();
    }

    public AuthorityController getAuthority() {
        return authority;
    }

    public RuntimeEventBus getEventBus() {
        return eventBus;
    }

    public SignalBus getSignalBus() {
        return signalBus;
    }

    public IMemoryProvider getMemoryService() {
        return memoryService;
    }

    public void setMemoryService(IMemoryProvider memoryService) {
        this.memoryService = memoryService;
    }

    public File getProjectRoot() {
        return projectRoot;
    }
}
