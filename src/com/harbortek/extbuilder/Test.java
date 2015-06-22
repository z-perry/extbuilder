package com.harbortek.extbuilder;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.model.data.ExtDataStore;
import com.harbortek.extbuilder.ui.dialogs.StorePropertiesDialog;

public class Test {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Shell");
		shell.setSize(200, 200);
		shell.open();
		StorePropertiesDialog dialog = new StorePropertiesDialog(shell);
		ExtDataStore store = new ExtDataStore();
		store.setVariableName("store1");
		dialog.setStore(store);
		dialog.open();
		ExtDataStore store2 = dialog.getStore();
		String code = store2.getCode(new CodeContext());
		System.out.println(code);
		
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
		display.dispose();

	}
}
