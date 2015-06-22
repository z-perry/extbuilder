package com.harbortek.extbuilder.ui.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.xmodel.ExtXmlManager;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ExtJsPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public ExtJsPreferencePage() {
		super(GRID);
		setPreferenceStore(ExtBuilderActivator.getDefault()
				.getPreferenceStore());
		setDescription("ExtJs Builder Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH,
				"&ExtJs Directory:", getFieldEditorParent()));
		addField(new UXFilePathEditor(PreferenceConstants.UX_JS_PATH,
				"User Extension JavaScript File List:",
				"Select ExtJs User Extension JavaScript File", new String[] {
						"*.js", "*.*" }, getFieldEditorParent()));
		addField(new UXFilePathEditor(PreferenceConstants.UX_XML_PATH,
				"User Extension XML Description File List:",
				"Select ExtJs User Extension Libarary File", new String[] {
						"*.xml", "*.*" }, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	public boolean performOk() {
		boolean result = super.performOk();
		if (result) {
			ExtXmlManager.reload();
		}
		return result;

	}

}