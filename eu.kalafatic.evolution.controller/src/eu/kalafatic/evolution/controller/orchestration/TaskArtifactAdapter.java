package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.Property;
import eu.kalafatic.evolution.model.orchestration.Task;
import eu.kalafatic.evolution.model.orchestration.OrchestrationFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * Adapter that presents a legacy Task as an ECOS Artifact.
 * This implements Phase C (Adapter Introduction) of the ECOS roadmap.
 */
public class TaskArtifactAdapter extends MinimalEObjectImpl.Container implements Artifact {
    private final Task task;

    public TaskArtifactAdapter(Task task) {
        this.task = task;
    }

    @Override public String getId() { return task.getId(); }
    @Override public void setId(String value) { task.setId(value); }

    @Override public String getType() { return task.getType(); }
    @Override public void setType(String value) { task.setType(value); }

    @Override public String getContent() { return task.getDescription(); }
    @Override public void setContent(String value) { task.setDescription(value); }

    @Override
    public EList<Property> getProperties() {
        // Return a proxy to task attributes if needed, or an empty list for now
        return new org.eclipse.emf.common.util.BasicEList<Property>();
    }
}
