package com.harbortek.extbuilder.ui.dialogs;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.harbortek.extbuilder.code.json.SerializerManager;
import com.harbortek.extbuilder.model.data.ExtArrayDataReader;
import com.harbortek.extbuilder.model.data.ExtDataField;
import com.harbortek.extbuilder.model.data.ExtDataStore;
import com.harbortek.extbuilder.model.data.ExtHttpProxy;
import com.harbortek.extbuilder.model.data.ExtJsonDataReader;
import com.harbortek.extbuilder.model.data.ExtMemoryProxy;
import com.harbortek.extbuilder.model.data.ExtXmlDataReader;
import com.harbortek.extbuilder.ui.editors.JSEditorFinder;

public class StorePropertiesDialog extends TitleAreaDialog {
	Composite panel;

	TabFolder folder;

	private Combo cmbStore;

	private Combo cmbProxy;

	private Text textUrl;

	private Combo cmbReader;

	private PropertyListEditor listEditor;

	private SelectionListener selectionListener;

	private Label labelData;

	private Text textData;

	private Combo cmbAutoLoad;

	private Combo cmbPrune;

	private Combo cmbRemote;

	private Text textSortInfo;

	private Text textStoreId;

	private Text textRoot;

	private Text textTotal;

	private Text textId;

	private FieldsListEditor fieldsEditor;

	private Button btnCreateDefault;

	private ExtDataStore store;

	public StorePropertiesDialog(Shell parentShell) {
		super(parentShell);
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		// Set the title
		setTitle("About This Application");

		// Set the message
		setMessage("This is a JFace dialog", IMessageProvider.INFORMATION);

		// Set the image
		// if (image != null) setTitleImage(image);

		getShell().pack();
		return contents;
	}

	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		panel = new Composite(dialogArea, SWT.NONE);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setFont(parent.getFont());

		// Label label = new Label(panel, SWT.NONE);
		// label.setText("Store Type:");
		// label.setLayoutData(new GridData(150, -1));
		//
		// cmbStore = new Combo(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN |
		// SWT.READ_ONLY);
		// cmbStore.setFocus();
		// cmbStore.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL |
		// GridData.FILL_HORIZONTAL));
		// cmbStore.setItems(new String[] { "Store" });
		// cmbStore.select(0);
		// cmbStore.addSelectionListener(getSelectionListener());

		folder = new TabFolder(panel, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		folder.setLayoutData(gridData);

		TabItem item1 = new TabItem(folder, SWT.NONE);
		item1.setText("Basic");
		item1.setControl(createPanel1());

		TabItem item2 = new TabItem(folder, SWT.NONE);
		item2.setText("Data");
		item2.setControl(createPanel2());

		dialogArea.pack();

		this.setData(store);
		return dialogArea;
	}

