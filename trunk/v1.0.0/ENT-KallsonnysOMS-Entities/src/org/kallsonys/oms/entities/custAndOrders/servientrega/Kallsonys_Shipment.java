package org.kallsonys.oms.entities.custAndOrders.servientrega;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Kallsonys_Shipment extends AbstractEntity {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private Long orderId;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 120)
	private String fName;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 120)
	private String lName;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 120)
	private String street;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 120)
	private String city;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 5)
	private String zipcode;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = true, length = 5)
	private String status;
	
	@OneToMany(mappedBy = "kallsonys_shipment", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<Kallsonys_Items> items;
	
	public Kallsonys_Shipment() {
		
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Kallsonys_Items> getItems() {
		if(items == null)
			items = new ArrayList<Kallsonys_Items>();
		return items;
	}

	public void setItems(List<Kallsonys_Items> items) {
		this.items = items;
	}
	
	/**
	 * Associate Item with Order
	 */
	public void addItem(Kallsonys_Items item) {
		if (item == null) return;
		getItems().add(item);
		item.setKallsonys_shipment(this);
	}
	
	/**
	 * Unassociate Item from Order
	 */
	public void removeItem(Kallsonys_Items item) {
		if (item == null) return;
		getItems().remove(item);
		item.setKallsonys_shipment(null);
	}
	
	/**
	 * Remove All Items
	 */
	public void removeAllItems() {
		List<Kallsonys_Items> remove = new java.util.ArrayList<Kallsonys_Items>();
		remove.addAll(getItems());
		for (Kallsonys_Items element : remove) {
			removeItem(element);
		}
	}

}
