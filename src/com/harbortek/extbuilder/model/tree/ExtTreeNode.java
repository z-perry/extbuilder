package com.harbortek.extbuilder.model.tree;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.utils.properties.BooleanPropertyDescriptor;
import com.harbortek.extbuilder.xmodel.ExtPredefinedElement;

public class ExtTreeNode extends ExtPredefinedElement implements IPropertySource{
	private static final long serialVersionUID = 1L;

	private String icon;

	private String iconCls;

	private String id = "0";

	private String text = "Root";

	private Boolean allowDrag = Boolean.TRUE;

	private Boolean allowDrop = Boolean.TRUE;

	private Boolean disabled = Boolean.FALSE;

	private Boolean draggable = Boolean.FALSE;

	private Boolean expandable = Boolean.TRUE;

	private Boolean expanded = Boolean.FALSE;

	public ExtTreeNode() {
		setUseXtype(false);
	}

	public String getNodeIcon() {
		return icon;
	}

	public void setNodeIcon(String icon) {
		this.icon = icon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getAllowDrag() {
		return allowDrag;
	}

	public void setAllowDrag(Boolean allowDrag) {
		this.allowDrag = allowDrag;
	}

	public Boolean getAllowDrop() {
		return allowDrop;
	}

	public void setAllowDrop(Boolean allowDrop) {
		this.allowDrop = allowDrop;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getDraggable() {
		return draggable;
	}

	public void setDraggable(Boolean draggable) {
		this.draggable = draggable;
	}

	public Boolean getExpandable() {
		return expandable;
	}

	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return  new IPropertyDescriptor[] {
				new TextPropertyDescriptor("icon", "icon"),
				new TextPropertyDescriptor("iconCls", "iconCls"),
				new TextPropertyDescriptor("id", "id"),
				new TextPropertyDescriptor("text", "text"),
				new BooleanPropertyDescriptor("allowDrag", "allowDrag"),
				new BooleanPropertyDescriptor("allowDrop", "allowDrop"),
				new BooleanPropertyDescriptor("disabled", "disabled"),
				new BooleanPropertyDescriptor("draggable", "draggable"),
				new BooleanPropertyDescriptor("expandable", "expandable"),
				new BooleanPropertyDescriptor("expanded", "expanded"), };
	}

	public Object getEditableValue() {
		return this;
	}

	public Object getPropertyValue(Object propertyId) {
		if ("icon".equals(propertyId)) {
			return getNodeIcon();
		} else if ("iconCls".equals(propertyId)) {
			return getIconCls();
		} else if ("id".equals(propertyId)) {
			return getId();
		} else if ("text".equals(propertyId)) {
			return getText();
		} else if ("allowDrag".equals(propertyId)) {
			return getAllowDrag();
		} else if ("allowDrop".equals(propertyId)) {
			return getAllowDrop();
		} else if ("disabled".equals(propertyId)) {
			return getDisabled();
		} else if ("draggable".equals(propertyId)) {
			return getDraggable();
		} else if ("expandable".equals(propertyId)) {
			return getExpandable();
		} else if ("expanded".equals(propertyId)) {
			return getExpanded();
		} 
		return null;
	}

	public boolean isPropertySet(Object propertyId) {
		return true;
	}

	public void resetPropertyValue(Object propertyId) {
//		super.resetPropertyValue(propertyId);
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if ("icon".equals(propertyId)) {
			setNodeIcon((String) value);
		} else if ("iconCls".equals(propertyId)) {
			setIconCls((String) value);
		} else if ("id".equals(propertyId)) {
			setId((String) value);
		} else if ("text".equals(propertyId)) {
			setText((String) value);
		} else if ("allowDrag".equals(propertyId)) {
			setAllowDrag((Boolean) value);
		} else if ("allowDrop".equals(propertyId)) {
			setAllowDrop((Boolean) value);
		} else if ("disabled".equals(propertyId)) {
			setDisabled((Boolean) value);
		} else if ("draggable".equals(propertyId)) {
			setDraggable((Boolean) value);
		} else if ("expandable".equals(propertyId)) {
			setExpandable((Boolean) value);
		} else if ("expanded".equals(propertyId)) {
			setExpanded((Boolean) value);
		} 
		firePropertyChange((String)propertyId, null, value);
	}

	public Object translate(CodeContext codeContext) {
		Map objMap = (Map) new HashMap();
		if (StringUtils.isNotEmpty(getNodeIcon())) {
			objMap.put("icon", getNodeIcon());
		}

		if (StringUtils.isNotEmpty(getIconCls())) {
			objMap.put("iconCls", getIconCls());
		}

		if (StringUtils.isNotEmpty(getId())) {
			objMap.put("id", getId());
		}

		if (StringUtils.isNotEmpty(getText())) {
			objMap.put("text", getText());
		}

		if (!getAllowDrag().booleanValue()) {
			objMap.put("allowDrap", getAllowDrag());
		}

		if (!getAllowDrop().booleanValue()) {
			objMap.put("allowDrop", getAllowDrop());
		}

		if (getDisabled().booleanValue()) {
			objMap.put("disabled", getDisabled());
		}

		if (!getDraggable().booleanValue()) {
			objMap.put("draggable", getDraggable());
		}

		if (getExpandable().booleanValue()) {
			objMap.put("expandable", getExpandable());
		}

		if (getExpanded().booleanValue()) {
			objMap.put("expanded", getExpanded());
		}

		return objMap;
	}

	public String getExtClassName() {
		return "Ext.tree.AsyncTreeNode";
	}

	public String getXtype() {
		return null;
	}
	
	public String toString(){
		return getText();
	}

}
