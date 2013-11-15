package org.kallsonnys.oms.dto;

import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;

public class ProductDTO extends BaseDTO {


	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Long prodId;
	
	private String name;
	
	private String description;
	
	private ProductCategoryEnum category;
	
	private Double price;
	
	private ProducerTypeEnum producer;
	
	private String image_url_full;
	
	private String image_url_thumb;
	
	private Top5DTO top5;
	
	private byte[] product_image_full;
	
	private byte[] product_image_thumb;
	

	public ProductDTO() {
		
	}

	public ProductDTO(Long prodId, String name) {
		super();
		this.prodId = prodId;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Top5DTO getTop5() {
		return top5;
	}

	public void setTop5(Top5DTO top5) {
		this.top5 = top5;
	}

	public byte[] getProduct_image_full() {
		return product_image_full;
	}

	public void setProduct_image_full(byte[] product_image_full) {
		this.product_image_full = product_image_full;
	}

	public byte[] getProduct_image_thumb() {
		return product_image_thumb;
	}

	public void setProduct_image_thumb(byte[] product_image_thumb) {
		this.product_image_thumb = product_image_thumb;
	}
	
}
