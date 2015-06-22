package com.harbortek.extbuilder.model.data;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.code.json.SerializerManager;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ExtDataReader extends ExtElement {
	private static final long serialVersionUID = 1L;

	
	private String idField;
	
	private String root;

	private String totalProperty;

	private List fields;
	
	private String successProperty;
	
	public ExtDataReader(){
		super();
		setUseXtype(false);
	}
	
	public ExtDataReader(String id,String root,String total,ExtDataField[] fields){
		this();
		this.idField = id;
		this.root = root;
		this.totalProperty = total;
		this.fields = Arrays.asList(fields);
	}

	public List getFields() {
		return fields;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}

	
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}
	
	
	

	public String getSuccessProperty() {
		return successProperty;
	}

	public void setSuccessProperty(String successProperty) {
		this.successProperty = successProperty;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getConfigString(CodeContext codeContext) {

		StringWriter writer = new StringWriter();
		Object o = translate(codeContext);
		SerializerManager.serialize(o, writer,codeContext);
		try {
			writer.close();
		} catch (IOException e) {
		}
		
		String part1 = writer.toString();
		
		StringWriter writer2 = new StringWriter();
		Object o2 = getFields();
		SerializerManager.serialize(o2, writer2,codeContext);
		try {
			writer2.close();
		} catch (IOException e) {
		}
		String part2 = writer2.toString();
		
		
		return "new " + getExtClassName() + "(" + part1 +","+ part2+")";
		

	}

	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(getIdField())){
			map.put("id", getIdField());
		}
		
		if (StringUtils.isNotEmpty(root)){
			map.put("root", getRoot());
		}
		
		if (StringUtils.isNotEmpty(getTotalProperty())){
			map.put("total", getTotalProperty());
		}
		
		return map;
	}

	public String getExtClassName() {
		return "Ext.data.DataReader";
	}

	public String getXtype() {
		// TODO Auto-generated method stub
		return null;
	}

}
