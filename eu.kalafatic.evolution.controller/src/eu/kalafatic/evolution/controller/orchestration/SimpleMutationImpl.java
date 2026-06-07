package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.*;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * Basic implementation of ECOS Mutation.
 */
public class SimpleMutationImpl extends MinimalEObjectImpl.Container implements Mutation {
    private String description;
    private String type;

    public SimpleMutationImpl(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String getDescription() { return description; }
    @Override
    public void setDescription(String value) { this.description = value; }
    @Override
    public String getType() { return type; }
    @Override
    public void setType(String value) { this.type = value; }
}
