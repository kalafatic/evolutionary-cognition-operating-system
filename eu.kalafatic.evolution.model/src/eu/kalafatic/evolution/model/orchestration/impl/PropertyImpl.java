package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.OrchestrationPackage;
import eu.kalafatic.evolution.model.orchestration.Property;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

public class PropertyImpl extends MinimalEObjectImpl.Container implements Property {
    protected static final String KEY_EDEFAULT = null;
    protected String key = KEY_EDEFAULT;
    protected static final String VALUE_EDEFAULT = null;
    protected String value = VALUE_EDEFAULT;

    protected PropertyImpl() { super(); }
    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.PROPERTY; }

    @Override public String getKey() { return key; }
    @Override public void setKey(String newKey) {
        String oldKey = key;
        key = newKey;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.PROPERTY__KEY, oldKey, key));
    }

    @Override public String getValue() { return value; }
    @Override public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.PROPERTY__VALUE, oldValue, value));
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.PROPERTY__KEY: return getKey();
            case OrchestrationPackage.PROPERTY__VALUE: return getValue();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.PROPERTY__KEY: setKey((String)newValue); return;
            case OrchestrationPackage.PROPERTY__VALUE: setValue((String)newValue); return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.PROPERTY__KEY: setKey(KEY_EDEFAULT); return;
            case OrchestrationPackage.PROPERTY__VALUE: setValue(VALUE_EDEFAULT); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.PROPERTY__KEY: return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
            case OrchestrationPackage.PROPERTY__VALUE: return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
        }
        return super.eIsSet(featureID);
    }
}
