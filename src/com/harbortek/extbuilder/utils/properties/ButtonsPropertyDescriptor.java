package com.harbortek.extbuilder.utils.properties;

import java.util.ArrayList;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.harbortek.extbuilder.model.button.ExtButtonArray;
import com.harbortek.extbuilder.ui.dialogs.ButtonsPropertiesDialog;

public class ButtonsPropertyDescriptor extends PropertyDescriptor {

	public ButtonsPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ButtonsPropertiesDialogCellEditor editor = new ButtonsPropertiesDialogCellEditor(
				parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	private class ButtonsPropertiesDialogCellEditor extends DialogCellEditor {

		public ButtonsPropertiesDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected Object openDialogBox(Control cellEditorWindow) {

			ArrayList buttons = null;
			ExtButtonArray array = (ExtButtonArray) getValue();
			if (array != null) {
				buttons = (ArrayList) array.getButtons();
			} else {
				buttons = new ArrayList();
			}
			ButtonsPropertiesDialog dialog = new ButtonsPropertiesDialog(
					cellEditorWindow.getShell(), buttons);

			if (dialog.open() == ButtonsPropertiesDialog.OK) {
				buttons = dialog.getButtons();
			}

			if (buttons != null && buttons.size() > 0) {
				ExtButtonArray extButtonArray = new ExtButtonArray();
				extButtonArray.setButtons(buttons);
				return extButtonArray;
			} else {
				return null;
			}
		}
	}

}