	private Composite createPanel1() {
		Composite panel1 = new Composite(folder, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout = new GridLayout(2, false);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		panel1.setLayout(layout);

		Label label1 = new Label(panel1, SWT.NONE);
		label1.setText("Proxy:");
		label1.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbProxy = new Combo(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbProxy.setFocus();
		cmbProxy.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbProxy.setItems(new String[] { "HttpProxy", "MemoryProxy" });
		cmbProxy.select(0);
		cmbProxy.addSelectionListener(getSelectionListener());

		Label label2 = new Label(panel1, SWT.NONE);
		label2.setText("url :");
		label2.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textUrl = new Text(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textUrl.setFocus();
		textUrl.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label3 = new Label(panel1, SWT.NONE);
		label3.setText("BaseParams:");
		label3.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		listEditor = new PropertyListEditor(panel1, SWT.BORDER | SWT.LEAD
				| SWT.DROP_DOWN);
		listEditor.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		listEditor.setFocus();

		Label label4 = new Label(panel1, SWT.NONE);
		label4.setText("autoLoad :");
		label4.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbAutoLoad = new Combo(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbAutoLoad.setFocus();
		cmbAutoLoad.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbAutoLoad.setItems(new String[] { "True", "False" });
		cmbAutoLoad.select(1);

		Label label6 = new Label(panel1, SWT.NONE);
		label6.setText("pruneModifiedRecords  :");
		label6.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbPrune = new Combo(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbPrune.setFocus();
		cmbPrune.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbPrune.setItems(new String[] { "True", "False" });
		cmbPrune.select(1);

		Label label7 = new Label(panel1, SWT.NONE);
		label7.setText("Remote Sort :");
		label7.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbRemote = new Combo(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbRemote.setFocus();
		cmbRemote.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbRemote.setItems(new String[] { "True", "False" });
		cmbRemote.select(1);

		Label label8 = new Label(panel1, SWT.NONE);
		label8.setText("Sort Info :");
		label8.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textSortInfo = new Text(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textSortInfo.setFocus();
		textSortInfo.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label9 = new Label(panel1, SWT.NONE);
		label9.setText("Store Id :");
		label9.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textStoreId = new Text(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textStoreId.setFocus();
		textStoreId.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		return panel1;
	}

	private Composite createPanel2() {
		Composite panel1 = new Composite(folder, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout = new GridLayout(2, false);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		panel1.setLayout(layout);

		Label label0 = new Label(panel1, SWT.NONE);
		label0.setText("Reader:");
		label0.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbReader = new Combo(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbReader.setFocus();
		cmbReader.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbReader.setItems(new String[] { "JsonReader", "XmlReader" });
		cmbReader.select(0);
		cmbReader.addSelectionListener(getSelectionListener());

		Label label1 = new Label(panel1, SWT.NONE);
		label1.setText("root/record :");
		label1.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textRoot = new Text(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textRoot.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label2 = new Label(panel1, SWT.NONE);
		label2.setText("totalProperty/totalRecords :");
		label2.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textTotal = new Text(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textTotal.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label3 = new Label(panel1, SWT.NONE);
		label3.setText("id :");
		label3.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textId = new Text(panel1, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textId.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label4 = new Label(panel1, SWT.NONE);
		label4.setText("fields :");
		label4.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		fieldsEditor = new FieldsListEditor(panel1, SWT.BORDER | SWT.LEAD
				| SWT.DROP_DOWN);
		fieldsEditor.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		labelData = new Label(panel1, SWT.NONE);
		labelData.setText("Data Inline:");
		labelData.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Composite c1 = new Composite(panel1, SWT.BORDER | SWT.LEAD
				| SWT.DROP_DOWN);
		c1.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		layout2.marginWidth = 0;
		layout2.marginHeight = 0;
		layout2.horizontalSpacing = 8;
		c1.setLayout(layout2);

		textData = new Text(c1, SWT.WRAP | SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 1;
		gridData.grabExcessVerticalSpace = true;
		gridData.widthHint = 300;
		gridData.heightHint = 100;
		textData.setLayoutData(gridData);

		btnCreateDefault = new Button(c1, SWT.PUSH);
		btnCreateDefault.setText("Sample");
		btnCreateDefault.setFont(c1.getFont());
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		int widthHint = convertHorizontalDLUsToPixels(btnCreateDefault,
				IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, btnCreateDefault.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x);
		btnCreateDefault.setLayoutData(data);
		btnCreateDefault.addSelectionListener(getSelectionListener());

		return panel1;
	}

	protected int convertHorizontalDLUsToPixels(Control control, int dlus) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		int averageWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		double horizontalDialogUnitSize = averageWidth * 0.25;

		return (int) Math.round(dlus * horizontalDialogUnitSize);
	}



	private SelectionListener getSelectionListener() {
		if (selectionListener == null) {
			createSelectionListener();
		}
		return selectionListener;
	}

	/**
	 * Creates a selection listener.
	 */
	public void createSelectionListener() {
		selectionListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Widget widget = event.widget;
				if (widget == cmbStore) {

				} else if (widget == cmbProxy) {
					proxyChanged();
				} else if (widget == btnCreateDefault) {
					createSampleData();
				}
			}

		};
	}

	private void proxyChanged() {
		int index = cmbProxy.getSelectionIndex();
		if (index == 0) { //http
			textUrl.setEnabled(true);
			listEditor.setEnabled(true);
			cmbAutoLoad.setEnabled(true);
			cmbPrune.setEnabled(true);
			cmbRemote.setEnabled(true);
			textSortInfo.setEnabled(true);
			textStoreId.setEnabled(true);
			textData.setEnabled(false);
			btnCreateDefault.setEnabled(false);
			cmbReader.setEnabled(true);
		} else {//memory
			textUrl.setEnabled(false);
			listEditor.setEnabled(false);
			cmbAutoLoad.setEnabled(false);
			cmbPrune.setEnabled(false);
			cmbRemote.setEnabled(false);
			textSortInfo.setEnabled(false);
			textStoreId.setEnabled(false);
			textData.setEnabled(true);
			btnCreateDefault.setEnabled(true);
			cmbReader.select(0);
			cmbReader.setEnabled(false);
		}
	}

	private void createSampleData() {
		List list = new ArrayList();
		List fields = fieldsEditor.getList();
		ExtDataField[] fieldArray = (ExtDataField[]) fields
				.toArray(new ExtDataField[fields.size()]);

		if (cmbReader.getSelectionIndex() == 0) {
			// create json data

			for (int i = 0; i < 2; i++) {
				Map m = new HashMap();
				for (int j = 0; j < fieldArray.length; j++) {
					ExtDataField field = fieldArray[j];
					String name = field.getName();
					String mapping = field.getMapping();
					String type = field.getType();
					if ("int".equals(type)) {
						m.put(mapping, new Integer(RandomStringUtils
								.randomNumeric(3)));
					} else if ("float".equals(type)) {
						m.put(mapping, new Float(RandomStringUtils
								.randomNumeric(3)));
					} else if ("boolean".equals(type)) {
						m.put(mapping, Boolean.TRUE);
					} else if ("date".equals(type)) {

					} else {
						m.put(mapping, RandomStringUtils.randomAlphanumeric(5));
					}
				}
				list.add(m);
			}
			Map map = new HashMap();

			map.put(textTotal.getText(), new Integer(2));
			map.put(textRoot.getText(), list);

			try {
				StringWriter os = new StringWriter();
				SerializerManager.serialize(map, os, true, null);
				os.close();
				String jsonData = new JSEditorFinder().formatJsCode(0, os
						.toString());
				textData.setText(jsonData);
			} catch (IOException e) {
			}

		} else if (cmbReader.getSelectionIndex() == 1) {
			// create xml data
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("UTF-8");
			Element dataset = document.addElement("dataset");
			dataset.addElement(textTotal.getText()).addText("2");
			for (int i = 0; i < 2; i++) {
				Element row = dataset.addElement(textRoot.getText());

				for (int j = 0; j < fieldArray.length; j++) {
					ExtDataField field = fieldArray[j];
					String name = field.getName();
					String mapping = field.getMapping();
					String type = field.getType();
					if ("int".equals(type)) {
						row.addElement(mapping).addText(RandomStringUtils
								.randomNumeric(3));
					} else if ("float".equals(type)) {
						row.addElement(mapping).addText(RandomStringUtils
								.randomNumeric(3));
					} else if ("boolean".equals(type)) {
						row.addElement(mapping).addText("true");
					} else if ("date".equals(type)) {

					} else {
						row.addElement(mapping).addText(RandomStringUtils
								.randomAscii(5));
					}
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			StringWriter os = new StringWriter();
			XMLWriter writer = new XMLWriter(os, format);
			try {
				writer.write(document);
				writer.close();
				textData.setText(os.toString());
			} catch (IOException e) {
			}
		}
	}

	protected void okPressed() {
		store = this.getData();
		super.okPressed();
	}

	public ExtDataStore getStore() {
		return store;
	}

	public void setStore(ExtDataStore store) {
		this.store = store;
	}

	public void setData(ExtDataStore store) {
		this.store = store;

		if (store.getProxy() instanceof ExtHttpProxy) {
			cmbProxy.select(0);
		} else if (store.getProxy() instanceof ExtMemoryProxy) {
			cmbProxy.select(1);
			ExtMemoryProxy mp = (ExtMemoryProxy) store.getProxy();

			if (StringUtils.isNotEmpty(mp.getData())) {
				textData.setText(mp.getData());
			}
		}

		if (store.getReader() instanceof ExtJsonDataReader) {
			cmbReader.select(0);
		} else if (store.getReader() instanceof ExtXmlDataReader) {
			cmbReader.select(1);
		} else if (store.getReader() instanceof ExtArrayDataReader) {
			cmbReader.select(2);
		}

		listEditor.setList(store.getBaseParams());

		if (store.getAutoLoad().booleanValue()) {
			cmbAutoLoad.select(0);
		} else {
			cmbAutoLoad.select(1);
		}

		if (store.getPruneModifiedRecords().booleanValue()) {
			cmbPrune.select(0);
		} else {
			cmbPrune.select(1);
		}

		if (store.getRemoteSort().booleanValue()) {
			cmbRemote.select(0);
		} else {
			cmbRemote.select(1);
		}

		textSortInfo.setText(store.getSortInfo());

		textStoreId.setText(store.getStoreId());

		textUrl.setText(store.getUrl());

		textId.setText(store.getReader().getIdField());
		textRoot.setText(store.getReader().getRoot());
		textTotal.setText(store.getReader().getTotalProperty());

		fieldsEditor.setList(store.getReader().getFields());

		proxyChanged();
	}

	public ExtDataStore getData() {
		store = new ExtDataStore();
		if (cmbProxy.getSelectionIndex() == 0) {
			store.setProxy(new ExtHttpProxy(textUrl.getText()));
			store.setUrl(textUrl.getText());
			store.setBaseParams(listEditor.getList());

			if (cmbAutoLoad.getSelectionIndex() == 0) {
				store.setAutoLoad(Boolean.TRUE);
			} else {
				store.setAutoLoad(Boolean.FALSE);
			}

			if (cmbPrune.getSelectionIndex() == 0) {
				store.setPruneModifiedRecords(Boolean.TRUE);
			} else {
				store.setPruneModifiedRecords(Boolean.FALSE);
			}

			if (cmbRemote.getSelectionIndex() == 0) {
				store.setRemoteSort(Boolean.TRUE);
			} else {
				store.setRemoteSort(Boolean.FALSE);
			}
			if (StringUtils.isNotEmpty(textSortInfo.getText())) {
				store.setSortInfo(textSortInfo.getText());
			}
			if (StringUtils.isNotEmpty(textStoreId.getText())) {
				store.setStoreId(textStoreId.getText());
			}

		} else {
			if (cmbReader.getSelectionIndex() == 0){
				store.setProxy(new ExtMemoryProxy(textData.getText()));
			}else{
				store.setProxy(new ExtMemoryProxy(""));
			}
		}

		if (cmbReader.getSelectionIndex() == 0) {
			String id = textId.getText();
			String root = textRoot.getText();
			String total = textTotal.getText();
			List fields = fieldsEditor.getList();
			ExtDataField[] fieldArray = (ExtDataField[]) fields
					.toArray(new ExtDataField[fields.size()]);
			store.setReader(new ExtJsonDataReader(id, root, total, fieldArray));
		} else if (cmbReader.getSelectionIndex() == 1) {
			String id = textId.getText();
			String root = textRoot.getText();
			String total = textTotal.getText();
			List fields = fieldsEditor.getList();
			ExtDataField[] fieldArray = (ExtDataField[]) fields
					.toArray(new ExtDataField[fields.size()]);
			store.setReader(new ExtXmlDataReader(id, root, total, fieldArray));
		} else if (cmbReader.getSelectionIndex() == 2) {
			store.setReader(new ExtArrayDataReader());
		}

		return this.store;
	}
}
