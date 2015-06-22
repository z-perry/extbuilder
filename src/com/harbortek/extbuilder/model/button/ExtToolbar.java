package com.harbortek.extbuilder.model.button;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ExtToolbar extends ExtElement {
	private static final long serialVersionUID = 1L;

	private ArrayList buttons = new ArrayList();

	public ArrayList getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList buttons) {
		this.buttons = buttons;
	}

	public Object translate(CodeContext codeContext) {
		return getButtons();
	}

	public String getCode() {
		return null;
	}

	public String getExtClassName() {
		return "Ext.Toolbar";
	}

	public String toString() {
		String[] s = new String[getButtons().size()];
		int i = 0;
		for (Iterator iter = getButtons().iterator(); iter.hasNext();) {
			Object item = iter.next();
			if (item instanceof ExtToolbarButton) {
				s[i++] = ((ExtToolbarButton) item).getText();
			} else {
				s[i++] = " ";
			}
		}
		return StringUtils.join(s, ",");
	}

	public String getXtype() {
		return null;
	}

}
