package com.harbortek.extbuilder.code.json;

import java.io.Writer;

import com.harbortek.extbuilder.code.CodeContext;


public interface JSONSerializer {
	

	public void serialize(Object obj,Writer os,CodeContext codeContext);

	public Object deseialize(Class paramType,String json);
}
