package com.harbortek.extbuilder.ui.wizards;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.FieldAssistColors;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ContainerGenerator;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.harbortek.extbuilder.code.XCodeGenerator;
import com.harbortek.extbuilder.ui.editors.JSEditorFinder;
import com.harbortek.extbuilder.xmodel.ExtDiagram;
import com.harbortek.extbuilder.xmodel.ExtXmlElement;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (ext).
 */

public class ExtJsNewWizardPage extends WizardPage implements Listener {
	private Text packageNameText;

	private Text classNameText;

	private Text fileNameText;

	private Combo cmbSuper;

	// private Text previewFileNameText;

	// widgets
	private ContainerSelectionGroup containerGroup;

	// initial value stores
	private String initialFileName;

	private IPath initialContainerFullPath;

	// the current resource selection
	private IStructuredSelection currentSelection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ExtJsNewWizardPage(IStructuredSelection selection) {
		super("wizardPage");
		this.currentSelection = selection;
		this.initialFileName = "newfile.ext";
		setTitle("ExtJs Visual File");
		setDescription("This wizard creates a new file with *.ext extension that can be opened by a ExtJs editor.");
	}

	protected String getNewFileLabel() {
		return "New File";
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));

		Composite topLevel = new Composite(container, SWT.NONE);
		topLevel.setLayout(new GridLayout());
		topLevel.setLayoutData(new GridData(SWT.FILL, GridData.CENTER, true,
				true, 2, 1));
		topLevel.setFont(parent.getFont());
		// topLevel.setBackground(ColorConstants.red);
		containerGroup = new ContainerSelectionGroup(topLevel, this, true,
				null, false, 250, 250);
		initialPopulateContainerNameField();

		Label label = new Label(container, SWT.NULL);
		label.setFont(parent.getFont());
		label.setText("Namespace:");
		packageNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		packageNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		packageNameText.setBackground(FieldAssistColors
				.getRequiredFieldBackgroundColor(packageNameText));
		packageNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleEvent(null);
			}
		});

		label = new Label(container, SWT.NULL);
		label.setFont(parent.getFont());
		label.setText("Class Name:");
		classNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		classNameText.setBackground(FieldAssistColors
				.getRequiredFieldBackgroundColor(classNameText));
		classNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				fileNameText.setText(classNameText.getText() + ".js");
				handleEvent(null);
			}
		});

		label = new Label(container, SWT.NULL);
		label.setFont(parent.getFont());
		label.setText("File Name:");
		fileNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileNameText.setBackground(FieldAssistColors
				.getRequiredFieldBackgroundColor(fileNameText));
		fileNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleEvent(null);
			}
		});

		label = new Label(container, SWT.NULL);
		label.setFont(parent.getFont());
		label.setText("Super Class:");
		cmbSuper = new Combo(container, SWT.BORDER | SWT.LEAD | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		cmbSuper.setFocus();
		cmbSuper.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		cmbSuper.setItems(new String[] { "Ext.Panel", "Ext.Window" });
		cmbSuper.select(0);

		setControl(container);
	}

	public void setContainerFullPath(IPath path) {
		IResource initial = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(path);
		if (initial != null) {
			if (!(initial instanceof IContainer)) {
				initial = initial.getParent();
			}
			containerGroup.setSelectedContainer((IContainer) initial);
		}
	}

	protected void initialPopulateContainerNameField() {
		if (initialContainerFullPath != null) {
			setContainerFullPath(initialContainerFullPath);
		} else {
			Iterator it = currentSelection.iterator();
			if (it.hasNext()) {
				Object object = it.next();
				IResource selectedResource = null;
				if (object instanceof IResource) {
					selectedResource = (IResource) object;
				} else if (object instanceof IAdaptable) {
					selectedResource = (IResource) ((IAdaptable) object)
							.getAdapter(IResource.class);
				}
				if (selectedResource != null) {
					if (selectedResource.getType() == IResource.FILE) {
						selectedResource = selectedResource.getParent();
					}
					if (selectedResource.isAccessible()) {
						setContainerFullPath(selectedResource.getFullPath());
					}
				}
			}
		}
	}

	public void handleEvent(Event event) {
		setPageComplete(validatePage());
	}

	protected boolean validatePage() {
		boolean valid = true;
		if (packageNameText == null) {
			return false;
		}
		String packageName = packageNameText.getText();
		IStatus status = JavaConventions.validatePackageName(packageName);
		if (status.getSeverity() == IStatus.ERROR) {
			setErrorMessage("namespace is not valid");
			valid = false;
			return false;
		}

		String className = classNameText.getText();
		status = JavaConventions.validateCompilationUnitName(className
				+ ".java");
		if (status.getSeverity() == IStatus.ERROR) {
			setErrorMessage("class name is not valid");
			valid = false;
			return false;
		}

		String fileName = fileNameText.getText();
		if (!fileName.endsWith(".js")) {
			setErrorMessage("file name must be end with .js");
			valid = false;
			return false;
		}
		setErrorMessage(null);
		setMessage("");

		return valid;
	}

	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}

	protected ISchedulingRule createRule(IResource resource) {
		IResource parent = resource.getParent();
		while (parent != null) {
			if (parent.exists()) {
				return resource.getWorkspace().getRuleFactory().createRule(
						resource);
			}
			resource = parent;
			parent = parent.getParent();
		}
		return resource.getWorkspace().getRoot();
	}

	private void createFile(IFile newExtFileHandle, IFile newJsFileHandle,
			String packageName, String className, String superClassName,
			SubProgressMonitor monitor) {

		try {
			ExtDiagram diagram = new ExtDiagram();
			diagram.setPackageName(packageName);
			diagram.setClassName(packageName + "." + className);
			diagram.setSuperClass(superClassName);
			ExtXmlElement el = new ExtXmlElement(superClassName);
			el.setDiagram(diagram);
			if ("Ext.Window".equals(superClassName)) {
				el.setPropertyValue("width", new Integer(600));
				el.setPropertyValue("height", new Integer(400));
			}
			diagram.addChild(el);

			XCodeGenerator cg = new XCodeGenerator(diagram, null);
			String jsContent = cg.getJsContent();
			jsContent = new JSEditorFinder().formatJsCode(0, jsContent);
			//String xmlContent = diagram.toXML();
			byte[] xmlContent = ExtDiagram.save(diagram);
			newExtFileHandle.create(new ByteArrayInputStream(xmlContent), true, monitor);

			newJsFileHandle.create(new ByteArrayInputStream(jsContent
					.getBytes()), true, monitor);
		} catch (Exception e) {
		}
	}

	public IFile createNewFile() {
		final IPath containerPath = containerGroup.getContainerFullPath();
		String extFileName = fileNameText.getText().substring(0,
				fileNameText.getText().length() - 2)
				+ "ext";
		String jsFileName = extFileName.substring(0, extFileName.length() - 3)
				+ "js";
		String superClass = "Ext.Panel";
		if (cmbSuper.getSelectionIndex() == 1) {
			superClass = "Ext.Window";
		}
		// String htmlName = previewFileNameText.getText();
		IPath newExtFilePath = containerPath.append(extFileName);
		IPath newJsFilePath = containerPath.append(jsFileName);
		// IPath newHtmlFilePath = containerPath.append(htmlName);
		final IFile newExtFileHandle = createFileHandle(newExtFilePath);
		final IFile newJsFileHandle = createFileHandle(newJsFilePath);
		// final IFile newHtmlFileHandle = createFileHandle(newHtmlFilePath);
		final String packageName = packageNameText.getText();
		final String className = classNameText.getText();
		final String superClassName = superClass;

		WorkspaceModifyOperation op = new WorkspaceModifyOperation(
				createRule(newExtFileHandle)) {
			protected void execute(IProgressMonitor monitor)
					throws CoreException {
				try {
					monitor.beginTask("", 2000);
					ContainerGenerator generator = new ContainerGenerator(
							containerPath);
					generator.generateContainer(new SubProgressMonitor(monitor,
							1000));
					createFile(newExtFileHandle, newJsFileHandle, packageName,
							className, superClassName, new SubProgressMonitor(
									monitor, 1000));
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog
						.openError(
								getContainer().getShell(), // Was
								// Utilities.getFocusShell()
								IDEWorkbenchMessages.WizardNewFileCreationPage_errorTitle,
								null, // no
								// special
								// message
								((CoreException) e.getTargetException())
										.getStatus());
			} else {
				// CoreExceptions are handled above, but unexpected runtime
				// exceptions and errors may still occur.
				IDEWorkbenchPlugin.log(getClass(),
						"createNewFile()", e.getTargetException()); //$NON-NLS-1$
				MessageDialog
						.openError(
								getContainer().getShell(),
								IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
								NLS
										.bind(
												IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
												e.getTargetException()
														.getMessage()));
			}
		}

		return newExtFileHandle;
	}

}