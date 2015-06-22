package com.harbortek.extbuilder.model.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtXmlDataReader extends ExtDataReader {
	private static final long serialVersionUID = 1L;

	public ExtXmlDataReader(String id, String root, String total, ExtDataField[] fields) {
		super(id, root, total, fields);
	}
	
	public String getExtClassName() {
		return "Ext.data.XmlReader";
	}
	
	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(getIdField())){
			map.put("id", getIdField());
		}
		
		if (StringUtils.isNotEmpty(getRoot())){
			map.put("record", getRoot());
		}
		
		if (StringUtils.isNotEmpty(getTotalProperty())){
			map.put("totalRecords", getTotalProperty());
		}
		
		if (StringUtils.isNotEmpty(getSuccessProperty())){
			map.put("success", getSuccessProperty());
		}
		
		return map;
	}

}
