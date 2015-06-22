package com.harbortek.extbuilder.model.button;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtToolbarTextItem extends ExtToolbarItem {
	private static final long serialVersionUID = 1L;

	public static final String TEXT = "text";

	private String text = "";

	public ExtToolbarTextItem() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public String getExtClassName() {
		return null;
	}

	public String getXtype() {
		return "tbtext";
	}

	public Object translate(CodeContext codeContext) {
		Map result = new HashMap();
		if (StringUtils.isNotEmpty(getXtype())) {
			result.put("xtype", getXtype());
		}
		if (StringUtils.isNotEmpty(getText())) {
			result.put(TEXT, getText());
		}
		return result;
	}
}
