package com.harbortek.extbuilder.xmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.extract.ExtClass;
import com.harbortek.extbuilder.extract.ExtProperty;
import com.harbortek.extbuilder.model.ExtListenerArray;
import com.harbortek.extbuilder.utils.XComponentUtils;
import com.harbortek.extbuilder.utils.properties.AdvancedBooleanPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.AdvancedIntegerPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.AdvancedTextPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.BooleanPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.ButtonsPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.ColumnsPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.DataStorePropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.IntegerPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.ListenersPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.NullSafeTextPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.SelectionModelPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.StandardComboBoxPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.ToolbarButtonsPropertyDescriptor;

public class ExtXmlElement extends ExtElement implements IPropertySource {
	private static final long serialVersionUID = 1L;

	public static final String PROP_CHILD_ADDED = "ChildAdded";

	public static final String PROP_CHILD_REMOVED = "ChildRemoved";

	transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	transient protected EditPart editPart;

	private String extClassId;

	private static final Image COMPONENT_ICON = new Image(null,
			ImageDescriptor.createFromFile(ExtBuilderActivator.class,
					"icons/component.gif").getImageData()); //$NON-NLS-1$

	public ExtXmlElement() {
	}

	public ExtXmlElement(String extClassId) {
		setExtClassId(extClassId);
	}

	public String getExtClassId() {
		return extClassId;
	}

	public void setExtClassId(String extClassId) {
		this.extClassId = extClassId;
		setUseVariableName(getExtClass().isUseVariableName());
		setUseXtype(getExtClass().isUseXtype());
		setListenerArray(new ExtListenerArray(extClassId));
		Collection properties = ExtXmlManager.getProperties(getExtClassId());
		for (Iterator iter = properties.iterator(); iter.hasNext();) {
			ExtProperty prop = (ExtProperty) iter.next();
			Object defaultValue = prop.getDefaultValue();
			if (defaultValue != null) {
				if (defaultValue instanceof ExtElement) {
					if (defaultValue instanceof ExtPredefinedElement) {
						((ExtPredefinedElement) defaultValue).setHost(this);
					}
					values.put(prop.getPropertyName(), defaultValue);

					if (((ExtElement) defaultValue).isUseVariableName()) {
						addReferencedItem((ExtElement) defaultValue);
					}
				} else if (defaultValue instanceof String) {
					if (StringUtils.isNotEmpty((String) defaultValue)) {
						values.put(prop.getPropertyName(), defaultValue);
					}
				} else if (defaultValue instanceof Boolean) {
					values.put(prop.getPropertyName(), defaultValue);
				} else if (defaultValue instanceof Integer) {
					values.put(prop.getPropertyName(), defaultValue);
				}
			}
		}
	}

	public EditPart getEditPart() {
		return editPart;
	}

	public void setEditPart(EditPart editPart) {
		this.editPart = editPart;
	}

	public ExtClass getExtClass() {
		return ExtXmlManager.getExtClass(extClassId);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listeners==null){
			listeners = new PropertyChangeSupport(
					this);
		}
		listeners.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(String prop, Object old, Object newValue) {
		listeners.firePropertyChange(prop, old, newValue);
	}

	public void fireStructureChange(String prop, Object child) {
		listeners.firePropertyChange(prop, null, child);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		Collection properties = ExtXmlManager.getProperties(extClassId);
		List propDescriptors = new ArrayList();
		for (Iterator iter = properties.iterator(); iter.hasNext();) {
			IPropertyDescriptor descriptor = null;
			ExtProperty prop = (ExtProperty) iter.next();
			String name = prop.getPropertyName();
			String type = prop.getPropertyType();
			if ("String".equalsIgnoreCase(type)) {
				if (prop.isAdvanced()) {
					descriptor = new AdvancedTextPropertyDescriptor(name, name);
				} else {
					descriptor = new NullSafeTextPropertyDescriptor(name, name);
				}
			} else if ("Number".equalsIgnoreCase(type)) {
				if (prop.isAdvanced()) {
					descriptor = new AdvancedIntegerPropertyDescriptor(name,
							name);
				} else {
					descriptor = new IntegerPropertyDescriptor(name, name);
				}

			} else if ("Boolean".equalsIgnoreCase(type)) {
				if (prop.isAdvanced()) {
					descriptor = new AdvancedBooleanPropertyDescriptor(name,
							name);
				} else {
					descriptor = new BooleanPropertyDescriptor(name, name);
				}
			} else if ("Combox".equalsIgnoreCase(type)) {
				if (prop.isAdvanced()) {
					descriptor = new StandardComboBoxPropertyDescriptor(name,
							name, prop.getValues(), prop.getValues());
				} else {
					descriptor = new StandardComboBoxPropertyDescriptor(name,
							name, prop.getValues(), prop.getValues());
				}
			} else {
				if ("Ext.data.Store".equalsIgnoreCase(type)) {
					descriptor = new DataStorePropertyDescriptor(name, name);
				} else if ("Ext.Toolbar".equalsIgnoreCase(type)) {
					descriptor = new ToolbarButtonsPropertyDescriptor(name,
							name);
				} else if ("Ext.ButtonArray".equalsIgnoreCase(type)) {
					descriptor = new ButtonsPropertyDescriptor(name, name);
				} else if ("Ext.grid.ColumnModel".equalsIgnoreCase(type)) {
					descriptor = new ColumnsPropertyDescriptor(name, name);
				} else if ("Ext.tree.TreeNode".equalsIgnoreCase(type)) {
					descriptor = new PropertyDescriptor(name, name);
				} else if ("Ext.tree.TreeLoader".equalsIgnoreCase(type)) {
					descriptor = new PropertyDescriptor(name, name);
				} else if ("Ext.grid.SelectionModel".equalsIgnoreCase(type)) {
					descriptor = new SelectionModelPropertyDescriptor(name,
							name);
				}
			}
			if (descriptor != null) {
				propDescriptors.add(descriptor);
			}
		}
		propDescriptors.add(new TextPropertyDescriptor("variableName",
				"variableName"));
		propDescriptors.add(new ListenersPropertyDescriptor("listeners",
				"listeners"));
		return (IPropertyDescriptor[]) propDescriptors
				.toArray(new IPropertyDescriptor[propDescriptors.size()]);
	}

