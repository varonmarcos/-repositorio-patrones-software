package org.kallsonnys.security.dao;

import javax.naming.CommunicationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;
import org.kallsonys.oms.commons.locator.LDAPLocator;

public abstract class LDAPBaseDAO {
	private static final Logger logger = Logger.getLogger(LDAPBaseDAO.class);

	private DirContext dirContext;

	public DirContext getDirContext() {
		return dirContext;
	}

	public void setDirContext(DirContext dirContext) {
		this.dirContext = dirContext;
	}

	protected void validateDirContext() {
		logger.debug("LDAP validateDirContext()::started");

		if (dirContext != LDAPLocator.getInstance().getInitialDirContext()) {
			setDirContext(LDAPLocator.getInstance().getInitialDirContext());
		}
		
		if (dirContext == null) {
			logger.error("Invalid argument");
			logger.debug("validateDirContext()::finished");
			throw new IllegalArgumentException();
		}
		
		try {
			dirContext.search("dc=etask,dc=it", "(objectClass=organization)", null);
		} catch (CommunicationException ce) {
			logger.error("- LDAP CommunicationException While Trying to Validate LDAP Connection - Reestablishing LDAP Connection...", ce);
			LDAPLocator.getInstance().close();
			setDirContext(LDAPLocator.getInstance().getInitialDirContext());
			logger.error("- LDAP Connection to LDAP Reestablished");
		} catch (NamingException ne) {}

		logger.debug("LDAP validateDirContext()::finished");
	}
	
	/**
	 * @param results
	 */
	protected void closeSearchResults(NamingEnumeration<SearchResult> results) {
		if (results != null) {
			try {
				results.close();
			} catch (Exception e) {
				logger.error("Exception while trying to close results");
			}
		}
	}

	/**
	 * @param resultAttributes
	 * @param key
	 * @return
	 * @throws NamingException
	 */
	protected Object getValue(Attributes resultAttributes, String key) {
		Object value = null;

		try {
			Attribute attribute = resultAttributes.get(key);
			if (attribute != null) {
				value = attribute.get();
			}
		} catch (Exception e) {
			logger.warn("Exception while trying to get value for key " + key
					+ ": " + e.getMessage());
		}

		return value;
	}
}
