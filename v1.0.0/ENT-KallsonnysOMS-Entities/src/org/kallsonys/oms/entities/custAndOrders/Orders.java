package org.kallsonys.oms.entities.custAndOrders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Orders extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date orderDate;
	
	@NotNull
	private Double price;
	
	@Enumerated
	private OrderStatusEnum status;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(length = 2000)
	private String comments;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Customer customer;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<Item> items;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 120)
	private String shippingProvider;
	
	@Enumerated
	private ProducerTypeEnum producer;
	
	public Orders() {
		super();
	}

	public Orders(Long id) {
		super(id);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getItems() {
		if(items == null){
			items = new ArrayList<Item>();
		}
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Associate Item with Order
	 */
	public void addItem(Item item) {
		if (item == null) return;
		getItems().add(item);
		item.setOrder(this);
	}
	
	/**
	 * Unassociate Item from Order
	 */
	public void removeItem(Item item) {
		if (item == null) return;
		getItems().remove(item);
		item.setOrder(null);
	}
	
	/**
	 * Remove All Items
	 */
	public void removeAllItems() {
		List<Item> remove = new java.util.ArrayList<Item>();
		remove.addAll(getItems());
		for (Item element : remove) {
			removeItem(element);
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
