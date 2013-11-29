package org.kallsonnys.oms.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kallsonnys.integration.util.WSConstants;
import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.MailDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.Top5DTO;
import org.kallsonnys.oms.dto.agent.SoapMessageDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.services.customers.CustomersFacadeLocal;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.kallsonys.oms.commons.mail.MailTemplateConstants;
import org.kallsonys.oms.entities.base.AbstractEntity;
import org.kallsonys.oms.entities.custAndOrders.Address;
import org.kallsonys.oms.entities.custAndOrders.Campaign;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.custAndOrders.Item;
import org.kallsonys.oms.entities.custAndOrders.Orders;
import org.kallsonys.oms.entities.products.Product;

public class OMSMapper {
	
	
	public static CustomerDTO mapCustomerBasicInfo(final Customer customer) {

		final CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		customerDTO.setSurname(customer.getSurname());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setStatus(customer.getStatus());
		customerDTO.setCardType(customer.getCardType());
		customerDTO.setPhoneNumber(customer.getPhoneNumber());
		customerDTO.setCreditCardToken(customer.getCreditCardToken());

		return customerDTO;

	}
	
	public static CustomerDTO mapCustomer(Customer customer) {
		final CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		customerDTO.setSurname(customer.getSurname());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setCardType(customer.getCardType());
		customerDTO.setPhoneNumber(customer.getPhoneNumber());
		customerDTO.setCreditCardToken(customer.getCreditCardToken());
		
		for (Address address : customer.getCustomerAddress()) {
			customerDTO.addCustomerAddress(mapAddress(address));
		}
		
		return customerDTO;
	}

	private static AddressDTO mapAddress(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(address.getId());
		addressDTO.setAddresstype(address.getAddresstype());
		addressDTO.setStreet(address.getStreet());
		addressDTO.setCountry(address.getCountry());
		addressDTO.setStateName(address.getState().getName());
		addressDTO.setCityName(address.getCity().getName());
		addressDTO.setZip(address.getZip());
		return addressDTO;
	}

