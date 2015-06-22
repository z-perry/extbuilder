package com.harbortek.extbuilder.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.xmodel.ExtDiagram;
import com.harbortek.extbuilder.xmodel.ExtElement;

/**
 * �ؼ�Id������
 * 
 * @author ���п�
 * 
 */
public class XComponentUtils {

	public static Collection getComponents(ExtElement ct) {
		if (ct == null) {
			return Collections.EMPTY_SET;
		}
		LinkedHashSet list = new LinkedHashSet();

		List list2 = ct.getReferencedItems();
		if (list2!=null){
			for (Iterator iter = list2.iterator(); iter.hasNext();) {
				ExtElement comp = (ExtElement) iter.next();
				list.add(comp);
				list.addAll(getComponents(comp));
			}
		}

		return list;
	}

	public static List findHasComponentNameComponents(ExtElement ct) {
		Collection list = getComponents(ct);
		List list2 = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ExtElement element = (ExtElement) iter.next();
			if (StringUtils.isNotEmpty(element.getVariableName())) {
				list2.add(element);
			}
		}
		Collections.reverse(list2);
		return list2;
	}
	

	/**
	 * ��4��ɴ�UI��Ψһ�Ŀؼ�id
	 * 
	 * @param component
	 * @return
	 */
	public static String generateName(ExtDiagram diagram, ExtElement component) {
		String modelName = component.getExtClassName();
		int index = modelName.lastIndexOf('.');
		if (index>0){
			modelName = modelName.substring(index+1);
			modelName = StringUtils.uncapitalize(modelName);
		}
		
		Collection components = getComponents(diagram);

		int maxSeqNum = 0;
		for (Iterator iter = components.iterator(); iter.hasNext();) {
			ExtElement comp = (ExtElement) iter.next();
			String compId = comp.getVariableName();
			if (compId!=null && compId.startsWith(modelName)) {
				String tail = compId.substring(modelName.length(), compId.length());
				int intTail = -1;
				try {
					intTail = Integer.parseInt(tail);
				} catch (Exception e) {
				}
				if (intTail > 0) {
					if (intTail > maxSeqNum) {
						maxSeqNum = intTail;
					}
				}
			}
		}
		return modelName + (maxSeqNum + 1);
	}
	
	public static void generateNameForAll(ExtDiagram diagram, ExtElement component){
		if (StringUtils.isEmpty(component.getVariableName())){
			component.setVariableName(generateName(component.getDiagram(), component));
		}
		component.scanReferencedItems();
		Collection c = XComponentUtils.getComponents(component);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			ExtElement comp = (ExtElement) iter.next();
			if (comp.isUseVariableName()
					&& StringUtils.isEmpty(comp.getVariableName())) {
				comp.setVariableName(XComponentUtils.generateName(
						component.getDiagram(), comp));
			}
		}
	}

	public static ExtDiagram getDiagram(ExtElement comp) {
		while (comp.getParent() != null) {
			comp = comp.getParent();
		}
		if (comp instanceof ExtDiagram) {
			return (ExtDiagram) comp;
		}
		return null;
	}
	

	public static Object cloneComponent(Object comp) {
//		XStream xStream = new XStream(new DomDriver());
//		String result = xStream.toXML(comp);
//		return xStream.fromXML(result);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(bos);
			os.writeObject(comp);
			os.close();
		} catch (IOException e) {
		}
		ByteArrayInputStream bis= new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream is;
		try {
			is = new ObjectInputStream(bis);
			return is.readObject();
		} catch (Exception e) {
		}
		return null;
	}

}
