package com.harbortek.extbuilder.utils.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.harbortek.extbuilder.model.grid.ExtAbstractSelectionModel;
import com.harbortek.extbuilder.model.grid.ExtRowSelectionModel;
import com.harbortek.extbuilder.ui.dialogs.ButtonsPropertiesDialog;
import com.harbortek.extbuilder.ui.dialogs.SelectionModelPropertiesDialog;

public class SelectionModelPropertyDescriptor extends PropertyDescriptor {

	public SelectionModelPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		SelectionModelPropertiesDialogCellEditor editor = new SelectionModelPropertiesDialogCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	private class SelectionModelPropertiesDialogCellEditor extends DialogCellEditor {

		public SelectionModelPropertiesDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			ExtAbstractSelectionModel model = ((ExtAbstractSelectionModel)getValue());
			if (model==null){
				model = new ExtRowSelectionModel();
			}
			 
			SelectionModelPropertiesDialog dialog = new SelectionModelPropertiesDialog(cellEditorWindow.getShell(), model);

			if (dialog.open() == ButtonsPropertiesDialog.OK) {
				model = dialog.getSelectionModel();
			}
			
			return model;
		}
	}

	

}
