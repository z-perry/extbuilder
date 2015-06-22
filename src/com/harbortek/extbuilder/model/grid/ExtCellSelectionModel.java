package com.harbortek.extbuilder.model.grid;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtCellSelectionModel extends ExtAbstractSelectionModel {
	private static final long serialVersionUID = 1L;

	public String getExtClassName() {
		return "Ext.grid.CellSelectionModel";
	}

	public String getXtype() {
		return null;
	}

	public Object translate(CodeContext codeContext) {
		return null;
	}

	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		return "CellSelectionModel";
	}

}
