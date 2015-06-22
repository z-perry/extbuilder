package com.harbortek.extbuilder.edit.command;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.Command;

import com.harbortek.extbuilder.utils.XComponentUtils;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class ComponentCreateCommand extends Command {
	private ExtXmlElement newComp;

	private ExtXmlElement parent;

	public ComponentCreateCommand(ExtXmlElement newComp, ExtXmlElement parent) {
		this.newComp = newComp;
		this.parent = parent;
		setLabel("component creation");
	}

	public boolean canExecute() {
		return newComp != null && parent != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		newComp.setParent(parent);
		newComp.setDiagram(parent.getDiagram());
		if (newComp.isUseVariableName() && StringUtils.isEmpty(newComp.getVariableName())) {
			XComponentUtils.generateNameForAll(parent.getDiagram(), newComp);
//			newComp.setVariableName(XComponentUtils.generateName(
//					XComponentUtils.getDiagram(parent), this.newComp));
		}
//		Collection c = XComponentUtils.getComponents(newComp);
//		for (Iterator iter = c.iterator(); iter.hasNext();) {
//			ExtElement comp = (ExtElement) iter.next();
//			if (comp.isUseVariableName()
//					&& StringUtils.isEmpty(comp.getVariableName())) {
//				comp.setVariableName(XComponentUtils.generateName(
//						XComponentUtils.getDiagram(parent), comp));
//			}
//		}
		
		parent.addChild(newComp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		parent.removeChild(newComp);
	}
}
