package com.harbortek.extbuilder.ui.editors;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.harbortek.extbuilder.code.XCodeGenerator;
import com.harbortek.extbuilder.utils.FileUtils;
import com.harbortek.extbuilder.xmodel.ExtDiagram;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class ExtMultiPageEditor extends MultiPageEditorPart implements IResourceChangeListener {

	/** The text editor used in page 0. */
	private TextEditor txtEditor;

	private ExtJsPageEditor gEditor;

	/**
	 * Creates a multi-page editor example.
	 */
	public ExtMultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Creates page 0 of the multi-page editor, which contains a text editor.
	 */
	void createPage0() {
		try {
			txtEditor = new JSEditorFinder().findEditor();
			
			IFileEditorInput fileEditorInput = (IFileEditorInput)  getEditorInput();
			IFile file = fileEditorInput.getFile();
			IPath path = file.getFullPath().removeFileExtension().addFileExtension("js");
			IFile jsFile = file.getWorkspace().getRoot().getFile(path);
			IFileEditorInput jsFileEditorInput = new FileEditorInput(jsFile);
			
			int index = addPage(txtEditor, jsFileEditorInput);
			setPageText(index, txtEditor.getTitle());
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor", null, e.getStatus());
		}
	}

	/**
	 * Creates page 1 of the multi-page editor, which allows you to change the
	 * font used in page 2.
	 */
	void createPage1() {
		gEditor = new ExtJsPageEditor();
		gEditor.setParent(this);

		int index;
		try {
			index = addPage(gEditor,getEditorInput());
			setPageText(index, "UI Editor");
		} catch (PartInitException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPage0();
		createPage1();
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
		getEditor(1).doSave(monitor);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the
	 * text for page 0's tab, and updates this multi-page editor's input to
	 * correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
//		IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
//		IFile file = fileEditorInput.getFile();
//		IEditorInput[] inputs = new IEditorInput[2];
//		if ("ext".equalsIgnoreCase(file.getFileExtension())) {
//			inputs[1] = editorInput;
//			IPath path = file.getFullPath().removeFileExtension().addFileExtension("js");
//			IFile jsFile = file.getWorkspace().getRoot().getFile(path);
//			inputs[0] = new FileEditorInput(jsFile);
//		}
//		MultiEditorInput input = new MultiEditorInput(new String[] {
//				"jsEdit",
//				"extEditor" }, inputs);

		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	public Object getAdapter(Class type) {
		if (type == IContentOutlinePage.class) {
			return gEditor.getAdapter(type);
		}
		return super.getAdapter(type);
	}

	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (newPageIndex == 0) {
			String content = txtEditor.getDocumentProvider().getDocument(txtEditor.getEditorInput()).get();
			if (gEditor.isDirty() || content == null || content.length() == 0) {
				updateTextContent();
			}
		} else if (newPageIndex == 1) {
			updateGrahpicsContent();
		}

	}

	public void updateGrahpicsContent() {
		String text =
		txtEditor.getDocumentProvider().getDocument(txtEditor.getEditorInput()).get();
		ExtDiagram diagram = gEditor.getModel();

		XCodeGenerator cg = new XCodeGenerator(diagram, text);

		String html = cg.getPreviewHtmlCode();

		File tempFile;
		try {
			tempFile = File.createTempFile("ext", "html");
			FileUtils.writeBytes(tempFile.getAbsolutePath(), html.getBytes("UTF-8"), false);
			gEditor.getBrowser().setUrl("file:///" + tempFile.getAbsolutePath());
			tempFile.deleteOnExit();
		} catch (IOException e) {
		}
	}

	public void updateTextContent() {
		ExtDiagram diagram = gEditor.getModel();
		String text = txtEditor.getDocumentProvider().getDocument(txtEditor.getEditorInput()).get();
		String result = new XCodeGenerator(diagram, text).getJsContent();
		result = new JSEditorFinder().formatJsCode(0, result);
		txtEditor.getDocumentProvider().getDocument(txtEditor.getEditorInput()).set(result);
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) txtEditor.getEditorInput()).getFile().getProject().equals(
								event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(txtEditor.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	public IEditorPart getActiveEditor() {
		return super.getActiveEditor();
	}

}
