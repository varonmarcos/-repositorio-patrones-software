package org.kallsonys.oms.entities.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Product extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Id
	@GeneratedValue
	private Long prodId;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String name;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(length = 2000)
	private String description;
	
	@Enumerated
	private ProductCategoryEnum category;
	
	@NotNull
	private Double price;
	
	@Enumerated
	private ProducerTypeEnum producer;
	
	private String image_url_full;
	
	private String image_url_thumb;
	
	public Product() {
		super();
	}

	public Product(Long id) {
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

	public ProductCategoryEnum getCategory() {
		return category;
	}

	public void setCategory(ProductCategoryEnum category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ProducerTypeEnum getProducer() {
		return producer;
	}

	public void setProducer(ProducerTypeEnum producer) {
		this.producer = producer;
	}

	public String getImage_url_full() {
		return image_url_full;
	}

	public void setImage_url_full(String image_url_full) {
		this.image_url_full = image_url_full;
	}

	public String getImage_url_thumb() {
		return image_url_thumb;
	}

	public void setImage_url_thumb(String image_url_thumb) {
		this.image_url_thumb = image_url_thumb;
	}

}
