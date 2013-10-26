package org.kallsonnys.oms.services.customers;

import org.kallsonnys.oms.dto.CustomerDTO;


public interface CustomersFacade {
	
	CustomerDTO getCustomerBasicInfo(String email);

}
