package com.harbortek.extbuilder.utils.properties;

import java.util.ArrayList;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.harbortek.extbuilder.model.grid.ExtColumnModel;
import com.harbortek.extbuilder.ui.dialogs.ButtonsPropertiesDialog;
import com.harbortek.extbuilder.ui.dialogs.ColumnsPropertiesDialog;

public class ColumnsPropertyDescriptor extends PropertyDescriptor {

	public ColumnsPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ColumnsPropertiesDialogCellEditor editor = new ColumnsPropertiesDialogCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	private class ColumnsPropertiesDialogCellEditor extends DialogCellEditor {

		public ColumnsPropertiesDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			ExtColumnModel model = ((ExtColumnModel)getValue());
			ArrayList columns = null;
			if (model!=null){
				columns = (ArrayList)model.getColumns();
			}else{
				columns = new ArrayList();
			}
			 
			ColumnsPropertiesDialog dialog = new ColumnsPropertiesDialog(cellEditorWindow.getShell(), columns);

			if (dialog.open() == ButtonsPropertiesDialog.OK) {
				columns = dialog.getColumns();
				if (columns!=null && columns.size()>0){
					model = new ExtColumnModel();
					model.setColumns(columns);

					return model;
				}
			}
			
			return model;
		}
	}

	

}
