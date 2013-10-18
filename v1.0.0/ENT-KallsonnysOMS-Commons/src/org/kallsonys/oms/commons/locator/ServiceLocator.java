package org.kallsonys.oms.commons.locator;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class ServiceLocator {

	/** Control and audit log */
	private static final Logger log = Logger.getLogger(ServiceLocator.class);

	private static final String LOCATOR_USER = "KALLSONNYS_USER";
	private static final String LOCATOR_PWD = "KALLSONNYS_PWD";
	private static final String EAR_JNDI_NAME_KEY = "ear_jndi_name";

	private Map<String, String> contextParams = null;

	private static ServiceLocator instance;

	/**
	 * Constant which indicates the application name was recorded in the server
	 * context.
	 * 
	 * If EJB is in an ear: earName/ejbInterfaceName/local (or /remote) If EJB
	 * is in an jar: ejbInterfaceName/local (or /remote)
	 */
	private String EAR_JNDI_NAME = ResourceBundleManager
			.getProperty(EAR_JNDI_NAME_KEY);

	private ServiceLocator() {

	}

	private ServiceLocator(Map<String, String> params) {
		this.setContextParams(params);
	}

	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

	public static ServiceLocator getInstance(Map<String, String> params) {
		if (instance == null) {
			instance = new ServiceLocator(params);
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

			EAR_JNDI_NAME = ResourceBundleManager
					.getProperty(EAR_JNDI_NAME_KEY);

			log.debug("Getting the remote object " + EAR_JNDI_NAME + "/"
					+ objname + "/remote");
			retValue = context
					.lookup(EAR_JNDI_NAME + "/" + objname + "/remote");

		} catch (Exception e) {
			log.error(
					"- General ERROR obtaining reference to remote object ->",
					e);
			throw new IllegalArgumentException(
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
	private Context getInitialContext(Map<String, String> params)
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

		properties.put(Context.INITIAL_CONTEXT_FACTORY, ResourceBundleManager
				.getProperty(Context.INITIAL_CONTEXT_FACTORY));
		properties.put(Context.URL_PKG_PREFIXES,
				ResourceBundleManager.getProperty(Context.URL_PKG_PREFIXES));

		properties.put(Context.PROVIDER_URL,
				ResourceBundleManager.getProperty(Context.PROVIDER_URL));

		return new InitialContext(properties);
	}

	public Map<String, String> getContextParams() {
		return contextParams;
	}

	public void setContextParams(Map<String, String> contextParams) {
		this.contextParams = contextParams;
	}

}