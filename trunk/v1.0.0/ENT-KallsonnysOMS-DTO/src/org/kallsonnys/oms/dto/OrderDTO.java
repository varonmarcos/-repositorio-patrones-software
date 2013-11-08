package org.kallsonnys.oms.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.enums.ProducerTypeEnum;

public class OrderDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Date orderDate;
	
	private Double price;
	
	private OrderStatusEnum status;

	private String comments;
	
	private CustomerDTO customer;

	private List<ItemDTO> items;
	
	private String shippingProvider;
	
	private ProducerTypeEnum producer;
	
	public OrderDTO() {
		
	}

	public OrderDTO(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		if(items == null)
			items = new ArrayList<ItemDTO>();
		this.items = items;
	}
	
	public void addItem(ItemDTO itemDTO){
		if(itemDTO == null)return;
		getItems().add(itemDTO);
	}
	
	public void removeItem(ItemDTO itemDTO){
		if(itemDTO == null)return;
		getItems().remove(itemDTO);
	}

	public void removeAllItems(List<ItemDTO> itemDTOs){
		if(itemDTOs == null)return;
		List<ItemDTO> remove = new ArrayList<ItemDTO>();
		remove.addAll(remove);
		for (ItemDTO itemDTO : remove) {
			removeItem(itemDTO);
		}
	}

	public String getShippingProvider() {
		return shippingProvider;
	}

	public void setShippingProvider(String shippingProvider) {
		this.shippingProvider = shippingProvider;
	}

	public ProducerTypeEnum getProducer() {
		return producer;
	}

	public void setProducer(ProducerTypeEnum producer) {
		this.producer = producer;
	}
	
	

}
