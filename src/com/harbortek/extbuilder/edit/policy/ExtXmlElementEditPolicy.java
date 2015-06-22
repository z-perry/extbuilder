package com.harbortek.extbuilder.edit.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.harbortek.extbuilder.edit.command.ComponentDeleteCommand;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class ExtXmlElementEditPolicy extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest request) {
		Object parent = getHost().getParent().getModel();
		ExtXmlElement form = (ExtXmlElement) parent;
		ExtXmlElement extComponent = (ExtXmlElement) getHost().getModel();
		ComponentDeleteCommand deleteCommand = new ComponentDeleteCommand(form, extComponent);
		return deleteCommand;
	}
}