package com.harbortek.extbuilder.model.button;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtToolbarSpacer extends ExtToolbarItem {
	private static final long serialVersionUID = 1L;

	public ExtToolbarSpacer() {
		super();
	}

	public String getExtClassName() {
		return null;
	}

	public String getXtype() {
		return "tbspacer";
	}

	public Object translate(CodeContext codeContext) {
		Map result = new HashMap();
		if (StringUtils.isNotEmpty(getXtype())) {
			result.put("xtype", getXtype());
		}
		return result;
	}
}
