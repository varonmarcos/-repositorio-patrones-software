package org.kallsonnys.oms.mapper;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonys.oms.entities.custAndOrders.Customer;

public class OMSMapper {

	public static CustomerDTO mapCustomer(Customer customer) {

		CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		customerDTO.setSurname(customer.getSurname());
		customerDTO.setEmail(customer.getEmail());
		return customerDTO;

	}

}
