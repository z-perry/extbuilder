package com.harbortek.extbuilder.code.json;

import java.io.IOException;
import java.io.Writer;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtScript;

public class ScriptSerializer implements JSONSerializer {

	public Object deseialize(Class paramType, String json) {
		return null;
	}

	public void serialize(Object obj, Writer os, CodeContext codeContext) {
		ExtScript comp = (ExtScript) obj;
		if (comp.isSingleWord()) {
			String value = comp.getContent();
			value = "\"" + value + "\"";
			try {
				os.write(value);
			} catch (Exception ex) {
			}
		} else {
			try {
				os.write(comp.getContent());
			} catch (IOException e) {
			}
		}
	}

}
