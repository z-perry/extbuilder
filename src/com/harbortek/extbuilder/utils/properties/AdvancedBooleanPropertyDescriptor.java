package com.harbortek.extbuilder.utils.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;

public class AdvancedBooleanPropertyDescriptor extends BooleanPropertyDescriptor {

	public AdvancedBooleanPropertyDescriptor(Object propertyID, String propertyDisplayname) {
		super(propertyID, propertyDisplayname);
		setFilterFlags(
			      new String[] {IPropertySheetEntry.FILTER_ID_EXPERT });

	}

}
