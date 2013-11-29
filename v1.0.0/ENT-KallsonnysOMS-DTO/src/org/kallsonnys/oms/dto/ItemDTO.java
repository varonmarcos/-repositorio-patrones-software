package org.kallsonnys.oms.dto;


public class ItemDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Long prodId;

	private String  productName;
	
	private String partNum;
	
	private Integer quantity;

	private String price;
	
	public ItemDTO() {
		
	}
	
	public ItemDTO(Long prodId) {
		this.prodId = prodId;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	

}
