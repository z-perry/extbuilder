package com.harbortek.extbuilder.model.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtScript;

public class ExtHttpProxy extends ExtDataProxy {
	private static final long serialVersionUID = 1L;

	private String url;

	public ExtHttpProxy() {

	}

	public ExtHttpProxy(String url) {
		this.url = url;
	}

	public String getUrl() {
		if (url==null){
			return  "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExtClassName() {
		return "Ext.data.HttpProxy";
	}

	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(url)){
			map.put("url", new ExtScript(url));
		}
		return map;
	}

//	public String getConfigString(CodeContext codeContext) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("new ").append(getExtClassName()).append("(");
//		sb.append("{ url:");
//		if (getUrl() != null && getUrl().indexOf("(") > 0) {
//			sb.append(getUrl());
//		} else {
//			sb.append("'").append(getUrl()).append("'");
//		}
//		sb.append("})");
//		return sb.toString();
//	}
	
	
}
