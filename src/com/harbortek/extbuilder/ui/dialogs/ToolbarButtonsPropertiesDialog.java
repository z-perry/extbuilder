package com.harbortek.extbuilder.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.harbortek.extbuilder.ExtBuilderActivator;
import com.harbortek.extbuilder.model.button.ExtButton;
import com.harbortek.extbuilder.model.button.ExtToolbarButton;
import com.harbortek.extbuilder.model.button.ExtToolbarFill;
import com.harbortek.extbuilder.model.button.ExtToolbarItem;
import com.harbortek.extbuilder.model.button.ExtToolbarSeperator;
import com.harbortek.extbuilder.model.button.ExtToolbarSpacer;
import com.harbortek.extbuilder.model.button.ExtToolbarSplitButton;
import com.harbortek.extbuilder.model.button.ExtToolbarTextItem;
import com.harbortek.extbuilder.utils.XComponentUtils;
import com.harbortek.extbuilder.utils.properties.BooleanCellEditor;
import com.harbortek.extbuilder.utils.properties.StandardComboBoxCellEditor;
import com.harbortek.extbuilder.xmodel.ExtElement;

public class ToolbarButtonsPropertiesDialog extends TitleAreaDialog {
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

	private ArrayList buttons;

	private ToolBar toolBar = null;

	private Table table = null;

	private TableViewer tableViewer = null;

	private static final String[] COLUM_NAMES = new String[] { "text",
			"handler", "icon", "iconCls", "tooltip", "xtype", "hidden",
			"enableToggle", "toggleGroup" };

	// static Map XTYPE_MAP;
	// static {
	// XTYPE_MAP = new HashMap();
	// XTYPE_MAP.put("tbbutton", "Button");
	// XTYPE_MAP.put("tbseparator", "Separator");
	// XTYPE_MAP.put("tbspacer", "Spacer");
	// XTYPE_MAP.put("tbfill", "Fill");
	// XTYPE_MAP.put("tbtext", "TextItem");
	// XTYPE_MAP.put("tbsplit", "MenuButton");
	// }

	public ToolbarButtonsPropertiesDialog(Shell parentShell, ArrayList buttons) {
		super(parentShell);
		this.buttons = buttons;
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		// Set the title
		setTitle("Ext Panel Toolbar settings");

		// Set the message
		setMessage("Add or remove toolbar items to an Ext Panel",
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
		// container.setSize(new Point(800, 300));

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

		tableViewer.setInput(buttons);
		return container;
	}

	private TableViewer buildAndLayoutTable(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.FILL);

		table = tableViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(20, 100, true));
		layout.addColumnData(new ColumnWeightData(10, 100, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 75, true));
		layout.addColumnData(new ColumnWeightData(10, 100, true));
		layout.addColumnData(new ColumnWeightData(10, 100, true));
		table.setLayout(layout);

		TableColumn textColumn = new TableColumn(table, SWT.LEAD);
		textColumn.setText("text");

		TableColumn handlerColumn = new TableColumn(table, SWT.LEAD);
		handlerColumn.setText("handler");

		TableColumn iconColumn = new TableColumn(table, SWT.LEAD);
		iconColumn.setText("icon");

		TableColumn iconClsColumn = new TableColumn(table, SWT.LEAD);
		iconClsColumn.setText("iconCls");

		TableColumn tooltipColumn = new TableColumn(table, SWT.LEAD);
		tooltipColumn.setText("tooltip");

		TableColumn typeColumn = new TableColumn(table, SWT.LEAD);
		typeColumn.setText("xtype");

		TableColumn hiddenColumn = new TableColumn(table, SWT.LEAD);
		hiddenColumn.setText("hidden");

		TableColumn enableToggleColumn = new TableColumn(table, SWT.LEAD);
		enableToggleColumn.setText("enableToggle");

