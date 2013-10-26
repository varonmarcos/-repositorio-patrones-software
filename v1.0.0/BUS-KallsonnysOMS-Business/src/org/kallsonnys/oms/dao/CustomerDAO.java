package org.kallsonnys.oms.dao;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ENTITY_NOT_FOUND;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Customer;

public class CustomerDAO implements BaseDAO {

	private EntityManager em;

	public CustomerDAO() {

	}

	public Customer getCustomer(Long id) {
		if (id == null)
			return null;
		final Customer customer = em.find(Customer.class, id);
		if (customer == null)
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		return customer;
	}

	public Customer getCustomer(String email) {
		if (email == null)
			return null;
		try {
			final Customer customer = (Customer) em
					.createQuery(
							"SELECT cus FROM Customer cus WHERE cus.email = :email")
					.setParameter("email", email).getSingleResult();

			return customer;
		} catch (NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Address> getCustomerAddresses(Long id) {
		if (id == null)
			return null;

		List<Address> resultList = em
				.createQuery(
						"SELECT address FROM address WHERE address.customer.id = :id")
				.setParameter("id", id).getResultList();

		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
