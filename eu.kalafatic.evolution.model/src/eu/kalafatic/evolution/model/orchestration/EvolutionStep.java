package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
public interface EvolutionStep extends EObject {
    long getTimestamp();
    void setTimestamp(long value);
    Mutation getMutation();
    void setMutation(Mutation value);
    EList<Evaluation> getEvaluations();
    Artifact getSelectedSurvivor();
    void setSelectedSurvivor(Artifact value);
}
