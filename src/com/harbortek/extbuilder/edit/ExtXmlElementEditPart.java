package com.harbortek.extbuilder.edit;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class ExtXmlElementEditPart extends AbstractGraphicalEditPart{

	protected IFigure createFigure() {
		return new Figure();
	}

	protected void createEditPolicies() {
		
	}

}
