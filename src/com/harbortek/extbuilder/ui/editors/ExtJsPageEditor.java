package com.harbortek.extbuilder.ui.editors;

import java.io.ByteArrayInputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.SimpleRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.harbortek.extbuilder.edit.ExtEditPartFactory;
import com.harbortek.extbuilder.edit.ExtTreeEditPartFactory;
import com.harbortek.extbuilder.ui.actions.CopyExtCompAction;
import com.harbortek.extbuilder.ui.actions.CutExtCompAction;
import com.harbortek.extbuilder.ui.actions.MoveDownAction;
import com.harbortek.extbuilder.ui.actions.MoveUpAction;
import com.harbortek.extbuilder.ui.actions.PasteExtCompAction;
import com.harbortek.extbuilder.utils.FileUtils;
import com.harbortek.extbuilder.xmodel.ExtDiagram;

public class ExtJsPageEditor extends GraphicalEditorWithPalette implements
		IResourceChangeListener {

	// private XStream xStream;

	private PaletteRoot root;

	private ExtDiagram extDiagram;

	private ExtMultiPageEditor parent;

	/**
	 * Creates a multi-page editor example.
	 */
	public ExtJsPageEditor() {
		super();
		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		setEditDomain(defaultEditDomain);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	public ExtMultiPageEditor getParent() {
		return parent;
	}

	public void setParent(ExtMultiPageEditor parent) {
		this.parent = parent;
	}

	public ExtDiagram getModel() {
		return extDiagram;
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		this.parent = null;
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	protected void createGraphicalViewer(Composite parent) {
		BrowserEditPartViewer viewer = new BrowserEditPartViewer();
		viewer.createControl(parent);
		setGraphicalViewer(viewer);
		configureGraphicalViewer();
		hookGraphicalViewer();
		initializeGraphicalViewer();
	}

	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(getModel()); // set the contents of this editor

		// viewer.addDropTargetListener(createTransferDropTargetListener());
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new ExtEditPartFactory());
		viewer.setRootEditPart(new SimpleRootEditPart());
		// ContextMenuProvider cmProvider = new
		// ExtJsEditorContextMenuProvider(viewer, getActionRegistry());
		// viewer.setContextMenu(cmProvider);
		// getSite().registerContextMenu(cmProvider, viewer);
		//
		// getGraphicalViewer().setKeyHandler(
		// new
		// GraphicalViewerKeyHandler(getGraphicalViewer()).setParent(getCommonKeyHandler()));
	}

	protected PaletteRoot getPaletteRoot() {
		if (root == null)
			root = PaletteFactory.createPalette((ExtDiagram) getModel());
		return root;
	}

	// private TransferDropTargetListener createTransferDropTargetListener() {
	// return new TemplateTransferDropTargetListener(getGraphicalViewer()) {
	// protected CreationFactory getFactory(Object template) {
	// return new SimpleFactory((Class) template);
	// }
	// };
	// }

	public Browser getBrowser() {
		return ((BrowserEditPartViewer) getGraphicalViewer()).getBrowser();
	}

	public Object getAdapter(Class type) {
		if (type == IContentOutlinePage.class)
			return new ExtJsOutlinePage(new TreeViewer());
		return super.getAdapter(type);
	}

	public void doSave(IProgressMonitor monitor) {
		try {
			//String result = getModel().toXML();

			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			file.setContents(new ByteArrayInputStream(ExtDiagram.save(getModel())), true, false, monitor);
//			file.setContents(new ByteArrayInputStream(result.getBytes()), true,
//					false, monitor);
			getCommandStack().markSaveLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resourceChanged(IResourceChangeEvent event) {

	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	/**
	 * Property changed and notify editor is dirty
	 * 
	 */
	public void notifyChanged() {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	protected void setInput(IEditorInput input) {
		super.setInput(input);
		try {
			IFile file = ((IFileEditorInput) input).getFile();
			extDiagram = ExtDiagram.load(FileUtils.readBytes(file.getContents()));
			//extDiagram.fromXML(file.getContents());
			setPartName(file.getName());
		} catch (Exception e) {
			handleLoadException(e);
		}
	}

	private void handleLoadException(Exception e) {
		System.err.println("** Load failed. Using default model. **");
		// e.printStackTrace();
		extDiagram = new ExtDiagram();
	}

	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new CopyExtCompAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new PasteExtCompAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new CutExtCompAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveUpAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveDownAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// If not the active editor, ignore selection changed.
		IEditorPart editor = getSite().getPage().getActiveEditor();
		if (editor instanceof MultiPageEditorPart) {
			// IEditorPart activeEditor =
			// ((MultiPageEditorPart)editor).getActiveEditor();
			// if (this.equals( activeEditor)){
			updateActions(getSelectionActions());
			// }
		}
	}

	// private KeyHandler sharedKeyHandler;
	//
	// protected KeyHandler getCommonKeyHandler() {
	// if (sharedKeyHandler == null) {
	// sharedKeyHandler = new KeyHandler();
	// sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
	// getActionRegistry().getAction(
	// ActionFactory.DELETE.getId()));
	// }
	// return sharedKeyHandler;
	// }

	public class ExtJsOutlinePage extends ContentOutlinePage {
		/**
		 * Create a new outline page for the shapes editor.
		 * 
		 * @param viewer
		 *            a viewer (TreeViewer instance) used for this outline page
		 * @throws IllegalArgumentException
		 *             if editor is null
		 */
		public ExtJsOutlinePage(EditPartViewer viewer) {
			super(viewer);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			getViewer().createControl(parent);
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new ExtTreeEditPartFactory());
			getSelectionSynchronizer().addViewer(getViewer());
			getViewer().setContents(getModel());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#dispose()
		 */
		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());
			super.dispose();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#getControl()
		 */
		public Control getControl() {
			return getViewer().getControl();
		}

		/**
		 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
		 */
		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
			String id = ActionFactory.UNDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.REDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));

			id = ActionFactory.COPY.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));

			id = ActionFactory.CUT.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));

			id = ActionFactory.PASTE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));

			id = MoveUpAction.MOVE_UP;
			bars.getToolBarManager().add(registry.getAction(id));

			id = MoveDownAction.MOVE_DOWN;
			bars.getToolBarManager().add(registry.getAction(id));
		}

	}

}
