package org.kallsonys.oms.entities.custAndOrders;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.kallsonys.oms.entities.AbstractEntity;

public class Customer extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String surname;
	private String phoneNumber;
	private String email;
	private String password;
	private CreditCardTypeEnum cardType;
	private String creditCardToken;
	private CustomerStatusEnum status;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private Set<CustomerAddress> customerAddress;

}
