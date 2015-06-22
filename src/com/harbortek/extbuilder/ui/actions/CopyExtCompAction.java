package com.harbortek.extbuilder.ui.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import com.harbortek.extbuilder.edit.ExtXmlElementTreeEditPart;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class CopyExtCompAction extends SelectionAction {

	public CopyExtCompAction(IWorkbenchPart part) {
		super(part);
		setId(ActionFactory.COPY.getId());
	}

	protected boolean calculateEnabled() {
		return true;
	}

	public void run() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart){
				ExtXmlElementTreeEditPart ep = (ExtXmlElementTreeEditPart)obj;
				ExtElement comp = (ExtElement)ep.getModel();
				Clipboard.getDefault().setContents(comp);
			} 	
		}
	}

}
