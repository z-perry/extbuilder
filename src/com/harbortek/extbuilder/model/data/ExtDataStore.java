package com.harbortek.extbuilder.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ExtDataStore extends ExtElement {
	private static final long serialVersionUID = 1L;

	public static final String STORE_ID = "storeId";

	public static final String SORT_INFO = "sortInfo";

	public static final String REMOTE_SORT = "remoteSort";

	public static final String PRUNE_MODIFIED_RECORDS = "pruneModifiedRecords";

	public static final String BASE_PARAMS = "baseParams";

	public static final String URL = "url";

	private String url = "";

	private List baseParams = new ArrayList();

	private Boolean pruneModifiedRecords = Boolean.FALSE;

	private Boolean remoteSort = Boolean.FALSE;

	private Boolean autoLoad = Boolean.FALSE;

	private String sortInfo = "";

	private String storeId = "";

	private ExtDataProxy proxy = new ExtHttpProxy();

	private ExtDataReader reader = new ExtJsonDataReader("id", "root", "total",
			new ExtDataField[] { new ExtDataField("name"),new ExtDataField("age","age","int") });

	private String data;

	public ExtDataStore() {
		super();
		setUseVariableName(true);
		setUseXtype(false);
	}

	public List getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(List baseParams) {
		this.baseParams = baseParams;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getPruneModifiedRecords() {
		return pruneModifiedRecords;
	}

	public void setPruneModifiedRecords(Boolean pruneModifiedRecords) {
		this.pruneModifiedRecords = pruneModifiedRecords;
	}

	public Boolean getRemoteSort() {
		return remoteSort;
	}

	public void setRemoteSort(Boolean remoteSort) {
		this.remoteSort = remoteSort;
	}

	public String getSortInfo() {
		return sortInfo;
	}

	public void setSortInfo(String sortInfo) {
		this.sortInfo = sortInfo;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public ExtDataProxy getProxy() {
		return proxy;
	}

	public void setProxy(ExtDataProxy proxy) {
		this.proxy = proxy;
	}

	public ExtDataReader getReader() {
		return reader;
	}

	public void setReader(ExtDataReader reader) {
		this.reader = reader;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Boolean getAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(Boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public String getExtClassName() {
		return "Ext.data.Store";
	}

	public Object translate(CodeContext codeContext) {
		Map result = new HashMap();
//		if (StringUtils.isNotEmpty(url)) {
//			result.put("url", url);
//		}
		if (!baseParams.isEmpty()) {
			result.put("baseParams", baseParams);
		}
		if (remoteSort.booleanValue()) {
			result.put("remoteSort", remoteSort);
		}

		if (StringUtils.isNotEmpty(sortInfo)) {
			result.put("sortInfo", sortInfo);
		}

		if (StringUtils.isNotEmpty(storeId)) {
			result.put("storeId", storeId);
		}

		if (getProxy() != null) {
			result.put("proxy", getProxy());
		}
		if (getReader() != null) {
			result.put("reader", getReader());
		}
		return result;
	}

	public String getXtype() {
		return null;
	}

	public String toString() {
		return url;
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return null;
	}

	public Object getPropertyValue(Object id) {
		return null;
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {

	}

	public void setPropertyValue(Object id, Object value) {

	}

}
