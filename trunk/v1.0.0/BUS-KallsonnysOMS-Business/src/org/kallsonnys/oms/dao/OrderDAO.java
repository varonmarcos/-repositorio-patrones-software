package org.kallsonnys.oms.dao;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ENTITY_NOT_FOUND;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.OrderFilterEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.DateUtil;
import org.kallsonys.oms.entities.custAndOrders.Item;
import org.kallsonys.oms.entities.custAndOrders.Orders;

public class OrderDAO implements BaseDAO {

	private EntityManager em;
	
	public OrderDAO() {
		
	}
	
	@Override
	public void setEm(final EntityManager em) {
		this.em = em;
		
	}
	
	@SuppressWarnings("unchecked")
	public TableResultDTO<Orders> getOrders(final TableFilterDTO filter) {
		
		final String columnSorter = (filter.getColumnSorter() == null) ? "id"
				: filter.getColumnSorter();
		final String sorterType = (filter.getSorterType() == null) ? TableFilterDTO.ASC
				: filter.getSorterType();
		
		final int start = filter.getStart();
		final int pageSize = filter.getPageSize();
		
		
		Long customerId = null;
		if(filter.getStringVal(FilterConstants.CUSTOMER_ID)!=null){
			customerId = Long.parseLong(filter.getStringVal(FilterConstants.CUSTOMER_ID));						
		}
		
		Long orderId = null;
		if(filter.getStringVal(FilterConstants.ID)!=null){
			orderId = Long.parseLong(filter.getStringVal(FilterConstants.ID));						
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
		
		OrderStatusEnum orderStatus = null;
		if(filter.getVal(FilterConstants.ORDER_ST)!=null){
			orderStatus = filter.getVal(FilterConstants.ORDER_ST);
		}
		
		OrderFilterEnum orderFilter = null;
		if(filter.getVal(FilterConstants.ORDER_FILTER)!=null){
			orderFilter = filter.getVal(FilterConstants.ORDER_FILTER);
		}
		
		final StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT ord FROM Orders ord ");
		
		if(prodId!=null){
			jpql.append("JOIN items item ");
		}
		
		jpql.append("WHERE 1=1 ");
		
		if(orderId!=null){
			jpql.append("AND ord.id = :orderId ");
		}
		
		if(customerId!=null){
			jpql.append("AND ord.customer.id = :customerId ");
		}
		
		if(orderStatus != null){
			jpql.append("AND ord.status = :orderStatus ");
		}
		
		if(startDate!=null && endDate != null){
			jpql.append("AND ord.orderDate BETWEEN :startDate AND :startDate ");
		}
		
		if(prodId!=null){
			jpql.append("AND item.prodId :prodId ");
		}
		
		if(orderFilter!=null){
			switch (orderFilter) {
			case ALL_ORDERS_OPEN_RANKING:
				jpql.append("AND ord.status != :shippedStatus ");
				break;
			case ALL_ORDER_CLOSED_MONTH:
			case  ALL_ORDER_CLOSED_RANKING:	
				jpql.append("(AND ord.status = :shippedStatus AND ord.orderDate BETWEEN :startDate AND :startDate) ");
				break;
			}			
		}
		
		if(orderFilter!=null){
			if(orderFilter == OrderFilterEnum.ALL_ORDER_CLOSED_RANKING){
				jpql.append(" ORDER BY ord.price ASC ");
			}else if (orderFilter == OrderFilterEnum.ALL_ORDERS_OPEN_RANKING){
				jpql.append(" ORDER BY ord.orderDate DESC ");
			}else{
				jpql.append(" ORDER BY ").append(columnSorter).append(" ").append(sorterType);			
			}			
		}else{
			jpql.append(" ORDER BY ").append(columnSorter).append(" ").append(sorterType);			
		}	
		
		// Create count query
		final StringBuilder jpqlCount = new StringBuilder(jpql.toString());
		jpqlCount.replace(7, 10, "COUNT(*) ");
				

		// Define parameters for queries
		final Query query = em.createQuery(jpql.toString());
		final Query queryCount = em.createQuery(jpqlCount.toString());
		
		if(orderId!=null){
			query.setParameter("orderId", orderId);
			queryCount.setParameter("orderId", orderId);
		}
		
		if(customerId!=null){
			query.setParameter("customerId", customerId);
			queryCount.setParameter("customerId", customerId);
		}
		
		if(orderStatus != null){
			query.setParameter("orderStatus", orderStatus);
			queryCount.setParameter("orderStatus", orderStatus);
		}
		
		if(startDate!=null && endDate != null){
			query.setParameter("startDate", startDate);
			queryCount.setParameter("startDate", startDate);
			
			query.setParameter("endDate", endDate);
			queryCount.setParameter("endDate", endDate);
		}
		
		if(prodId!=null){
			query.setParameter("prodId", prodId);
			queryCount.setParameter("prodId", prodId);
		}
		
		if(orderFilter!=null){
			switch (orderFilter) {
			case ALL_ORDERS_OPEN_RANKING:
				query.setParameter("shippedStatus", OrderStatusEnum.SHIPPED);
				queryCount.setParameter("shippedStatus", OrderStatusEnum.SHIPPED);
				break;
			case ALL_ORDER_CLOSED_MONTH:
			case  ALL_ORDER_CLOSED_RANKING:	
				query.setParameter("shippedStatus", OrderStatusEnum.SHIPPED);
				queryCount.setParameter("shippedStatus", OrderStatusEnum.SHIPPED);
				
				query.setParameter("startDate", startDate);
				queryCount.setParameter("startDate", startDate);
				
				query.setParameter("endDate", endDate);
				queryCount.setParameter("endDate", endDate);
				break;
			}
		}
		
		if(orderId==null){
			query.setFirstResult(pageSize * start).setMaxResults(pageSize);			
		}
		
		final List<Orders> resultList = query.getResultList();
		
		int maxResults = 1;
		if(orderId==null){
			final List<Long> resultListCount = queryCount.getResultList();
			maxResults = (resultListCount.size() == 0) ? 0 : resultListCount.get(0).intValue();
		}
		
		final TableResultDTO<Orders> tableResultDTO = new TableResultDTO<Orders>();
		tableResultDTO.setResult(resultList);
		tableResultDTO.setTotalOfRecords(maxResults);
		
		return tableResultDTO;
	}


	public Item createOrderItem(final ItemDTO itemDTO) {
		final Item item = new Item();
		item.setPartNum(itemDTO.getPartNum());
		item.setPrice(itemDTO.getPrice());
		item.setProductName(itemDTO.getProductName());
		item.setProdId(itemDTO.getProdId());
		item.setQuantity(itemDTO.getQuantity());
		em.persist(item);
		return item;
	}
	

	public void deleteOrderItems(Long orderId){
		em.createQuery("DELETE FROM Item WHERE order.id = :orderId").setParameter("orderId", orderId).executeUpdate();
	}

	public Orders getOrder(Long orderId) {
		if (orderId == null)
			return null;
		try {
			final Orders order = (Orders) em
					.createQuery(
							"SELECT ord FROM Orders ord JOIN FETCH ord.customer JOIN FETCH ord.items WHERE ord.id = :orderId")
					.setParameter("orderId", orderId).getSingleResult();

			return order;
		} catch (final NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}

}
