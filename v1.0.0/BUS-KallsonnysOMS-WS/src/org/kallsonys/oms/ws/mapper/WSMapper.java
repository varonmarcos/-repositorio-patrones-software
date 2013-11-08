package org.kallsonys.oms.ws.mapper;

import static org.kallsonnys.oms.dto.FilterConstants.ID;
import static org.kallsonnys.oms.enums.AddressTypeEnum.BILLING_ADDRESS;
import static org.kallsonnys.oms.enums.AddressTypeEnum.SHIPPING_ADDRESS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.Top5DTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CountryEnum;
import org.kallsonnys.oms.enums.CreditCardTypeEnum;
import org.kallsonys.oms.commons.util.DateUtil;

import com.integration.kallsonys.kallsonysschema.types.Address;
import com.integration.kallsonys.kallsonysschema.types.Campaign;
import com.integration.kallsonys.kallsonysschema.types.CampaignList;
import com.integration.kallsonys.kallsonysschema.types.Customer;
import com.integration.kallsonys.kallsonysschema.types.Order;
import com.integration.kallsonys.kallsonysschema.types.OrderItem;
import com.integration.kallsonys.kallsonysschema.types.OrderList;
import com.integration.kallsonys.kallsonysschema.types.PagingDto;
import com.integration.kallsonys.kallsonysschema.types.PagingFilterItem;
import com.integration.kallsonys.kallsonysschema.types.Product;
import com.integration.kallsonys.kallsonysschema.types.ProductList;
import com.integration.kallsonys.kallsonysschema.types.Top5;

public class WSMapper {

	public static TableFilterDTO mapTableFilterDTO(PagingDto pagingDto) {

		TableFilterDTO filterDTO = new TableFilterDTO();

		if (pagingDto.getStartPage() == null) {
			filterDTO.setStart(1);
		} else {
			filterDTO.setStart(Integer.valueOf(pagingDto.getStartPage()));
		}

		if (pagingDto.getMaxSize() == null) {
			filterDTO.setPageSize(30);
		} else {
			filterDTO.setPageSize(Integer.valueOf(pagingDto.getMaxSize()));
		}

		if (pagingDto.getColumnSorter() == null) {
			filterDTO.setColumnSorter(ID);
		} else {
			filterDTO.setColumnSorter(pagingDto.getColumnSorter());
		}

		if (pagingDto.getFilter() != null) {
			for (PagingFilterItem pagingFilterItem : pagingDto.getFilter()
					.getParams()) {
				filterDTO.addFilter(pagingFilterItem.getKey(),
						pagingFilterItem.getValue());
			}

		}

		return filterDTO;
	}

	public static ProductList mapProductList(List<ProductDTO> result) {
		ProductList productList = new ProductList();
		productList.getProductsResult().addAll(mapProducts(result));
		return productList;
	}
	
	public static Collection<Product> mapProducts(List<ProductDTO> productDTOs){
		List<Product> products = new ArrayList<Product>(productDTOs.size());
		for (ProductDTO productDTO : productDTOs) {
			products.add(mapProduct(productDTO));
		}
		return products;
	}
	
	public static Product mapProduct(ProductDTO productDTO){
		Product product = new Product();
		product.setId(productDTO.getProdId().toString());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setCategory(productDTO.getCategory().toString());
		product.setPrice(productDTO.getPrice().toString());
		product.setUrlFull(productDTO.getImage_url_full());
		product.setUrlThumb(productDTO.getImage_url_thumb());
		product.setProducer(productDTO.getProducer().toString());
		product.setTop5(mapTop5(productDTO.getTop5()));
		return product;
	}
	
	public static Product mapBasictInfoProduct(ProductDTO productDTO){
		Product product = new Product();
		product.setId(productDTO.getProdId().toString());
		product.setName(productDTO.getName());
		product.setUrlThumb(productDTO.getImage_url_thumb());
		return product;
	}

	private static Top5 mapTop5(Top5DTO top5dto) {
		if(top5dto != null){
			Top5 top5 = new Top5();
			top5.setTop1(mapBasictInfoProduct(top5dto.getTop1()));
			top5.setTop2(mapBasictInfoProduct(top5dto.getTop2()));
			top5.setTop3(mapBasictInfoProduct(top5dto.getTop3()));
			top5.setTop4(mapBasictInfoProduct(top5dto.getTop4()));
			top5.setTop5(mapBasictInfoProduct(top5dto.getTop5()));
			return top5;			
		}
		return null;
	}

	public static CampaignList mapCampaignList(List<CampaignDTO> campaignDTOs) {
		
		CampaignList CampaignList = new CampaignList();
		CampaignList.getCampaignRessult().addAll(mapCampaigns(campaignDTOs));
		return CampaignList;
	}

	private static Collection<Campaign> mapCampaigns(List<CampaignDTO> campaignDTOs) {
		List<Campaign> campaigns = new ArrayList<Campaign>(campaignDTOs.size());
		for (CampaignDTO campaignDTO : campaignDTOs) {
			campaigns.add(mapCampaign(campaignDTO));
		}
		
		return campaigns;
	}

