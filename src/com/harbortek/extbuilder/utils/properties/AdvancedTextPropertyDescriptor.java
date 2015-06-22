package com.harbortek.extbuilder.utils.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class AdvancedTextPropertyDescriptor extends TextPropertyDescriptor {

	public AdvancedTextPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		setFilterFlags(
			      new String[] {IPropertySheetEntry.FILTER_ID_EXPERT });

	}

}
