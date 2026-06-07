package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.Evaluation;
import eu.kalafatic.evolution.model.orchestration.EvolutionStep;
import eu.kalafatic.evolution.model.orchestration.Mutation;
import eu.kalafatic.evolution.model.orchestration.OrchestrationPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

public class EvolutionStepImpl extends MinimalEObjectImpl.Container implements EvolutionStep {
    protected static final long TIMESTAMP_EDEFAULT = 0L;
    protected long timestamp = TIMESTAMP_EDEFAULT;
    protected Mutation mutation;
    protected EList<Evaluation> evaluations;
    protected Artifact selectedSurvivor;

    protected EvolutionStepImpl() { super(); }
    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.EVOLUTION_STEP; }

    @Override public long getTimestamp() { return timestamp; }
    @Override public void setTimestamp(long newTimestamp) {
        long oldTimestamp = timestamp;
        timestamp = newTimestamp;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVOLUTION_STEP__TIMESTAMP, oldTimestamp, timestamp));
    }

    @Override public Mutation getMutation() { return mutation; }
    public NotificationChain basicSetMutation(Mutation newMutation, NotificationChain msgs) {
        Mutation oldMutation = mutation;
        mutation = newMutation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVOLUTION_STEP__MUTATION, oldMutation, newMutation);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }
    @Override public void setMutation(Mutation newMutation) {
        if (newMutation != mutation) {
            NotificationChain msgs = null;
            if (mutation != null) msgs = ((InternalEObject)mutation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrchestrationPackage.EVOLUTION_STEP__MUTATION, null, msgs);
            if (newMutation != null) msgs = ((InternalEObject)newMutation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrchestrationPackage.EVOLUTION_STEP__MUTATION, null, msgs);
            msgs = basicSetMutation(newMutation, msgs);
            if (msgs != null) msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVOLUTION_STEP__MUTATION, newMutation, newMutation));
    }

    @Override public EList<Evaluation> getEvaluations() {
        if (evaluations == null) {
            evaluations = new EObjectContainmentEList<Evaluation>(Evaluation.class, this, OrchestrationPackage.EVOLUTION_STEP__EVALUATIONS);
        }
        return evaluations;
    }

    @Override public Artifact getSelectedSurvivor() {
        if (selectedSurvivor != null && selectedSurvivor.eIsProxy()) {
            InternalEObject oldSelectedSurvivor = (InternalEObject)selectedSurvivor;
            selectedSurvivor = (Artifact)eResolveProxy(oldSelectedSurvivor);
            if (selectedSurvivor != oldSelectedSurvivor) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, OrchestrationPackage.EVOLUTION_STEP__SELECTED_SURVIVOR, oldSelectedSurvivor, selectedSurvivor));
            }
        }
        return selectedSurvivor;
    }
    public Artifact basicGetSelectedSurvivor() { return selectedSurvivor; }
    @Override public void setSelectedSurvivor(Artifact newSelectedSurvivor) {
        Artifact oldSelectedSurvivor = selectedSurvivor;
        selectedSurvivor = newSelectedSurvivor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.EVOLUTION_STEP__SELECTED_SURVIVOR, oldSelectedSurvivor, selectedSurvivor));
    }

    @Override public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case OrchestrationPackage.EVOLUTION_STEP__MUTATION: return basicSetMutation(null, msgs);
            case OrchestrationPackage.EVOLUTION_STEP__EVALUATIONS: return ((InternalEList<?>)getEvaluations()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.EVOLUTION_STEP__TIMESTAMP: return getTimestamp();
            case OrchestrationPackage.EVOLUTION_STEP__MUTATION: return getMutation();
            case OrchestrationPackage.EVOLUTION_STEP__EVALUATIONS: return getEvaluations();
            case OrchestrationPackage.EVOLUTION_STEP__SELECTED_SURVIVOR:
                if (resolve) return getSelectedSurvivor();
                return basicGetSelectedSurvivor();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @SuppressWarnings("unchecked")
    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.EVOLUTION_STEP__TIMESTAMP: setTimestamp((Long)newValue); return;
            case OrchestrationPackage.EVOLUTION_STEP__MUTATION: setMutation((Mutation)newValue); return;
            case OrchestrationPackage.EVOLUTION_STEP__EVALUATIONS:
                getEvaluations().clear();
                getEvaluations().addAll((Collection<? extends Evaluation>)newValue);
                return;
            case OrchestrationPackage.EVOLUTION_STEP__SELECTED_SURVIVOR: setSelectedSurvivor((Artifact)newValue); return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.EVOLUTION_STEP__TIMESTAMP: setTimestamp(TIMESTAMP_EDEFAULT); return;
            case OrchestrationPackage.EVOLUTION_STEP__MUTATION: setMutation((Mutation)null); return;
            case OrchestrationPackage.EVOLUTION_STEP__EVALUATIONS: getEvaluations().clear(); return;
            case OrchestrationPackage.EVOLUTION_STEP__SELECTED_SURVIVOR: setSelectedSurvivor((Artifact)null); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.EVOLUTION_STEP__TIMESTAMP: return timestamp != TIMESTAMP_EDEFAULT;
            case OrchestrationPackage.EVOLUTION_STEP__MUTATION: return mutation != null;
            case OrchestrationPackage.EVOLUTION_STEP__EVALUATIONS: return evaluations != null && !evaluations.isEmpty();
            case OrchestrationPackage.EVOLUTION_STEP__SELECTED_SURVIVOR: return selectedSurvivor != null;
        }
        return super.eIsSet(featureID);
    }
}
