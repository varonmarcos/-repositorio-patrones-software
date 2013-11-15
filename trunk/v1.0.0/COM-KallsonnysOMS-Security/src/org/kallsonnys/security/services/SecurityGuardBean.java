package org.kallsonnys.security.services;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.PASSWORD_ERROR;
import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.SECURITY_INITIAL_ERROR;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.ProfileDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.OptionMenuDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.CasTokensDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.security.casclient.CasClientUtil;
import org.kallsonnys.security.dao.LDAPUserDAO;
import org.kallsonnys.security.dao.UserDAO;
import org.kallsonnys.security.util.SecurityUtils;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.entities.security.OptionMenu;
import org.kallsonys.oms.entities.security.Profile;
import org.kallsonys.oms.entities.security.User;

@Stateless
public class SecurityGuardBean implements SecurityGuardFacadeRemote,
		SecurityGuardFacadeLocal {
	
	@PersistenceContext(unitName = "kallsonnysSecurity")
	private EntityManager em;

	private UserDAO userDAO = new UserDAO();
	private CasClientUtil casClientUtil = new CasClientUtil();

	public SecurityGuardBean() {

	}

	public CasTokensDTO getCustomerLogin(String email, String password) {

		CasTokensDTO casTokensDTO = null;
		try {
			casTokensDTO = casClientUtil.grantingUserSessionTicket(email,
					password);
		} catch (Exception e) {
			throw new OMSException(SECURITY_INITIAL_ERROR.getCode(), SECURITY_INITIAL_ERROR.getMsg(), e);
		}
		
		return casTokensDTO;
	}
	
	public String updateCustomer(CustomerDTO customerDTO, String currentPass) {
		try {
			Boolean validatePassword;

			validatePassword = SecurityUtils.validatePassword(customerDTO.getPassword(), currentPass);

			if (validatePassword != null && !validatePassword) {
				String passwordHash = SecurityUtils.createHash(customerDTO.getPassword());
				
				UserDTO userDTO = new UserDTO();
				userDTO.setEmail(customerDTO.getEmail());
				userDTO.setName(customerDTO.getName());
				userDTO.setSurname(customerDTO.getSurname());
				
				LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
				ldapUserDAO.updateUserAndPassword(userDTO, passwordHash);
				
				return passwordHash;
			}
			
			return null;
			
			
		} catch (NoSuchAlgorithmException e) {
			throw new OMSException(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getMsg(), e);
		} catch (InvalidKeySpecException e) {
			throw new OMSException(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getMsg(), e);
		}
	}
	

	public IntialUserLoginDTO getIntialUserLogin(String email, String password) {

		userDAO.setEm(em);
		
		CasTokensDTO casTokensDTO = casClientUtil.grantingUserSessionTicket(email, password);

		User user = userDAO.getUser(email);
		
		List<OptionMenu> optionMenusByUser = userDAO.getOptionMenusByUser(user.getId());
		List<Profile> userProfiles = userDAO.getUserProfiles(user.getId());
		
		UserDTO userDTO = getUserDTO(user, userProfiles, optionMenusByUser);
		
		IntialUserLoginDTO intialUserLoginDTO = new IntialUserLoginDTO();
		intialUserLoginDTO.setServiceTicket(casTokensDTO.getServiceTicket());
		intialUserLoginDTO.setTgt(casTokensDTO.getTgt());
		intialUserLoginDTO.setUser(userDTO);
		

		return intialUserLoginDTO;
	}
	
	public List<ProfileDTO> getProfiles(List<Profile> profiles){
		
		List<ProfileDTO> profileDTOs = new ArrayList<ProfileDTO>();
		ProfileDTO profileDTO;
		for (Profile profile : profiles) {
			profileDTO = new ProfileDTO();
			profileDTO.setId(profile.getId());
			profileDTO.setName(profile.getName());
			profileDTO.setStatus(profile.getStatus());
			profileDTOs.add(profileDTO);
		}
		
		return profileDTOs;
	}
	
	public UserDTO getUserDTO(User user, List<Profile> profiles, List<OptionMenu> optionMenus){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setSurname(user.getSurname());
		userDTO.setStatus(user.getStatus());
		userDTO.setProfiles(getProfiles(profiles));
		userDTO.setListamenu(getOptionMenuDTOs(optionMenus));
		return userDTO;
	}
	
	public List<OptionMenuDTO> getOptionMenuDTOs(List<OptionMenu> optionMenus){
		List<OptionMenuDTO>  optionMenuDTOs = new ArrayList<OptionMenuDTO>();
		OptionMenuDTO optionMenuDTO;
		for (OptionMenu optionMenu : optionMenus) {
			optionMenuDTO = new OptionMenuDTO();
			optionMenuDTO.setId(optionMenu.getId());
			optionMenuDTO.setOption(optionMenu.getOptions());
			optionMenuDTO.setUrl(optionMenu.getUrl());
			optionMenuDTOs.add(optionMenuDTO);
		}
		return optionMenuDTOs;
	}
	
	public String createLDAPUser(UserDTO userDTO){
		
		String passwordHash;
		try {
			passwordHash = SecurityUtils.createHash(userDTO.getPassword());
		} catch (NoSuchAlgorithmException e) {
			throw new OMSException(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getMsg(), e);
		} catch (InvalidKeySpecException e) {
			throw new OMSException(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getMsg(), e);
		}
		userDTO.setPassword(passwordHash);
		
		LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
		ldapUserDAO.createUser(userDTO);
		

		return passwordHash;
	}

}
