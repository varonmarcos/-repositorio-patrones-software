package org.kallsonnys.oms.dto.security;

import org.kallsonnys.oms.dto.BaseDTO;
import org.kallsonnys.oms.dto.CustomerDTO;

public class IntialCustomerLoginDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private CustomerDTO customer;
	
	private String tgt;
	
	private String serviceTicket;
	
	public IntialCustomerLoginDTO() {
		
	}

	public IntialCustomerLoginDTO(CustomerDTO customer, String tgt,
			String serviceTicket) {
		super();
		this.customer = customer;
		this.tgt = tgt;
		this.serviceTicket = serviceTicket;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public String getTgt() {
		return tgt;
	}

	public void setTgt(String tgt) {
		this.tgt = tgt;
	}

	public String getServiceTicket() {
		return serviceTicket;
	}

	public void setServiceTicket(String serviceTicket) {
		this.serviceTicket = serviceTicket;
	}
	
}
