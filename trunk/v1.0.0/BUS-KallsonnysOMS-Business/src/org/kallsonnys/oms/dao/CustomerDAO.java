package org.kallsonnys.oms.dao;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ENTITY_NOT_FOUND;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CountryEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.DateUtil;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.City;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.custAndOrders.State;

public class CustomerDAO implements BaseDAO {

	private EntityManager em;

	public CustomerDAO() {

	}
	
	@SuppressWarnings("unchecked")
	public TableResultDTO<Customer> getCustomers(final TableFilterDTO filter) {
		
		final String columnSorter = (filter.getColumnSorter() == null) ? "id"
				: filter.getColumnSorter();
		final String sorterType = (filter.getSorterType() == null) ? TableFilterDTO.ASC
				: filter.getSorterType();
		
		CustomerStatusEnum customerStatus;
		if(filter.getVal(FilterConstants.CUSTOMER_STATUS)!=null){
			 customerStatus = filter.getVal(FilterConstants.CUSTOMER_STATUS);
		}else{
			customerStatus = CustomerStatusEnum.PLATINUM;
		}
		
		Long customerId = null;
		if(filter.getStringVal(FilterConstants.CUSTOMER_ID)!=null){
			customerId = Long.parseLong(filter.getStringVal(FilterConstants.CUSTOMER_ID));						
		}
		
		Long prodId = null;
		if(filter.getStringVal(FilterConstants.PROD_ID)!=null){
			prodId = Long.parseLong(filter.getStringVal(FilterConstants.PROD_ID));						
		}
		
		Date startDate = null;
		if(filter.getStringVal(FilterConstants.START_DATE)!=null){
			startDate = DateUtil.formatDate(filter.getStringVal(FilterConstants.START_DATE));
		}
		
		Date endDate = null;
		if(filter.getStringVal(FilterConstants.END_DATE)!=null){
			endDate = DateUtil.formatDate(filter.getStringVal(FilterConstants.END_DATE));
		}
		
		final int start = filter.getStart();
		final int pageSize = filter.getPageSize();
		
		final StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT cust FROM Customer cust ");
		
		if(startDate!=null&&endDate!=null){
			jpql.append("JOIN cust.orders order ");
		}
		
		if(prodId!= null){
			if(startDate==null&&endDate==null){
				jpql.append("JOIN cust.orders order ");
			}
			jpql.append("JOIN order.items item ");
		}
		
		jpql.append("WHERE cust.status = :customerStatus ");
		
		if(customerId!=null){
			jpql.append("AND cust.id = :customerId ");
		}
		
		if(prodId!=null){
			jpql.append("AND item.prodId = :prodId ");
		}
		
		if(startDate!=null&&endDate!=null){
			jpql.append("AND ord.status = :approvedStatus ");
			jpql.append("AND ord.orderDate BETWEEN :startDate AND :startDate ");
		}
		
		if(startDate!=null&&endDate!=null){
			jpql.append(" ORDER BY ord.price ASC ");
		}else{
			jpql.append(" ORDER BY ").append(columnSorter).append(" ").append(sorterType);	
		}
		
		jpql.append(" ORDER BY ").append(columnSorter).append(" ").append(sorterType);
		
		// Create count query
		final StringBuilder jpqlCount = new StringBuilder(jpql.toString());
		jpqlCount.replace(7, 11, "COUNT(*) ");
		
		// Define parameters for queries
		final Query query = em.createQuery(jpql.toString());
		final Query queryCount = em.createQuery(jpqlCount.toString());
		
		query.setParameter("customerStatus", customerStatus);
		queryCount.setParameter("customerStatus", customerStatus);
		
		if(customerId!=null){
			query.setParameter("customerId", customerId);
			queryCount.setParameter("customerId", customerId);
		}
		
		if(prodId!=null){
			query.setParameter("prodId", prodId);
			queryCount.setParameter("prodId", prodId);
		}
		

		if(startDate!=null && endDate != null){
			query.setParameter("startDate", startDate);
			queryCount.setParameter("startDate", startDate);
			
			query.setParameter("endDate", endDate);
			queryCount.setParameter("endDate", endDate);
		}
		
		query.setFirstResult(pageSize * start).setMaxResults(pageSize);	
		
		final List<Customer> resultList = query.getResultList();
		
		final List<Long> resultListCount = queryCount.getResultList();
		int maxResults = (resultListCount.size() == 0) ? 0 : resultListCount.get(0).intValue();
		
		final TableResultDTO<Customer> tableResultDTO = new TableResultDTO<Customer>();
		tableResultDTO.setResult(resultList);
		tableResultDTO.setTotalOfRecords(maxResults);
		
		return tableResultDTO;
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
			return null;
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
	

	@SuppressWarnings("unchecked")
	public Address getCustomerAddress(final Long id, final AddressTypeEnum addresstype) {
		if (id == null)
			return null;

		final List<Address> resultList = em
				.createQuery(
						"SELECT address FROM Address address WHERE address.customer.id = :id AND address.addresstype = :addresstype")
				.setParameter("id", id)
				.setParameter("addresstype", addresstype).getResultList();

		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList.get(0);
	}
	
	public Address persistAddress(final AddressDTO addressDTO){
		final Address address = new Address();
		address.setAddresstype(addressDTO.getAddresstype());
		address.setStreet(addressDTO.getStreet());
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
							"SELECT st FROM State st WHERE UPPER(st.name) = UPPER(:cname) AND st.country = :country")
					.setParameter("cname", name)
					.setParameter("country", country).getSingleResult();

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
