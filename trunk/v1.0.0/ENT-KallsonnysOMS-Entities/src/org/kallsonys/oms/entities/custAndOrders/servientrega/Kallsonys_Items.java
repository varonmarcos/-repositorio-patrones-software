package org.kallsonys.oms.entities.custAndOrders.servientrega;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Kallsonys_Items extends AbstractEntity {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Kallsonys_Shipment kallsonys_shipment;
	
	@NotNull
	private Long orderId;
	
	private Long prodId;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String  productName;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 120)
	private String partNum;
	
	private Double price;
	
	private Integer quantity;
	
	
	public Kallsonys_Items() {
		
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Kallsonys_Shipment getKallsonys_shipment() {
		return kallsonys_shipment;
	}

	public void setKallsonys_shipment(Kallsonys_Shipment kallsonys_shipment) {
		this.kallsonys_shipment = kallsonys_shipment;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
