package com.harbortek.extbuilder.xmodel;

import org.eclipse.ui.views.properties.IPropertyDescriptor;


public abstract class ExtPredefinedElement extends ExtElement {
	private static final long serialVersionUID = 1L;

	private ExtXmlElement host;

	public ExtXmlElement getHost() {
		return host;
	}

	public void setHost(ExtXmlElement host) {
		this.host = host;
	}
	
	public void firePropertyChange(String prop, Object old, Object newValue) {
		host.firePropertyChange(prop, old, newValue);
	}
	
	
	public String getXtype() {
		return null;
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return null;
	}

	public Object getPropertyValue(Object id) {
		return null;
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
		
	}

	public void setPropertyValue(Object id, Object value) {
		
	}

}
