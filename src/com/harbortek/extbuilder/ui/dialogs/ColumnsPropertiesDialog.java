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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.model.grid.ExtColumn;
import com.harbortek.extbuilder.utils.properties.BooleanCellEditor;
import com.harbortek.extbuilder.utils.properties.IntegerCellEditor;
import com.harbortek.extbuilder.utils.properties.StandardComboBoxCellEditor;

public class ColumnsPropertiesDialog extends TitleAreaDialog {
	private static final Image ICON_ADD = new Image(null, ImageDescriptor.createFromFile(ExtBuilderActivator.class,
			"icons/add.gif").getImageData()); //$NON-NLS-1$

	private static final Image ICON_REMOVE = new Image(null, ImageDescriptor.createFromFile(ExtBuilderActivator.class,
			"icons/remove.gif").getImageData()); //$NON-NLS-1$

	private static final Image ICON_UP = new Image(null, ImageDescriptor
			.createFromFile(ExtBuilderActivator.class, "icons/up.gif").getImageData()); //$NON-NLS-1$

	private static final Image ICON_DOWN = new Image(null, ImageDescriptor.createFromFile(ExtBuilderActivator.class,
			"icons/down.gif").getImageData()); //$NON-NLS-1$

	private ArrayList columns;

	private ToolBar toolBar = null;

	private Table table = null;

	private TableViewer tableViewer = null;

	private static final String[] COLUM_NAMES = new String[] {
			"header",
			"dataIndex",
			"width",
			"align",
			"renderer",
			"hidden",
			"hideable",
			"resizable",
			"sortable",
			"fixed"
	};

	public ColumnsPropertiesDialog(Shell parentShell, ArrayList columns) {
		super(parentShell);
		this.columns = columns;
	}
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		// Set the title
		setTitle("ColumnModel Settings");

		// Set the message
		setMessage("Add or remove columns to an Ext CoolumnModel",
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
		
		createToolBar(container);
		//container.setSize(new Point(800, 300));
		
		tableViewer = buildAndLayoutTable(container);
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.widthHint = 800;
		gridData1.heightHint = 300;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.grabExcessVerticalSpace = true;
		tableViewer.getTable().setLayoutData(gridData1);
		
		attachContentProvider(tableViewer);
		attachLabelProvider(tableViewer);
		attachCellEditors(tableViewer, table);
		tableViewer.setInput(columns);
		return container;
	}

