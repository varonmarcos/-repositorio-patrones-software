package org.kallsonnys.security.dao;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonys.oms.commons.locator.LDAPLocator;


public class LDAPUserDAO extends LDAPBaseDAO {
	

	private static final Logger logger = Logger.getLogger(LDAPUserDAO.class);
	
	private static final String USER_ID_KEY = "klsUserEmail";
	private static final String USER_PASSWORD_KEY = "klsUserPassword";

	private static final String PEOPLE_ROOT_DN = "ou=People,dc=kallsonnys,dc=com";
	private static final String USER_CN_KEY = "cn";
	private static final String USER_SN_KEY = "sn";

	public UserDTO getUser(UserDTO user) {
		logger.debug("getUser(User user)::started");

		NamingEnumeration<SearchResult> results = null;

		try {

			validateDirContext();

			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

			String filter = "(&(klsUserEmail="
					+ user.getEmail()+ "))";

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
	
	public void createUser(UserDTO userDTO){
		
		validateDirContext();
		
		try {
			getDirContext().bind(getUserDN(userDTO), null, mapUserToAttributes(userDTO));
		} catch (NamingException e) {
			e.printStackTrace();
			logger.error("Exception while trying to get user " + userDTO, e);
			throw new RuntimeException("ERROR IN LDAP",e);
		}finally {
			try {
				LDAPLocator.getInstance().close();
				
			} catch (Exception e) {
				logger.error("- Error closing LDAP connection ", e);
			}
			logger.info("LDAP createUser(user)::finished");
		}
	}

	private Attributes mapUserToAttributes(UserDTO userDTO) {
		BasicAttributes attributes = new BasicAttributes();

		// Define attributes
		BasicAttribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("KallsonnysUser");
		
		
		attributes.put(objectClass);
		
		if (userDTO.getEmail() != null) {
			BasicAttribute id = new BasicAttribute(USER_ID_KEY, userDTO.getEmail().toString());
			attributes.put(id);
		}
		
		BasicAttribute cn = new BasicAttribute(USER_CN_KEY,userDTO.getName()+userDTO.getSurname());
		attributes.put(cn);
		
		BasicAttribute sn = new BasicAttribute(USER_SN_KEY,userDTO.getSurname());
		attributes.put(sn);

		if (userDTO.getPassword()!= null) {

			BasicAttribute name = new BasicAttribute(USER_PASSWORD_KEY, userDTO.getPassword());
			attributes.put(name);
		}

		attributes.put(objectClass);
		
		logger.info("mapProjectToAttributes(project)::finished");
		return attributes;
	}
	
	public void updateUserAndPassword(UserDTO user, String pwd) {
		logger.info("updateUserAndPassword(user, pwd)::started");

		try {
			validateDirContext();
		

			ModificationItem[] items = new ModificationItem[2];
			items[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute(USER_ID_KEY, user.getEmail()));
			items[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute(USER_PASSWORD_KEY, pwd));

			getDirContext().modifyAttributes(getUserDN(user), items);

		} catch (Exception e) {
			logger
			.error("Exception while trying to update user and password entry in LDAP.", e);
			throw new RuntimeException("ERROR IN LDAP",e);
		} finally {
			logger.info("updateUserAndPassword(user, pwd)::finished");
		}
	}

	private String getUserDN(UserDTO userDTO) {
		return "klsUserEmail=" + userDTO.getEmail() + "," + PEOPLE_ROOT_DN;
	}

	private void mapToUserDTO(UserDTO user, Attributes attributes) {
		
		Object value = null;

		value = getValue(attributes, USER_ID_KEY);
		if (value != null) {
			user.setEmail((String) value);
		}
		
		String passWordHash = (String) getValue(attributes, USER_PASSWORD_KEY);
		if (value != null) {
			user.setPassword(passWordHash);
		}
		
	}
}
