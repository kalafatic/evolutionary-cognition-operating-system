package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.OrchestrationPackage;
import eu.kalafatic.evolution.model.orchestration.Property;

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

public class ArtifactImpl extends MinimalEObjectImpl.Container implements Artifact {
    protected static final String ID_EDEFAULT = null;
    protected String id = ID_EDEFAULT;
    protected static final String TYPE_EDEFAULT = null;
    protected String type = TYPE_EDEFAULT;
    protected static final String CONTENT_EDEFAULT = null;
    protected String content = CONTENT_EDEFAULT;
    protected EList<Property> properties;

    protected ArtifactImpl() { super(); }

    @Override protected EClass eStaticClass() { return OrchestrationPackage.Literals.ARTIFACT; }

    @Override public String getId() { return id; }
    @Override public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.ARTIFACT__ID, oldId, id));
    }

    @Override public String getType() { return type; }
    @Override public void setType(String newType) {
        String oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.ARTIFACT__TYPE, oldType, type));
    }

    @Override public String getContent() { return content; }
    @Override public void setContent(String newContent) {
        String oldContent = content;
        content = newContent;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrchestrationPackage.ARTIFACT__CONTENT, oldContent, content));
    }

    @Override public EList<Property> getProperties() {
        if (properties == null) {
            properties = new EObjectContainmentEList<Property>(Property.class, this, OrchestrationPackage.ARTIFACT__PROPERTIES);
        }
        return properties;
    }

    @Override public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case OrchestrationPackage.ARTIFACT__PROPERTIES:
                return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    @Override public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrchestrationPackage.ARTIFACT__ID: return getId();
            case OrchestrationPackage.ARTIFACT__TYPE: return getType();
            case OrchestrationPackage.ARTIFACT__CONTENT: return getContent();
            case OrchestrationPackage.ARTIFACT__PROPERTIES: return getProperties();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    @SuppressWarnings("unchecked")
    @Override public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OrchestrationPackage.ARTIFACT__ID: setId((String)newValue); return;
            case OrchestrationPackage.ARTIFACT__TYPE: setType((String)newValue); return;
            case OrchestrationPackage.ARTIFACT__CONTENT: setContent((String)newValue); return;
            case OrchestrationPackage.ARTIFACT__PROPERTIES:
                getProperties().clear();
                getProperties().addAll((Collection<? extends Property>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    @Override public void eUnset(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.ARTIFACT__ID: setId(ID_EDEFAULT); return;
            case OrchestrationPackage.ARTIFACT__TYPE: setType(TYPE_EDEFAULT); return;
            case OrchestrationPackage.ARTIFACT__CONTENT: setContent(CONTENT_EDEFAULT); return;
            case OrchestrationPackage.ARTIFACT__PROPERTIES: getProperties().clear(); return;
        }
        super.eUnset(featureID);
    }

    @Override public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OrchestrationPackage.ARTIFACT__ID: return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case OrchestrationPackage.ARTIFACT__TYPE: return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
            case OrchestrationPackage.ARTIFACT__CONTENT: return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
            case OrchestrationPackage.ARTIFACT__PROPERTIES: return properties != null && !properties.isEmpty();
        }
        return super.eIsSet(featureID);
    }
}
