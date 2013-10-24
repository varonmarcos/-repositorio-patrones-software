package org.kallsonnys.security.services;

import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;

public interface SecurityGuardFacade {
	
	public IntialCustomerLoginDTO getCustomerLogin(String email, String password);
	
	public IntialUserLoginDTO getIntialUserLogin(String email, String password);
}
