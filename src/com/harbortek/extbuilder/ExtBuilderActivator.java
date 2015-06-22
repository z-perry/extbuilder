package com.harbortek.extbuilder;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.harbortek.extbuilder.xmodel.ExtXmlManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class ExtBuilderActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.harbortek.extbuilder";

	// The shared instance
	private static ExtBuilderActivator plugin;

	/**
	 * The constructor
	 */
	public ExtBuilderActivator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		ExtXmlManager.loadXmlDefinitions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ExtBuilderActivator getDefault() {
		return plugin;
	}

}
