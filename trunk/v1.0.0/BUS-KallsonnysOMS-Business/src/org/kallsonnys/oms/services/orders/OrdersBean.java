package org.kallsonnys.oms.services.orders;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ERROR_PROCESSING_ORDER;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.OrderDAO;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ShipmentCotizationDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonnys.oms.services.customers.CustomersFacadeLocal;
import org.kallsonys.oms.commons.Exception.ErrorCodesEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.SendMailTxListener;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.custAndOrders.Item;
import org.kallsonys.oms.entities.custAndOrders.Orders;


@Stateless
public class OrdersBean implements OrdersFacadeRemote, OrdersFacadeLocal {

	@PersistenceContext(unitName = "kallsonnysOrders")
	private EntityManager em;
	
	@EJB
	CustomersFacadeLocal customersFacadeEJB;
	
	private OrderDAO orderDAO = new OrderDAO();
    
    public OrdersBean() {
       
    }
    
    
    public List<ShipmentCotizationDTO> getOrderCotization(Long orderId){
    	
    	return null;
    	
    }
    
    public OrderDTO getOrderDetail(Long orderId){
    	
    	orderDAO.setEm(em);
    	Orders order = orderDAO.getOrder(orderId);
    	
    	return OMSMapper.mapOrder(order);
    }
    
    public TableResultDTO<OrderDTO> getOrdersList(final TableFilterDTO filter){
    	
    	orderDAO.setEm(em);
    	
    	TableResultDTO<Orders> orders = orderDAO.getOrders(filter);
    	
    	final TableResultDTO<OrderDTO> tableResultDTO = new TableResultDTO<OrderDTO>();
    	tableResultDTO.setTotalOfRecords(orders.getTotalOfRecords());
    	
    	final List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>(orders.getResult().size());
    	
    	for (Orders order : orders.getResult()) {
    		orderDTOs.add(OMSMapper.mapOrderBasicInfo(order));
		}
    	
    	tableResultDTO.setResult(orderDTOs);
    	
    	return tableResultDTO;
    	
    }
    

	@Override
	public void processOrder(OrderDTO orderDTO) {
		
		try {
			
			orderDAO.setEm(em);
			
			Customer customer = customersFacadeEJB.getCustomer(orderDTO.getCustomer().getEmail());

			Orders order = new Orders();
			order.setComments(orderDTO.getComments());
			
			if (customer.getStatus() == CustomerStatusEnum.PLATINUM
					|| (customer.getStatus() == CustomerStatusEnum.GOLDEN && orderDTO.getPrice() < 120000D)) {
				order.setStatus(OrderStatusEnum.APPROVED);	
				
			}else{
				order.setStatus(OrderStatusEnum.CREATED);				
			}
			
			order.setPrice(orderDTO.getPrice());
			order.setCustomer(customer);
			order.setOrderDate(orderDTO.getOrderDate());
			
			em.persist(order);
			
			customer.addOrder(order);
			
			Item item;
			for (ItemDTO itemDTO : orderDTO.getItems()) {
				item = orderDAO.createOrderItem(itemDTO);
				order.addItem(item);
			}
			
			em.flush();

			
			SendMailTxListener.getInstance().sendAsyncMailOnCommit(OMSMapper.createOrderEmail(order));
			
		} catch (Exception e) {
			//TODO Enviar correo de informe de error al cliente
			throw new OMSException(ErrorCodesEnum.ERROR_PROCESSING_ORDER.getCode(), ERROR_PROCESSING_ORDER.getMsg());
		}
	
	}

}
