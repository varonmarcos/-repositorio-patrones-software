package org.kallsonnys.oms.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.custAndOrders.Item;
import org.kallsonys.oms.entities.custAndOrders.Orders;
import org.kallsonys.oms.entities.custAndOrders.servientrega.Kallsonys_Items;
import org.kallsonys.oms.entities.custAndOrders.servientrega.Kallsonys_Shipment;

public class ServientregaDAO implements BaseDAO {

	private EntityManager em;
	
	public ServientregaDAO() {
		
	}
	
	@Override
	public void setEm(final EntityManager em) {
		this.em = em;
		
	}
	
	public void insertShipment(Orders order, Address address, Customer customer){
		
		
		Kallsonys_Shipment kallsonys_Shipment = new Kallsonys_Shipment();
		kallsonys_Shipment.setOrderId(order.getId());
		kallsonys_Shipment.setfName(customer.getName());
		kallsonys_Shipment.setlName(customer.getSurname());
		kallsonys_Shipment.setStreet(address.getStreet());
		kallsonys_Shipment.setCity(address.getCity().getName());
		kallsonys_Shipment.setZipcode(address.getZip());
		
		em.persist(kallsonys_Shipment);
		
		Kallsonys_Items kallsonys_Items;
		for (Item item : order.getItems()) {
			kallsonys_Items = new Kallsonys_Items();
			kallsonys_Items.setOrderId(order.getId());
			kallsonys_Items.setProdId(item.getProdId());
			kallsonys_Items.setProductName(item.getProductName());
			kallsonys_Items.setPartNum(item.getPartNum());
			kallsonys_Items.setQuantity(item.getQuantity());
			kallsonys_Items.setPrice(item.getPrice());
			
			em.persist(kallsonys_Items);
			kallsonys_Shipment.addItem(kallsonys_Items);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String getOrderStatus(Long orderId){
		
		if (orderId == null)
			return null;

		final List<String> resultList = em
				.createQuery(
						"SELECT ks.status FROM Kallsonys_Shipment ks WHERE ks.orderId = :orderId ")
				.setParameter("orderId", orderId).getResultList();

		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList.get(0);
		
	}

}
