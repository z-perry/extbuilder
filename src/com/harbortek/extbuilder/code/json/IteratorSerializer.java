package com.harbortek.extbuilder.code.json;

import java.io.Writer;
import java.util.Iterator;

import com.harbortek.extbuilder.code.CodeContext;

public class IteratorSerializer implements JSONSerializer {


	public void serialize(Object data, Writer os,CodeContext codeContext) {
		Iterator iter = (Iterator)data;

		try {
			os.write("[");
			while(iter.hasNext()){
				Object obj = iter.next();
				SerializerManager.serialize(obj, os,codeContext);
				if (iter.hasNext()) {
					os.write(ConversionConstants.INBOUND_ARRAY_SEPARATOR);
				}
			}
			os.write("]");//$NON-NLS-1$
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Object deseialize(Class paramType, String value) {
		return null;
	}

}
