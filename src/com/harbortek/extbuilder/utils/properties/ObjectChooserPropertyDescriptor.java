package com.harbortek.extbuilder.utils.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class ObjectChooserPropertyDescriptor extends StandardComboBoxPropertyDescriptor {
	private Class claz;
	
	public ObjectChooserPropertyDescriptor(Object id, String displayName,Class claz) {
		super(id, displayName);
		this.claz = claz;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		String[] items = new String[]{};
		
//		ExtComponentEditPart editPart = (ExtComponentEditPart)((PropertySheetEntry)parent.getData()).getValues()[0];
//		items = ComponentUtils.findObjectName((ExtComponent)editPart.getModel(), claz);
		
		CellEditor editor = new StandardComboBoxCellEditor(parent, items, items);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}
}
