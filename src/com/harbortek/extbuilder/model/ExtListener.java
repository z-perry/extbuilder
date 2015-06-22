package com.harbortek.extbuilder.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.extract.ExtEvent;
import com.harbortek.extbuilder.extract.ExtParameter;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtScript;

public class ExtListener extends ExtElement {
	private static final long serialVersionUID = 1L;

	private String eventName;
	private String functionName;
	private String comment;
	private ExtEvent extEvent;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public ExtEvent getExtEvent() {
		return extEvent;
	}

	public void setExtEvent(ExtEvent extEvent) {
		this.extEvent = extEvent;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getExtClassName() {
		return null;
	}

	public String getXtype() {
		return null;
	}

	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append("function(");
		for (Iterator iter = extEvent.getParameters().iterator(); iter.hasNext();) {
			ExtParameter para = (ExtParameter) iter.next();
			if ("this".equalsIgnoreCase(para.getParameterName())){
				sb.append(extEvent.getExtClass().getXtype());
			}else{
				sb.append(para.getParameterName());
			}
			if (iter.hasNext()){
				sb.append(",");
			}
		}
		sb.append("){").append("\n");
		sb.append("this.").append(getFunctionName()).append("(");
		for (Iterator iter = extEvent.getParameters().iterator(); iter.hasNext();) {
			ExtParameter para = (ExtParameter) iter.next();
			if ("this".equalsIgnoreCase(para.getParameterName())){
				sb.append(extEvent.getExtClass().getXtype());
			}else{
				sb.append(para.getParameterName());
			}
			if (iter.hasNext()){
				sb.append(",");
			}
		}
		sb.append(");").append("\n");
		sb.append("}.createDelegate(this)");
		
		map.put("fn",  new ExtScript(sb.toString()));
		return map;
	}

}
