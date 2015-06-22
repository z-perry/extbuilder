package com.harbortek.extbuilder.ui.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
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
import org.eclipse.ui.dialogs.PropertyPage;

public class ExtJsPropertiesPage extends PropertyPage {

	public static final String PREVIEW_TEMPLATE = "PREVIEW_TEMPLATE";

	SourceViewer viewer;
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public ExtJsPropertiesPage() {
		super();
	}


	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		GridLayout layout= new GridLayout();
		layout.numColumns= 1;
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		parent.setLayout(layout);
		
		Label label = new Label(parent,SWT.NONE);
		label.setText("Preview Template");
		
        viewer= createViewer(parent);

		IDocument document= new Document();
		document.set(getHtmlCode());
		viewer.setEditable(true);
		viewer.setDocument(document);
		
		try {
			String codeTemplate = ((IResource) getElement()).getPersistentProperty(
					new QualifiedName("com.harbortek.extb.ui.properties", PREVIEW_TEMPLATE));
			document.set(codeTemplate);
		} catch (CoreException e) {
			e.printStackTrace();
		}


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


	protected void performDefaults() {
		viewer.getDocument().set(getHtmlCode());
	}
	
	public boolean performOk() {
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("com.harbortek.extb.ui.properties", PREVIEW_TEMPLATE),
				viewer.getDocument().get());
		} catch (CoreException e) {
			return false;
		}
		return true;
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
		return "<link rel=\"stylesheet\" type=\"text/css\"	href=\"file:///${projectRoot}/WebSource/framework/ext/resources/css/ext-all.css\" />";
	}

	private String getExtAll() {
		return "<script type=\"text/javascript\" src=\"file:///${projectRoot}/WebSource/framework/ext/ext-all.js\"></script>";
	}

	private String getExtBase() {
		return "<script type=\"text/javascript\" src=\"file:///${projectRoot}/WebSource/framework/ext/adapter/ext/ext-base.js\"></script>";
	}

}