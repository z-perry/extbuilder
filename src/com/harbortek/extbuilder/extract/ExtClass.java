package com.harbortek.extbuilder.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ExtClass {
	private transient String className;

	transient String content;

	private transient List events;

	private transient List hideProperties;

	private transient List hideEvents;

	private transient String id;

	transient int index;

	transient private String label;

	private transient Map properties;

	private transient List subClasses;

	transient private ExtClass superClass;

	private transient String superClassName;

	private transient String xtype;

	private transient boolean useXtype;

	private transient boolean useVariableName;

	private transient boolean container;

	public void setContainer(boolean container) {
		this.container = container;
	}

	public void addEvent(ExtEvent e) {
		if (this.events == null) {
			this.events = new ArrayList();
		}
		this.events.add(e);
	}

	public void addProperty(ExtProperty p) {
		if (this.properties == null) {
			this.properties = new HashMap();
		}
		this.properties.put(p.getPropertyName(), p);
	}

	public void addHideEvent(String e) {
		if (this.hideEvents == null) {
			this.hideEvents = new ArrayList();
		}
		this.hideEvents.add(e);

		if (events != null) {
			for (Iterator iter = events.iterator(); iter.hasNext();) {
				ExtEvent ev = (ExtEvent) iter.next();
				if (ev.getEventName().equals(e)) {
					iter.remove();
				}
			}
		}
	}

	public void addHideProperty(String p) {
		if (this.hideProperties == null) {
			this.hideProperties = new ArrayList();
		}
		this.hideProperties.add(p);

		if (properties != null) {
			for (Iterator iter = properties.values().iterator(); iter.hasNext();) {
				ExtProperty prop = (ExtProperty) iter.next();
				if (prop.getPropertyName().equals(p)) {
					iter.remove();
				}
			}
		}
	}

	public void addSubClass(ExtClass clz) {
		if (subClasses == null) {
			subClasses = new ArrayList();
		}
		subClasses.add(clz);
		clz.setSuperClass(this);
	}

	public String getClassName() {
		return className;
	}

	public List getEvents() {
		return events;
	}

	public List getHideEvents() {
		return hideEvents;
	}

	public List getHideProperties() {
		return hideProperties;
	}

	public List getInheritedEvents() {
		List list1 = null;
		if (superClass != null) {
			list1 = superClass.getInheritedEvents();
		} else {
			list1 = new ArrayList();
		}
		if (events != null) {
			list1.addAll(events);
		}
		return list1;
	}

	public String[] getFullExtClassPath() {
		ArrayList list = new ArrayList();
		ExtClass clz = this;
		while (clz != null) {
			list.add(clz.getClassName());
			clz = clz.superClass;
		}
		Collections.reverse(list);
		return (String[]) list.toArray(new String[list.size()]);
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	private String getNextComment(String contains) {
		if (index > content.length()) {
			return null;
		}
		int start = content.indexOf(contains, index);
		if (start > 0) {
			index = start;
			int end = content.indexOf("*/", start);
			if (end > 0) {
				String line = content.substring(start, end);
				index = end;
				return line;
			}
		}
		return null;
	}

	private String getNextValue(String type) {
		if (index > content.length()) {
			return null;
		}
		int start = content.indexOf(type, index);
		if (start > 0) {
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

	public Map getInheritedProperties() {
		Map list1 = null;
		if (superClass != null) {
			list1 = superClass.getInheritedProperties();
		} else {
			list1 = new HashMap();
		}
		if (properties != null) {
			list1.putAll(properties);
		}
		return list1;
	}

	public Collection getProperties() {
		if (properties == null) {
			return Collections.EMPTY_LIST;
		}

		List list = new ArrayList();
		list.addAll(properties.values());
		return list;
	}

	public ExtProperty getProperty(String propertyName) {
		
		ExtProperty prop = null;
		if (properties != null) {
			prop = (ExtProperty) properties.get(propertyName);
		}
			
		if (prop == null) {
			if (superClass != null) {
				return superClass.getProperty(propertyName);
			}
		}
		return prop;
	}

	public List getInheritedHideProperties() {
		List list1 = null;
		if (superClass != null) {
			list1 = superClass.getInheritedHideProperties();
		} else {
			list1 = new ArrayList();
		}
		if (hideProperties != null) {
			list1.addAll(hideProperties);
		}
		return list1;
	}

	public List getInheritedHideEvents() {
		List list1 = null;
		if (superClass != null) {
			list1 = superClass.getInheritedHideEvents();
		} else {
			list1 = new ArrayList();
		}
		if (hideEvents != null) {
			list1.addAll(hideEvents);
		}
		return list1;
	}

	public List getSubClasses() {
		return subClasses;
	}

	public ExtClass getSuperClass() {
		return superClass;
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public boolean isContainer() {
		if (container) {
			return true;
		}
		String[] path = getFullExtClassPath();
		for (int i = 0; i < path.length; i++) {
			String p = path[i];
			if (p != null && p.indexOf("Ext.Container") >= 0) {
				return true;
			}
		}
		return false;
	}

	public void parse(String content) {
		this.content = content;
		index = 0;

		String className = getNextValue("@class");
		String superClassName = getNextValue("@extends");
		setClassName(className);
		if (superClassName != null && !superClassName.equals(className))
			setSuperClassName(superClassName);

		while (true) {
			String event = getNextComment("@event");
			if (StringUtils.isEmpty(event) || event.indexOf("@hide") > 0) {
				break;
			}
			ExtEvent ev = new ExtEvent();
			ev.parse(event);
			addEvent(ev);
		}
		index = 0;
		while (true) {
			String prop = getNextComment("@cfg");
			if (StringUtils.isEmpty(prop) || prop.indexOf("@hide") > 0) {
				break;
			}
			try {
				ExtProperty p = new ExtProperty();
				p.parse(prop);
				addProperty(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// add hide property
		Pattern p = Pattern.compile("@cfg\\s(.*)\\s@hide$", Pattern.MULTILINE);
		Matcher matcher = p.matcher(content);
		while (matcher.find()) {
			String prop = matcher.group(1);
			try {
				if (prop.indexOf("{") >= 0) {
					StringTokenizer st = new StringTokenizer(prop, "{} ");
					if (st.hasMoreTokens()) {
						String propType = st.nextToken();
					}
					if (st.hasMoreTokens()) {
						String propName = st.nextToken();
						addHideProperty(propName);
					}
				} else {
					String propName = StringUtils.trim(prop);
					addHideProperty(propName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// add hide event
		p = Pattern.compile("@event\\s(\\w*)\r\n\\s*\\*\\s@hide$",
				Pattern.DOTALL | Pattern.MULTILINE);
		matcher = p.matcher(content);
		while (matcher.find()) {
			String event = matcher.group(1);
			event = StringUtils.trim(event);
			if (StringUtils.isNotEmpty(event)) {
				addHideEvent(StringUtils.trim(event));
			}
		}
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setEvents(List event) {
		this.events = event;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setSubClasses(List subClasses) {
		this.subClasses = subClasses;
	}

	public void setSuperClass(ExtClass superClass) {
		this.superClass = superClass;
	}

	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public boolean isUseVariableName() {
		return useVariableName;
	}

	public void setUseVariableName(boolean useVariableName) {
		this.useVariableName = useVariableName;
	}

	public boolean isUseXtype() {
		return useXtype;
	}

	public void setUseXtype(boolean useXtype) {
		this.useXtype = useXtype;
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<Component ").append("extClass=\"").append(getClassName())
				.append("\"");
		sb.append(" extends=\"").append(getSuperClassName()).append("\"");
		sb.append(">").append("\n");

		sb.append("<Properties>").append("\n");
		if (getProperties() != null) {
			Collection props = getProperties();
			Collections.sort((List) props, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					ExtProperty p1 = (ExtProperty) arg0;
					ExtProperty p2 = (ExtProperty) arg1;
					return p1.getPropertyName().compareTo(p2.getPropertyName());
				}
			});
			for (Iterator iter = props.iterator(); iter.hasNext();) {
				ExtProperty p = (ExtProperty) iter.next();
				sb.append(p.toXML()).append("\n");
			}
		}
		if (getHideProperties() != null) {
			for (Iterator iter = hideProperties.iterator(); iter.hasNext();) {
				String p = (String) iter.next();
				sb.append("<Property name=\"").append(p).append(
						"\" hide=\"true\" />").append("\n");
			}
		}
		sb.append("</Properties>").append("\n");

		sb.append("<Events>").append("\n");
		if (getEvents() != null) {
			List evs = getEvents();
			Collections.sort((List) evs, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					ExtEvent p1 = (ExtEvent) arg0;
					ExtEvent p2 = (ExtEvent) arg1;
					return p1.getEventName().compareTo(p2.getEventName());
				}
			});
			for (Iterator iter = evs.iterator(); iter.hasNext();) {
				ExtEvent p = (ExtEvent) iter.next();
				sb.append(p.toXML()).append("\n");
			}
		}
		if (getHideEvents() != null) {
			for (Iterator iter = hideEvents.iterator(); iter.hasNext();) {
				String e = (String) iter.next();
				sb.append("<Event name=\"").append(e).append(
						"\" hide=\"true\" />").append("\n");
			}
		}
		sb.append("</Events>").append("\n");
		sb.append("</Component>").append("\n");

		return sb.toString();
	}

}
