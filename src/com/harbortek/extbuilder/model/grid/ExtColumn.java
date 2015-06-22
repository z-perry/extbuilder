package com.harbortek.extbuilder.model.grid;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtScript;

public class ExtColumn extends ExtElement {
	private static final long serialVersionUID = 1L;

	private String align = "";

	private String dataIndex = "";

	private Boolean fixed = Boolean.FALSE;

	private String header = "";

	private Boolean hidden = Boolean.FALSE;

	private Boolean hideable = Boolean.TRUE;

	private Boolean resizable = Boolean.TRUE;

	private Boolean sortable = Boolean.TRUE;

	private Integer width = new Integer(0);

	private String renderer = "";

	public ExtColumn() {
		super();
		setUseXtype(true);
	}

	public ExtColumn(String header, String dataIndex) {
		super();
		setUseXtype(true);
		this.dataIndex = dataIndex;
		this.header = header;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public Boolean getFixed() {
		return fixed;
	}

	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getHideable() {
		return hideable;
	}

	public void setHideable(Boolean hideable) {
		this.hideable = hideable;
	}

	public Boolean getResizable() {
		return resizable;
	}

	public void setResizable(Boolean resizable) {
		this.resizable = resizable;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public Object translate(CodeContext codeContext) {
		Map result = new HashMap();

		if (StringUtils.isNotEmpty(getAlign())) {
			result.put("align", getAlign());
		}
		if (StringUtils.isNotEmpty(getDataIndex())) {
			result.put("dataIndex", getDataIndex());
		}

		if (getFixed().booleanValue()) {
			result.put("fixed", getFixed());
		}

		if (StringUtils.isNotEmpty(getHeader())) {
			result.put("header", getHeader());
		}

		if (!getHidden().booleanValue()) {
			result.put("hidden", getHidden());
		}
		if (!getHideable().booleanValue()) {
			result.put("hideable", getHideable());
		}
		if (!getResizable().booleanValue()) {
			result.put("resizable", getResizable());
		}
		if (getSortable().booleanValue()) {
			result.put("sortable", getSortable());
		}
		if (getWidth().intValue() > 0) {
			result.put("width", getWidth());
		}
		
		if (StringUtils.isNotEmpty(getRenderer())){
			result.put("renderer", new ExtScript(getRenderer()));
		}

		return result;
	}

	public String getExtClassName() {
		return null;
	}

	public String getXtype() {
		return null;
	}

	public String toString(){
		return getHeader();
	}
}