	private static Campaign mapCampaign(CampaignDTO campaignDTO) {
		Campaign campaign = new Campaign();
		campaign.setId(campaignDTO.getId().toString());
		campaign.setName(campaignDTO.getName());
		campaign.setInitDate(DateUtil.parseDate(campaignDTO.getStartDate()));
		campaign.setEndDate(DateUtil.parseDate(campaignDTO.getEndDate()));
		campaign.setDescription(campaignDTO.getDescription());
		campaign.setUrlImage(campaignDTO.getImage_url_full());
		
		Product product = new Product();
		product.setId(campaignDTO.getProdId().toString());
		product.setName(campaignDTO.getProductName());

		return campaign;
	}

	public static CustomerDTO mapCustomerDTO(Customer customer) {
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName(customer.getName());
		customerDTO.setSurname(customer.getSurname());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setPassword(customer.getPassword());
		customerDTO.setPhoneNumber(customer.getPhoneNumber());
		customerDTO.setCardType(CreditCardTypeEnum.valueOf(customer.getCreditCardType()));
		customerDTO.setCreditCardToken(customer.getCreditCardToken());
		
		if(customer.getBillTo()!=null){
			customerDTO.addCustomerAddress(mapAddressDTO(customer.getBillTo(),BILLING_ADDRESS));
		}
		
		if(customer.getShipTo()!=null){
			customerDTO.addCustomerAddress(mapAddressDTO(customer.getShipTo(),SHIPPING_ADDRESS));
		}
		
		return customerDTO;
		
	}

	private static AddressDTO mapAddressDTO(Address address, AddressTypeEnum type) {
		
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddresstype(type);
		addressDTO.setStreet(address.getStreet());
		addressDTO.setZip(address.getZip());
		addressDTO.setCountry(CountryEnum.valueOf(address.getCountry()));
		addressDTO.setStateName(address.getState());
		addressDTO.setCityName(address.getCity());
		
		return addressDTO;
	}

	public static OrderDTO mapOrderDTO(Order createOrderRequest) {
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setComments(createOrderRequest.getComment());
		orderDTO.setOrderDate(DateUtil.formatDate(createOrderRequest.getDate()));
		orderDTO.setPrice(Double.valueOf(createOrderRequest.getTotalPrice()));
		orderDTO.setCustomer(mapBasicInfoCustomer(createOrderRequest.getCustomerInfo()));
		orderDTO.setItems(mapItemDTOs(createOrderRequest.getOrderItem()));
		
		return orderDTO;
	}

	private static List<ItemDTO> mapItemDTOs(List<OrderItem> orderItem) {
		List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>(orderItem.size());
		for (OrderItem oi : orderItem) {
			itemDTOs.add(mapItemDTO(oi));
		}
		return itemDTOs;
	}

	private static ItemDTO mapItemDTO(OrderItem oi) {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setProdId(Long.valueOf(oi.getProductDetail().getId()));
		itemDTO.setProductName(oi.getProductDetail().getName());
		itemDTO.setQuantity(Integer.valueOf(oi.getQuantity()));
		itemDTO.setPrice(Double.valueOf(oi.getTotal()));
		return itemDTO;
	}

	private static CustomerDTO mapBasicInfoCustomer(Customer customerInfo) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName(customerInfo.getName());
		customerDTO.setSurname(customerInfo.getSurname());
		customerDTO.setEmail(customerInfo.getEmail());
		return customerDTO;
	}

	public static OrderList mapOrderList(List<OrderDTO> result) {
		OrderList orderList = new OrderList();
		orderList.getOrdersResult().addAll(mapOrders(result));
		return orderList;
	}

	private static List<Order> mapOrders(List<OrderDTO> orderDTOs) {
		List<Order> orders  = new ArrayList<Order>(orderDTOs.size());
		for (OrderDTO orderDTO : orderDTOs) {
			orders.add(mapOrderBasicInfo(orderDTO));
		}
		return orders;
	}

	public static Order mapOrderBasicInfo(OrderDTO orderDTO) {
		Order order = new Order();
		order.setId(orderDTO.getId().toString());
		order.setComment(orderDTO.getComments());
		order.setDate(DateUtil.parseDate(orderDTO.getOrderDate()));
		order.setTotalPrice(orderDTO.getPrice().toString());
		return order;
	}
	
	public static Order mapOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setComment(orderDTO.getComments());
		order.setDate(DateUtil.parseDate(orderDTO.getOrderDate()));
		order.setTotalPrice(orderDTO.getPrice().toString());
		order.getOrderItem().addAll(mapOrderItems(orderDTO.getItems()));
		return order;
	}

	private static List<OrderItem> mapOrderItems(List<ItemDTO> itemDTOs) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>(itemDTOs.size());
		for (ItemDTO itemDTO : itemDTOs) {
			orderItems.add(mapOrderItem(itemDTO));
		}
		return orderItems;
	}

	private static OrderItem mapOrderItem(ItemDTO itemDTO) {
		OrderItem orderItem = new OrderItem();
		
		Product product = new Product();
		product.setId(itemDTO.getProdId().toString());
		product.setName(itemDTO.getProductName());
		
		orderItem.setProductDetail(product);
		orderItem.setQuantity(itemDTO.getQuantity().toString());
		orderItem.setTotal(itemDTO.getPrice().toString());
		return orderItem;
	}

}
