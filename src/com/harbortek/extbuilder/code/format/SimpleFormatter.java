package com.harbortek.extbuilder.code.format;

import java.io.StringReader;
import java.io.StringWriter;

import com.harbortek.extbuilder.utils.FileUtils;

public class SimpleFormatter {
	public String format(String code) throws Exception{
		StringWriter writer = new StringWriter();
		StringReader reader =  new StringReader(code);
		int c;
		while((c = reader.read())!=-1){
			if (c==','||c==';'||c=='{' || c=='['){
				writer.write(c);
				writer.write("\n");
			}else{
				writer.write(c);
			}
		}
		return writer.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String content = new String(FileUtils.readBytes("D:/runtime-EclipseApplication/test/aa.js"));
		SimpleFormatter formatter = new SimpleFormatter();
		System.out.println(formatter.format(content));
	}
}
