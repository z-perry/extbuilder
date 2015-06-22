package com.harbortek.extbuilder.code.json;

import java.io.Writer;

import com.harbortek.extbuilder.code.CodeContext;

public class ReferenceSerializer implements JSONSerializer{

	public Object deseialize(Class paramType, String json) {
		return null;
	}

	public void serialize(Object obj, Writer os,CodeContext codeContext) {
		String value = obj.toString();
		try {
			os.write("this."+value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}