package com.harbortek.extbuilder.ui.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import com.harbortek.extbuilder.edit.ExtXmlElementTreeEditPart;
import com.harbortek.extbuilder.utils.XComponentUtils;
import com.harbortek.extbuilder.xmodel.ExtDiagram;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class PasteExtCompAction extends SelectionAction {

	public PasteExtCompAction(IWorkbenchPart part) {
		super(part);
		setId(ActionFactory.PASTE.getId());
	}

	protected boolean calculateEnabled() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart) {

				Object content = Clipboard.getDefault().getContents();
				if (content instanceof ExtElement) {
					return true;
				}
			}
		}
		return false;
	}

	public void run() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart) {
				ExtXmlElement ct = (ExtXmlElement) ((ExtXmlElementTreeEditPart) obj)
						.getModel();
				Object content = Clipboard.getDefault().getContents();
				if (content instanceof ExtElement) {
					ExtElement comp = (ExtElement) content;
					comp = (ExtElement) XComponentUtils.cloneComponent(comp);

					// set component names
					ExtDiagram diagram = XComponentUtils.getDiagram(ct);
					Collection comps = XComponentUtils.getComponents(comp);
					for (Iterator iter = comps.iterator(); iter.hasNext();) {
						ExtElement c = (ExtElement) iter.next();
						if (c.isUseVariableName()) {
							c.setVariableName(XComponentUtils.generateName(
									diagram, c));
						}
					}
					if (comp.isUseVariableName()) {
						comp.setVariableName(XComponentUtils.generateName(
								diagram, comp));
					}

					ct.addChild(comp);
				}
			}
		}
	}

}
