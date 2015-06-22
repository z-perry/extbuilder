package com.harbortek.extbuilder.code.json;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.harbortek.extbuilder.code.CodeContext;

public class BigNumberSerializer implements JSONSerializer {

	public void serialize(Object obj,Writer os,CodeContext codeContext) {
		try {
			os.write( obj.toString() );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object deseialize(Class paramType, String json) {
		if (paramType == BigDecimal.class)
        {
            return new BigDecimal(json.trim());
        }

        if (paramType == BigInteger.class)
        {
            return new BigInteger(json.trim());
        }
        
		return null;
	}

}
