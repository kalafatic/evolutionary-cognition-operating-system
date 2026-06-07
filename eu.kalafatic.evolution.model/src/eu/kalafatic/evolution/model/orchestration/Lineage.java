package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
public interface Lineage extends EObject {
    String getId();
    void setId(String value);
    Artifact getSurvivor();
    void setSurvivor(Artifact value);
    EList<Artifact> getCandidates();
    EList<EvolutionStep> getHistory();
}
