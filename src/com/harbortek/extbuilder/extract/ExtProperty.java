package com.harbortek.extbuilder.extract;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.model.data.ExtDataStore;
import com.harbortek.extbuilder.model.grid.ExtColumnModel;
import com.harbortek.extbuilder.model.grid.ExtPagingToolbar;
import com.harbortek.extbuilder.model.grid.ExtRowSelectionModel;
import com.harbortek.extbuilder.model.tree.ExtTreeLoader;
import com.harbortek.extbuilder.model.tree.ExtTreeNode;

public class ExtProperty {
	private String propertyName;

	private String propertyType;

	private Object propertyValue;

	private Object defaultValue;

	private boolean hide;

	private boolean advanced;

	private String[] values;

	private String comment;
	
	public ExtProperty(){
		
	}

	public ExtProperty(String propertyName, String propertyType, String comment) {
		super();
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.comment = comment;
	}

	public ExtProperty(String propertyName, String propertyType, boolean hide) {
		super();
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.hide = hide;
	}

	public String getComment() {
		return comment!=null? comment:"";
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public boolean isAdvanced() {
		return advanced;
	}

	public void setAdvanced(boolean advanced) {
		this.advanced = advanced;
	}

	public Object getPropertyValue() {
		if (propertyValue == null) {
			return getDefaultValue();
		}
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public Object getDefaultValue() {
		if (defaultValue == null) {
			if ("String".equalsIgnoreCase(propertyType)) {
				return "";
			} else if ("Number".equalsIgnoreCase(propertyType)) {
				// return new Integer(0);
			} else if ("Boolean".equalsIgnoreCase(propertyType)) {

			} else if ("Combox".equalsIgnoreCase(propertyType)) {
				return "";
			} else if ("Ext.tree.TreeNode".equalsIgnoreCase(propertyType)) {
				return new ExtTreeNode();
			} else if ("Ext.tree.TreeLoader".equalsIgnoreCase(propertyType)) {
				return new ExtTreeLoader();
			} else if ("Ext.data.Store".equalsIgnoreCase(propertyType)) {
				return new ExtDataStore();
			} else if ("Ext.PagingToolbar".equalsIgnoreCase(propertyType)) {
				return new ExtPagingToolbar();
			} else if ("Ext.grid.ColumnModel".equalsIgnoreCase(propertyType)) {
				return new ExtColumnModel();
			} else if ("Ext.grid.SelectionModel".equalsIgnoreCase(propertyType)){
				return new ExtRowSelectionModel();
			}
		}
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Property ").append("name=\"").append(getPropertyName())
				.append("\" type=\"").append(getPropertyType()).append(
						"\"");
		if (StringUtils.isNotEmpty(getComment())){
			sb.append(" comment=\"").append(getComment()).append("\"");
		}
		sb.append(" />");
		return sb.toString();
	}
	
	public void parse(String content){
//		Pattern p = Pattern.compile("@cfg\\s\\{(.*)\\}\\s(\\S*)",Pattern.MULTILINE);
//		Matcher matcher = p.matcher(content);
//		if (matcher.matches()){
//			propertyType = StringUtils.trim( matcher.group(1));
//			propertyName = StringUtils.trim( matcher.group(2));
//			comment = StringUtils.trim( matcher.group(3));
//			comment = StringUtils.replace(comment, "\"", "'");
//			//addProperty(new ExtProperty(propName, propType,propComment));
//		}
//		p = Pattern.compile("\\{(.*)\\}\\s(\\S*)$");
//		matcher = p.matcher(content);
//		if (matcher.matches()){
//			String propType = StringUtils.trim( matcher.group(1));
//			String propName = StringUtils.trim( matcher.group(2));
//			//addProperty(new ExtProperty(propName, propType,""));
//		}
		
		if (content.indexOf("{") >= 0) {
			content = content.substring("@cfg ".length());
			StringTokenizer st = new StringTokenizer(content, "{} \r\n\t");
			propertyType = st.nextToken(); 
			propertyName = st.nextToken(); 
			
			
			comment = content.substring(("{"+propertyType+"} "+propertyName).length());
			
			comment = StringUtils.replace(comment, "*", "");
			String[] s = StringUtils.split(comment, "\r\n");
			for (int i = 0; i < s.length; i++) {
				s[i] = StringUtils.trim(s[i]);
			}
			comment = StringUtils.join(s," ");
			comment = StringUtils.trim(comment);
			comment = StringUtils.replace(comment, "\"", "'");
		}
	}

}
