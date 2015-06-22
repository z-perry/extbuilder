package com.harbortek.extbuilder.extract;

public class ExtParameter {
	private String parameterName;

	private String parameterType;

	private String parameterCommnet;

	public ExtParameter(String parameterName, String parameterType,
			String parameterComment) {
		super();
		this.parameterName = parameterName;
		this.parameterType = parameterType;
		this.parameterCommnet = parameterComment;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterCommnet() {
		return parameterCommnet;
	}

	public void setParameterCommnet(String parameterCommnet) {
		this.parameterCommnet = parameterCommnet;
	}

}
