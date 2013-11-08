package org.kallsonnys.oms.dto;

import org.kallsonnys.oms.enums.ProducerTypeEnum;

public class ShipmentCotizationDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private ProducerTypeEnum producer;
	private Double price;
	
	public ShipmentCotizationDTO() {
		// TODO Auto-generated constructor stub
	}

	public ShipmentCotizationDTO(ProducerTypeEnum producer, Double price) {
		this.setProducer(producer);
		this.setPrice(price);
	}

	public ProducerTypeEnum getProducer() {
		return producer;
	}

	public void setProducer(ProducerTypeEnum producer) {
		this.producer = producer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
