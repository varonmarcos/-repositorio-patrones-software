package org.kallsonnys.security.dao;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;
import org.kallsonnys.oms.dto.security.UserDTO;


public class LDAPUserDAO extends LDAPBaseDAO {
	

	private static final Logger logger = Logger.getLogger(LDAPUserDAO.class);
	
	private static final String USER_ID_KEY = "uid";
	private static final String USER_PASSWORD_KEY = "userPassword";

	private static final String PEOPLE_ROOT_DN = "ou=People,dc=maxcrc,dc=com";

	public UserDTO getUser(UserDTO user) {
		logger.debug("getUser(User user)::started");

		NamingEnumeration<SearchResult> results = null;

		try {

			validateDirContext();

			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

			String filter = "(&(uid="
					+ user.getUid()+ "))";

			results = getDirContext().search(PEOPLE_ROOT_DN, filter,
					searchControls);

			if (results.hasMoreElements()) {
				UserDTO storedUser = new UserDTO();
				mapToUserDTO(storedUser, results.nextElement().getAttributes());
				return storedUser;
			} else {
				logger.error("User " + user + " not found.");
				throw new RuntimeException("USER NOT FOUND");
			}

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while trying to get user " + user, e);
			throw new RuntimeException("ERROR IN LDAP",e);
		} finally {
			closeSearchResults(results);
			logger.debug("getUser(User user)::finished");
		}
	}

	private void mapToUserDTO(UserDTO user, Attributes attributes) {
		
		Object value = null;

		value = getValue(attributes, USER_ID_KEY);
		if (value != null) {
			user.setUid((String) value);
		}
		
		byte[] bytes = (byte[]) getValue(attributes, USER_PASSWORD_KEY);
		if (value != null) {
			String password = new String(bytes);
			user.setPassword(password.substring(password.indexOf('}') + 1));
		}
		
	}
}
