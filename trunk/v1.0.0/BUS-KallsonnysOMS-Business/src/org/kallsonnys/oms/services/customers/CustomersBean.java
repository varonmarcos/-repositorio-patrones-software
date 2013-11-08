package org.kallsonnys.oms.services.customers;

import static org.kallsonnys.oms.enums.CustomerStatusEnum.SILVER;
import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.USER_ALREADY_EXISTS;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.CustomerDAO;
import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.CasTokensDTO;
import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonnys.security.services.SecurityGuardFacadeRemote;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.locator.SecurityLocator;
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
	
	public IntialCustomerLoginDTO getInitialLoginInfo(final String email, final String password){

		final SecurityGuardFacadeRemote securityGuardFacade = SecurityLocator
				.getInstance().getRemoteObject("SecurityGuardBean");
		
		final CasTokensDTO casTokensDTO = securityGuardFacade.getCustomerLogin(email, password);
		
		final CustomerDTO customerBasicInfo = getCustomerBasicInfo(email);

		final IntialCustomerLoginDTO loginDTO = new IntialCustomerLoginDTO(
				customerBasicInfo, casTokensDTO.getTgt(),
				casTokensDTO.getServiceTicket());
		
		return loginDTO;
	}

	public CustomerDTO getCustomerBasicInfo(final String email) {
		
		dao.setEm(em);
		
		final Customer customer = dao.getCustomer(email);
		if(customer!=null){
			return OMSMapper.mapCustomerBasicInfo(customer);			
		}
		return null;
	}
	
	public Customer getCustomer(final String email) {
		
		dao.setEm(em);
		
		return  dao.getCustomer(email);
		
	}
	
	public Address getCustomerAddress(final Long id, final AddressTypeEnum addresstype){
		dao.setEm(em);
		return dao.getCustomerAddress(id, addresstype);
	}
	
	public CustomerDTO createCustomer(final CustomerDTO customerDTO){
		
	
		if(getCustomer(customerDTO.getEmail())!=null){
			throw new OMSException(USER_ALREADY_EXISTS.getCode(), USER_ALREADY_EXISTS.getMsg());
		}
		
		final Customer customer = new Customer();
		customer.setName(customerDTO.getName());
		customer.setSurname(customerDTO.getSurname());
		customer.setEmail(customerDTO.getEmail());
		customer.setCardType(customerDTO.getCardType());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		customer.setCreditCardToken(customerDTO.getCreditCardToken());
		customer.setStatus(SILVER);
		
		em.persist(customer);
		
		Address address;
		for (final AddressDTO addressDTO : customerDTO.getCustomerAddress()) {
			address = dao.persistAddress(addressDTO);
			customer.addCustomerAddress(address);
		}
		
		final UserDTO  userDTO = new UserDTO();
		userDTO.setEmail(customer.getEmail());
		userDTO.setName(customer.getName());
		userDTO.setSurname(customer.getSurname());
		userDTO.setPassword(customerDTO.getPassword());
		
		final SecurityGuardFacadeRemote securityGuardFacade = SecurityLocator
				.getInstance().getRemoteObject("SecurityGuardBean");
		final String passwordHash = securityGuardFacade.createLDAPUser(userDTO);
		customer.setPassword(passwordHash);
		
		em.flush();
		
		customerDTO.setPassword(null);
		customerDTO.setId(customer.getId());
		customerDTO.setStatus(SILVER);
		
		return customerDTO;
		
	}

}
