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
import org.kallsonnys.oms.dao.ProductDAO;
import org.kallsonnys.oms.dao.ServientregaDAO;
import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ShipmentCotizationDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonnys.oms.services.customers.CustomersFacadeLocal;
import org.kallsonnys.oms.services.products.ProductsLocal;
import org.kallsonys.oms.commons.Exception.ErrorCodesEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.SendMailTxListener;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.custAndOrders.Item;
import org.kallsonys.oms.entities.custAndOrders.Orders;
import org.kallsonys.oms.entities.products.Product;


@Stateless
public class OrdersBean implements OrdersFacadeRemote, OrdersFacadeLocal {

	@PersistenceContext(unitName = "kallsonnysOrders")
	private EntityManager em;
	
	@EJB
	CustomersFacadeLocal customersFacadeEJB;
	
	@EJB
	ProductsLocal productsEJB;
	
	private OrderDAO orderDAO = new OrderDAO();
	private ServientregaDAO servientregaDAO = new ServientregaDAO();
	private CustomerDAO customerDAO = new CustomerDAO();
	private ProductDAO productDAO = new ProductDAO();
    
    public OrdersBean() {
       
    }
    
    
    public List<ShipmentCotizationDTO> getOrderCotization(Long orderId){
    
    	orderDAO.setEm(em);
    	
    	Orders order = orderDAO.getOrder(orderId);
    	
    	ServiceAgentSOAP agentSOAP = new ServiceAgentSOAP();
    	
    	List<ShipmentCotizationDTO> shipmentCotizationDTOs = new ArrayList<ShipmentCotizationDTO>();
    	
    	ShipmentCotizationDTO cotizationDTO1 = new ShipmentCotizationDTO();
    	cotizationDTO1.setProducer( ProducerTypeEnum.SONY);
    	
    	String supplierPrice = (String) agentSOAP.sendSOAPMessage(OMSMapper.createProducerMessageForQuote(order, ProducerTypeEnum.SONY));
    	
    	cotizationDTO1.setPrice(Double.parseDouble(supplierPrice));
    	
    	shipmentCotizationDTOs.add(cotizationDTO1);
    	
    	ShipmentCotizationDTO cotizationDTO2 = new ShipmentCotizationDTO();
    	cotizationDTO2.setProducer( ProducerTypeEnum.RAPID_SERVICE);
		
    	supplierPrice = (String) agentSOAP.sendSOAPMessage(OMSMapper.createProducerMessageForQuote(order, ProducerTypeEnum.RAPID_SERVICE));
    	
    	cotizationDTO2.setPrice(Double.parseDouble(supplierPrice));
    	
    	shipmentCotizationDTOs.add(cotizationDTO2);
    	
    	return shipmentCotizationDTOs;
    	
    }
    
    public OrderDTO approveOrder(OrderDTO orderDTO){
    	
    	orderDAO.setEm(em);
    	
    	Orders order = orderDAO.getOrder(orderDTO.getId());
    	order.setStatus(OrderStatusEnum.APPROVED);	
    	em.flush();

    	orderDTO.setStatus(OrderStatusEnum.APPROVED);
    	
		SendMailTxListener.getInstance().sendAsyncMailOnCommit(OMSMapper.createOrderEmail(order));
		
    	return orderDTO;
    }
    
    public OrderDTO fabricOrder(OrderDTO orderDTO, ShipmentCotizationDTO shipmentCotizationDTO){
    	//TODO cambiar el estado a PROCESSING de la orden despues de enviar la fabricacion al fabricante
    	
    	orderDAO.setEm(em);
    	
    	Orders order = orderDAO.getOrder(orderDTO.getId());
    	
    	ServiceAgentSOAP agentSOAP = new ServiceAgentSOAP();
    	
    	switch (shipmentCotizationDTO.getProducer()) {
		case RAPID_SERVICE:
			agentSOAP.sendSOAPMessage(OMSMapper.createProducerMessageForFabric(order, ProducerTypeEnum.RAPID_SERVICE));
			break;

		case SONY:
			agentSOAP.sendSOAPMessage(OMSMapper.createProducerMessageForFabric(order, ProducerTypeEnum.SONY));
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
    		order.setShippingProvider("DHL");
    		ServiceAgentSOAP agentSOAP = new ServiceAgentSOAP();
    		agentSOAP.sendSOAPMessage(OMSMapper.createShipmentProviderMessageForFullfill(order, ProducerTypeEnum.RAPID_SERVICE));
    	}else if(!customerAddress.getCountry().isInternational()){
    		switch (order.getProducer()) {
			case RAPID_SERVICE:
				//SERVIENTREGA WS
				order.setShippingProvider("SERV");
				servientregaDAO.setEm(em);
				servientregaDAO.insertShipment(order, customerAddress, order.getCustomer());
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
    
    public void removeOrder(OrderDTO orderDTO){
    	
    	orderDAO.setEm(em);
    	Orders order = orderDAO.getOrder(orderDTO.getId());
    	orderDAO.deleteOrderItems(orderDTO.getId());
    	em.remove(order);
    	
    	SendMailTxListener.getInstance().sendAsyncMailOnCommit(OMSMapper.createOrderDeletedEmail(order));
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
		
		if(status != null && (status.equals("shipped")||status.equals("ok"))){
			order.setStatus(OrderStatusEnum.SHIPPED);
		}
		
    	
    	return OMSMapper.mapOrder(order);
    }
    
    public TableResultDTO<OrderDTO> getOrdersList(final TableFilterDTO filter){
    	
    	orderDAO.setEm(em);
    	customerDAO.setEm(em);
    	
    	String customerEmail = filter.getVal(FilterConstants.CUSTOMER_EMAIL);
    	if(customerEmail!=null){
    		Customer customer = customerDAO.getCustomer(customerEmail);
    		filter.addFilter(FilterConstants.CUSTOMER_ID, customer.getId().toString());    		
    	}
    	
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
			productDAO.setEm(em);
			
			Customer customer = customersFacadeEJB.getCustomer(orderDTO.getCustomer().getEmail());

			Orders order = new Orders();
			order.setComments(orderDTO.getComments());
			
			double price = Double.parseDouble(orderDTO.getPrice());
			
			if (customer.getStatus() == CustomerStatusEnum.PLATINUM
					|| (customer.getStatus() == CustomerStatusEnum.GOLDEN && price < 120000)) {
				order.setStatus(OrderStatusEnum.APPROVED);	
				
			}else{
				order.setStatus(OrderStatusEnum.CREATED);				
			}
			
			order.setPrice(price);
			order.setCustomer(customer);
			order.setOrderDate(orderDTO.getOrderDate());
			
			em.persist(order);
			
			customer.addOrder(order);
			
			Item item;
			Product prod;
			for (ItemDTO itemDTO : orderDTO.getItems()) {
				prod = productsEJB.getProduct(itemDTO.getProdId());
				itemDTO.setProdId(prod.getId());
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
