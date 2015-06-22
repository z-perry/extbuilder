package com.harbortek.extbuilder.xmodel;

import com.harbortek.extbuilder.code.CodeContext;

public interface ExtSerializable {
	public String getCode(CodeContext codeContext);

	public String getConfigString(CodeContext codeContext);

	public Object translate(CodeContext codeContext);
}
