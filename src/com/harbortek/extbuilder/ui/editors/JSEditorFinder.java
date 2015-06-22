package com.harbortek.extbuilder.ui.editors;

import java.util.HashMap;

import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.editors.text.TextEditor;

import com.spket.js.formatter.CodeFormatter;

public class JSEditorFinder {

	private String[] editors = new String[] { "com.spket.js.editors.JSEditor",
			"org.eclipse.wst.javascript.ui.internal.editor.JSEditor",
			"org.eclipse.ui.editors.text.TextEditor" };

	private String[] formaters = new String[] { "com.spket.js.formatter.CodeFormatter" };

	public TextEditor findEditor() {
		TextEditor editor = new TextEditor();// new
												// com.spket.js.editors.JSEditor();
		// for (int i = 0; i < editors.length; i++) {
		// String className = editors[i];
		// try {
		// editor = (TextEditor) Class.forName(className).newInstance();
		// break;
		// } catch (InstantiationException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// }
		// }
		return editor;
	}

	public String formatJsCode(int indent, String code2) {
		try {
			CodeFormatter formatter = com.spket.js.formatter.CodeFormatterUtil
					.createCodeFormatter(new HashMap());
			//
			// // Object formatter =
			// //
			// ReflectionUtils.invoke("com.spket.js.formatter.CodeFormatterUtil",
			// // "createCodeFormatter",new Object[]{ new HashMap()});
			// // TextEdit edits= (TextEdit)ReflectionUtils.invoke(formatter,
			// // "format", new Object[]{new Integer(0),code2,new Integer(0),new
			// // Integer(code2.length()),new Integer(indent),null});
			TextEdit edits = (TextEdit) formatter.format(0, code2, 0, code2
					.length(), indent, null);
			Document doc = new Document(code2);
			if (edits != null) {
				edits.apply(doc);
			}
			code2 = doc.get();
			return code2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code2;
	}

}
