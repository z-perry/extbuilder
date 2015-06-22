package com.harbortek.extbuilder.model.grid;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.model.data.ExtDataStore;
import com.harbortek.extbuilder.utils.properties.BooleanPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.IntegerPropertyDescriptor;
import com.harbortek.extbuilder.xmodel.ExtPredefinedElement;

public class ExtPagingToolbar extends ExtPredefinedElement implements IPropertySource{
	private static final long serialVersionUID = 1L;

	private Boolean displayInfo = Boolean.FALSE;

	private String displayMsg = "Displaying {0} - {1} of {2}";

	private String emptyMsg = "No data to display";

	private Integer pageSize = new Integer(20);

	public ExtPagingToolbar() {
		super();
	}

	public Boolean getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(Boolean displayInfo) {
		this.displayInfo = displayInfo;
	}

	public String getDisplayMsg() {
		return displayMsg;
	}

	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}

	public String getEmptyMsg() {
		return emptyMsg;
	}

	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new BooleanPropertyDescriptor("displayInfo", "displayInfo"),
				new TextPropertyDescriptor("displayMsg", "displayMsg"),
				new TextPropertyDescriptor("emptyMsg", "emptyMsg"),
				new IntegerPropertyDescriptor("pageSize", "pageSize"), };
	}

	public Object getPropertyValue(Object propertyId) {
		if ("displayInfo".equals(propertyId)) {
			return getDisplayInfo();
		} else if ("displayMsg".equals(propertyId)) {
			return getDisplayMsg();
		} else if ("emptyMsg".equals(propertyId)) {
			return getEmptyMsg();
		} else if ("pageSize".equals(propertyId)) {
			return getPageSize();
		} 
		return null;
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if ("displayInfo".equals(propertyId)) {
			setDisplayInfo((Boolean) value);
		} else if ("displayMsg".equals(propertyId)) {
			setDisplayMsg((String) value);
		} else if ("emptyMsg".equals(propertyId)) {
			setEmptyMsg((String) value);
		} else if ("pageSize".equals(propertyId)) {
			setPageSize((Integer) value);
		}
	}

	public Object translate(CodeContext codeContext) {
		Map result = new HashMap();
		if (getDisplayInfo().booleanValue()) {
			result.put("displayInfo", getDisplayInfo());
		}
		if (!"".equals(getDisplayMsg())) {
			result.put("displayMsg", getDisplayMsg());
		}
		if (!"".equals(getEmptyMsg())) {
			result.put("emptyMsg", getEmptyMsg());
		}
		if (getPageSize().intValue() != 20) {
			result.put("pageSize", getPageSize());
		}

		ExtDataStore store = (ExtDataStore) getHost().getPropertyValue("store");
		result.put("store", store);

		result.put("xtype", "paging");

		return result;
	}


	public String getExtClassName() {
		return "Ext.PagingToolbar";
	}

	public String getXtype() {
		return "paging";
	}

	public Object getEditableValue() {
		return this;
	}

	public boolean isPropertySet(Object id) {
		return true;
	}

	public void resetPropertyValue(Object id) {
	}
	
	

}
