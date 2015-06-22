package com.harbortek.extbuilder.model.data;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ExtDataProxy extends ExtElement {
	private static final long serialVersionUID = 1L;

	public ExtDataProxy(){
		super();
		setUseXtype(false);
	}

	public String getExtClassName() {
		return "Ext.data.DataProxy";
	}

	public Object translate(CodeContext codeContext) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getXtype() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
