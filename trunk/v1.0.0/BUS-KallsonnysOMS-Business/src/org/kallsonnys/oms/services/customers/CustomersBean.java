package org.kallsonnys.oms.services.customers;

import static org.kallsonnys.oms.enums.CustomerStatusEnum.SILVER;
import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.USER_ALREADY_EXISTS;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.CustomerDAO;
import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Customer;

@Stateless
public class CustomersBean implements CustomersFacadeRemote,
		CustomersFacadeLocal {

	@PersistenceContext(unitName = "kallsonnysOrders")
	private EntityManager em;

	CustomerDAO dao = new CustomerDAO();

	public CustomersBean() {

	}

	public CustomerDTO getCustomerBasicInfo(final String email) {
		
		dao.setEm(em);
		
		final Customer customer = dao.getCustomer(email);
		return OMSMapper.mapCustomer(customer);
	}
	
	public CustomerDTO createCustomer(CustomerDTO customerDTO){
		
		if(getCustomerBasicInfo(customerDTO.getEmail())!=null){
			throw new OMSException(USER_ALREADY_EXISTS.getCode(), USER_ALREADY_EXISTS.getMsg());
		}
		
		Customer customer = new Customer();
		customer.setName(customer.getName());
		customer.setSurname(customer.getSurname());
		customer.setEmail(customer.getEmail());
		customer.setPassword(customerDTO.getPassword());
		customer.setCardType(customerDTO.getCardType());
		customer.setCreditCardToken(customerDTO.getCreditCardToken());
		customer.setStatus(SILVER);
		
		em.persist(customer);
		
		Address address;
		for (AddressDTO addressDTO : customerDTO.getCustomerAddress()) {
			address = dao.persistAddress(addressDTO);
			customer.addCustomerAddress(address);
		}
		
		em.flush();
		
		customerDTO.setId(customer.getId());
		customerDTO.setStatus(SILVER);
		
		return customerDTO;
		
	}

}
