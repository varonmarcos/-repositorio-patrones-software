package org.kallsonnys.security.services;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.CasTokensDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;

public interface SecurityGuardFacade {
	
	public CasTokensDTO getCustomerLogin(String email, String password);
	
	public IntialUserLoginDTO getIntialUserLogin(String email, String password);

	String createLDAPUser(UserDTO userDTO);

	String updateCustomer(CustomerDTO customerDTO, String currentPass);
}
