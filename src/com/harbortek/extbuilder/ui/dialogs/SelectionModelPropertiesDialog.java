package com.harbortek.extbuilder.ui.dialogs;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.harbortek.extbuilder.model.grid.ExtAbstractSelectionModel;
import com.harbortek.extbuilder.model.grid.ExtCellSelectionModel;
import com.harbortek.extbuilder.model.grid.ExtCheckboxSelectionModel;
import com.harbortek.extbuilder.model.grid.ExtRowSelectionModel;

public class SelectionModelPropertiesDialog extends TitleAreaDialog {
	Composite panel;

	private Combo cmbModel;

	private Combo cmbSortable;

	private Text textHeader;

	private Combo cmbSingleSelect;

	private SelectionListener selectionListener;

	private Text textWidth;

	private ExtAbstractSelectionModel model;

	public SelectionModelPropertiesDialog(Shell parentShell,
			ExtAbstractSelectionModel model) {
		super(parentShell);

		this.model = model;
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		// Set the title
		setTitle("Ext SeletionModel Settings");

		// Set the message
		setMessage("Set properties of Ext Selection Model",
				IMessageProvider.INFORMATION);

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

		Label label = new Label(panel, SWT.NONE);
		label.setText("Selection Model Type:");
		label.setLayoutData(new GridData(150, -1));

		cmbModel = new Combo(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbModel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbModel.setItems(new String[] { "RowSelectionModel",
				"CellSelectionModel", "CheckboxSelectionModel" });
		cmbModel.select(0);
		cmbModel.addSelectionListener(getSelectionListener());

		Label label0 = new Label(panel, SWT.NONE);
		label0.setText("Signle Select:");
		label0.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbSingleSelect = new Combo(panel, SWT.BORDER | SWT.LEAD
				| SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbSingleSelect.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbSingleSelect.setItems(new String[] { "True", "False" });
		cmbSingleSelect.select(0);

		Label label2 = new Label(panel, SWT.NONE);
		label2.setText("Header :");
		label2.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textHeader = new Text(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textHeader.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label3 = new Label(panel, SWT.NONE);
		label3.setText("Width :");
		label3.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		textWidth = new Text(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN);
		textWidth.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));

		Label label1 = new Label(panel, SWT.NONE);
		label1.setText("Sortable:");
		label1.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbSortable = new Combo(panel, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbSortable.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbSortable.setItems(new String[] { "True", "False" });
		cmbSortable.select(0);

		dialogArea.pack();

		setModel(this.model);

		return dialogArea;
	}

	protected int convertHorizontalDLUsToPixels(Control control, int dlus) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		int averageWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		double horizontalDialogUnitSize = averageWidth * 0.25;

		return (int) Math.round(dlus * horizontalDialogUnitSize);
	}

	/**
	 * Creates the buttons for the button bar
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
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
				if (widget == cmbModel) {
					modelTypeChanged();
				}
			}

		};
	}

	private void modelTypeChanged() {
		int index = cmbModel.getSelectionIndex();
		switch(index){
		case 0:
		case 1:
				cmbSingleSelect.setEnabled(true);
				cmbSortable.setEnabled(false);
				textHeader.setEnabled(false);
				textWidth.setEnabled(false);
				break;
		case 2:
				cmbSingleSelect.setEnabled(false);
				cmbSortable.setEnabled(true);
				textHeader.setEnabled(true);
				textWidth.setEnabled(true);
				break;
		}
	}

	protected void okPressed() {
		applyData();
		super.okPressed();
	}

	private void applyData() {
		int index = cmbModel.getSelectionIndex();
		switch (index) {
		case 0:
			model = new ExtRowSelectionModel();
			boolean singleSelect = cmbSingleSelect.getSelectionIndex() == 0;
			((ExtRowSelectionModel) model).setSingleSelect(new Boolean(
					singleSelect));
			break;
		case 1:
			model = new ExtCellSelectionModel();
			break;
		case 2:
			model = new ExtCheckboxSelectionModel();
			((ExtCheckboxSelectionModel) model).setHeader(textHeader.getText());
			((ExtCheckboxSelectionModel) model).setWidth(new Integer(textWidth
					.getText()));
			boolean sortable = cmbSortable.getSelectionIndex() == 0;
			((ExtCheckboxSelectionModel) model).setSortable(new Boolean(
					sortable));
			break;
		}
	}

	public ExtAbstractSelectionModel getSelectionModel() {
		return model;
	}

	public void setModel(ExtAbstractSelectionModel model) {
		this.model = model;
		if (model instanceof ExtCheckboxSelectionModel) {
			cmbModel.select(2);
			textHeader.setText(((ExtCheckboxSelectionModel) model).getHeader());
			textWidth.setText(((ExtCheckboxSelectionModel) model).getWidth()
					.toString());
			Boolean sortable = ((ExtCheckboxSelectionModel) model)
					.getSortable();
			if (sortable.booleanValue()) {
				cmbSortable.select(0);
			} else {
				cmbSortable.select(1);
			}
		} else if (model instanceof ExtCellSelectionModel) {
			cmbModel.select(1);
		} else if (model instanceof ExtRowSelectionModel) {
			cmbModel.select(0);
			Boolean singleSelect = ((ExtRowSelectionModel) model)
					.getSingleSelect();
			if (singleSelect.booleanValue()) {
				cmbSingleSelect.select(0);
			} else {
				cmbSingleSelect.select(1);
			}
		}
	}

}
