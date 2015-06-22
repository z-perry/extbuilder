/**
 * 
 */
package com.harbortek.extbuilder.model.button;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ExtButtonArray extends ExtElement {
	private static final long serialVersionUID = 1L;

	private List buttons;

	public ExtButtonArray() {
		setUseVariableName(false);
		setUseXtype(true);
	}

	public List getButtons() {
		return buttons;
	}

	public void setButtons(List buttons) {
		this.buttons = buttons;
	}

	public String toString() {
		String[] s = new String[getButtons().size()];
		int i = 0;
		for (Iterator iter = getButtons().iterator(); iter.hasNext();) {
			ExtButton col = (ExtButton) iter.next();
			s[i++] = col.getText();
		}
		return StringUtils.join(s, ",");
	}

	public String getExtClassName() {
		return null;
	}

//	public String getConfigString(CodeContext codeContext) {
//
//		StringWriter writer = new StringWriter();
//		Object o = translate(codeContext);
//		SerializerManager.serialize(o, writer, codeContext);
//		try {
//			writer.close();
//		} catch (IOException e) {
//		}
//
//		return writer.toString();
//	}

	public Object translate(CodeContext codeContext) {
		return getButtons();
	}

	public String getXtype() {
		return null;
	}
}