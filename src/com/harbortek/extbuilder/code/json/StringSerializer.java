package com.harbortek.extbuilder.code.json;

import java.io.Writer;

import com.harbortek.extbuilder.code.CodeContext;

public class StringSerializer implements JSONSerializer {

	public void serialize(Object obj, Writer os,CodeContext codeContext) {
		String value = (String) obj;
		value = "\"" + value + "\"";
		try {
			os.write(value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Object deseialize(Class paramType, String json) {

		//return JavaScriptUtil.unescapeJavaScript(json);
		return json;
	}

}
