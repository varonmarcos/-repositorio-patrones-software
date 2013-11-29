package org.kallsonnys.oms.services.customers;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Customer;


public interface CustomersFacade {
	
	CustomerDTO getCustomerBasicInfo(String email);

	CustomerDTO createCustomer(CustomerDTO customerDTO);

	IntialCustomerLoginDTO getInitialLoginInfo(String email, String password);

	Customer getCustomer(String email);

	CustomerDTO getCustomerDetail(String email);

	CustomerDTO updateCustomer(CustomerDTO customerDTO);
	
	Address getCustomerAddress(Long id, AddressTypeEnum addresstype);

	TableResultDTO<CustomerDTO> getCustomers(TableFilterDTO filter);

	CustomerDTO updateCustomerStatus(CustomerDTO customerDTO);

	CustomerDTO getCustomerDetail(Long id);

}
