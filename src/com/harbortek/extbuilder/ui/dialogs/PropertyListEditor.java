package com.harbortek.extbuilder.ui.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;

import com.harbortek.extbuilder.xmodel.ExtScript;

public class PropertyListEditor extends Composite {

	private List properties = new ArrayList();

	private Composite buttonBox;
	
	private TableViewer tv;

	/**
	 * The Add button.
	 */
	private Button addButton;

	/**
	 * The Remove button.
	 */
	private Button removeButton;

	/**
	 * The Up button.
	 */
	private Button upButton;

	/**
	 * The Down button.
	 */
	private Button downButton;

	private SelectionListener selectionListener;
	
	

	public PropertyListEditor(Composite parent, int style) {
		super(parent, style);

		createControls();
	}

	private void createControls() {

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 8;
		this.setLayout(layout);

		tv = new TableViewer(this, SWT.FULL_SELECTION|SWT.FILL);
		tv.setContentProvider(new PropertyContentProvider());
		tv.setLabelProvider(new PropertyLabelProvider());
		tv.setInput(properties);

		// Set up the table
		Table table = tv.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		new TableColumn(table, SWT.CENTER).setText("name");
		new TableColumn(table, SWT.CENTER).setText("value");
		
		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).setWidth(100);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(table);
		editors[1] = new TextCellEditor(table);

		// Set the editors, cell modifier, and column properties
		tv.setColumnProperties(new String[] {
				"name",
				"value" });
		tv.setCellModifier(new PropertyCellModifier(tv));
		tv.setCellEditors(editors);
		tv.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				PropertyListEditor.this.selectionChanged();
			}
			
		});

		buttonBox = new Composite(this, SWT.NULL);
		layout = new GridLayout();
		layout.marginWidth = 0;
		buttonBox.setLayout(layout);
		createButtons(buttonBox);
		buttonBox.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				addButton = null;
				removeButton = null;
				upButton = null;
				downButton = null;
				buttonBox = null;
			}
		});

	}

	private void createButtons(Composite box) {
		addButton = createPushButton(box, "Add");//$NON-NLS-1$
		removeButton = createPushButton(box, "Remove");//$NON-NLS-1$
		upButton = createPushButton(box, "Up");//$NON-NLS-1$
		downButton = createPushButton(box, "Down");//$NON-NLS-1$
	}

	private Button createPushButton(Composite parent, String key) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(JFaceResources.getString(key));
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button, IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(getSelectionListener());
		return button;
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
				if (widget == addButton) {
					addPressed();
				} else if (widget == removeButton) {
					removePressed();
				} else if (widget == upButton) {
					upPressed();
				} else if (widget == downButton) {
					downPressed();
				} else if (widget == tv.getTable()) {
					selectionChanged();
				}
			}
		};
	}

	private void addPressed() {
		Map input = new HashMap();
		input.put("name", "");
		input.put("value", new ExtScript(""));

		if (input != null) {
			int index = tv.getTable().getSelectionIndex();
			if (index >= 0) {
				properties.add(index + 1,input);
			} else {
				properties.add(0,input);
			}
			tv.refresh();
			tv.getTable().setSelection(index+1);
			selectionChanged();
		}
	}

	private void removePressed() {
		int index = tv.getTable().getSelectionIndex();
		if (index >= 0) {
			properties.remove(index);
			tv.refresh();
			if (properties.size()>=index-1){
				tv.getTable().setSelection(index);
			}else if (properties.size()>0){
				tv.getTable().setSelection(index-1);
			}
			selectionChanged();
		}
	}

	private void upPressed() {
		swap(true);
	}

	/**
	 * Notifies that the Down button has been pressed.
	 */
	private void downPressed() {
		swap(false);
	}

	private void swap(boolean up) {
		int index = tv.getTable().getSelectionIndex();
		int target = up ? index - 1 : index + 1;

		if (index >= 0) {
			int[] selection = tv.getTable().getSelectionIndices();
			Assert.isTrue(selection.length == 1);
			Object v = properties.remove(index);
			properties.add(target,v);
			tv.refresh();
			//properties.setSelection(target);
			//tv.getTable().setSelection(target);
		}
		selectionChanged();
	}
	
	
	private void selectionChanged() {

        int index = tv.getTable().getSelectionIndex();
        int size = properties.size();

        removeButton.setEnabled(index >= 0);
        upButton.setEnabled(size > 1 && index > 0);
        downButton.setEnabled(size > 1 && index >= 0 && index < size - 1);
    }
	

	protected int convertHorizontalDLUsToPixels(Control control, int dlus) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		int averageWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		double horizontalDialogUnitSize = averageWidth * 0.25;

		return (int) Math.round(dlus * horizontalDialogUnitSize);
	}

	class PropertyContentProvider implements IStructuredContentProvider {
		/**
		 * Returns the Person objects
		 */
		public Object[] getElements(Object inputElement) {
			return ((List) inputElement).toArray();
		}

		/**
		 * Disposes any created resources
		 */
		public void dispose() {
			// Do nothing
		}

		/**
		 * Called when the input changes
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// Ignore
		}
	}

	class PropertyLabelProvider implements ITableLabelProvider {
		/**
		 * Returns the image
		 * 
		 * @param element
		 *            the element
		 * @param columnIndex
		 *            the column index
		 * @return Image
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * Returns the column text
		 * 
		 * @param element
		 *            the element
		 * @param columnIndex
		 *            the column index
		 * @return String
		 */
		public String getColumnText(Object element, int columnIndex) {
			Map obj = (Map) element;
			switch (columnIndex) {
			case 0:
				return obj.get("name").toString();
			case 1:
				return ((ExtScript)obj.get("value")).getContent();
			}
			return null;
		}

		/**
		 * Adds a listener
		 * 
		 * @param listener
		 *            the listener
		 */
		public void addListener(ILabelProviderListener listener) {
			// Ignore it
		}

		/**
		 * Disposes any created resources
		 */
		public void dispose() {
			// Nothing to dispose
		}

		/**
		 * Returns whether altering this property on this element will affect
		 * the label
		 * 
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @return boolean
		 */
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		/**
		 * Removes a listener
		 * 
		 * @param listener
		 *            the listener
		 */
		public void removeListener(ILabelProviderListener listener) {
			// Ignore
		}
	}

	class PropertyCellModifier implements ICellModifier {
		private Viewer viewer;

		public PropertyCellModifier(Viewer viewer) {
			this.viewer = viewer;
		}

		/**
		 * Returns whether the property can be modified
		 * 
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @return boolean
		 */
		public boolean canModify(Object element, String property) {
			// Allow editing of all values
			return true;
		}

		/**
		 * Returns the value for the property
		 * 
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @return Object
		 */
		public Object getValue(Object element, String property) {
			Map p = (Map) element;
			if ("name".equals(property))
				return p.get("name");
			else if ("value".equals(property))
				return ((ExtScript)p.get("value")).getContent();
			else
				return null;
		}

		/**
		 * Modifies the element
		 * 
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @param value
		 *            the value
		 */
		public void modify(Object element, String property, Object value) {
			if (element instanceof Item)
				element = ((Item) element).getData();

			Map p = (Map) element;
			if ("name".equals(property))
				p.put("name", (String) value);
			else if ("value".equals(property))
				((ExtScript)p.get("value")).setContent((String) value);
			viewer.refresh();
		}
	}
	
	public void setList(List list){
		properties = list;
		tv.setInput(properties);
	}
	
	public List getList(){
		return properties;
	}

}