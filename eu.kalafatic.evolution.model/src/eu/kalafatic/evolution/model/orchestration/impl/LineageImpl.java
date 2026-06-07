package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.EvolutionStep;
import eu.kalafatic.evolution.model.orchestration.Lineage;
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

public class LineageImpl extends MinimalEObjectImpl.Container implements Lineage {
    protected static final String ID_EDEFAULT = null;
    protected String id = ID_EDEFAULT;
    protected Artifact survivor;
    protected EList<Artifact> candidates;
    protected EList<EvolutionStep> history;

    protected LineageImpl() { super(); }

    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.LINEAGE; }

    @Override public String getId() { return id; }
    @Override public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.LINEAGE__ID, oldId, id));
    }

    @Override public Artifact getSurvivor() {
        if (survivor != null && survivor.eIsProxy()) {
            InternalEObject oldSurvivor = (InternalEObject)survivor;
            survivor = (Artifact)eResolveProxy(oldSurvivor);
            if (survivor != oldSurvivor) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, OrchestrationPackage.LINEAGE__SURVIVOR, oldSurvivor, survivor));
            }
        }
        return survivor;
    }
    public Artifact basicGetSurvivor() { return survivor; }
    @Override public void setSurvivor(Artifact newSurvivor) {
        Artifact oldSurvivor = survivor;
        survivor = newSurvivor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.LINEAGE__SURVIVOR, oldSurvivor, survivor));
    }

    @Override public EList<Artifact> getCandidates() {
        if (candidates == null) {
            candidates = new EObjectContainmentEList<Artifact>(Artifact.class, this, OrchestrationPackage.LINEAGE__CANDIDATES);
        }
        return candidates;
    }

    @Override public EList<EvolutionStep> getHistory() {
        if (history == null) {
            history = new EObjectContainmentEList<EvolutionStep>(EvolutionStep.class, this, OrchestrationPackage.LINEAGE__HISTORY);
        }
        return history;
    }

    @Override public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case OrchestrationPackage.LINEAGE__CANDIDATES:
                return ((InternalEList<?>)getCandidates()).basicRemove(otherEnd, msgs);
            case OrchestrationPackage.LINEAGE__HISTORY:
                return ((InternalEList<?>)getHistory()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.LINEAGE__ID: return getId();
            case OrchestrationPackage.LINEAGE__SURVIVOR:
                if (resolve) return getSurvivor();
                return basicGetSurvivor();
            case OrchestrationPackage.LINEAGE__CANDIDATES: return getCandidates();
            case OrchestrationPackage.LINEAGE__HISTORY: return getHistory();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @SuppressWarnings("unchecked")
    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.LINEAGE__ID: setId((String)newValue); return;
            case OrchestrationPackage.LINEAGE__SURVIVOR: setSurvivor((Artifact)newValue); return;
            case OrchestrationPackage.LINEAGE__CANDIDATES:
                getCandidates().clear();
                getCandidates().addAll((Collection<? extends Artifact>)newValue);
                return;
            case OrchestrationPackage.LINEAGE__HISTORY:
                getHistory().clear();
                getHistory().addAll((Collection<? extends EvolutionStep>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.LINEAGE__ID: setId(ID_EDEFAULT); return;
            case OrchestrationPackage.LINEAGE__SURVIVOR: setSurvivor((Artifact)null); return;
            case OrchestrationPackage.LINEAGE__CANDIDATES: getCandidates().clear(); return;
            case OrchestrationPackage.LINEAGE__HISTORY: getHistory().clear(); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.LINEAGE__ID: return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case OrchestrationPackage.LINEAGE__SURVIVOR: return survivor != null;
            case OrchestrationPackage.LINEAGE__CANDIDATES: return candidates != null && !candidates.isEmpty();
            case OrchestrationPackage.LINEAGE__HISTORY: return history != null && !history.isEmpty();
        }
        return super.eIsSet(featureID);
    }
}
