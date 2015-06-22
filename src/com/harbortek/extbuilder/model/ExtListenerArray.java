package com.harbortek.extbuilder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.extract.ExtEvent;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtXmlManager;

public class ExtListenerArray extends ExtElement {
	private static final long serialVersionUID = 1L;

	private List listeners;

	private String extClassId;

	public ExtListenerArray(String extClassId) {
		this.extClassId = extClassId;
	}

	public List getListeners() {
		return listeners;
	}

	public void setListeners(List listeners) {
		this.listeners = listeners;
	}

	public String getExtClassId() {
		return extClassId;
	}

	public void setExtClassId(String extClassId) {
		this.extClassId = extClassId;
	}

	public List getPredefinedListeners() {
		ArrayList list = new ArrayList();
		List events = (List) ExtXmlManager.getEvents(extClassId);
		if (events != null) {
			for (Iterator iter = events.iterator(); iter.hasNext();) {
				ExtEvent event = (ExtEvent) iter.next();
				ExtListener listener = new ExtListener();
				listener.setEventName(event.getEventName());
				listener.setExtEvent(event);
				listener.setFunctionName("");
				listener.setComment("");
				list.add(listener);
			}
		}
		if (listeners != null) {
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				ExtListener element = (ExtListener) iter.next();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					ExtListener listener = (ExtListener) iterator.next();
					if (listener.getEventName().equals(element.getEventName())) {
						listener.setFunctionName(element.getFunctionName());
						break;
					}
				}
			}
		}
		return list;
	}

	public String getExtClassName() {
		return null;
	}

	public String getXtype() {
		return null;
	}

	public Object translate(CodeContext codeContext) {
		Map map = new HashMap();

		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			ExtListener listener = (ExtListener) iter.next();
			map.put(listener.getEventName(), listener);
		}

		return map;
	}

	public String toString() {
		if (listeners != null) {
			String[] s = new String[listeners.size()];
			int i = 0;
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				ExtListener listener = (ExtListener) iter.next();
				s[i++] = listener.getEventName();
			}
			return StringUtils.join(s, ",");
		}
		return "";
	}

}
