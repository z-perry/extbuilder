package com.harbortek.extbuilder.utils.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.harbortek.extbuilder.model.data.ExtDataStore;
import com.harbortek.extbuilder.ui.dialogs.ButtonsPropertiesDialog;
import com.harbortek.extbuilder.ui.dialogs.StorePropertiesDialog;

public class DataStorePropertyDescriptor extends PropertyDescriptor {

	public DataStorePropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		DataStorePropertiesDialogCellEditor editor = new DataStorePropertiesDialogCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	private class DataStorePropertiesDialogCellEditor extends DialogCellEditor {

		public DataStorePropertiesDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			ExtDataStore store = (ExtDataStore)getValue();
			StorePropertiesDialog dialog = new StorePropertiesDialog(cellEditorWindow.getShell());
			dialog.setStore(store);
			if (dialog.open() == ButtonsPropertiesDialog.OK) {
				store = dialog.getStore();
			}
			return store;
		}
	}
}
