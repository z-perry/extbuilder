package com.harbortek.extbuilder.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.model.ExtListener;

public class ListenersPropertiesDialog extends TitleAreaDialog {
	private static final Image ICON_ADD = new Image(null,
			ImageDescriptor.createFromFile(ExtBuilderActivator.class,
					"icons/add.gif").getImageData()); //$NON-NLS-1$

	private static final Image ICON_REMOVE = new Image(null,
			ImageDescriptor.createFromFile(ExtBuilderActivator.class,
					"icons/remove.gif").getImageData()); //$NON-NLS-1$

	private static final Image ICON_UP = new Image(null,
			ImageDescriptor.createFromFile(ExtBuilderActivator.class,
					"icons/up.gif").getImageData()); //$NON-NLS-1$

	private static final Image ICON_DOWN = new Image(null,
			ImageDescriptor.createFromFile(ExtBuilderActivator.class,
					"icons/down.gif").getImageData()); //$NON-NLS-1$

	private ArrayList listeners;

	private ToolBar toolBar = null;

	private TableViewer tableViewer = null;

	private static final String[] COLUM_NAMES = new String[] { "class",
			"event", "function" };

	public ListenersPropertiesDialog(Shell parentShell, ArrayList columns) {
		super(parentShell);
		this.listeners = columns;
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		// Set the title
		setTitle("Ext listeners settings");

		// Set the message
		setMessage("Add or remove event listeners to an Ext component",
				IMessageProvider.INFORMATION);

		// Set the image
		// if (image != null) setTitleImage(image);

		getShell().pack();
		return contents;
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		gridLayout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		gridLayout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		gridLayout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		gridLayout.makeColumnsEqualWidth = true;
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		container.setFont(parent.getFont());

		Label label = new Label(container, SWT.NONE | SWT.READ_ONLY);
		label.setText("Attach event listener:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		tableViewer = buildAndLayoutTable(container);
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.widthHint = 600;
		gridData1.heightHint = 300;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.grabExcessVerticalSpace = true;
		tableViewer.getTable().setLayoutData(gridData1);
		attachContentProvider(tableViewer);
		attachLabelProvider(tableViewer);
		attachCellEditors(tableViewer, tableViewer.getTable());
		tableViewer.setInput(listeners);
		return container;
	}

	private TableViewer buildAndLayoutTable(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.FILL);

		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(25, 100, true));
		layout.addColumnData(new ColumnWeightData(25, 100, true));
		layout.addColumnData(new ColumnWeightData(50, 100, true));
		table.setLayout(layout);

		new TableColumn(table, SWT.LEAD).setText("class");
		new TableColumn(table, SWT.LEAD).setText("event");
		new TableColumn(table, SWT.LEAD).setText("function");

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		return tableViewer;
	}

	private void attachLabelProvider(TableViewer viewer) {
		viewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				ExtListener column = (ExtListener) element;
				switch (columnIndex) {
				case 0:
					return column.getExtEvent().getExtClass().getClassName();
				case 1:
					return column.getEventName();
				case 2:
					return column.getFunctionName();
				default:
					return "Invalid column: " + columnIndex;
				}
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener lpl) {
			}
		});
	}

	private void attachContentProvider(TableViewer viewer) {
		viewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return (Object[]) ((List) inputElement).toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
	}

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				return property.equalsIgnoreCase("function");
			}

			public Object getValue(Object element, String propertyId) {
				ExtListener column = (ExtListener) element;
				if ("event".equals(propertyId)) {
					return column.getEventName();
				} else if ("function".equals(propertyId)) {
					return column.getFunctionName();
				} else {
					return null;
				}
			}

			public void modify(Object element, String propertyId, Object value) {
				TableItem tableItem = (TableItem) element;
				ExtListener column = (ExtListener) tableItem.getData();
				if ("event".equals(propertyId)) {
					column.setEventName((String) value);
				} else if ("function".equals(propertyId)) {
					column.setFunctionName((String) value);
				} else {
				}
				viewer.refresh(column);
			}
		});
		Table table = tableViewer.getTable();
		CellEditor[] editors = new CellEditor[3];
		editors[0] = new TextCellEditor(table, SWT.READ_ONLY);
		editors[1] = new TextCellEditor(table, SWT.READ_ONLY);
		editors[2] = new TextCellEditor(table);
		tableViewer.setCellEditors(editors);
		viewer.setColumnProperties(COLUM_NAMES);
	}

	protected void okPressed() {
		listeners.clear();
		TableItem[] items = tableViewer.getTable().getItems();
		for (int i = 0; i < items.length; i++) {
			listeners.add(items[i].getData());
		}
		super.okPressed();
	}

	public ArrayList getListeners() {
		return listeners;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
