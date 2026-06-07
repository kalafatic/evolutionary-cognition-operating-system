package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.ecore.EObject;
public interface Evaluation extends EObject {
    Pressure getPressure();
    void setPressure(Pressure value);
    double getScore();
    void setScore(double value);
    String getComment();
    void setComment(String value);
}
