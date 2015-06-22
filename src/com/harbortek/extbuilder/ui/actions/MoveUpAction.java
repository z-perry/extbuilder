package com.harbortek.extbuilder.ui.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.edit.ExtXmlElementTreeEditPart;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class MoveUpAction extends SelectionAction {

	public static String MOVE_UP = "MOVE_UP";

	public MoveUpAction(IWorkbenchPart part) {
		super(part);
		setId(MOVE_UP);
		setImageDescriptor(ImageDescriptor.createFromFile(
				ExtBuilderActivator.class, "icons/up.gif"));
	}

	protected boolean calculateEnabled() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart) {
				ExtXmlElement m = (ExtXmlElement) ((ExtXmlElementTreeEditPart) obj)
						.getModel();
				if (m.getParent() != null) {
					int index = ((ExtXmlElement) m.getParent()).getItems()
							.indexOf(m);
					if (index > 0) {
						return true;
					}
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
				ExtXmlElement m = (ExtXmlElement) ((ExtXmlElementTreeEditPart) obj)
						.getModel();
				if (m.getParent() != null) {
					ExtXmlElement p = (ExtXmlElement) m.getParent();
					int index = p.getItems().indexOf(m);
					if (index > 0) {
						p.removeChild(m);
						m.setParent(null);

						p.addChild(m, index - 1);

						// m.getParent().getItems().set(index-1, m);
						// m.getParent().getItems().set(index, old);
						// m.getParent().firePropertyChange("CHILD_ADD", null,
						// m);
					}
				}
			}
		}
	}

}
