package com.harbortek.extbuilder.code.json;

import java.io.Writer;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class VariableSerializer implements JSONSerializer{

	public Object deseialize(Class paramType, String json) {
		return null;
	}

	public void serialize(Object obj, Writer os,CodeContext codeContext) {
		ExtElement comp = (ExtElement) obj;
		String value = "";
		if (codeContext.getGeneratedComponents().containsKey(comp.getVariableName())){
			value = "this."+comp.getVariableName();
		}else{
			 value = comp.getCode(codeContext);
		}
		try {
			os.write(value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
