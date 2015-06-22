package com.harbortek.extbuilder.edit.command;

import org.eclipse.gef.commands.Command;

import com.harbortek.extbuilder.xmodel.ExtXmlElement;


public class ComponentDeleteCommand extends Command{
	private ExtXmlElement extContainer;
	private ExtXmlElement extComponent;
	
	public ComponentDeleteCommand(ExtXmlElement extContainer, ExtXmlElement extComponent) {
		super();
		this.extContainer = extContainer;
		this.extComponent = extComponent;
	}
	
	
	public void execute()
	{
		this.extContainer.removeChild(extComponent);
		extComponent.setParent(null);
	}

	public void undo()
	{
		this.extContainer.addChild(extComponent);
		extComponent.setParent(this.extContainer);
	}

	public void redo()
	{
		this.execute();
	}
}
