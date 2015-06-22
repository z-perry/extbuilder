package com.harbortek.extbuilder.xmodel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.constant.PropertyConstant;
import com.harbortek.extbuilder.constant.PropertyLabelConstant;
import com.harbortek.extbuilder.utils.XComponentUtils;

public class ExtDiagram extends ExtXmlElement {
	private static final long serialVersionUID = 1L;

	private String packageName = "Ext.ux";

	private String className = "Ext.ux.MyPanel";

	private String superClass;

	public ExtDiagram() {
		super();
		setExtClassId("Ext.Component");
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
				PropertyConstant.PROP_PACKAGENAME,
				PropertyConstant.PROP_CLASSNAME };
		return descriptors;
	}

	public Object getPropertyValue(Object propertyId) {

		if (PropertyLabelConstant.PACKAGENAME.equals(propertyId)) {
			return getPackageName();
		} else if (PropertyLabelConstant.CLASSNAME.equals(propertyId)) {
			return getClassName();
		}
		return null;
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if (PropertyLabelConstant.PACKAGENAME.equals(propertyId)) {
			setPackageName((String) value);
		} else if (PropertyLabelConstant.CLASSNAME.equals(propertyId)) {
			setClassName((String) value);
		}
	}

	public String getCode(CodeContext codeContext) {
		String code = createNamedComponents(codeContext) + "\n"
				+ createUIComponents(codeContext);
		return code;
	}

	private String createUIComponents(CodeContext codeContext) {
		StringBuffer sb = new StringBuffer();

		for (Iterator iter = this.getItems().iterator(); iter.hasNext();) {
			ExtElement comp = (ExtElement) iter.next();
			if (StringUtils.isEmpty(comp.getVariableName())) {
				String code = comp.getConfigString(codeContext);
				sb.append("Ext.apply(this,").append(code).append(");\n");
				break;
			}
		}
		return sb.toString();
	}

	private String createNamedComponents(CodeContext codeContext) {
		StringBuffer sb = new StringBuffer();
		List compsWithName = XComponentUtils
				.findHasComponentNameComponents(this);
		for (Iterator iter = compsWithName.iterator(); iter.hasNext();) {
			ExtElement comp = (ExtElement) iter.next();
			if (StringUtils.isNotEmpty(comp.getVariableName())) {
				String code = comp.getCode(codeContext);
				codeContext.getGeneratedComponents().put(
						comp.getVariableName(), comp);
				if (code != null) {
					sb.append(code).append("\n");
				}
			}
		} 
		return sb.toString();
	}

//	private static XStream xStream;
//
//	private static XStream getXStream() {
//		if (xStream == null) {
//			xStream = new XStream(new DomDriver());
//			xStream.alias("Diagram", ExtDiagram.class);
//			xStream.alias("Component", ExtXmlElement.class);
//			xStream.alias("SelectionModel", ExtAbstractSelectionModel.class);
//			xStream.alias("CellSelectionMode", ExtCellSelectionModel.class);
//			xStream.alias("RowSelectionModel", ExtRowSelectionModel.class);
//			xStream.alias("CheckboxSelectionModel",
//					ExtCheckboxSelectionModel.class);
//			xStream.alias("Button", ExtButton.class);
//			xStream.alias("SplitButton", ExtSplitButton.class);
//			xStream.alias("CycleButton", ExtCycleButton.class);
//			xStream.alias("ToolbarSplitButton", ExtToolbarSplitButton.class);
//			xStream.alias("ToolbarButton", ExtToolbarButton.class);
//			xStream.alias("ButtonArray", ExtButtonArray.class);
//			xStream.alias("Column", ExtColumn.class);
//			xStream.alias("DataField", ExtDataField.class);
//			xStream.alias("DataProxy", ExtDataProxy.class);
//			xStream.alias("HttpProxy", ExtHttpProxy.class);
//			xStream.alias("MemoryProxy", ExtMemoryProxy.class);
//			xStream.alias("ScriptTagProxy", ExtScriptTagProxy.class);
//			xStream.alias("DataReader", ExtDataReader.class);
//			xStream.alias("ArrayDataReader", ExtArrayDataReader.class);
//			xStream.alias("JsonDataReader", ExtJsonDataReader.class);
//			xStream.alias("XmlDataReader", ExtXmlDataReader.class);
//			xStream.alias("DataStore", ExtDataStore.class);
//			xStream.alias("Listener", ExtListener.class);
//			xStream.alias("ListenerArray", ExtListenerArray.class);
//			xStream.alias("PredefinedElement", ExtPredefinedElement.class);
//			xStream.alias("ColumnModel", ExtColumnModel.class);
//			xStream.alias("PagingToolbar", ExtPagingToolbar.class);
//			xStream.alias("TreeLoader", ExtTreeLoader.class);
//			xStream.alias("TreeNode", ExtTreeNode.class);
//			xStream.alias("Toolbar", ExtToolbar.class);
//			xStream.alias("ToolbarItem", ExtToolbarItem.class);
//			xStream.alias("ToolbarSeperator", ExtToolbarSeperator.class);
//			xStream.alias("ToolbarSpacer", ExtToolbarSpacer.class);
//			xStream.alias("ToolbarFill", ExtToolbarFill.class);
//			xStream.alias("ToolbarTextItem", ExtToolbarTextItem.class);
//			xStream.alias("ToolbarSeperator", ExtToolbarSeperator.class);
//
//		}
//		return xStream;
//	}

	public static byte[] save(ExtDiagram diagram) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(bos);
			os.writeObject(diagram);
			os.close();
		} catch (IOException e) {
		}
		return bos.toByteArray();
//		XStream xStream = getXStream();
//		String result = xStream.toXML(this);
//		return result;
	}

	public static ExtDiagram load(byte[] bytes) {
		ByteArrayInputStream bis= new ByteArrayInputStream(bytes);
		ObjectInputStream is;
		try {
			is = new ObjectInputStream(bis);
			return (ExtDiagram) is.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ExtDiagram();
		
//		XStream xStream = getXStream();
//		xStream.fromXML(is, this);
	}

}
