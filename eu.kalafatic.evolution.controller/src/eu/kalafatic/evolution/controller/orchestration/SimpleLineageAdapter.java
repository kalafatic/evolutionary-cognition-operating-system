package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * Basic implementation of ECOS Lineage.
 */
public class SimpleLineageAdapter extends MinimalEObjectImpl.Container implements Lineage {
    private String id;
    private Artifact survivor;
    private final EList<Artifact> candidates = new BasicEList<>();
    private final EList<EvolutionStep> history = new BasicEList<>();

    public SimpleLineageAdapter(String id) {
        this.id = id;
    }

    @Override
    public String getId() { return id; }
    @Override
    public void setId(String value) { this.id = value; }
    @Override
    public Artifact getSurvivor() { return survivor; }
    @Override
    public void setSurvivor(Artifact value) { this.survivor = value; }
    @Override
    public EList<Artifact> getCandidates() { return candidates; }
    @Override
    public EList<EvolutionStep> getHistory() { return history; }
}
