package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.Mutation;
import eu.kalafatic.evolution.model.orchestration.OrchestrationPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

public class MutationImpl extends MinimalEObjectImpl.Container implements Mutation {
    protected static final String DESCRIPTION_EDEFAULT = null;
    protected String description = DESCRIPTION_EDEFAULT;
    protected static final String TYPE_EDEFAULT = null;
    protected String type = TYPE_EDEFAULT;

    protected MutationImpl() { super(); }
    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.MUTATION; }

    @Override public String getDescription() { return description; }
    @Override public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.MUTATION__DESCRIPTION, oldDescription, description));
    }

    @Override public String getType() { return type; }
    @Override public void setType(String newType) {
        String oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.MUTATION__TYPE, oldType, type));
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.MUTATION__DESCRIPTION: return getDescription();
            case OrchestrationPackage.MUTATION__TYPE: return getType();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.MUTATION__DESCRIPTION: setDescription((String)newValue); return;
            case OrchestrationPackage.MUTATION__TYPE: setType((String)newValue); return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.MUTATION__DESCRIPTION: setDescription(DESCRIPTION_EDEFAULT); return;
            case OrchestrationPackage.MUTATION__TYPE: setType(TYPE_EDEFAULT); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.MUTATION__DESCRIPTION: return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
            case OrchestrationPackage.MUTATION__TYPE: return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
        }
        return super.eIsSet(featureID);
    }
}
