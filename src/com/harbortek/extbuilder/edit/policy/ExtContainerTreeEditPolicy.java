package com.harbortek.extbuilder.edit.policy;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.harbortek.extbuilder.edit.command.ComponentCreateCommand;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class ExtContainerTreeEditPolicy extends AbstractEditPolicy {

	public Command getCommand(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			return getCreateCommand((CreateRequest) request);
		return null;
	}

	private Command getCreateCommand(CreateRequest request) {
		ExtXmlElement child = (ExtXmlElement) request.getNewObject();

		ExtXmlElement parent = (ExtXmlElement) getHost().getModel();
		ComponentCreateCommand create = new ComponentCreateCommand(child, parent);
		// ComponentSetConstraintCommand setConstraint = new
		// ComponentSetConstraintCommand(child,parent,null);
		return create;
	}
}
