package org.kallsonnys.oms.services.customers;

import static org.kallsonnys.oms.enums.CustomerStatusEnum.SILVER;
import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.USER_ALREADY_EXISTS;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.CustomerDAO;
import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.CasTokensDTO;
import org.kallsonnys.oms.dto.security.IntialCustomerLoginDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonnys.security.services.SecurityGuardFacadeRemote;
import org.kallsonys.oms.commons.Exception.ErrorCodesEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.locator.SecurityLocator;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Customer;

@Stateless
public class CustomersBean implements CustomersFacadeRemote,
		CustomersFacadeLocal {

	@PersistenceContext(unitName = "kallsonnysOrders")
	private EntityManager em;

	CustomerDAO customerDAO = new CustomerDAO();

	public CustomersBean() {

	}
	
	public TableResultDTO<CustomerDTO> getCustomers(TableFilterDTO filter){
		
		customerDAO.setEm(em);
		
		TableResultDTO<Customer> customers = customerDAO.getCustomers(filter);
		
		final TableResultDTO<CustomerDTO> tableResultDTO = new TableResultDTO<CustomerDTO>();
    	tableResultDTO.setTotalOfRecords(customers.getTotalOfRecords());
    	
    	final List<CustomerDTO> customerDTOS = new ArrayList<CustomerDTO>(customers.getResult().size());
    	
    	for (Customer customer : customers.getResult()) {
    		customerDTOS.add(OMSMapper.mapCustomerBasicInfo(customer));
		}
    	
    	tableResultDTO.setResult(customerDTOS);
    	
    	return tableResultDTO;
	}
	
	public CustomerDTO updateCustomerStatus(CustomerDTO customerDTO){
		customerDAO.setEm(em);
		Customer customer = customerDAO.getCustomer(customerDTO.getId());
		customer.setStatus(customerDTO.getStatus());
		em.flush();
		return customerDTO;
		
	}
	
	public CustomerDTO updateCustomer(CustomerDTO customerDTO){
		customerDAO.setEm(em);
		Customer customer = customerDAO.getCustomer(customerDTO.getEmail());
		customer.setName(customerDTO.getName());
		customer.setSurname(customerDTO.getSurname());
		customer.setCardType(customerDTO.getCardType());
		customer.setCreditCardToken(customerDTO.getCreditCardToken());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		
		customerDTO.setId(customer.getId());
		updateCustomerAddress(customerDTO);
		
	
		final SecurityGuardFacadeRemote securityGuardFacade = SecurityLocator
				.getInstance().getRemoteObject("SecurityGuardBean");
		
		String newPasswordHash = securityGuardFacade.updateCustomer(customerDTO, customer.getPassword());
		
		if(newPasswordHash!=null){
			customer.setPassword(newPasswordHash);
		}
		
		em.flush();
		
		return customerDTO;
		
	}
	
	private void updateCustomerAddress(CustomerDTO customerDTO) {
		
		customerDAO.setEm(em);
		
		List<Address> customerAddresses = customerDAO.getCustomerAddresses(customerDTO.getId());
		for (Address address : customerAddresses) {
			for (AddressDTO addressDTO : customerDTO.getCustomerAddress()) {
				if(address.getId().equals(addressDTO.getId())){
					address.setStreet(addressDTO.getStreet());
					address.setZip(addressDTO.getZip());
					address.setCountry(addressDTO.getCountry());
					address.setState(customerDAO.getStateByName(addressDTO.getStateName(), addressDTO.getCountry()));
					address.setCity(customerDAO.getCityByName(addressDTO.getCityName()));
				}
			}
		}
		
	}

	public IntialCustomerLoginDTO getInitialLoginInfo(final String email, final String password){
		
		customerDAO.setEm(em);
		
		if(getCustomer(email)==null){
			throw new OMSException(ErrorCodesEnum.SECURITY_INITIAL_ERROR.getCode(), ErrorCodesEnum.SECURITY_INITIAL_ERROR.getMsg());
		}

		final SecurityGuardFacadeRemote securityGuardFacade = SecurityLocator
				.getInstance().getRemoteObject("SecurityGuardBean");
		
		final CasTokensDTO casTokensDTO = securityGuardFacade.getCustomerLogin(email, password);
		
		final CustomerDTO customerDTO = getCustomerDetail(email);

		final IntialCustomerLoginDTO loginDTO = new IntialCustomerLoginDTO(
				customerDTO, casTokensDTO.getTgt(),
				casTokensDTO.getServiceTicket());
		
		return loginDTO;
	}

	public CustomerDTO getCustomerBasicInfo(final String email) {
		
		customerDAO.setEm(em);
		
		final Customer customer = customerDAO.getCustomer(email);
		if(customer!=null){
			return OMSMapper.mapCustomerBasicInfo(customer);			
		}
		return null;
	}
	
	public CustomerDTO getCustomerDetail(final String email) {
		
		customerDAO.setEm(em);
		Customer customer = customerDAO.getCustomer(email);
		
		return  OMSMapper.mapCustomer(customer);
		
	}
	
	public CustomerDTO getCustomerDetail(final Long id) {
		
		customerDAO.setEm(em);
		Customer customer = em.find(Customer.class, id);
		
		return  OMSMapper.mapCustomer(customer);
		
	}
	
	
	public Customer getCustomer(final String email) {
		
		customerDAO.setEm(em);
		
		return  customerDAO.getCustomer(email);
		
	}
	
	public Address getCustomerAddress(final Long id, final AddressTypeEnum addresstype){
		customerDAO.setEm(em);
		return customerDAO.getCustomerAddress(id, addresstype);
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
			address = customerDAO.persistAddress(addressDTO);
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