		TableColumn toggleGroupColumn = new TableColumn(table, SWT.LEAD);
		toggleGroupColumn.setText("toggleGroup");

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
				ExtElement button = (ExtElement) element;
				boolean canEdit = button instanceof ExtButton;
				switch (columnIndex) {
				case 0:
					if (button instanceof ExtToolbarTextItem) {
						return ((ExtToolbarTextItem) button).getText();
					}
					if (button instanceof ExtButton) {
						return ((ExtButton) button).getText();
					}

					return "";
				case 1:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getHandler();
				case 2:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getButtonIcon();
				case 3:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getIconCls();
				case 4:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getTooltip();
				case 5:
					return (String) button.getXtype();
				case 6:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getHidden().toString();
				case 7:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getEnableToggle().toString();
				case 8:
					if (!canEdit) {
						return "";
					}
					return ((ExtButton) button).getToggleGroup();
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

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
	}

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				boolean canEdit = element instanceof ExtButton
						|| (element instanceof ExtToolbarTextItem && "text"
								.equals(property));
				if ("xtype".equals(property)) {
					canEdit = false;
				}
				return canEdit;
			}

			public Object getValue(Object element, String propertyId) {
				if (element instanceof ExtButton) {
					ExtButton button = (ExtButton) element;
					if ("clickEvent".equals(propertyId)) {
						return button.getClickEvent();
					} else if ("disabled".equals(propertyId)) {
						return button.getDisabled();
					} else if ("enableToggle".equals(propertyId)) {
						return button.getEnableToggle();
					} else if ("handleMouseEvents".equals(propertyId)) {
						return button.getHandleMouseEvents();
					} else if ("hidden".equals(propertyId)) {
						return button.getHidden();
					} else if ("icon".equals(propertyId)) {
						return button.getButtonIcon();
					} else if ("iconCls".equals(propertyId)) {
						return button.getIconCls();
					} else if ("menuAlign".equals(propertyId)) {
						return button.getMenuAlign();
					} else if ("minWidth".equals(propertyId)) {
						return button.getMinWidth();
					} else if ("pressed".equals(propertyId)) {
						return button.getPressed();
					} else if ("tabIndex".equals(propertyId)) {
						return button.getTabIndex();
					} else if ("text".equals(propertyId)) {
						return button.getText();
					} else if ("toggleGroup".equals(propertyId)) {
						return button.getToggleGroup();
					} else if ("tooltip".equals(propertyId)) {
						return button.getTooltip();
					} else if ("tooltipType".equals(propertyId)) {
						return button.getTooltipType();
					} else if ("xtype".equals(propertyId)) {
						return button.getXtype();
					} else if ("handler".equals(propertyId)) {
						return button.getHandler();
					}
				} else if (element instanceof ExtToolbarTextItem) {
					if ("text".equals(propertyId)) {
						return ((ExtToolbarTextItem) element).getText();
					}
				} else if (element instanceof ExtToolbarItem) {
					if ("xtype".equals(propertyId)) {
						return ((ExtToolbarItem) element).getXtype();
					}
				}
				return null;
			}

			public void modify(Object element, String propertyId, Object value) {
				TableItem tableItem = (TableItem) element;
				if (tableItem.getData() instanceof ExtButton) {
					ExtButton button = (ExtButton) tableItem.getData();
					if ("clickEvent".equals(propertyId)) {
						button.setClickEvent((String) value);
					} else if ("disabled".equals(propertyId)) {
						button.setDisabled((Boolean) value);
					} else if ("enableToggle".equals(propertyId)) {
						button.setEnableToggle((Boolean) value);
					} else if ("handleMouseEvents".equals(propertyId)) {
						button.setHandleMouseEvents((Boolean) value);
					} else if ("hidden".equals(propertyId)) {
						button.setHidden((Boolean) value);
					} else if ("icon".equals(propertyId)) {
						button.setButtonIcon((String) value);
					} else if ("iconCls".equals(propertyId)) {
						button.setIconCls((String) value);
					} else if ("menuAlign".equals(propertyId)) {
						button.setMenuAlign((String) value);
					} else if ("minWidth".equals(propertyId)) {
						button.setMinWidth((Integer) value);
					} else if ("pressed".equals(propertyId)) {
						button.setPressed((Boolean) value);
					} else if ("tabIndex".equals(propertyId)) {
						button.setTabIndex((Integer) value);
					} else if ("text".equals(propertyId)) {
						button.setText((String) value);
						if (button instanceof ExtToolbarButton) {
							button.setHandler("on"
									+ StringUtils.capitalize((String) value)
									+ "Click");
						}
					} else if ("toggleGroup".equals(propertyId)) {
						button.setToggleGroup((String) value);
					} else if ("tooltip".equals(propertyId)) {
						button.setTooltip((String) value);
					} else if ("tooltipType".equals(propertyId)) {
						button.setTooltipType((String) value);
						// } else if ("xtype".equals(propertyId)) {
						// button.setXtype((String) value);
					} else if ("handler".equals(propertyId)) {
						button.setHandler((String) value);
					}
				} else if (tableItem.getData() instanceof ExtToolbarTextItem) {
					ExtToolbarTextItem button = (ExtToolbarTextItem) tableItem
							.getData();
					if ("text".equals(propertyId)) {
						button.setText((String) value);
					}
				}
				viewer.refresh(tableItem.getData());
			}
		});
		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(parent), // text
				new TextCellEditor(parent), // handler
				new TextCellEditor(parent), // icon
				new TextCellEditor(parent), // iconCls
				new TextCellEditor(parent), // tooltip
				new StandardComboBoxCellEditor(parent,
						new String[] { "Button", "Separator", "Spacer", "Fill",
								"TextItem", "MenuButton" }, new String[] {
								"tbbutton", "tbseparator", "tbspacer",
								"tbfill", "tbtext", "tbsplit" }), // type
				new BooleanCellEditor(parent), // hidden
				new BooleanCellEditor(parent), // enableToggle
				new TextCellEditor(parent), // toggleGroup
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

		final Menu popupMenu = new Menu(getShell(), SWT.POP_UP);
		MenuItem menuItem1 = new MenuItem(popupMenu, SWT.PUSH);
		menuItem1.setText("Button");
		menuItem1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExtButton newItem = new ExtToolbarButton();
				newItem.setText("button");
				newItem.setHandler("onButtonClick");
				addItem(newItem);
			}
		});

		MenuItem menuItem2 = new MenuItem(popupMenu, SWT.PUSH);
		menuItem2.setText("Separator");
		menuItem2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExtElement newItem = new ExtToolbarSeperator();
				addItem(newItem);
			}
		});

		MenuItem menuItem3 = new MenuItem(popupMenu, SWT.PUSH);
		menuItem3.setText("Spacer");
		menuItem3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExtElement newItem = new ExtToolbarSpacer();
				addItem(newItem);
			}
		});

		MenuItem menuItem4 = new MenuItem(popupMenu, SWT.PUSH);
		menuItem4.setText("Fill");
		menuItem4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExtElement newItem = new ExtToolbarFill();
				addItem(newItem);
			}
		});

		MenuItem menuItem5 = new MenuItem(popupMenu, SWT.PUSH);
		menuItem5.setText("TextItem");
		menuItem5.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExtToolbarTextItem newItem = new ExtToolbarTextItem();
				newItem.setText("textItem");
				addItem(newItem);
			}
		});

		MenuItem menuItem6 = new MenuItem(popupMenu, SWT.PUSH);
		menuItem6.setText("MenuButton");
		menuItem6.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExtButton newItem = new ExtToolbarSplitButton();
				newItem.setText("menuButton");
				addItem(newItem);
			}
		});

		ToolItem addItem = new ToolItem(toolBar, SWT.DROP_DOWN);
		addItem.setImage(ICON_ADD);
		addItem.setToolTipText("Add");
		addItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Point point = new Point(event.x, event.y);
					point = ToolbarButtonsPropertiesDialog.this.getShell()
							.getDisplay().map(toolBar, null, point);
					popupMenu.setLocation(point);
					popupMenu.setVisible(true);
				}
			}
		});

		ToolItem removeItem = new ToolItem(toolBar, SWT.PUSH);
		removeItem.setImage(ICON_REMOVE);
		removeItem.setToolTipText("Remove");
		removeItem
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						int index = table.getSelectionIndex();
						if (index >= 0) {
							buttons.remove(index);
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
		upItem
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
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
		downItem
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
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
				buttons.add(index + 1,input);
			} else {
				buttons.add(0,input);
			}
			tableViewer.refresh();
			tableViewer.getTable().setSelection(index+1);
		}
	}

	protected void okPressed() {
		buttons.clear();
		TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
			Object o = XComponentUtils.cloneComponent(items[i].getData());
			buttons.add(o);
		}
		super.okPressed();
	}

	public ArrayList getButtons() {
		return buttons;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
