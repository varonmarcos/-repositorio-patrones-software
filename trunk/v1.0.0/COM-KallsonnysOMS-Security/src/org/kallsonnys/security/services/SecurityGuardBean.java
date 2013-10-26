package org.kallsonnys.security.services;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.SECURITY_INITIAL_ERROR;

import javax.ejb.Stateless;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.oms.services.customers.CustomersFacadeRemote;
import org.kallsonnys.security.casclient.CasClientUtil;
import org.kallsonnys.security.casclient.CasTokensDTO;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.locator.ServiceLocator;

@Stateless
public class SecurityGuardBean implements SecurityGuardFacadeRemote,
		SecurityGuardFacadeLocal {

	private CasClientUtil casClientUtil = new CasClientUtil();

	public SecurityGuardBean() {

	}

	public IntialCustomerLoginDTO getCustomerLogin(String email, String password) {

		CasTokensDTO casTokensDTO = null;
		try {
			casTokensDTO = casClientUtil.grantingUserSessionTicket(email,
					password);
		} catch (Exception e) {
			throw new OMSException(SECURITY_INITIAL_ERROR.getCode(), SECURITY_INITIAL_ERROR.getMsg(), e);
		}

		CustomersFacadeRemote customersFacadeRemote = ServiceLocator
				.getInstance().getRemoteObject("CustomersBean");
		CustomerDTO customerBasicInfo = customersFacadeRemote
				.getCustomerBasicInfo(email);

		IntialCustomerLoginDTO loginDTO = new IntialCustomerLoginDTO(
				customerBasicInfo, casTokensDTO.getTgt(),
				casTokensDTO.getServiceTicket());
		return loginDTO;
	}

	public IntialUserLoginDTO getIntialUserLogin(String email, String password) {

		casClientUtil.grantingUserSessionTicket(email, password);

		return null;
	}

}