	public Object getPropertyValue(Object propertyName) {
		Object value = values.get(propertyName);
		if (value == null) {
//			ExtProperty prop = ExtXmlManager.getProperty(extClassId,
//					(String) propertyName);
//			if (prop != null) {
//				value = prop.getDefaultValue();
//			} else 
			if ("variableName".equalsIgnoreCase((String) propertyName)) {
				return getVariableName();
			} else if ("listeners".equalsIgnoreCase((String) propertyName)) {
				return getListenerArray();
			}
		}
		return value;
	}

	public boolean isPropertySet(Object propertyName) {
		Object value = values.get(propertyName);
		return value != null;
	}

	public void resetPropertyValue(Object propertyName) {
		ExtProperty prop = ExtXmlManager.getProperty(extClassId,
				(String) propertyName);
		if (prop != null && prop.getDefaultValue() != null) {
			values.put(propertyName, prop.getDefaultValue());
		}
	}

	public void setPropertyValue(Object propertyName, Object value) {
		if ("variableName".equalsIgnoreCase((String) propertyName)) {
			setVariableName((String) value);
			firePropertyChange((String) propertyName, null, value);
		} else if ("listeners".equalsIgnoreCase((String) propertyName)) {
			setListenerArray((ExtListenerArray) value);
			firePropertyChange((String) propertyName, null, value);
		} else {
			Object old = values.get(propertyName);
			values.put(propertyName, value);
			if (value==null){
				values.remove(propertyName);
			}
			
			if (value instanceof ExtElement) {
				if (value instanceof ExtPredefinedElement) {
					((ExtPredefinedElement) value).setHost(this);
				}

				if (((ExtElement) value).isUseVariableName()) {

					if (old != null) {
						removeReferencedItem((ExtElement) old);
					}
					if (StringUtils.isEmpty(((ExtElement) value)
							.getVariableName())) {
						if(old!=null){
							((ExtElement) value).setVariableName(((ExtElement) old).getVariableName());
						}
						XComponentUtils.generateNameForAll(getDiagram(), (ExtElement) value);
					}
					addReferencedItem((ExtElement) value);
				}
			}

			firePropertyChange((String) propertyName, old, value);
		}
	}

	public boolean addChild(ExtElement s) {
		if (s != null && items.add(s)) {
			s.setParent(this);

			addReferencedItem(s);

			// calculateRegion(s);

			firePropertyChange(PROP_CHILD_ADDED, null, s);
			return true;
		}
		return false;
	}

	public boolean addChild(ExtElement s, int index) {
		if (s != null) {
			items.add(index, s);
			s.setParent(this);
			addReferencedItem(s);

			// calculateRegion(s);

			firePropertyChange(PROP_CHILD_ADDED, null, s);
			return true;
		}
		return false;
	}

	

	public boolean removeChild(ExtElement s) {
		if (s != null && items.remove(s)) {
			removeReferencedItem(s);

			firePropertyChange(PROP_CHILD_REMOVED, null, s);
			return true;
		}
		return false;
	}

	public boolean isContainer() {
		return ExtXmlManager.isContainer(extClassId);
	}

	public Image getIcon() {
		return COMPONENT_ICON;
	}

	public String getExtClassName() {
		return getExtClass().getClassName();
	}

	public Object translate(CodeContext codeContext) {
		ExtListenerArray l = getListenerArray();
		if (l != null && l.getListeners() != null
				&& l.getListeners().size() > 0) {
			values.put("listeners", l);
		} else {
			values.remove("listeners");
		}

		if (items != null && items.size() > 0) {
			values.put("items", items);
		} else {
			values.remove("items");
		}
		if (isUseXtype() && !"Ext.Panel".equals(extClassId)) {
			values.put("xtype", getExtClass().getXtype());
		} else {
			values.remove("xtype");
		}
		return values;
	}

	public String getXtype() {
		return getExtClass().getXtype();
	}

}
