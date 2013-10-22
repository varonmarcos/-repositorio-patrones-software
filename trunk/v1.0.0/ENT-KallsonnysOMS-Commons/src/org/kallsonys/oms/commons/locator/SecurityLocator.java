package org.kallsonys.oms.commons.locator;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class SecurityLocator{

	/** Control and audit log */
	private static final Logger log = Logger.getLogger(SecurityLocator.class);

	private static final String LOCATOR_USER = "KALLSONNYS_USER";
	private static final String LOCATOR_PWD = "KALLSONNYS_PWD";
	private static final String EAR_JNDI_NAME_KEY = "ear_jndi_name";
	private static final String PROPERTIES_PATH = "KallsonnysOMS-Security";

	private Map<String, String> contextParams = null;

	private static SecurityLocator instance;
	
	private ResourceBundleManager bundleManager;

	/**
	 * Constant which indicates the application name was recorded in the server
	 * context.
	 * 
	 * If EJB is in an ear: earName/ejbInterfaceName/local (or /remote) If EJB
	 * is in an jar: ejbInterfaceName/local (or /remote)
	 */
	private String EAR_JNDI_NAME;

	private SecurityLocator() {
		bundleManager = new ResourceBundleManager(PROPERTIES_PATH);
		EAR_JNDI_NAME = bundleManager.getProperty(EAR_JNDI_NAME_KEY);
	}

	private SecurityLocator(Map<String, String> params) {
		bundleManager = new ResourceBundleManager(PROPERTIES_PATH);
		EAR_JNDI_NAME = bundleManager.getProperty(EAR_JNDI_NAME_KEY);
		this.setContextParams(params);
	}

	public static SecurityLocator getInstance() {
		if (instance == null) {
			instance = new SecurityLocator();
		}
		return instance;
	}

	public static SecurityLocator getInstance(Map<String, String> params) {
		if (instance == null) {
			instance = new SecurityLocator(params);
		}
		return instance;
	}

	/**
	 * Method used to obtain a remote reference to a platform object (eTask) is
	 * considered EJB that implements the interface
	 * 
	 * @param objname
	 *            JNDI name of the object to obtain.
	 * @return A server remote reference found under the name parameter passed.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getRemoteObject(String objname) {

		log.debug("getRemoteObject(String objname)::started");
		Object retValue = null;

		try {

			Context context = getInitialContext(getContextParams());

			log.debug("Getting the remote object " + EAR_JNDI_NAME + "/"
					+ objname + "/remote");
			retValue = context
					.lookup(EAR_JNDI_NAME + "/" + objname + "/remote");

		} catch (Exception e) {
			log.error(
					"- General ERROR obtaining reference to remote object ->",
					e);
			throw new LocatorException(
					"- Could not obtain reference to remote object " + objname);

		} finally {
			log.debug("getRemoteObject(String objname)::finished");
		}
		return (T) retValue;
	}

	/**
	 * Method that gets the initial context with the parameters to establish the
	 * connection
	 * 
	 * @param params
	 *            load the URl, the EAR JNDI Name and the security credentials
	 *            for the initial connection if this object is null load the
	 *            values from a properties files
	 * @return The Initial Context to make the JNDI Connection
	 * @throws NamingException
	 *             An exception if some of the given parameters could not load
	 *             in the Context
	 */
	public Context getInitialContext(Map<String, String> params)
			throws NamingException {
		Properties properties = new Properties();

		if (null != params) {

			String usr = params.get(LOCATOR_USER);
			if (null != usr)
				properties.put(Context.SECURITY_PRINCIPAL, usr);

			String pwd = params.get(LOCATOR_PWD);
			if (null != pwd)
				properties.put(Context.SECURITY_CREDENTIALS, pwd);

		}

		properties.put(Context.INITIAL_CONTEXT_FACTORY, bundleManager
				.getProperty(Context.INITIAL_CONTEXT_FACTORY));
		properties.put(Context.URL_PKG_PREFIXES,
				bundleManager.getProperty(Context.URL_PKG_PREFIXES));

		properties.put(Context.PROVIDER_URL,
				bundleManager.getProperty(Context.PROVIDER_URL));

		return new InitialContext(properties);
	}

	public Map<String, String> getContextParams() {
		return contextParams;
	}

	public void setContextParams(Map<String, String> contextParams) {
		this.contextParams = contextParams;
	}
	

}