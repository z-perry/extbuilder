package com.harbortek.extbuilder.model.grid;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtCheckboxSelectionModel extends ExtRowSelectionModel {
	private String header;
	private Boolean sortable = Boolean.FALSE;
	private Integer width = new Integer(20);
	
	
	public ExtCheckboxSelectionModel(){
		setUseVariableName(true);
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
//		Object oldObj = this.header;
		this.header = header;
//		this.firePropertyChange("header", oldObj, header);
	}
	public Boolean getSortable() {
		return sortable;
	}
	public void setSortable(Boolean sortable) {
//		Object oldObj = this.sortable;
		this.sortable = sortable;
//		this.firePropertyChange("sortable", oldObj, sortable);
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
//		Object oldObj = this.width;
		this.width = width;
//		this.firePropertyChange("width", oldObj, width);
	}
	
//	public IPropertyDescriptor[] getPropertyDescriptors() {
//		return  new IPropertyDescriptor[] {
//				new AdvancedTextPropertyDescriptor("header", "header"),
//				new AdvancedIntegerPropertyDescriptor("sortable", "sortable"),
//				new AdvancedIntegerPropertyDescriptor("width", "width"),
//		};
//	}
//	
//	public Object getPropertyValue(Object propertyId) {
//		if ("header".equals(propertyId)) {
//			return getHeader();
//		} else if ("sortable".equals(propertyId)) {
//			return getSortable();
//		} else if ("width".equals(propertyId)) {
//			return getWidth();
//		}else {
//			return super.getPropertyValue(propertyId);
//		}
//	}
//	
//	public boolean isPropertySet(Object propertyId) {
//		return true;
//	}
//
//	public void resetPropertyValue(Object propertyId) {
//		super.resetPropertyValue(propertyId);
//	}
//	
//	public void setPropertyValue(Object propertyId, Object value) {
//		if ("header".equals(propertyId)) {
//			setHeader((String) value);
//		} else if ("sortable".equals(propertyId)) {
//			setSortable((Boolean) value);
//		} else if ("width".equals(propertyId)) {
//			setWidth((Integer) value);
//		}else {
//			super.setPropertyValue(propertyId, value);
//		}
//	}
	
	public Object translate(CodeContext codeContext) {
		Map result = (Map) super.translate(codeContext);
		
		if (StringUtils.isNotEmpty(header)){
			result.put("header", getHeader());
		}
		
		if (getSortable().booleanValue()){
			result.put("sortable", getSortable());
		}
		
		if (getWidth().intValue()!=20){
			result.put("width", getWidth());
		}
		return result;
	}
	public String getExtClassName() {
		return "Ext.grid.CheckboxSelectionModel";
	}
	
	public String toString(){
		return "CheckboxSelectionModel";
	}
	
}
