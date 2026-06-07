package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.ecore.EObject;
public interface Mutation extends EObject {
    String getDescription();
    void setDescription(String value);
    String getType();
    void setType(String value);
}
