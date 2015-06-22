package com.harbortek.extbuilder.ui.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.edit.ExtXmlElementTreeEditPart;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class MoveDownAction extends SelectionAction {
	public static String MOVE_DOWN = "MOVE_DOWN";
	
	public MoveDownAction(IWorkbenchPart part) {
		super(part);
		setId(MOVE_DOWN);
		setImageDescriptor(ImageDescriptor.createFromFile(ExtBuilderActivator.class, "icons/down.gif"));
	}

	protected boolean calculateEnabled() {
		List list = getSelectedObjects();
		Object obj = null;
		if (!list.isEmpty()) {
			obj = list.iterator().next();
			if (obj instanceof ExtXmlElementTreeEditPart) {
				ExtXmlElement m = (ExtXmlElement) ((ExtXmlElementTreeEditPart) obj).getModel();
				if (m.getParent()!=null){
					int size = ((ExtXmlElement)m.getParent()).getItems().size();
					int index = ((ExtXmlElement)m.getParent()).getItems().indexOf(m);
					if (index<size-1){
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
				ExtElement m = (ExtElement) ((ExtXmlElementTreeEditPart) obj).getModel();
				if (m.getParent()!=null){
					ExtXmlElement p = (ExtXmlElement)m.getParent();
					int size = p.getItems().size();
					int index =p.getItems().indexOf(m);
					if (index<size-1){
						p.removeChild(m);
						m.setParent(null);
						
						p.addChild(m,index+1);
						
					}
				}
			}
		}
	}
	
	
}
