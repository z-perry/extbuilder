package com.harbortek.extbuilder.model.grid;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtRowSelectionModel extends ExtAbstractSelectionModel {
	private static final long serialVersionUID = 1L;

	private Boolean singleSelect = Boolean.FALSE;
	

	public Boolean getSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(Boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public String getExtClassName() {
		return "Ext.grid.RowSelectionModel";
	}

	public Object translate(CodeContext codeContext) {
		Map result = (Map) new HashMap();
		
		if (getSingleSelect().booleanValue()){
			result.put("singleSelect", getSingleSelect());
		}
		
		return result;
	}

	public String getXtype() {
		return null;
	}

	public Object getEditableValue() {
		return this;
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
	
	public String toString(){
		return "RowSelectionModel";
	}
	
	
	
	
}
