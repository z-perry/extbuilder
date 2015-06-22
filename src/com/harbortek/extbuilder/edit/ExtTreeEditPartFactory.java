package com.harbortek.extbuilder.edit;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.harbortek.extbuilder.xmodel.ExtXmlElement;

/**
 * Factory that maps model elements to TreeEditParts. TreeEditParts are used in
 * the outline view
 * 
 */
public class ExtTreeEditPartFactory implements EditPartFactory {

	public ExtTreeEditPartFactory() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 *      java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// if (modelElement instanceof ExtContainer) {
		// return new ContainerTreeEditPart((ExtContainer) modelElement);
		// } else if (modelElement instanceof ExtNoneUIComponent) {
		// return new NoneUIComponentTreeEditPart((ExtComponent) modelElement);
		// } else if (modelElement instanceof ExtComponent) {
		// return new ComponentTreeEditPart((ExtComponent) modelElement);
		// }

//		ExtXmlElement el = (ExtXmlElement) modelElement;

		return new ExtXmlElementTreeEditPart((ExtXmlElement) modelElement);

	}

}
