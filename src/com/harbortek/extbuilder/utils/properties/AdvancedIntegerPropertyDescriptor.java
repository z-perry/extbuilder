package com.harbortek.extbuilder.utils.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;

public class AdvancedIntegerPropertyDescriptor extends IntegerPropertyDescriptor {

	public AdvancedIntegerPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		setFilterFlags(
			      new String[] {IPropertySheetEntry.FILTER_ID_EXPERT });
	}

}
