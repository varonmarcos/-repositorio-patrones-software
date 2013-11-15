package org.kallsonnys.oms.services.customers;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonys.oms.entities.custAndOrders.Customer;


public interface CustomersFacade {
	
	CustomerDTO getCustomerBasicInfo(String email);

	CustomerDTO createCustomer(CustomerDTO customerDTO);

	IntialCustomerLoginDTO getInitialLoginInfo(String email, String password);

	Customer getCustomer(String email);

	CustomerDTO getCustomerDetail(String email);

	CustomerDTO updateCustomer(CustomerDTO customerDTO);

}
