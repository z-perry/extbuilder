package com.harbortek.extbuilder.utils.properties;

import java.util.ArrayList;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.harbortek.extbuilder.model.button.ExtToolbar;
import com.harbortek.extbuilder.ui.dialogs.ButtonsPropertiesDialog;
import com.harbortek.extbuilder.ui.dialogs.ToolbarButtonsPropertiesDialog;

public class ToolbarButtonsPropertyDescriptor extends PropertyDescriptor {

	public ToolbarButtonsPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ToolbarPropertiesDialogCellEditor editor = new ToolbarPropertiesDialogCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	private class ToolbarPropertiesDialogCellEditor extends DialogCellEditor {

		public ToolbarPropertiesDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			
			ArrayList buttons = null;
			ExtToolbar toolbar = (ExtToolbar) getValue();
			if (toolbar!=null){
				buttons = toolbar.getButtons();	
			}else{
				buttons  = new ArrayList();
			}
			 
			ToolbarButtonsPropertiesDialog dialog = new ToolbarButtonsPropertiesDialog(cellEditorWindow.getShell(),
					buttons);

			if (dialog.open() == ButtonsPropertiesDialog.OK) {
				buttons = dialog.getButtons();
			}
			if (buttons.isEmpty()){
				return null;
			}
			toolbar = new ExtToolbar();
			toolbar.setButtons(buttons);
			return toolbar;
		}
	}

}
