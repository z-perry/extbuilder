package com.harbortek.extbuilder.code;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Preferences;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.ui.editors.JSEditorFinder;
import com.harbortek.extbuilder.ui.preferences.PreferenceConstants;
import com.harbortek.extbuilder.utils.FileUtils;
import com.harbortek.extbuilder.xmodel.ExtDiagram;

public class XCodeGenerator {
	ExtDiagram diagram;

	String content;

	private CodeContext codeContext = new CodeContext();

	public XCodeGenerator(ExtDiagram diagram, String text) {
		this.diagram = diagram;
		this.content = text;
	}

	public CodeContext getCodeContext() {
		return codeContext;
	}

	public void setCodeContext(CodeContext codeContext) {
		this.codeContext = codeContext;
	}

	public String getJsContent() {
		if (content == null || content.length() == 0) {
			initJsContent();
		}
		int index = content.indexOf("// BEGIN OF CODE GENERATION");
		int fromIndex = content.indexOf("\n", index);
		int endIndex = content.indexOf("// END OF CODE GENERATION PARTS");
		String result = content.substring(0, fromIndex)
				+ diagram.getCode(codeContext)
				+ content.substring(endIndex, content.length());
		return result;
	}

	public String getPreviewJsContent() {
		if (content == null || content.length() == 0) {
			initJsContent();
		}
		int index = content.indexOf("// BEGIN OF CODE GENERATION");
		int fromIndex = content.indexOf("\n", index);
		int endIndex = content.indexOf("// END OF CODE GENERATION PARTS");
		codeContext.setForPreview(true);
		String result = content.substring(0, fromIndex)
				+ diagram.getCode(codeContext)
				+ content.substring(endIndex, content.length());
		return result;
	}

	private void initJsContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("Ext.namespace('").append(diagram.getPackageName()).append(
				"');").append("\n");
		sb.append(diagram.getClassName()).append(" = function(config){")
				.append("\n");
		sb.append("\t").append("Ext.applyIf(this,config);").append("\n");
		sb.append("\t").append("this.initUIComponents();").append("\n");
		sb.append("\t").append(diagram.getClassName()).append(
				".superclass.constructor.call(this);").append("\n");
		sb.append("};").append("\n");

		sb.append("Ext.extend(").append(diagram.getClassName()).append(",");
		sb.append(diagram.getSuperClass()).append(",{").append("\n");

		sb.append("\t").append("initUIComponents : function(){").append("\n");
		sb.append("\t").append(
				"// BEGIN OF CODE GENERATION PARTS, DON'T DELETE CODE BELOW")
				.append("\n");
		sb.append("\t").append(
				"// END OF CODE GENERATION PARTS, DON'T DELETE CODE ABOVE")
				.append("\n");
		sb.append("\t").append("}").append("\n");
		sb.append("});").append("\n");
		content = new JSEditorFinder().formatJsCode(0, sb.toString());
	}

	public String getPreviewHtmlCode() {
		Preferences preferences = ExtBuilderActivator.getDefault()
				.getPluginPreferences();

		String extJsDir = preferences.getString(PreferenceConstants.P_PATH);
		String uxDir = preferences.getString(PreferenceConstants.UX_JS_PATH);

		ArrayList cssFiles = new ArrayList();
		cssFiles.add(extJsDir + "/resources/css/ext-all.css");

		ArrayList scriptFiles = new ArrayList();
		scriptFiles.add(extJsDir + "/adapter/ext/ext-base.js");
		scriptFiles.add(extJsDir + "/ext-all-debug.js");
		scriptFiles.addAll(Arrays.asList(StringUtils.split(uxDir, ";")));

		return getHtmlCode((String[]) cssFiles.toArray(new String[cssFiles
				.size()]), (String[]) scriptFiles
				.toArray(new String[scriptFiles.size()]), getPreviewJsContent());
	}

	public String getHtmlCode(String[] cssFiles, String[] scriptFiles,
			String jsFileContent) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(
						"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
				.append("\n");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").append("\n");
		sb.append("<head>").append("\n");
		sb
				.append(
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> ")
				.append("\n");
		for (int i = 0; i < scriptFiles.length; i++) {
			sb.append(" <script type=\"text/javascript\" src=\"").append(
					"file:////").append(FileUtils.normalize(scriptFiles[i]))
					.append("\"></script>").append("\n");
		}

		for (int i = 0; i < cssFiles.length; i++) {
			String cssFile = cssFiles[i];
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"")
					.append(FileUtils.normalize(cssFile)).append("\" />")
					.append("\n");
		}
		sb.append("</head>").append("\n");
		sb.append("<body>").append("\n");
		sb.append("<script>").append("\n");
		sb.append("\t").append("Ext.onReady(function() {").append("\n");
		sb.append("\t\t").append("Ext.QuickTips.init();").append("\n");
		sb.append("\t\t")
				.append("Ext.form.Field.prototype.msgTarget = 'side';").append(
						"\n");
		if ("Ext.Panel".equals(diagram.getSuperClass())) {
			sb.append("\t\t").append("var viewport = new Ext.Viewport( {")
					.append("\n");
			sb.append("\t\t\t").append("layout : 'fit',").append("\n");
			sb.append("\t\t\t").append("border : false,").append("\n");
			sb.append("\t\t\t").append("items : [new ").append(
					diagram.getClassName()).append("()]").append("\n");
			sb.append("\t\t").append("});").append("\n");
			sb.append("\t\t").append("viewport.render();").append("\n");
		} else if ("Ext.Window".equals(diagram.getSuperClass())) {
			sb.append("\t\t\t").append("var win = new ").append(
					diagram.getClassName()).append("();").append("\n");
			sb.append("win.show();").append("\n");
			// sb.append("win.center();").append("\n");
		}

		sb.append("\t").append("});").append("\n");
		sb.append("</script>").append("\n");

		sb.append("<script>").append("\n");
		sb.append(jsFileContent).append("\n");
		sb.append("</script>").append("\n");

		sb.append("</body>").append("\n");
		sb.append("</html>").append("\n");

		return sb.toString();
	}

}
