package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * Adapter that wraps a String prompt as an ECOS Artifact.
 */
public class PromptArtifactAdapter extends MinimalEObjectImpl.Container implements Artifact {
    private String content;
    private final String id;
    private final EList<Property> properties = new BasicEList<>();

    public PromptArtifactAdapter(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String getId() { return id; }
    @Override
    public void setId(String value) { /* Read only */ }
    @Override
    public String getType() { return "PROMPT"; }
    @Override
    public void setType(String value) { }
    @Override
    public String getContent() { return content; }
    @Override
    public void setContent(String value) { this.content = value; }
    @Override
    public EList<Property> getProperties() { return properties; }
}
