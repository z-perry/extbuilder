package com.harbortek.extbuilder.model.data;

import com.harbortek.extbuilder.code.CodeContext;

public class ExtMemoryProxy extends ExtDataProxy {
	private static final long serialVersionUID = 1L;

	private String data;
	
	public ExtMemoryProxy(){
		
	}
	
	public ExtMemoryProxy(String data){
		this.data = data;
	}
	
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getExtClassName() {
		return "Ext.data.MemoryProxy";
	}

	public String getConfigString(CodeContext codeContext) {

		return "new " + getExtClassName() + "(" + data+")";
	}
	
	
}
