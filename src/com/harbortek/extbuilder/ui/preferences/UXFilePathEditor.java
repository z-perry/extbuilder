package com.harbortek.extbuilder.ui.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

public class UXFilePathEditor extends ListEditor {

	/**
	 * The last path, or <code>null</code> if none.
	 */
	private String lastPath;

	/**
	 * The special label text for directory chooser, or <code>null</code> if
	 * none.
	 */
	private String dirChooserLabelText;
	
	
	private String[] filterExtensions;

	/**
	 * Creates a new path field editor
	 */
	protected UXFilePathEditor() {
	}

	/**
	 * Creates a path field editor.
	 * 
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param dirChooserLabelText
	 *            the label text displayed for the directory chooser
	 * @param parent
	 *            the parent of the field editor's control
	 */
	public UXFilePathEditor(String name, String labelText, String dirChooserLabelText, String[] filterExtensions, Composite parent) {
		init(name, labelText);
		this.dirChooserLabelText = dirChooserLabelText;
		this.filterExtensions = filterExtensions;
		createControl(parent);
	}

	/*
	 * (non-Javadoc) Method declared on ListEditor. Creates a single string from
	 * the given array by separating each string with the appropriate
	 * OS-specific path separator.
	 */
	protected String createList(String[] items) {
		StringBuffer path = new StringBuffer("");//$NON-NLS-1$

		for (int i = 0; i < items.length; i++) {
			path.append(items[i]);
			path.append(File.pathSeparator);
		}
		return path.toString();
	}

	/*
	 * (non-Javadoc) Method declared on ListEditor. Creates a new path element
	 * by means of a directory dialog.
	 */
	protected String getNewInputObject() {
//		if (true){
//			MessageDialog.openInformation(getShell(), "Information", "This is free version, please purchase Commercial verion.");
//	        return null;
//		}
		
		FileDialog dialog = new FileDialog(getShell());
		dialog.setFilterExtensions(this.filterExtensions);
		if (dirChooserLabelText != null) {
			//dialog.setMessage(dirChooserLabelText);
		}
		if (lastPath != null) {
			if (new File(lastPath).exists()) {
				dialog.setFilterPath(lastPath);
			}
		}
		String dir = dialog.open();
		if (dir != null) {
			dir = dir.trim();
			if (dir.length() == 0) {
				return null;
			}
			lastPath = dir;
		}
		return dir;
	}

	/*
	 * (non-Javadoc) Method declared on ListEditor.
	 */
	protected String[] parseString(String stringList) {
		StringTokenizer st = new StringTokenizer(stringList, File.pathSeparator + "\n\r");//$NON-NLS-1$
		ArrayList v = new ArrayList();
		while (st.hasMoreElements()) {
			v.add(st.nextElement());
		}
		return (String[]) v.toArray(new String[v.size()]);
	}
	
	
}