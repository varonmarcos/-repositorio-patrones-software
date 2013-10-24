package org.kallsonnys.security.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.security.casclient.CasClientUtil;


@Stateless
public class SecurityGuardBean implements SecurityGuardFacadeRemote, SecurityGuardFacadeLocal {

	@PersistenceContext(unitName="kallsonnysOrdersSec")
	private EntityManager em1;	
	
	@PersistenceContext(unitName="kallsonnysSecurity")
	private EntityManager em2;	
	
	private CasClientUtil casClientUtil = new CasClientUtil();
   
    public SecurityGuardBean() {
        
    }

	
	public IntialCustomerLoginDTO getCustomerLogin(String email, String password) {
		
		casClientUtil.grantingUserSessionTicket(email, password);
		
		return null;
	}

	
	public IntialUserLoginDTO getIntialUserLogin(String email, String password) {
		
		casClientUtil.grantingUserSessionTicket(email, password);
		
		return null;
	}
    
    
    
     

}
