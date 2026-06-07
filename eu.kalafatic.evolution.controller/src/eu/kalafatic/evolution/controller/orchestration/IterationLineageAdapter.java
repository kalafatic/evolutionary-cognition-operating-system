package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.EvolutionStep;
import eu.kalafatic.evolution.model.orchestration.Iteration;
import eu.kalafatic.evolution.model.orchestration.Lineage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * Adapter that presents a legacy Iteration as an ECOS Lineage.
 * This implements Phase C (Adapter Introduction) of the ECOS roadmap.
 */
public class IterationLineageAdapter extends MinimalEObjectImpl.Container implements Lineage {
    private final Iteration iteration;
    private final EList<Artifact> candidates = new BasicEList<>();
    private final EList<EvolutionStep> history = new BasicEList<>();

    public IterationLineageAdapter(Iteration iteration) {
        this.iteration = iteration;
        // Map tasks to candidates
        iteration.getTasks().forEach(t -> candidates.add(new TaskArtifactAdapter(t)));
    }

    @Override public String getId() { return iteration.getId(); }
    @Override public void setId(String value) { iteration.setId(value); }

    @Override public Artifact getSurvivor() {
        // In the legacy model, the last task often represents the current state
        if (candidates.isEmpty()) return null;
        return candidates.get(candidates.size() - 1);
    }
    @Override public void setSurvivor(Artifact value) { /* Read-only mapping */ }

    @Override public EList<Artifact> getCandidates() { return candidates; }
    @Override public EList<EvolutionStep> getHistory() { return history; }
}
