package com.harbortek.extbuilder.extract;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.utils.FileUtils;

public class ExtExtractor {
	File dir;

	ArrayList classList = new ArrayList();

	public ExtExtractor() {
	}

	public ExtExtractor(File dir) {
		this.dir = dir;
	}

	public void extract() {
		File[] files = FileUtils.listAllFiles(dir, new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getPath().endsWith(".js");
			}
		});
		System.out.println(files.length);
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				System.out.print("parsing " + file.getName() + " ......");
				String content = new String(FileUtils
						.readBytes(new FileInputStream(file)));
				ExtClass c = new ExtClass();
				c.parse(content);
				classList.add(c);
				System.out.println("result:" + c.getClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map map = new HashMap();
		for (Iterator iter = classList.iterator(); iter.hasNext();) {
			ExtClass clz = (ExtClass) iter.next();
			map.put(clz.getClassName(), clz);
		}

		for (Iterator iter = classList.iterator(); iter.hasNext();) {
			ExtClass clz = (ExtClass) iter.next();
			String superclass = clz.getSuperClassName();
			if (StringUtils.isNotEmpty(superclass)) {
				ExtClass sc = (ExtClass) map.get(superclass);
				if (sc != null) {
					sc.addSubClass(clz);
					// iter.remove();
				}
			}
		}

//		File outputDir = new File(
//				"d:/work/com.harbortek.extbuilder/src/com/harbortek/extbuilder/xml");
		File outputDir = new File(
		"d:/temp/ext");
		for (Iterator iter = classList.iterator(); iter.hasNext();) {
			ExtClass clz = (ExtClass) iter.next();
			if (StringUtils.isNotEmpty(clz.getClassName())) {
				File file = new File(outputDir, clz.getClassName() + ".xml");
				try {
					FileUtils.writeBytes(new FileOutputStream(file), clz
							.toXML().getBytes());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void extractSingle(File input, File outputDir) {
		System.out.print("parsing " + input.getName() + " ......");
		String content;
		try {
			
			content = new String(FileUtils
					.readBytes(new FileInputStream(input)));
//			 content = "@cfg {Boolean} enableFormat Enable the bold, italic and underline buttons (defaults to true)";
//			 Pattern p = Pattern.compile("@cfg\\s\\{(.*)\\}\\s(\\S*)\\s(.*)$");
//
//			Pattern p = Pattern.compile("@event([\\s\\S]*?)\\*/",Pattern.MULTILINE);
//			Pattern p = Pattern.compile("@cfg\\s(\\.*)\\s@hide$",Pattern.DOTALL|Pattern.MULTILINE);
//			Pattern p = Pattern.compile("@cfg\\s(.*)\\s@hide$",Pattern.MULTILINE);
//			Matcher matcher = p.matcher(content);
//			while (matcher.find()) {
//				String ev = matcher.group(1);
//				System.out.println(ev);
//			}
			 ExtClass clz = new ExtClass();
			 clz.parse(content);
			 File file = new File(outputDir, clz.getClassName()+".xml");
			 FileUtils.writeBytes(new FileOutputStream(file),
			 clz.toXML().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void printClass(ExtClass clz) {

		System.out.print(clz.getClassName());
		if (clz.getSuperClass() != null) {
			System.out.print("--->" + clz.getSuperClass().getClassName());
		}

		if (clz.getProperties() != null) {
			for (Iterator iter = clz.getProperties().iterator(); iter.hasNext();) {
				ExtProperty p = (ExtProperty) iter.next();
				System.out.println(p.getPropertyName() + ":"
						+ p.getPropertyType());
			}
		}

		if (clz.getEvents() != null) {
			for (Iterator iter = clz.getEvents().iterator(); iter.hasNext();) {
				ExtEvent p = (ExtEvent) iter.next();
				System.out.println(p.getEventName() + ":");
				if (p.getParameters() != null) {
					for (Iterator iterator = p.getParameters().iterator(); iterator
							.hasNext();) {
						ExtParameter param = (ExtParameter) iterator.next();
						System.out.println(param.getParameterName() + ":"
								+ param.getParameterType());
					}
				}
			}
		}

		System.out.println();

		List sub = clz.getSubClasses();
		if (sub != null) {
			for (Iterator iter = sub.iterator(); iter.hasNext();) {
				ExtClass ec = (ExtClass) iter.next();
				printClass(ec);
			}
		}
	}

	public static void main(String[] args) {
//		 new ExtExtractor(new
//		 File("D:/work/lawmis/WebSource/framework/ext/source")).extract();
		File input = new File(
				"D:/work/lawmis/WebSource/framework/ext/source/widgets/form/DateField.js");
		File outputDir = new File("D:/temp/ext");
		outputDir.mkdirs();
		new ExtExtractor().extractSingle(input, outputDir);
	}

}
