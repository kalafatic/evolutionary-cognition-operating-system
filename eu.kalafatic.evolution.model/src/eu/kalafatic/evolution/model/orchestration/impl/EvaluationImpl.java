package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.Evaluation;
import eu.kalafatic.evolution.model.orchestration.OrchestrationPackage;
import eu.kalafatic.evolution.model.orchestration.Pressure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

public class EvaluationImpl extends MinimalEObjectImpl.Container implements Evaluation {
    protected Pressure pressure;
    protected static final double SCORE_EDEFAULT = 0.0;
    protected double score = SCORE_EDEFAULT;
    protected static final String COMMENT_EDEFAULT = null;
    protected String comment = COMMENT_EDEFAULT;

    protected EvaluationImpl() { super(); }
    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.EVALUATION; }

    @Override public Pressure getPressure() {
        if (pressure != null && pressure.eIsProxy()) {
            InternalEObject oldPressure = (InternalEObject)pressure;
            pressure = (Pressure)eResolveProxy(oldPressure);
            if (pressure != oldPressure) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, OrchestrationPackage.EVALUATION__PRESSURE, oldPressure, pressure));
            }
        }
        return pressure;
    }
    public Pressure basicGetPressure() { return pressure; }
    @Override public void setPressure(Pressure newPressure) {
        Pressure oldPressure = pressure;
        pressure = newPressure;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVALUATION__PRESSURE, oldPressure, pressure));
    }

    @Override public double getScore() { return score; }
    @Override public void setScore(double newScore) {
        double oldScore = score;
        score = newScore;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVALUATION__SCORE, oldScore, score));
    }

    @Override public String getComment() { return comment; }
    @Override public void setComment(String newComment) {
        String oldComment = comment;
        comment = newComment;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVALUATION__COMMENT, oldComment, comment));
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.EVALUATION__PRESSURE:
                if (resolve) return getPressure();
                return basicGetPressure();
            case OrchestrationPackage.EVALUATION__SCORE: return getScore();
            case OrchestrationPackage.EVALUATION__COMMENT: return getComment();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.EVALUATION__PRESSURE: setPressure((Pressure)newValue); return;
            case OrchestrationPackage.EVALUATION__SCORE: setScore((Double)newValue); return;
            case OrchestrationPackage.EVALUATION__COMMENT: setComment((String)newValue); return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.EVALUATION__PRESSURE: setPressure((Pressure)null); return;
            case OrchestrationPackage.EVALUATION__SCORE: setScore(SCORE_EDEFAULT); return;
            case OrchestrationPackage.EVALUATION__COMMENT: setComment(COMMENT_EDEFAULT); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.EVALUATION__PRESSURE: return pressure != null;
            case OrchestrationPackage.EVALUATION__SCORE: return score != SCORE_EDEFAULT;
            case OrchestrationPackage.EVALUATION__COMMENT: return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
        }
        return super.eIsSet(featureID);
    }
}
