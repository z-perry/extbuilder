package com.harbortek.extbuilder.extract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ExtEvent {
	private String eventName;

	private String comment;

	private ExtClass extClass;

	private List parameters;

	String content;

	int index;

	boolean hide;

	public ExtEvent() {

	}

	public String getComment() {
		return comment != null ? comment : "";
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public ExtClass getExtClass() {
		return extClass;
	}

	public void setExtClass(ExtClass extClass) {
		this.extClass = extClass;
	}

	public List getParameters() {
		return parameters;
	}

	public void setParameters(List parameters) {
		this.parameters = parameters;
	}

	public void addParameter(ExtParameter p) {
		if (this.parameters == null) {
			this.parameters = new ArrayList();
		}
		this.parameters.add(p);
	}

	public void parse(String content) {
		this.content = content;
		index = 0;
		eventName = getNextValue("@event");

		int cEnd = content.indexOf('@', index);
		if (cEnd < 0) {
			cEnd = content.length();
		}
		comment = content.substring(index, cEnd);
		comment = StringUtils.replace(comment, "*", "");
		String[] s = StringUtils.split(comment, "\r\n");
		for (int i = 0; i < s.length; i++) {
			s[i] = StringUtils.trim(s[i]);
		}
		comment = StringUtils.join(s," ");
		comment = StringUtils.trim(comment);
		comment = StringUtils.replace(comment, "\"", "'");
		while (true) {
			String param = getNextValue("@param");
			if (StringUtils.isEmpty(param)) {
				break;
			}
			StringTokenizer st = new StringTokenizer(param, "{} ");

			String paraType = st.nextToken(); // param.substring(param.indexOf('{')+1,
			// param.lastIndexOf('}'));
			String paraName = st.nextToken();// param.substring(param.lastIndexOf('}')+1);
			
			String paraComment = "";
			if (st.hasMoreTokens()){
				paraComment = st.nextToken("");
				if (StringUtils.isNotEmpty(paraComment)){
					paraComment = StringUtils.trim(paraComment);
					comment = StringUtils.replace(comment, "\"", "'");
				}
			}
			addParameter(new ExtParameter(paraName, paraType,paraComment));
		}
	}

	private String getNextValue(String type) {
		if (index > content.length()) {
			return null;
		}
		int start = content.indexOf(type, index);
		if (start >= 0) {
			index = start;
			int end = content.indexOf("\n", start);
			if (end > 0) {
				String line = content.substring(start + type.length(), end);
				index = end;
				return line.replaceAll("\r", "").replaceAll("\n", "").trim();
			}
		}
		return null;
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Event ").append("name=\"").append(getEventName()).append(
		"\"");
		if (StringUtils.isNotEmpty(getComment())){
			sb.append(" comment=\"").append(getComment()).append("\"");
		}
		sb.append(" >").append("\n");

		if (getParameters() != null) {
			for (Iterator iter = getParameters().iterator(); iter.hasNext();) {
				ExtParameter p = (ExtParameter) iter.next();
				sb.append("<Param ").append("name=\"").append(
						p.getParameterName()).append("\" type=\"").append(
						p.getParameterType()).append("\"");
				if (StringUtils.isNotEmpty(p.getParameterCommnet())){
					sb.append(" comment=\"").append(p.getParameterCommnet()).append("\"");
				}
				sb.append(" />").append("\n");
			}
		}

		sb.append("</Event>");
		return sb.toString();
	}

}
