package com.harbortek.extbuilder.model.grid;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtPredefinedElement;

public class ExtColumnModel extends ExtPredefinedElement {
	private static final long serialVersionUID = 1L;

	private ArrayList columns = new ArrayList();

	public ExtColumnModel() {
		super();
		setUseVariableName(false);
		setUseXtype(true);
		this.columns.add(new ExtColumn("name", "name"));
		this.columns.add(new ExtColumn("age", "age"));
	}

	public ArrayList getColumns() {
		return columns;
	}

	public void setColumns(ArrayList config) {
		this.columns = config;
	}

	public Object translate(CodeContext codeContext) {
		if (getHost()!=null){
			Object sm = getHost().getPropertyValue("selModel");
			if (sm instanceof ExtCheckboxSelectionModel){
				ArrayList list = new ArrayList();
				list.add(sm);
				list.addAll(columns);
				return list;
			}
		}
		return columns;
	}

	public String getExtClassName() {
		return "Ext.grid.ColumnModel";
	}

	public String toString() {
		String[] s = new String[getColumns().size()];
		int i = 0;
		for (Iterator iter = getColumns().iterator(); iter.hasNext();) {
			Object col = (Object) iter.next();
			s[i++] = col.toString();
		}
		return StringUtils.join(s, ",");
	}

	public String getXtype() {
		return null;
	}

	
}
