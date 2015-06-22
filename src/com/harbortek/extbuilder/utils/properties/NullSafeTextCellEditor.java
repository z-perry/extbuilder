package com.harbortek.extbuilder.utils.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class NullSafeTextCellEditor extends TextCellEditor {
	public NullSafeTextCellEditor(Composite parent) {
		super(parent);
	}

	protected void doSetValue(Object value) {
		if (value == null) {
			super.doSetValue("");
		} else {
			super.doSetValue(value);
		}
	}

	protected Object doGetValue() {
		if (StringUtils.isEmpty(text.getText())){
			return null;
		}
		return text.getText();
	}
}
