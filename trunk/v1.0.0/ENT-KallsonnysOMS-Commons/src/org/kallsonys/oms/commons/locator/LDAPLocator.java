package org.kallsonys.oms.commons.locator;

import java.io.InputStream;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

public class LDAPLocator {
	private static final Logger logger = Logger.getLogger(LDAPLocator.class);

	private static final String LDAP_PROPERTIES_FILE = "KallsonnysOMS-LDAP.properties";

	private LDAPLocator() {
	}

	private static class LDAPLocatorHolder {
		private final static LDAPLocator INSTANCE = new LDAPLocator();
	}

	public static LDAPLocator getInstance() {
		return LDAPLocatorHolder.INSTANCE;
	}

	private DirContext dirContext;

	public DirContext getInitialDirContext() {
		logger.debug("LDAP getInitialDirContext()::started");

		if (dirContext == null) {
			Properties properties = new Properties();
			try {
				// properties.load(new FileInputStream(LDAP_PROPERTIES_FILE));
				InputStream im = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(LDAP_PROPERTIES_FILE);
				properties.load(im);
			} catch (Exception e) {
				logger.error("Error trying to load file "+ LDAP_PROPERTIES_FILE, e);
				logger.info("getInitialDirContext()::finished");
				throw new RuntimeException("ERROR IN LDAP", e);
			}

			try {
				dirContext = new InitialDirContext(properties);
			} catch (NamingException e) {
				logger.error("- LDAP Error trying to get initial context...", e);
				throw new RuntimeException("ERROR IN LDAP", e);
			}
		}

		logger.debug("LDAP getInitialDirContext()::finished");
		return dirContext;
	}

	public void close() {
		logger.debug("LDAP close()::started");

		try {
			if (dirContext != null) {
				dirContext.close();
				dirContext = null;
			}
		} catch (Exception e) {
			logger.error("- LDAP Exception while trying to close dir context.", e);
		} finally {
			logger.debug("LDAP close()::finished");
		}
	}
}
