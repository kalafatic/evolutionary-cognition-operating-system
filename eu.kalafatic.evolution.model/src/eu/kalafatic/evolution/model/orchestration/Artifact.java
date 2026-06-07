package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
public interface Artifact extends EObject {
    String getId();
    void setId(String value);
    String getType();
    void setType(String value);
    String getContent();
    void setContent(String value);
    EList<Property> getProperties();
}
