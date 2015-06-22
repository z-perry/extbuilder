package com.harbortek.extbuilder.ui.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import com.harbortek.extbuilder.edit.ExtXmlElementTreeEditPart;
import com.harbortek.extbuilder.xmodel.ExtDiagram;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class CutExtCompAction extends SelectionAction {

	public CutExtCompAction(IWorkbenchPart part) {
		super(part);
		setId(ActionFactory.CUT.getId());
	}

	protected boolean calculateEnabled() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart) {
				ExtElement m = (ExtElement) ((ExtXmlElementTreeEditPart) obj).getModel();
				if (m.getParent() instanceof ExtDiagram) {
					return false;
				}
			}
		}
		return true;
	}

	public void run() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart) {
				ExtXmlElementTreeEditPart ep = (ExtXmlElementTreeEditPart) obj;
				ExtElement comp = (ExtElement) ep.getModel();
				Clipboard.getDefault().setContents(comp);
				((ExtXmlElement)comp.getParent()).removeChild(comp);
				comp.setParent(null);
			}
		}
	}

}
