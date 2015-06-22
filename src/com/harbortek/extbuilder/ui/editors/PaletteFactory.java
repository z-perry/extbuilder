package com.harbortek.extbuilder.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;

import com.harbortek.extbuilder.extract.ExtClass;
import com.harbortek.extbuilder.xmodel.ExtDiagram;
import com.harbortek.extbuilder.xmodel.ExtXmlManager;

public class PaletteFactory {
	public static PaletteRoot createPalette(ExtDiagram diagram) {
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.addAll(createCategories(paletteRoot, diagram));
		return paletteRoot;
	}

	private static List createCategories(PaletteRoot root, ExtDiagram diagram) {
		List categories = new ArrayList();

		String[] cs = ExtXmlManager.getCategories();
		for (int i = 0; i < cs.length; i++) {
			String category = cs[i];

			if (StringUtils.isNotEmpty(category)) {
				PaletteDrawer pc = (PaletteDrawer) createPalette(category,
						diagram);
				categories.add(pc);
				if (i == 0) {
					pc.setInitialState(PaletteDrawer.INITIAL_STATE_OPEN);
				} else {
					pc.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
				}
			}

		}

		// categories.add(createGeneralControlGroup(root));
		// categories.add(createContainer());
		// categories.add(createFormField());
		// categories.add(createDataDrawer());

		return categories;
	}

	private static PaletteContainer createPalette(String category,
			ExtDiagram diagram) {
		PaletteDrawer drawer = new PaletteDrawer(category);
		List entries = new ArrayList();
		List compList = ExtXmlManager.getExtClassList(category);
		Collections.sort(compList, new Comparator() {
			public int compare(Object o1, Object o2) {
				ExtClass extClass1 = (ExtClass) o1;
				ExtClass extClass2 = (ExtClass) o2;
				return extClass1.getClassName().compareTo(
						extClass2.getClassName());
			}
		});
		for (Iterator iter = compList.iterator(); iter.hasNext();) {
			ExtClass extClass = (ExtClass) iter.next();
			if (StringUtils.isNotEmpty(extClass.getLabel())
					&& StringUtils.isNotEmpty(extClass.getId())) {
				CreationToolEntry entry = new CreationToolEntry(extClass
						.getLabel(), "", new ExtFactory(extClass.getId(),
						diagram), null, null);
				entries.add(entry);
			}
		}
		drawer.addAll(entries);
		return drawer;
	}

	// private static PaletteContainer createGeneralControlGroup(PaletteRoot
	// root) {
	// PaletteGroup controlGroup = new PaletteGroup("Tools");
	//
	// List entries = new ArrayList();
	// ToolEntry tool = null;
	// tool = new SelectionToolEntry("Selection");
	// entries.add(tool);
	// root.setDefaultEntry(tool);
	//
	// tool = new MarqueeToolEntry("Area Selection");
	// entries.add(tool);
	//
	// controlGroup.addAll(entries);
	// return controlGroup;
	// }
	//
	// private static PaletteContainer createContainer() {
	// PaletteDrawer drawer = new PaletteDrawer("Container");
	// List entries = new ArrayList();
	//
	// // entries.add(new CreationToolEntry("BoxComponent", "", new
	// // SimpleFactory(ExtBoxComponent.class), null, null));
	//
	// entries.add(new CreationToolEntry("Panel", "", new SimpleFactory(
	// ExtPanel.class), null, null));
	//
	// entries.add(new CreationToolEntry("FormPanel", "", new SimpleFactory(
	// ExtFormPanel.class), null, null));
	//
	// entries.add(new CreationToolEntry("TabPanel", "", new SimpleFactory(
	// ExtTabPanel.class), null, null));
	//
	// entries.add(new CreationToolEntry("FieldSet", "", new SimpleFactory(
	// ExtFieldSet.class), null, null));
	//
	// entries.add(new CreationToolEntry("TreePanel", "", new SimpleFactory(
	// ExtTreePanel.class), null, null));
	//
	// entries.add(new CreationToolEntry("GridPanel", "", new SimpleFactory(
	// ExtGridPanel.class), null, null));
	//
	// entries.add(new CreationToolEntry("GridPagingPanel", "",
	// new SimpleFactory(ExtPagingGridPanel.class), null, null));
	//
	// drawer.addAll(entries);
	// return drawer;
	// }
	//
	// private static PaletteContainer createFormField() {
	//
	// PaletteDrawer drawer = new PaletteDrawer("Form Field");
	// List entries = new ArrayList();
	//
	// entries.add(new CreationToolEntry("Text", "", new SimpleFactory(
	// ExtTextField.class), null, null));
	//
	// entries.add(new CreationToolEntry("Password", "", new SimpleFactory(
	// ExtPasswordField.class), null, null));
	//
	// entries.add(new CreationToolEntry("Number", "", new SimpleFactory(
	// ExtNumberField.class), null, null));
	//
	// entries.add(new CreationToolEntry("Combox", "", new SimpleFactory(
	// ExtCombox.class), null, null));
	//
	// entries.add(new CreationToolEntry("Checkbox", "", new SimpleFactory(
	// ExtCheckbox.class), null, null));
	//
	// entries.add(new CreationToolEntry("Radio", "", new SimpleFactory(
	// ExtRadio.class), null, null));
	//
	// entries.add(new CreationToolEntry("TextArea", "", new SimpleFactory(
	// ExtTextArea.class), null, null));
	//
	// entries.add(new CreationToolEntry("Date Field", "", new SimpleFactory(
	// ExtDateField.class), null, null));
	//
	// entries.add(new CreationToolEntry("Time Field", "", new SimpleFactory(
	// ExtTimeField.class), null, null));
	//
	// entries.add(new CreationToolEntry("Html Editor", "", new SimpleFactory(
	// ExtHtmlEditor.class), null, null));
	//
	// entries.add(new CreationToolEntry("Button", "", new SimpleFactory(
	// ExtButton.class), null, null));
	//
	// drawer.addAll(entries);
	// return drawer;
	// }
	//
	// private static PaletteContainer createDataDrawer() {
	//
	// PaletteDrawer drawer = new PaletteDrawer("Data");
	// List entries = new ArrayList();
	//
	// entries.add(new CreationToolEntry("DataStore", "", new SimpleFactory(
	// ExtDataStore.class), null, null));
	//
	// entries.add(new CreationToolEntry("Column Model", "",
	// new SimpleFactory(ExtColumnModel.class), null, null));
	//
	// drawer.addAll(entries);
	// return drawer;
	// }

}