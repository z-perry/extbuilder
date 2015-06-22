package com.harbortek.extbuilder.model.tree;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.utils.properties.BooleanPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.StandardComboBoxPropertyDescriptor;
import com.harbortek.extbuilder.xmodel.ExtPredefinedElement;

public class ExtTreeLoader extends ExtPredefinedElement implements IPropertySource{
	private static final long serialVersionUID = 1L;

	public static final String DATAURL = "dataUrl";

	public static final String REQUEST_METHOD = "requestMethod";

	public static final String PRELOAD_CHILDREN = "preloadChildren";

	public static final String CLEAR_ON_LOAD = "clearOnLoad";

	public static final String BASE_PARAMS = "baseParams";

	public static final String BASE_ATTRS = "baseAttrs";

	private String baseAttrs = "";

	private String baseParams = "";

	private Boolean clearOnLoad = Boolean.TRUE;

	private Boolean preloadChildren = Boolean.FALSE;

	private String requestMethod = "POST";

	private String dataUrl = "";

	public ExtTreeLoader() {
		super();
		setUseXtype(false);
	}

	public String getBaseAttrs() {
		return baseAttrs;
	}

	public void setBaseAttrs(String baseAttrs) {
		this.baseAttrs = baseAttrs;
	}

	public String getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(String baseParams) {
		this.baseParams = baseParams;
	}

	public Boolean getClearOnLoad() {
		return clearOnLoad;
	}

	public void setClearOnLoad(Boolean clearOnLoad) {
		this.clearOnLoad = clearOnLoad;
	}

	public Boolean getPreloadChildren() {
		return preloadChildren;
	}

	public void setPreloadChildren(Boolean preloadChildren) {
		this.preloadChildren = preloadChildren;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String url) {
		this.dataUrl = url;
	}

	 public IPropertyDescriptor[] getPropertyDescriptors() {
		return 
				new IPropertyDescriptor[] {
						new TextPropertyDescriptor(BASE_ATTRS, BASE_ATTRS),
						new TextPropertyDescriptor(BASE_PARAMS, BASE_PARAMS),
						new BooleanPropertyDescriptor(CLEAR_ON_LOAD,
								CLEAR_ON_LOAD),
						new BooleanPropertyDescriptor(PRELOAD_CHILDREN,
								PRELOAD_CHILDREN),
						new StandardComboBoxPropertyDescriptor(REQUEST_METHOD,
								REQUEST_METHOD, new String[] { "POST", "GET" },
								new String[] { "POST", "GET" }),
						new TextPropertyDescriptor(DATAURL, DATAURL) };
	}

	public Object getPropertyValue(Object propertyId) {
		if (BASE_ATTRS.equals(propertyId)) {
			return getBaseAttrs();
		} else if (BASE_PARAMS.equals(propertyId)) {
			return getBaseParams();
		} else if (CLEAR_ON_LOAD.equals(propertyId)) {
			return getClearOnLoad();
		} else if (PRELOAD_CHILDREN.equals(propertyId)) {
			return getPreloadChildren();
		} else if (REQUEST_METHOD.equals(propertyId)) {
			return getRequestMethod();
		} else if (DATAURL.equals(propertyId)) {
			return getDataUrl();
		} else {
			return null;
		}
	}

	public boolean isPropertySet(Object propertyId) {
		return true;
	}

	public void resetPropertyValue(Object propertyId) {
		//super.resetPropertyValue(propertyId);
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if (BASE_ATTRS.equals(propertyId)) {
			setBaseAttrs((String) value);
		} else if (BASE_PARAMS.equals(propertyId)) {
			setBaseParams((String) value);
		} else if (CLEAR_ON_LOAD.equals(propertyId)) {
			setClearOnLoad((Boolean) value);
		} else if (PRELOAD_CHILDREN.equals(propertyId)) {
			setPreloadChildren((Boolean) value);
		} else if (REQUEST_METHOD.equals(propertyId)) {
			setRequestMethod((String) value);
		} else if (DATAURL.equals(propertyId)) {
			setDataUrl((String) value);
		} 
		firePropertyChange((String)propertyId, null, value);
	}

	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();

		if (StringUtils.isNotEmpty(getBaseAttrs())) {
			map.put(BASE_ATTRS, getBaseAttrs());
		}
		if (StringUtils.isNotEmpty(getBaseParams())) {
			map.put(BASE_PARAMS, getBaseParams());
		}
		if (!getClearOnLoad().booleanValue()) {
			map.put(CLEAR_ON_LOAD, getClearOnLoad());
		}
		if (getPreloadChildren().booleanValue()) {
			map.put(PRELOAD_CHILDREN, getPreloadChildren());
		}
		if (!"POST".equals(getRequestMethod())) {
			map.put(REQUEST_METHOD, getRequestMethod());
		}
		if (StringUtils.isNotEmpty(getDataUrl())) {
			map.put(DATAURL, getDataUrl());
		}
		return map;
	}

	public String getExtClassName() {
		return "Ext.tree.TreeLoader";
	}

	public String getXtype() {
		return null;
	}

	public Object getEditableValue() {
		return this;
	}
	
	public String toString(){
		return "";
	}

}