	private TableViewer buildAndLayoutTable(Composite parent) {
		tableViewer = new TableViewer(parent,SWT.FULL_SELECTION | SWT.FILL);
		
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(10, 100, true));
		layout.addColumnData(new ColumnWeightData(10, 100, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		table.setLayout(layout);

		TableColumn headerColumn = new TableColumn(table, SWT.LEAD);
		headerColumn.setText("header");

		TableColumn dataIndexColumn = new TableColumn(table, SWT.LEAD);
		dataIndexColumn.setText("dataIndex");

		TableColumn widthColumn = new TableColumn(table, SWT.LEAD);
		widthColumn.setText("width");

		TableColumn alignColumn = new TableColumn(table, SWT.LEAD);
		alignColumn.setText("align");

		TableColumn rendererColumn = new TableColumn(table, SWT.LEAD);
		rendererColumn.setText("renderer");

		TableColumn hiddenColumn = new TableColumn(table, SWT.LEAD);
		hiddenColumn.setText("hidden");

		TableColumn hideableColumn = new TableColumn(table, SWT.LEAD);
		hideableColumn.setText("hideable");

		TableColumn resizableColumn = new TableColumn(table, SWT.LEAD);
		resizableColumn.setText("resizable");

		TableColumn sortableColumn = new TableColumn(table, SWT.LEAD);
		sortableColumn.setText("sortable");

		TableColumn fixedColumn = new TableColumn(table, SWT.LEAD);
		fixedColumn.setText("fixed");
		
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
				ExtColumn column = (ExtColumn) element;
				switch (columnIndex) {
				case 0:
					return column.getHeader();
				case 1:
					return column.getDataIndex();
				case 2:
					return column.getWidth().toString();
				case 3:
					return column.getAlign();
				case 4:
					return column.getRenderer();
				case 5:
					return column.getHidden().toString();
				case 6:
					return column.getHideable().toString();
				case 7:
					return column.getResizable().toString();
				case 8:
					return column.getSortable().toString();
				case 9:
					return column.getFixed().toString();
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
				return (Object[]) ((List)inputElement).toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
	}

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				return true;
			}

			public Object getValue(Object element, String propertyId) {
				ExtColumn column = (ExtColumn) element;
				if ("header".equals(propertyId)) {
					return column.getHeader();
				} else if ("dataIndex".equals(propertyId)) {
					return column.getDataIndex();
				} else if ("width".equals(propertyId)) {
					return column.getWidth();
				} else if ("align".equals(propertyId)) {
					return column.getAlign();
				} else if ("renderer".equals(propertyId)) {
					return column.getRenderer();
				} else if ("hidden".equals(propertyId)) {
					return column.getHidden();
				} else if ("hideable".equals(propertyId)) {
					return column.getHideable();
				} else if ("resizable".equals(propertyId)) {
					return column.getResizable();
				} else if ("sortable".equals(propertyId)) {
					return column.getSortable();
				} else if ("fixed".equals(propertyId)) {
					return column.getFixed();
				} else {
					return null;
				}
			}

			public void modify(Object element, String propertyId, Object value) {
				TableItem tableItem = (TableItem) element;
				ExtColumn column = (ExtColumn) tableItem.getData();
				if ("header".equals(propertyId)) {
					 column.setHeader((String)value);
				} else if ("dataIndex".equals(propertyId)) {
					 column.setDataIndex((String)value);
				} else if ("width".equals(propertyId)) {
					 column.setWidth((Integer)value);
				} else if ("align".equals(propertyId)) {
					 column.setAlign((String)value);
				} else if ("renderer".equals(propertyId)) {
					 column.setRenderer((String)value);
				} else if ("hidden".equals(propertyId)) {
					 column.setHidden((Boolean)value);
				} else if ("hideable".equals(propertyId)) {
					 column.setHideable((Boolean)value);
				} else if ("resizable".equals(propertyId)) {
					 column.setResizable((Boolean)value);
				} else if ("sortable".equals(propertyId)) {
					 column.setSortable((Boolean)value);
				} else if ("fixed".equals(propertyId)) {
					 column.setFixed((Boolean)value);
				} else {
				}
				viewer.refresh(column);
			}
		});
		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(parent), // text
				new TextCellEditor(parent), // handler
				new IntegerCellEditor(parent), // icon
				new StandardComboBoxCellEditor(parent, new String[] {
						"left",
						"center",
						"right" }, new String[] {
						"left",
						"center",
						"right" }), // iconCls
				new TextCellEditor(parent), // tooltip
				new BooleanCellEditor(parent), // hidden
				new BooleanCellEditor(parent), // hideable
				new TextCellEditor(parent), // resizable
				new TextCellEditor(parent), // sortable
				new TextCellEditor(parent), // fixed
		});
		viewer.setColumnProperties(COLUM_NAMES);
	}

	/**
	 * This method initializes toolBar
	 * 
	 */
	private void createToolBar(Composite container) {
		GridData gridData = new GridData();
		gridData.widthHint = 400;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		toolBar = new ToolBar(container, SWT.NONE);
		toolBar.setLayoutData(gridData);
		ToolItem addItem = new ToolItem(toolBar, SWT.PUSH);
		addItem.setImage(ICON_ADD);
		addItem.setToolTipText("Add");

		addItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ExtColumn newItem = new ExtColumn();
				newItem.setHeader("column1");
				newItem.setDataIndex("column1");
				addItem(newItem);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		ToolItem removeItem = new ToolItem(toolBar, SWT.PUSH);
		removeItem.setImage(ICON_REMOVE);
		removeItem.setToolTipText("Remove");
		removeItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = table.getSelectionIndex();
				if (index >= 0) {
					columns.remove(index);
				}
				tableViewer.refresh();
				if (index-1>=0){
					tableViewer.getTable().setSelection(index-1);
				}else {
					tableViewer.getTable().setSelection(-1);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		
		ToolItem upItem = new ToolItem(toolBar, SWT.PUSH);
		upItem.setImage(ICON_UP);
		upItem.setToolTipText("Up");
		upItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = table.getSelectionIndex();
				if (index > 0) {
					Object data = tableViewer.getElementAt(index);
					table.remove(index);
					tableViewer.insert(data, index - 1);
					tableViewer.getTable().setSelection(index-1);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		ToolItem downItem = new ToolItem(toolBar, SWT.PUSH);
		downItem.setImage(ICON_DOWN);
		downItem.setToolTipText("Down");
		downItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = table.getSelectionIndex();
				if (index < table.getItemCount() - 1 && index >= 0) {
					Object data = tableViewer.getElementAt(index);
					tableViewer.remove(data);
					tableViewer.insert(data, index + 1);
					tableViewer.getTable().setSelection(index+1);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	
	protected void addItem(Object input){
		if (input != null) {
			int index = tableViewer.getTable().getSelectionIndex();
			if (index >= 0) {
				columns.add(index + 1,input);
			} else {
				columns.add(0,input);
			}
			tableViewer.refresh();
			tableViewer.getTable().setSelection(index+1);
		}
	}

	protected void okPressed() {
		columns.clear();
		TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
			columns.add(items[i].getData());
		}
		super.okPressed();
	}

	public ArrayList getColumns() {
		return columns;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
