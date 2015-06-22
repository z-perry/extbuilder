package com.harbortek.extbuilder.xmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Preferences;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.extract.ExtClass;
import com.harbortek.extbuilder.extract.ExtEvent;
import com.harbortek.extbuilder.extract.ExtParameter;
import com.harbortek.extbuilder.extract.ExtProperty;
import com.harbortek.extbuilder.ui.preferences.PreferenceConstants;

public class ExtXmlManager {
	static Map categories = new LinkedHashMap();

	static Map elements = new HashMap();
	
	
	public static ExtClass getExtClass(String extClassId){
		ExtClass extClass = (ExtClass) elements.get(extClassId);
		return extClass;
	}

	public static Collection getProperties(String extClassId) {

		ExtClass extClass = (ExtClass) elements.get(extClassId);
		
		Collection list = null;
		List hideList = null;
		if (extClass != null) {
			list = extClass.getInheritedProperties().values();
			hideList = extClass.getInheritedHideProperties();
			if (hideList!=null && hideList.size()>0){
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					ExtProperty prop = (ExtProperty) iter.next();
					if (hideList.indexOf(prop.getPropertyName())>=0){
						iter.remove();
					}
				}
			}
			return list;
		}

		return null;
	}
	
	public static Collection getEvents(String extClassId) {

		ExtClass extClass = (ExtClass) elements.get(extClassId);
		
		Collection list = null;
		List hideList = null;
		if (extClass != null) {
			list = extClass.getInheritedEvents();
			hideList = extClass.getInheritedHideEvents();
			if (hideList!=null && hideList.size()>0){
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					ExtEvent prop = (ExtEvent) iter.next();
					if (hideList.indexOf(prop.getEventName())>=0){
						iter.remove();
					}
				}
			}
			return list;
		}

		return null;
	}

	public static ExtProperty getProperty(String extClassId, String propertyName) {
		ExtClass extClass = (ExtClass) elements.get(extClassId);

		if (extClass != null) {
			return extClass.getProperty(propertyName);
		}
		return null;
	}

	public static String[] getCategories() {
		Collection keys = categories.keySet();
		String[] rs = (String[]) keys.toArray(new String[keys.size()]);
		//Arrays.sort(rs);
		return rs;
	}

	public static List getExtClassList(String category){
		List list = (List)categories.get(category);
		return list;
	}
	
	public static boolean isContainer(String extClassId){
		ExtClass extClass = (ExtClass) elements.get(extClassId);
		if (extClass != null) {
			return extClass.isContainer();
		}
		return false;
	}
	
	public static void reload(){
		categories = new HashMap();
		elements = new HashMap();
		loadXmlDefinitions();
	}

	public static void loadXmlDefinitions() {
		InputStream is = ExtBuilderActivator.getDefault().getClass().getClassLoader().getResourceAsStream(
				"com/harbortek/extbuilder/Library.xml");
		readLibraryFile(is);
		
		readExtensionXmlDefinitions();
		
		for (Iterator iter = elements.values().iterator(); iter.hasNext();) {
			ExtClass clz = (ExtClass) iter.next();
			String superclass = clz.getSuperClassName();
			if (StringUtils.isNotEmpty(superclass)) {
				ExtClass sc = (ExtClass) elements.get(superclass);
				if (sc != null) {
					sc.addSubClass(clz);
				}
			}
		}

	}
	
	private static void readExtensionXmlDefinitions(){
		Preferences preferences = ExtBuilderActivator.getDefault().getPluginPreferences();
		String uxFiles = preferences.getString(PreferenceConstants.UX_XML_PATH);
		List files = Arrays.asList(StringUtils.split(uxFiles, ";"));
		for (Iterator iter = files.iterator(); iter.hasNext();) {
			String  fileName = (String ) iter.next();
			File file = new File(fileName);
			if (file.exists()){
				try {
					readLibraryFile(new FileInputStream(file));
				} catch (FileNotFoundException e) {
				}
			}
		}
	}
	
	private static void readLibraryFile(InputStream is){
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(is);

			Element root = document.getRootElement();
			Iterator iter = root.elementIterator();
			for (; iter.hasNext();) {
				Element element = (Element) iter.next();
				String file = element.attributeValue("file",null);
				if (file != null) {
					readXmlDefinition(file);
				}else{
					readXmlDefinition(element);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static void readXmlDefinition(String file) {
		InputStream is = ExtBuilderActivator.getDefault().getClass().getClassLoader().getResourceAsStream(file);
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(is);

			Element root = document.getRootElement();
			readXmlDefinition(root);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(file);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static void readXmlDefinition(Element root) {
		ExtClass extClass = new ExtClass();

		String category = root.attributeValue("category", "");
		String extClassName = root.attributeValue("extClass");
		String superClassName = root.attributeValue("extends");
		String id = root.attributeValue("id", extClassName);
		String label = root.attributeValue("label", "");
		String xtype = root.attributeValue("xtype","");
		boolean useXtype = "true".equalsIgnoreCase( root.attributeValue("useXtype","true") );
		boolean useVariableName = "true".equalsIgnoreCase( root.attributeValue("useVariableName","false") );
		boolean isContainer = "true".equalsIgnoreCase( root.attributeValue("isContainer","false") );
		
		extClass.setId(id);
		extClass.setClassName(extClassName);
		extClass.setSuperClassName(superClassName);
		extClass.setLabel(label);
		extClass.setXtype(xtype);
		extClass.setUseXtype(useXtype);
		extClass.setUseVariableName(useVariableName);
		extClass.setContainer(isContainer);
		

		Element propEls = root.element("Properties");
		List els = propEls.elements("Property");
		for (Iterator iter = els.iterator(); iter.hasNext();) {
			Element el = (Element) iter.next();
			String name = el.attributeValue("name");
			boolean hide = "true".equals( el.attributeValue("hide","false") );
			if (hide){
				extClass.addHideProperty(name);
				continue;
			}
			String type = el.attributeValue("type");
			String comment = el.attributeValue("comment", "");
			String value = el.attributeValue("value", null);
			String defaultValue = el.attributeValue("default", null);
			String isAdvanced = el.attributeValue("advanced", "false");
			String[] values = StringUtils.split( el.attributeValue("values",""),",");
			
			for (int i = 0; i < values.length; i++) {
				String string = values[i];
				if (StringUtils.isEmpty(StringUtils.trim(string))){
					values[i] = "";
				}
			}
			
			ExtProperty p = new ExtProperty(name, type,comment);
			p.setPropertyValue(value);
			p.setDefaultValue(defaultValue);
			p.setHide(hide);
			
			p.setValues( values);
			p.setAdvanced("true".equalsIgnoreCase(isAdvanced));

			extClass.addProperty(p);
		}
		
		Element eventEls = root.element("Events");
		if (eventEls!=null){
			els = eventEls.elements("Event");
			for (Iterator iter = els.iterator(); iter.hasNext();) {
				Element el = (Element) iter.next();
				String name = el.attributeValue("name");
				String comment = el.attributeValue("comment", "");
				boolean hide = "true".equals( el.attributeValue("hide","false") );
				if (hide){
					extClass.addHideEvent(name);
					continue;
				}
				ExtEvent event = new ExtEvent();
				event.setEventName(name);
				event.setComment(comment);
				event.setHide(hide);
				event.setExtClass(extClass);

				List paramsEls = el.elements("Param");
				for (Iterator iter2 = paramsEls.iterator(); iter2.hasNext();) {
					Element paraEl = (Element) iter2.next();
					String paraName = paraEl.attributeValue("name");
					String paraType = paraEl.attributeValue("type");
					String paraComment = paraEl.attributeValue("comment", "");
					ExtParameter extPara = new ExtParameter(paraName, paraType,paraComment);
					event.addParameter(extPara);
				}

				extClass.addEvent(event);
			}
		}
		

		List comps = (List) categories.get(category);
		if (comps == null) {
			comps = new ArrayList();
			categories.put(category, comps);
		}
		comps.add(extClass);

		elements.put(id, extClass);
	}
}
