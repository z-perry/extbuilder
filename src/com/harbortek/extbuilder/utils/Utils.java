package com.harbortek.extbuilder.utils;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;

import com.harbortek.extbuilder.ExtBuilderActivator;


public class Utils {
	public static String getShortClassName(String className) {
		if (className == null)
			return "";
		if (className.length() == 0)
			return "";
		char[] chars = className.toCharArray();
		int lastDot = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '.')
				lastDot = i + 1;
			else if (chars[i] == '$')
				chars[i] = '.';
		}
		return new String(chars, lastDot, chars.length - lastDot);
	}
	
	public static Image createImage(String name) {
		InputStream stream = ExtBuilderActivator.class.getResourceAsStream(name);
		Image image = new Image(null, stream);
		try {
			stream.close();
		} catch (IOException ioe) {
		}
		return image;
	}
}
