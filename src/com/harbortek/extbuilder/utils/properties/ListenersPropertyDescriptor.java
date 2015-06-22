package com.harbortek.extbuilder.utils.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.harbortek.extbuilder.model.ExtListener;
import com.harbortek.extbuilder.model.ExtListenerArray;
import com.harbortek.extbuilder.ui.dialogs.ButtonsPropertiesDialog;
import com.harbortek.extbuilder.ui.dialogs.ListenersPropertiesDialog;

public class ListenersPropertyDescriptor extends PropertyDescriptor {

	public ListenersPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		ListenerPropertiesDialogCellEditor editor = new ListenerPropertiesDialogCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	private class ListenerPropertiesDialogCellEditor extends DialogCellEditor {

		public ListenerPropertiesDialogCellEditor(Composite parent) {
			super(parent);
		}

		protected Object openDialogBox(Control cellEditorWindow) {
			
			List listeners = null;
			ExtListenerArray listenerArray = (ExtListenerArray) getValue();
			if (listenerArray!=null){
				listeners = listenerArray.getPredefinedListeners();
			}else{
				listeners  = new ArrayList();
			}
			 
			ListenersPropertiesDialog dialog = new ListenersPropertiesDialog(cellEditorWindow.getShell(),
					(ArrayList)listeners);

			if (dialog.open() == ButtonsPropertiesDialog.OK) {
				listeners = dialog.getListeners();
				listenerArray = new ExtListenerArray(listenerArray.getExtClassId());
				for (Iterator iter = listeners.iterator(); iter.hasNext();) {
					ExtListener listener = (ExtListener) iter.next();
					if (StringUtils.isEmpty(listener.getFunctionName())){
						iter.remove();
					}
				}
				
				listenerArray.setListeners(listeners);
				return listenerArray;
			}
			return listenerArray;
			
		}
	}

}
