package org.kallsonys.oms.entities.custAndOrders;

import javax.persistence.Entity;

import org.kallsonys.oms.entities.AbstractEntity;

@Entity
public class Address extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String addresstype;
	private String street;
	private String state;
	private String zip;
	private Country country;
	private City city;

}
