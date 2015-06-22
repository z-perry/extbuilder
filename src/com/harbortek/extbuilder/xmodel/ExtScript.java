package com.harbortek.extbuilder.xmodel;

import org.apache.commons.lang.StringUtils;

public class ExtScript {
	private String content;

	public ExtScript(String c){
		setContent(c);
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content.trim();
	}
	
	public boolean isSingleWord(){
		if (StringUtils.indexOfAny(content, new char[]{'+','-','(','{',' ','.'})>=0){
			return false;
		}
		return true;
	}
	
}
