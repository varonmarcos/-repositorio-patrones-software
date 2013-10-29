package org.kallsonnys.oms.dao;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ENTITY_NOT_FOUND;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.enums.CountryEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.City;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.custAndOrders.State;

public class CustomerDAO implements BaseDAO {

	private EntityManager em;

	public CustomerDAO() {

	}

	public Customer getCustomer(final Long id) {
		if (id == null)
			return null;
		final Customer customer = em.find(Customer.class, id);
		if (customer == null)
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		return customer;
	}

	public Customer getCustomer(final String email) {
		if (email == null)
			return null;
		try {
			final Customer customer = (Customer) em
					.createQuery(
							"SELECT cus FROM Customer cus WHERE cus.email = :email")
					.setParameter("email", email).getSingleResult();

			return customer;
		} catch (final NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Address> getCustomerAddresses(final Long id) {
		if (id == null)
			return null;

		final List<Address> resultList = em
				.createQuery(
						"SELECT address FROM address WHERE address.customer.id = :id")
				.setParameter("id", id).getResultList();

		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList;
	}
	
	public Address persistAddress(final AddressDTO addressDTO){
		final Address address = new Address();
		address.setAddresstype(addressDTO.getAddresstype());
		address.setStreet(address.getStreet());
		address.setZip(addressDTO.getZip());
		address.setCountry(addressDTO.getCountry());
		address.setState(getStateByName(addressDTO.getStateName(), addressDTO.getCountry()));
		address.setCity(getCityByName(addressDTO.getCityName()));
		
		em.persist(address);
		
		return address;
		
		
	}
	
	public State getStateByName(final String name, final CountryEnum country){
		if (name == null)
			return null;
		try {
			final State state = (State) em
					.createQuery(
							"SELECT st FROM State st WHERE UPPER(st.name) = UPPER(:name) AND st.country = :country")
					.setParameter("name", name)
					.setParameter("country", "country").getSingleResult();

			return state;
		} catch (final NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}
	
	public City getCityByName(final String name) {
		if (name == null)
			return null;
		try {
			final City city = (City) em
					.createQuery(
							"SELECT ct FROM City ct WHERE UPPER(ct.name) = UPPER(:name)")
					.setParameter("name", name).getSingleResult();

			return city;
		} catch (final NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}

	@Override
	public void setEm(final EntityManager em) {
		this.em = em;
	}

}
