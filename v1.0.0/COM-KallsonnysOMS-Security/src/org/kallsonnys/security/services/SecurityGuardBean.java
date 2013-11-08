package org.kallsonnys.security.services;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.PASSWORD_ERROR;
import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.SECURITY_INITIAL_ERROR;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.Stateless;

import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.CasTokensDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.security.casclient.CasClientUtil;
import org.kallsonnys.security.dao.LDAPUserDAO;
import org.kallsonnys.security.util.SecurityUtils;
import org.kallsonys.oms.commons.Exception.OMSException;

@Stateless
public class SecurityGuardBean implements SecurityGuardFacadeRemote,
		SecurityGuardFacadeLocal {

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
	

	public IntialUserLoginDTO getIntialUserLogin(String email, String password) {

		casClientUtil.grantingUserSessionTicket(email, password);

		return null;
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