	public static ProductDTO mapProduct(final Product product) {

		final ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setProdId(product.getProdId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setCategory(product.getCategory());
		productDTO.setPrice(product.getPrice());
		productDTO.setImage_url_full(product.getImage_url_full());
		productDTO.setImage_url_thumb(product.getImage_url_thumb());
		productDTO.setProducer(product.getProducer());
		return productDTO;
	}
	
	public static CampaignDTO mapCampaign(final Campaign campaign) {
		
		final CampaignDTO campaignDTO = new CampaignDTO();
		campaignDTO.setId(campaign.getId());
		campaignDTO.setName(campaign.getName());
		campaignDTO.setDescription(campaign.getDescription());
		campaignDTO.setProdId(campaign.getProdId());
		campaignDTO.setProductName(campaign.getProductName());
		campaignDTO.setImage_url_full(campaign.getImage_url_full());
		campaignDTO.setStartDate(campaign.getStartDate());
		campaignDTO.setEndDate(campaign.getEndDate());
		return campaignDTO;
	}
	
	public static <T extends AbstractEntity> List<Long> getEntitiesAsList(final List<T> list){
		final List<Long> ids = new ArrayList<Long>();
		for (final T t : list) {
			ids.add(t.getId());
		}
		return ids;
	}

	public static List<ProductDTO> mapProducts(final List<Product> topProducts) {
		final List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(topProducts.size());
		for (final Product product : topProducts) {
			productDTOs.add(mapProduct(product));
		}
		return productDTOs;
	}

	public static Top5DTO mapTop5(final List<Product> list) {
		if(list.size() == 5){
			final Top5DTO top5dto = new Top5DTO();
			top5dto.setTop1(mapProduct(list.get(0)));
			top5dto.setTop2(mapProduct(list.get(1)));
			top5dto.setTop3(mapProduct(list.get(2)));
			top5dto.setTop4(mapProduct(list.get(3)));
			top5dto.setTop5(mapProduct(list.get(4)));
			return top5dto;			
		}
		return null;
		
	}

	public static OrderDTO mapOrderBasicInfo(final Orders order) {
		
		final OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setStatus(order.getStatus());
		orderDTO.setShippingProvider(order.getShippingProvider());
		orderDTO.setComments(order.getComments());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setPrice(String.format("%.0f", order.getPrice()));
		orderDTO.setCustomer(mapCustomerBasicInfo(order.getCustomer()));
		
		return orderDTO;
	}

	public static OrderDTO mapOrder(final Orders order) {
		final OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setStatus(order.getStatus());
		orderDTO.setShippingProvider(order.getShippingProvider());
		orderDTO.setComments(order.getComments());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setPrice(String.format("%.0f", order.getPrice()));
		orderDTO.setCustomer(mapCustomer(order.getCustomer()));
		orderDTO.setItems(mapOrderItems(order.getItems()));
		return orderDTO;
	}

	private static List<ItemDTO> mapOrderItems(final List<Item> orderItem) {
		final List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
		for (final Item item : orderItem) {
			itemDTOs.add(mapOrderItem(item));
		}
		return itemDTOs;
	}

	private static ItemDTO mapOrderItem(final Item item) {
		final ItemDTO itemDTO = new ItemDTO();
		itemDTO.setProdId(item.getProdId());
		itemDTO.setProductName(item.getProductName());
		itemDTO.setQuantity(item.getQuantity());
		itemDTO.setPrice(String.format("%.0f", item.getPrice()));
		return itemDTO;
	}
	
	public static SoapMessageDTO createShippingProviderMessageForStatus(Orders order){
		
		SoapMessageDTO soapMessageDTO = new SoapMessageDTO();
		
		soapMessageDTO.setServiceDir(WSConstants.DHL_SERVICE_URI);
		soapMessageDTO.setServiceOp(WSConstants.DHL_SERVICE_OP_CHECK_SHIPMENT);
		soapMessageDTO.setServiceTempl(WSConstants.DHL_SERVICE_TEMPL_CHECK_SHIPMENT);
		soapMessageDTO.addParam("orderId", order.getId());
		
		return soapMessageDTO;
		
	}
	
	public static SoapMessageDTO createShipmentProviderMessageForFullfill(Orders order, ProducerTypeEnum producer){
		
		SoapMessageDTO soapMessageDTO = new SoapMessageDTO();
		
		soapMessageDTO.setServiceDir(WSConstants.DHL_SERVICE_URI);
		soapMessageDTO.setServiceOp(WSConstants.DHL_SERVICE_OP_FULLFILL_SHIPMENT);
		soapMessageDTO.setServiceTempl(WSConstants.DHL_SERVICE_TEMPL_FULLFILL_SHIPMENT);
		
		
		final List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		for (final Item item : order.getItems()) {
			final Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("itemId", item.getProdId().toString());
			itemMap.put("prodId", item.getProdId().toString());
			itemMap.put("productName", item.getProductName().toString());
			itemMap.put("partNumber", item.getPartNum() == null ? "Part001" : item.getPartNum().toString());
			itemMap.put("price", String.format("%.0f", item.getPrice()));
			itemMap.put("quantity", item.getQuantity().toString());
			itemsList.add(itemMap);
		}
	
		soapMessageDTO.addParam("itemList", itemsList);
		soapMessageDTO.addParam("orderId", order.getId());
		soapMessageDTO.addParam("partner", "Kallsonys");
		soapMessageDTO.addParam("supplier", producer.name());
		
		
		final Customer customer = order.getCustomer();
		
		soapMessageDTO.addParam("addresseeName", customer.getName());
		soapMessageDTO.addParam("addresseeLastName", customer.getSurname());
		
		final CustomersFacadeLocal customersFacadeLocal =  ServiceLocator.getInstance().getLocalObject("CustomersBean");
		final Address customerAddress = customersFacadeLocal.getCustomerAddress(customer.getId(), AddressTypeEnum.SHIPPING_ADDRESS);
		
		soapMessageDTO.addParam("country", customerAddress.getCountry());
		soapMessageDTO.addParam("city", customerAddress.getCity().getName());
		soapMessageDTO.addParam("street", customerAddress.getStreet());
		soapMessageDTO.addParam("state", customerAddress.getState().getName());
		soapMessageDTO.addParam("zipcode", customerAddress.getZip());
		
		return soapMessageDTO;
		
	}
	
	public static SoapMessageDTO createProducerMessageForQuote(Orders order, ProducerTypeEnum producer){
		
		SoapMessageDTO soapMessageDTO = new SoapMessageDTO();
		
		if(producer == ProducerTypeEnum.SONY){
			soapMessageDTO.setServiceDir(WSConstants.SONY_SERVICE_URI);
			soapMessageDTO.setServiceOp(WSConstants.SONY_SERVICE_OP_INITIATE);
			soapMessageDTO.setServiceTempl(WSConstants.SONY_SERVICE_TEMPL_INITIATE);
		}else{
			soapMessageDTO.setServiceDir(WSConstants.RAPIDSERVICE_SERVICE_URI);
			soapMessageDTO.setServiceOp(WSConstants.RAPIDSERVICE_OP_PROCESSQUOTE);
			soapMessageDTO.setServiceTempl(WSConstants.RAPIDSERVICE_TEMPL_PROCESSQUOTE);
		}
		
		final List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		for (final Item item : order.getItems()) {
			final Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("itemId", item.getProdId().toString());
			itemMap.put("quantity", item.getQuantity().toString());
			itemsList.add(itemMap);
		}
	
		soapMessageDTO.addParam("itemList", itemsList);
		soapMessageDTO.addParam("orderId", order.getId());
		
		return soapMessageDTO;
		
	}
	
	public static SoapMessageDTO createProducerMessageForFabric(Orders order, ProducerTypeEnum producer){
		
		SoapMessageDTO soapMessageDTO = new SoapMessageDTO();
		
		if(producer == ProducerTypeEnum.SONY){
			soapMessageDTO.setServiceDir(WSConstants.SONY_SERVICE_URI);
			soapMessageDTO.setServiceOp(WSConstants.SONY_SERVICE_OP_ORDERQUOTE);
			soapMessageDTO.setServiceTempl(WSConstants.SONY_SERVICE_TEMPL_ORDERQUOTE);
		}else{
			soapMessageDTO.setServiceDir(WSConstants.RAPIDSERVICE_SERVICE_URI);
			soapMessageDTO.setServiceOp(WSConstants.RAPIDSERVICE_OP_ORDERQUOTE);
			soapMessageDTO.setServiceTempl(WSConstants.RAPIDSERVICE_TEMPL_ORDERQUOTE);
		}
		
		soapMessageDTO.addParam("orderId", order.getId());
		
		return soapMessageDTO;
		
	}
	
	public static MailDTO createOrderDeletedEmail(final Orders order) {
		
		final MailDTO mailDTO = new MailDTO();
		mailDTO.setTemplateName(MailTemplateConstants.ORDER_DELETED_TEMPL);	
		mailDTO.setSubject("Kallsony's - Su Orden ha sido eliminada");
		mailDTO.setFrom(MailTemplateConstants.ORDERS_MAIL_ACCOUNT);
		final Customer customer = order.getCustomer();
		
		mailDTO.addRecipient(customer.getEmail());
		
		mailDTO.addParam("orderId", order.getId());
		mailDTO.addParam("customerName", customer.getName() +" "+ customer.getSurname());
		mailDTO.addParam("customerEmail", customer.getEmail());
		if(customer.getCardType() !=null && customer.getCreditCardToken()!=null){
			mailDTO.addParam("credicCardType", customer.getCardType().toString());
			final String numbers = customer.getCreditCardToken();
			mailDTO.addParam("credicCardNum", numbers.substring(numbers.length()-4, numbers.length()));
		}
		
		final List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		int totalQuantity = 0;
		for (final Item item : order.getItems()) {
			final Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("productName", item.getProductName());
			itemMap.put("quantity", item.getQuantity().toString());
			itemMap.put("price", String.format("%.0f", item.getPrice()));
			itemsList.add(itemMap);
			totalQuantity += item.getQuantity();
		}
	
		mailDTO.addParam("totalQuantity", totalQuantity);
		mailDTO.addParam("totalPrice", String.format("%.0f", order.getPrice()));
		mailDTO.addParam("itemList", itemsList);
		
		
		return mailDTO;
	}

	public static MailDTO createOrderEmail(final Orders order) {
		final MailDTO mailDTO = new MailDTO();
		if(order.getStatus() == OrderStatusEnum.CREATED){
			mailDTO.setTemplateName(MailTemplateConstants.ORDER_CREATED_TEMPL);	
			mailDTO.setSubject("Kallsony's - Su Orden ha sido procesada");
		}else if(order.getStatus() == OrderStatusEnum.APPROVED){
			mailDTO.setTemplateName(MailTemplateConstants.ORDER_APPROVED_TEMPL);	
			mailDTO.setSubject("Kallsony's - Su Orden ha sido aprobada");
		}
		
		mailDTO.setFrom(MailTemplateConstants.ORDERS_MAIL_ACCOUNT);
		
		
		final Customer customer = order.getCustomer();
		
		mailDTO.addRecipient(customer.getEmail());
		
		if(order.getStatus() == OrderStatusEnum.APPROVED){
			
			final CustomersFacadeLocal customersFacadeLocal =  ServiceLocator.getInstance().getLocalObject("CustomersBean");
			final Address customerAddress = customersFacadeLocal.getCustomerAddress(customer.getId(), AddressTypeEnum.SHIPPING_ADDRESS);
			mailDTO.addParam("addressStreet",customerAddress.getStreet());
			mailDTO.addParam("addressCountry",customerAddress.getCountry().toString().toLowerCase());
			mailDTO.addParam("addressZip",customerAddress.getZip());
			mailDTO.addParam("addressCity",customerAddress.getCity().getName());
			mailDTO.addParam("phoneNumber", customer.getPhoneNumber());
		}
		
		mailDTO.addParam("orderId", order.getId());
		mailDTO.addParam("customerName", customer.getName() +" "+ customer.getSurname());
		mailDTO.addParam("customerEmail", customer.getEmail());
		if(customer.getCardType() !=null && customer.getCreditCardToken()!=null){
			mailDTO.addParam("credicCardType", customer.getCardType().toString());
			final String numbers = customer.getCreditCardToken();
			mailDTO.addParam("credicCardNum", numbers.substring(numbers.length()-4, numbers.length()));
		}
		
		final List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		int totalQuantity = 0;
		for (final Item item : order.getItems()) {
			final Map<String, String> itemMap = new HashMap<String, String>();
			itemMap.put("productName", item.getProductName());
			itemMap.put("quantity", item.getQuantity().toString());
			itemMap.put("price", String.format("%.0f", item.getPrice()));
			itemsList.add(itemMap);
			totalQuantity += item.getQuantity();
		}
	
		mailDTO.addParam("totalQuantity", totalQuantity);
		mailDTO.addParam("totalPrice", String.format("%.0f", order.getPrice()));
		mailDTO.addParam("itemList", itemsList);
		
		
		return mailDTO;
	}

}
