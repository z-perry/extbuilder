package com.harbortek.extbuilder.ui.editors;

import org.eclipse.gef.requests.CreationFactory;

import com.harbortek.extbuilder.xmodel.ExtDiagram;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

public class ExtFactory implements CreationFactory {

	private Class type;
	private String extClassId;
	private ExtDiagram diagram;

	/**
	 * Creates a ExtFactory.
	 * 
	 * @param aClass
	 *            The class to be instantiated using this factory.
	 */
	public ExtFactory(String extClassId,ExtDiagram diagram) {
		type = ExtXmlElement.class;
		this.extClassId = extClassId;
		this.diagram = diagram;
	}

	/**
	 * Create the new object.
	 * 
	 * @return The newly created object.
	 */
	public Object getNewObject() {
		try {
			ExtXmlElement o = (ExtXmlElement)type.newInstance();
			o.setExtClassId(extClassId);
			o.setDiagram(diagram);
			return o;
		} catch (Exception exc) {
			return null;
		}
	}

	/**
	 * Returns the type of object this factory creates.
	 * 
	 * @return The type of object this factory creates.
	 */
	public Object getObjectType() {
		return type;
	}

}
