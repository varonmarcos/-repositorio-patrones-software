package org.kallsonnys.oms.services.customers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.CustomerDAO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.mapper.OMSMapper;
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

}
