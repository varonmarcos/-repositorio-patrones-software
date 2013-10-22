package org.kallsonys.oms.entities.custAndOrders;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.kallsonnys.oms.enums.CreditCardTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String name;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String surname;
	
	@NotNull
	@Length(min = 0, max = 50)
	private String phoneNumber;
	
	@NotNull
	@Email
	@Length(min = 8, max = 250)
	@Column(unique = true, precision = 0, scale = 0, nullable = false, insertable = true, updatable = true)
	private String email;
	
	@NotNull
	@Length(min = 0, max = 255)
	private String password;
	
	@NotNull
	@Enumerated
	private CreditCardTypeEnum cardType;
	
	@NotNull
	@Length(min = 0, max = 255)
	private String creditCardToken;
	
	@Enumerated
	private CustomerStatusEnum status;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<CustomerAddress> customerAddress;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<Orders> orders;

	public Customer() {
		super();
	}

	public Customer(Long id) {
		super(id);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CreditCardTypeEnum getCardType() {
		return cardType;
	}

	public void setCardType(CreditCardTypeEnum cardType) {
		this.cardType = cardType;
	}

	public String getCreditCardToken() {
		return creditCardToken;
	}

	public void setCreditCardToken(String creditCardToken) {
		this.creditCardToken = creditCardToken;
	}

	public CustomerStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CustomerStatusEnum status) {
		this.status = status;
	}

	public List<CustomerAddress> getCustomerAddress() {
		if (customerAddress == null) {
			customerAddress = new ArrayList<CustomerAddress>();
		}
		return customerAddress;
	}

	public void setCustomerAddress(List<CustomerAddress> customerAddress) {
		this.customerAddress = customerAddress;
	}

	/**
	 * Associate Customer with customerAddress
	 */
	public void addCustomerAddress(CustomerAddress customerAddress) {
		if (customerAddress == null) return;
		getCustomerAddress().add(customerAddress);
		customerAddress.setCustomer(this);
	}
	
	/**
	 * Unassociate Customer from customerAddress
	 */
	public void removeCustomerAddress(CustomerAddress customerAddress) {
		if (customerAddress == null) return;
		getCustomerAddress().remove(customerAddress);
		customerAddress.setCustomer(this);
	}
	
	/**
	 * Remove All
	 */
	public void removeAllCustomerAddress() {
		List<CustomerAddress> remove = new java.util.ArrayList<CustomerAddress>();
		remove.addAll(getCustomerAddress());
		for (CustomerAddress element : remove) {
			removeCustomerAddress(element);
		}
	}

	public List<Orders> getOrders() {
		if (orders == null) {
			orders = new ArrayList<Orders>();
		}
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	
	/**
	 * Associate Customer with order
	 */
	public void addOrder(Orders order) {
		if (order == null) return;
		getOrders().add(order);
		order.setCustomer(this);
	}
	
	/**
	 * Unassociate Customer from order
	 */
	public void removeOrder(Orders order) {
		if (order == null) return;
		getOrders().remove(order);
		order.setCustomer(this);
	}
	
	/**
	 * Remove All
	 */
	public void removeAllOrders() {
		List<Orders> remove = new java.util.ArrayList<Orders>();
		remove.addAll(getOrders());
		for (Orders element : remove) {
			removeOrder(element);
		}
	}


}
