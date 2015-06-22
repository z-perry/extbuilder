package com.harbortek.extbuilder.model.data;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.code.json.SerializerManager;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ExtDataField extends ExtElement {
	private static final long serialVersionUID = 1L;

	private String name;

	private String mapping;

	private String type;

	public ExtDataField(String name) {
		this(name, name);
	}

	public ExtDataField(String name, String mapping) {
		this(name, mapping, null);
	}

	public ExtDataField(String name, String mapping, String type) {
		this.name = name;
		this.mapping = mapping;
		this.type = type;
		setUseXtype(false);
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfigString(CodeContext codeContext) {
		StringWriter writer = new StringWriter();
		Object o = translate(codeContext);
		SerializerManager.serialize(o, writer, codeContext);
		try {
			writer.close();
		} catch (IOException e) {
		}

		return writer.toString();

	}

	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(getName())) {
			map.put("name", getName());
		}
		if (StringUtils.isNotEmpty(getMapping())) {
			map.put("mapping", getMapping());
		}

		if (StringUtils.isNotEmpty(getType())) {
			map.put("type", getType());
		}

		return map;
	}

	public String getExtClassName() {
		return null;
	}

	public String getXtype() {
		// TODO Auto-generated method stub
		return null;
	}

}
