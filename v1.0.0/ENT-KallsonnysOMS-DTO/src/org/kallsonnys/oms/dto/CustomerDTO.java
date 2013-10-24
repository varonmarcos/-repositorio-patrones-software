package org.kallsonnys.oms.dto;

import java.util.ArrayList;
import java.util.List;

import org.kallsonnys.oms.enums.CreditCardTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;

public class CustomerDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String surname;

	private String phoneNumber;

	private String email;

	private String password;

	private CreditCardTypeEnum cardType;

	private String creditCardToken;

	private CustomerStatusEnum status;

	private List<AddressDTO> customerAddress;

	private List<OrderDTO> orders;

	public CustomerDTO() {

	}

	public CustomerDTO(Long id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

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

	public List<AddressDTO> getCustomerAddress() {
		if(customerAddress == null){
			customerAddress = new ArrayList<AddressDTO>();
		}
		return customerAddress;
	}

	public void setCustomerAddress(List<AddressDTO> customerAddress) {
		this.customerAddress = customerAddress;
	}

	public void addCustomerAddress(AddressDTO customerAddress) {
		if (customerAddress == null)
			return;
		getCustomerAddress().add(customerAddress);
	}

	public void removeCustomerAddress(AddressDTO customerAddress) {
		if (customerAddress == null)
			return;
		getCustomerAddress().remove(customerAddress);
	}

	public void removeAllCustomerAddress() {
		List<AddressDTO> remove = new java.util.ArrayList<AddressDTO>();
		remove.addAll(getCustomerAddress());
		for (AddressDTO element : remove) {
			removeCustomerAddress(element);
		}
	}

	public List<OrderDTO> getOrders() {
		if (orders == null) {
			orders = new ArrayList<OrderDTO>();
		}
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public void addOrder(OrderDTO order) {
		if (order == null)
			return;
		getOrders().add(order);
	}

	public void removeOrder(OrderDTO order) {
		if (order == null)
			return;
		getOrders().remove(order);
	}

	public void removeAllOrders() {
		List<OrderDTO> remove = new java.util.ArrayList<OrderDTO>();
		remove.addAll(getOrders());
		for (OrderDTO element : remove) {
			removeOrder(element);
		}
	}

}
