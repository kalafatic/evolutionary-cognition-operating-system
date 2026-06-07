package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.OrchestrationPackage;
import eu.kalafatic.evolution.model.orchestration.Pressure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

public class PressureImpl extends MinimalEObjectImpl.Container implements Pressure {
    protected static final String NAME_EDEFAULT = null;
    protected String name = NAME_EDEFAULT;
    protected static final String DESCRIPTION_EDEFAULT = null;
    protected String description = DESCRIPTION_EDEFAULT;

    protected PressureImpl() { super(); }
    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.PRESSURE; }

    @Override public String getName() { return name; }
    @Override public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.PRESSURE__NAME, oldName, name));
    }

    @Override public String getDescription() { return description; }
    @Override public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.PRESSURE__DESCRIPTION, oldDescription, description));
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.PRESSURE__NAME: return getName();
            case OrchestrationPackage.PRESSURE__DESCRIPTION: return getDescription();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.PRESSURE__NAME: setName((String)newValue); return;
            case OrchestrationPackage.PRESSURE__DESCRIPTION: setDescription((String)newValue); return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.PRESSURE__NAME: setName(NAME_EDEFAULT); return;
            case OrchestrationPackage.PRESSURE__DESCRIPTION: setDescription(DESCRIPTION_EDEFAULT); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.PRESSURE__NAME: return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case OrchestrationPackage.PRESSURE__DESCRIPTION: return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
        }
        return super.eIsSet(featureID);
    }
}
