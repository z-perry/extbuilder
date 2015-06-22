package com.harbortek.extbuilder.model.data;

public class ExtJsonDataReader extends ExtDataReader {
	private static final long serialVersionUID = 1L;

	public ExtJsonDataReader(String id, String root, String total, ExtDataField[] fields) {
		super(id, root, total, fields);
	}

	public String getExtClassName() {
		return "Ext.data.JsonReader";
	}

}
