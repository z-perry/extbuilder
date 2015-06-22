package com.harbortek.extbuilder.code.json;

import java.io.Writer;

import com.harbortek.extbuilder.code.CodeContext;


public class BooleanSerializer implements JSONSerializer {

	public void serialize(Object obj,Writer os,CodeContext codeContext) {
		
		String value = ((Boolean)obj).booleanValue()? "true":"false";
		try{
			os.write(value);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	public Object deseialize(Class paramType, String json) {
		return  "true".equalsIgnoreCase(json) ? Boolean.TRUE : Boolean.FALSE;
	}

}
