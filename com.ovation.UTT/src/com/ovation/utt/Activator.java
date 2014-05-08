package com.ovation.utt;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.sabre.edge.cf.core.registry.service.ISRWRuntime;
import com.sabre.edge.cf.core.token.IAuthenticationTokenService;
import com.sabre.edge.platform.core.common.plugin.base.AbstractEdgeBasePlugin;
/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractEdgeBasePlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.ovation.UTT"; //$NON-NLS-1$

	// The shared instance
	private ServiceTracker srwRuntimeTracker = null;
	private IAuthenticationTokenService tokenService=null;
	private static Activator plugin;
	private BundleContext ctxRef = null;
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
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
	public static Activator getDefault() {
		return plugin;
	}
	public IAuthenticationTokenService getTokenService(){
		if(tokenService==null){
			tokenService = getServiceReference(IAuthenticationTokenService.class);
		}
		return tokenService;
	}
	public String getUTTWebURL()
	{
		return "apps3.ovationtravel.com/UnusedTickets";
	}
	public ISRWRuntime getSRWRuntime(){
		if(srwRuntimeTracker==null){
			srwRuntimeTracker = new ServiceTracker(ctxRef, ISRWRuntime.class.getName(), null);
			srwRuntimeTracker.open();
		}
		
		return (ISRWRuntime) srwRuntimeTracker.getService();
	}
	/* public String getToken() {
			if (tokenService == null) {
			    tokenService = getServiceReference(IAuthenticationTokenService.class);
			}

			return tokenService.getToken();
		    }*/
}
