package com.harbortek.extbuilder.xmodel;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.code.json.SerializerManager;
import com.harbortek.extbuilder.model.ExtListenerArray;

public abstract class ExtElement implements ExtSerializable,Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean useXtype;

	private boolean useVariableName;
	
	private List referencedItems;

	private String variableName;

	private ExtElement parent;
	
	private ExtListenerArray listenerArray;
	
	protected Map values = new HashMap();

	protected List items = new ArrayList();
	
	private ExtDiagram diagram;
	
	public ExtDiagram getDiagram() {
		return diagram;
	}

	public void setDiagram(ExtDiagram diagram) {
		this.diagram = diagram;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
		if (StringUtils.isNotEmpty(variableName)) {
			if (parent!=null){
				parent.addReferencedItem(this);
			}
		}
	}
	
	public void setUseVariableName(boolean useVariableName) {
		this.useVariableName = useVariableName;
	}

	public void setUseXtype(boolean useXtype) {
		this.useXtype = useXtype;
	}

	public void setReferencedItems(List referencedItems) {
		this.referencedItems = referencedItems;
	}

	public void addReferencedItem(ExtElement el) {
		if (this.referencedItems == null) {
			this.referencedItems = new ArrayList();
		}
		if (!this.referencedItems.contains(el)){
			this.referencedItems.add(el);
		}
	}

	public void removeReferencedItem(ExtElement el) {
		if (referencedItems != null) {
			referencedItems.remove(el);
		}
	}

	public String getVariableName() {
		return variableName;
	}

	public boolean isUseVariableName() {
		return useVariableName;
	}

	public boolean isUseXtype() {
		return useXtype;
	}

	public List getReferencedItems() {
		return referencedItems;
	}
	
	public void scanReferencedItems(){
		if (referencedItems!=null){
			referencedItems.clear();
		}
		
		if (values!=null){
			for (Iterator iter = values.values().iterator(); iter.hasNext();) {
				ExtElement element = (ExtElement) iter.next();
				element.scanReferencedItems();
				if (element.isUseVariableName()){
					addReferencedItem(element);
				}
			}
		}
		if (items!=null){
			for (Iterator iter = items.iterator(); iter.hasNext();) {
				ExtElement element = (ExtElement) iter.next();
				element.scanReferencedItems();
				addReferencedItem(element);
			}
		}
	}

	public ExtElement getParent() {
		return parent;
	}

	public void setParent(ExtElement parent) {
		this.parent = parent;
	}

	public ExtListenerArray getListenerArray() {
		return listenerArray;
	}

	public void setListenerArray(ExtListenerArray listenerArray) {
		this.listenerArray = listenerArray;
	}
	
	public List getItems() {
		return items;
	}

	public String getCode(CodeContext codeContext) {
		
		if (isUseVariableName()){
			StringBuffer sb = new StringBuffer();

			sb.append("\t\tthis.").append(getVariableName()).append(" = new ")
					.append(getExtClassName()).append("(");

			sb.append(getConfigString(codeContext));

			sb.append("\t\t").append(");").append("\n");
			return sb.toString();
		}else{
			return getConfigString(codeContext);
		}
		
	}

	public String getConfigString(CodeContext codeContext) {

		StringWriter writer = new StringWriter();
		Object o = translate(codeContext);
		SerializerManager.serialize(o, writer, codeContext);
		try {
			writer.close();
		} catch (IOException e) {
		}
		if (isUseXtype() || StringUtils.isEmpty( getExtClassName()) || isUseVariableName() ) {
			return writer.toString();
		} else {
			return "new " + getExtClassName() + "(" + writer.toString() + ")";
		}
	}

	public abstract String getExtClassName();

	public abstract Object translate(CodeContext codeContext);
	
	public abstract String getXtype();

}
