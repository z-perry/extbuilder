package com.harbortek.extbuilder.utils.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class IntegerCellEditor extends TextCellEditor {
	public IntegerCellEditor(Composite composite) {
		super(composite);
		setValidator(new ICellEditorValidator() {
			public String isValid(Object object) {
				if (object ==null){
					return null;
				}
				if (object instanceof Integer)
					return null;
				String string = (String) object;
				if (StringUtils.isEmpty(string)){
					return null;
				}
				try {
					Integer.parseInt(string);
				} catch (NumberFormatException exception) {
					return exception.getMessage();
				}
				return null;
			}
		});
	}

	protected Object doGetValue() {
		Object obj = super.doGetValue();
		if (obj == null || obj.toString().trim().length() <= 0) {
			return null;
		}
		return Integer.valueOf(obj.toString());
	}

	protected void doSetValue(Object value) {
		if (value == null || StringUtils.isEmpty(value.toString())) {
			super.doSetValue("");
		}else{
			super.doSetValue(value.toString());
		}
	}

}
