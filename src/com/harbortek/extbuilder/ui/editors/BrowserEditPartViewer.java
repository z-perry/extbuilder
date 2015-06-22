package com.harbortek.extbuilder.ui.editors;

import java.util.Collection;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Handle;
import org.eclipse.gef.ui.parts.AbstractEditPartViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class BrowserEditPartViewer extends AbstractEditPartViewer implements GraphicalViewer {

	public Control createControl(Composite parent) {
		final Browser browser = new Browser(parent, SWT.NONE);
		setControl(browser);
		browser.addControlListener(new ControlListener(){

			public void controlMoved(ControlEvent e) {
			}

			public void controlResized(ControlEvent e) {
				browser.refresh();
			}});
		return browser;
	}

	public Browser getBrowser() {
		return (Browser) getControl();
	}

	public EditPart findObjectAtExcluding(Point location, Collection exclusionSet, Conditional conditional) {
		// TODO Auto-generated method stub
		return null;
	}

	public Handle findHandleAt(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
