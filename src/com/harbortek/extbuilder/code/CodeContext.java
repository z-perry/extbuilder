package com.harbortek.extbuilder.code;

import java.util.HashMap;
import java.util.Map;

import com.harbortek.extbuilder.model.data.ExtDataStore;

public class CodeContext {
	private boolean forPreview;

	private Map generatedComponents = new HashMap();

	public boolean isForPreview() {
		return forPreview;
	}

	public void setForPreview(boolean forPreview) {
		this.forPreview = forPreview;
	}

	public Map getGeneratedComponents() {
		return generatedComponents;
	}

	public void setGeneratedComponents(Map generatedComponents) {
		this.generatedComponents = generatedComponents;
	}

	public boolean isGenerated(ExtDataStore store) {
		return generatedComponents.containsKey(store.getVariableName());
	}

}
