package com.harbortek.extbuilder.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.harbortek.extbuilder.ExtBuilderActivator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class ExtJsPreference
	extends PreferencePage
	implements IWorkbenchPreferencePage {

	public ExtJsPreference() {
		super();
		setPreferenceStore(ExtBuilderActivator.getDefault().getPreferenceStore());
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	protected Control createContents(Composite ancestor) {
		Composite parent= new Composite(ancestor, SWT.NONE);
		
		GridLayout layout= new GridLayout();
		layout.numColumns= 1;
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		parent.setLayout(layout);
		
		Label label = new Label(parent,SWT.NONE);
		label.setText("Preview Template");
		
        SourceViewer viewer= createViewer(parent);

		IDocument document= new Document();
		document.set(getHtmlCode());
		viewer.setEditable(true);
		viewer.setDocument(document);


		int nLines= document.getNumberOfLines();
		if (nLines < 5) {
			nLines= 5;
		} else if (nLines > 12) {
			nLines= 12;
		}

		Control control= viewer.getControl();
		GridData data= new GridData(GridData.FILL_BOTH);
		data.widthHint= convertWidthInCharsToPixels(80);
		data.heightHint= convertHeightInCharsToPixels(nLines);
		control.setLayoutData(data);
		
		return parent;
	}
	
	protected SourceViewer createViewer(Composite parent) {
		SourceViewer viewer= new SourceViewer(parent, null, null, false, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		SourceViewerConfiguration configuration= new SourceViewerConfiguration();
		viewer.configure(configuration);
		return viewer;
	}
	
	public String getHtmlCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">").append("\n");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").append("\n");
		//sb.append("<html>").append("\n");
		sb.append("<head>").append("\n");
		sb.append("\t").append(getExtBase()).append("\n");
		sb.append("\t").append(getExtAll()).append("\n");
		sb.append("\t").append(getExtCss()).append("\n");
		sb.append("<script>").append("\n");
		sb.append("\t").append("Ext.onReady(function() {").append("\n");
		sb.append("\t\t").append("Ext.QuickTips.init();").append("\n");
		sb.append("\t\t").append("Ext.form.Field.prototype.msgTarget = 'side';").append("\n");
		sb.append("\t\t").append("var viewport = new Ext.Viewport( {").append("\n");
		sb.append("\t\t\t").append("layout : 'fit',").append("\n");
		sb.append("\t\t\t").append("border : false,").append("\n");
		sb.append("\t\t\t").append("items : [new ").append("${classname}").append("()]").append("\n");
		sb.append("\t\t").append("});").append("\n");
		sb.append("\t\t").append("viewport.render();").append("\n");
		sb.append("\t").append("});").append("\n");
		sb.append("</script>").append("\n");
		sb.append("</head>").append("\n");
		sb.append("<body>").append("\n");
		sb.append("</body>").append("\n");
		sb.append("</html>").append("\n");

		return sb.toString();
	}
	
	private String getExtCss() {
		return "<link rel=\"stylesheet\" type=\"text/css\"	href=\"file:///d:/work/ext/resources/css/ext-all.css\" />";
	}

	private String getExtAll() {
		return "<script type=\"text/javascript\" src=\"file:///d:/work/ext/ext-all.js\"></script>";
	}

	private String getExtBase() {
		return "<script type=\"text/javascript\" src=\"file:///d:/work/ext/adapter/ext/ext-base.js\"></script>";
	}
	
}