package org.kallsonnys.oms.services.orders;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ERROR_PROCESSING_ORDER;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.integration.agent.ServiceAgentSOAP;
import org.kallsonnys.oms.dao.CustomerDAO;
import org.kallsonnys.oms.dao.OrderDAO;
import org.kallsonnys.oms.dao.ServientregaDAO;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ShipmentCotizationDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonnys.oms.services.customers.CustomersFacadeLocal;
import org.kallsonys.oms.commons.Exception.ErrorCodesEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.SendMailTxListener;
import org.kallsonys.oms.entities.custAndOrders.Address;
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
	private ServientregaDAO servientregaDAO = new ServientregaDAO();
	private CustomerDAO customerDAO = new CustomerDAO();
    
    public OrdersBean() {
       
    }
    
    
    public List<ShipmentCotizationDTO> getOrderCotization(Long orderId){
    	//TODO Obtener las cotizaciones de los fabricantes
    	return null;
    	
    }
    
    public OrderDTO fabricOrder(OrderDTO orderDTO, ShipmentCotizationDTO shipmentCotizationDTO){
    	//TODO cambiar el estado a PROCESSING de la orden despues de enviar la fabricacion al fabricante
    	
    	orderDAO.setEm(em);
    	
    	Orders order = orderDAO.getOrder(orderDTO.getId());
    	
    	
    	switch (shipmentCotizationDTO.getProducer()) {
		case RAPID_SERVICE:
			//Operacion de inicio de fabricacion
			break;

		case SONY:
			//Operacion de inicio de fabricacion
			break;
		}
    	
    	order.setProducer(shipmentCotizationDTO.getProducer());
    	order.setStatus(OrderStatusEnum.PROCESSING);
    	
    	
    	return OMSMapper.mapOrder(order);
    }
    
    public OrderDTO sendOrder(OrderDTO orderDTO){
    	//TODO cambiar el estado a IN_TRANSIT de la orden despues enviar a los proveedores de mensajeria
    	
    	customerDAO.setEm(em);
    	orderDAO.setEm(em);
    	
    	Orders order = orderDAO.getOrder(orderDTO.getId());
    	
    	Address customerAddress = customerDAO.getCustomerAddress(order.getCustomer().getId(), AddressTypeEnum.SHIPPING_ADDRESS);
    	
    	if(customerAddress.getCountry().isInternational()){
    		//DHL
    		order.setShippingProvider("DHL");
    	}else if(!customerAddress.getCountry().isInternational()){
    		switch (order.getProducer()) {
			case RAPID_SERVICE:
				//SERVIENTREGA WS
				order.setShippingProvider("SERV");
				break;
			case SONY:
				//DESPRISA WS
				order.setShippingProvider("DEPR");
				break;
			}
    	}
    	
    	order.setStatus(OrderStatusEnum.IN_TRANSIT);
    	
    	return OMSMapper.mapOrder(order);
    }
    
    public OrderDTO getOrderDetail(Long orderId){
    	
    	orderDAO.setEm(em);
    	Orders order = orderDAO.getOrder(orderId);
    	
    	String status = null;
		if (order.getStatus() == OrderStatusEnum.IN_TRANSIT) {
			if (order.getShippingProvider().equals("DHL")) {
				ServiceAgentSOAP agentSOAP = new ServiceAgentSOAP();
				status = (String) agentSOAP.sendSOAPMessage(OMSMapper
						.createShippingProviderMessageForStatus(order));
			} else if (order.getShippingProvider().equals("SERV")) {
				servientregaDAO.setEm(em);
				status = servientregaDAO.getOrderStatus(order.getId());
			}
		}
		
		if(status != null && status.equals("shipped")){
			order.setStatus(OrderStatusEnum.SHIPPED);
		}
		
    	
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
