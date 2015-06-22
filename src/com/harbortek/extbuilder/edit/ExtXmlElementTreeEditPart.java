package com.harbortek.extbuilder.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;

import com.harbortek.extbuilder.edit.policy.ExtContainerTreeEditPolicy;
import com.harbortek.extbuilder.edit.policy.ExtXmlElementEditPolicy;
import com.harbortek.extbuilder.ui.editors.ExtJsPageEditor;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;
import com.harbortek.extbuilder.xmodel.ExtXmlManager;

public class ExtXmlElementTreeEditPart extends AbstractTreeEditPart implements
		PropertyChangeListener {

	public ExtXmlElementTreeEditPart(ExtXmlElement model) {
		super(model);
	}

	public EditPart getTargetEditPart(Request request) {
		return this;
	}

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			((ExtXmlElement) getModel()).addPropertyChangeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();

		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ExtXmlElementEditPolicy());

		if (ExtXmlManager.isContainer(getCastedModel().getExtClassId())) {
			installEditPolicy(EditPolicy.CONTAINER_ROLE,
					new ExtContainerTreeEditPolicy());
		}
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((ExtXmlElement) getModel()).removePropertyChangeListener(this);
		}
	}

	private ExtXmlElement getCastedModel() {
		return (ExtXmlElement) getModel();
	}

	protected List getModelChildren() {
		return ((ExtXmlElement) getModel()).getItems(); // a list of shapes
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	protected Image getImage() {
		return getCastedModel().getIcon();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() {
		return ExtXmlManager.getExtClass(getCastedModel().getExtClassId())
				.getXtype();
		// return ComponentUtils.getXtype(getCastedModel().getExtClassId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (ExtXmlElement.PROP_CHILD_ADDED.equals(prop)
				|| ExtXmlElement.PROP_CHILD_REMOVED.equals(prop)) {
			refreshVisuals();
			refreshChildren();
			updateEditor();
		}
		refreshVisuals();
		updateEditor();
	}

	protected void updateEditor() {
		IEditorPart ep = ((org.eclipse.gef.DefaultEditDomain) this.getRoot()
				.getViewer().getEditDomain()).getEditorPart();
		// System.out.println("structure changed");
		ExtJsPageEditor editor = (ExtJsPageEditor) ep;
		editor.getParent().updateTextContent();
		editor.getParent().updateGrahpicsContent();
	}
}