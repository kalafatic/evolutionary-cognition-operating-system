package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.ecore.EObject;
public interface Property extends EObject {
    String getKey();
    void setKey(String value);
    String getValue();
    void setValue(String value);
}
