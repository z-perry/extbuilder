/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
 �* All rights reserved. This program and the accompanying materials
 �* are made available under the terms of the Eclipse Public License v1.0
 �* which accompanies this distribution, and is available at
 �* http://www.eclipse.org/legal/epl-v10.html
 �*
 �* Contributors:
 �*����Elias Volanakis - initial API and implementation
 �*******************************************************************************/
package com.harbortek.extbuilder.edit;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.harbortek.extbuilder.xmodel.ExtXmlElement;

/**
 * Factory that maps model elements to edit parts.
 * 
 * @author Elias Volanakis
 */
public class ExtEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 *      java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		// store model element in EditPart
		part.setModel(modelElement);
//		if (modelElement instanceof ExtElement){
//			((ExtElement)modelElement).setEditPart(part);
//		}
		
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *             if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof ExtXmlElement){
			return new ExtXmlElementEditPart();
		}
		return null;
		
//		if (modelElement instanceof ExtContainer){
//			return new ExtContainerEditPart();
//		}else if (modelElement instanceof ExtNoneUIComponent){
//			return new ExtNoneUIComponentEditPart();
//		}else{
//			return new ExtComponentEditPart();
//		}
//		if (modelElement.getClass() == ExtJsDiagram.class) {
//			return new ExtDiagramEditPart();
//		} else if (modelElement.getClass() == ExtBoxComponent.class) {
//			return new ExtBoxComponentEditPart();
//		} else if (modelElement.getClass() == ExtContainer.class) {
//			return new ExtContainerEditPart();
//		} else if (modelElement.getClass() == ExtPanel.class) {
//			return new ExtPanelEditPart();
//		} else if (modelElement.getClass() == ExtFormPanel.class){
//			return new ExtFormPanelEditPart();
//		} else if (modelElement.getClass() == ExtTabPanel.class) {
//			return new ExtTabPanelEditPart();
//		} else if (modelElement.getClass() == ExtTreePanel.class) {
//			return new ExtTreePanelEditPart();
//		} else if (modelElement.getClass() == ExtGridPanel.class) {
//			return new ExtGridPanelEditPart();
//		} else if (modelElement.getClass() == ExtToolbar.class){
//			return new ExtToolbarEditPart();
//		} else if (modelElement.getClass() == ExtPagingToolbar.class){
//			return new ExtPagingToolbarEditPart();
//		} else if (modelElement.getClass() == ExtFieldSet.class) {
//			return new ExtFieldSetEditPart();
//		} else if (modelElement.getClass() == ExtTextField.class) {
//			return new ExtTextFieldEditPart();
//		} else if (modelElement.getClass() == ExtPasswordField.class) {
//			return new ExtPasswordFieldEditPart();
//		} else if (modelElement.getClass() == ExtNumberField.class) {
//			return new ExtNumberFieldEditPart();
//		} else if (modelElement.getClass() == ExtCombox.class) {
//			return new ExtComboxEditPart();
//		} else if (modelElement.getClass() == ExtCheckbox.class) {
//			return new ExtCheckboxEditPart();
//		} else if (modelElement.getClass() == ExtRadio.class) {
//			return new ExtRadioEditPart();
//		} else if (modelElement.getClass() == ExtTextArea.class) {
//			return new ExtTextAreaEditPart();
//		} else if (modelElement.getClass() == ExtDateField.class) {
//			return new ExtDateFieldEditPart();
//		} else if (modelElement.getClass() == ExtTimeField.class) {
//			return new ExtTimeFieldEditPart();
//		} else if (modelElement.getClass() == ExtHtmlEditor.class) {
//			return new ExtHtmlEditorEditPart();
//		} else if (modelElement.getClass() == ExtDataStore.class) {
//			return new ExtDataStoreEditPart();
//		} else if (modelElement.getClass() == ExtColumnModel.class) {
//			return new ExtColumnModelEditPart();
//		} 
	}

}