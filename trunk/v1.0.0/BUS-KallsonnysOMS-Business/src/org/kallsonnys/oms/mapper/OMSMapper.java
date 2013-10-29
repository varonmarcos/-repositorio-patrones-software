package org.kallsonnys.oms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.Top5DTO;
import org.kallsonys.oms.entities.base.AbstractEntity;
import org.kallsonys.oms.entities.custAndOrders.Campaign;
import org.kallsonys.oms.entities.custAndOrders.Customer;
import org.kallsonys.oms.entities.products.Product;

public class OMSMapper {
	
	
	public static CustomerDTO mapCustomer(Customer customer) {

		CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		customerDTO.setSurname(customer.getSurname());
		customerDTO.setEmail(customer.getEmail());
		return customerDTO;

	}

	public static ProductDTO mapProduct(Product product) {

		ProductDTO productDTO = new ProductDTO();
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
	
	public static CampaignDTO mapCampaign(Campaign campaign) {
		
		CampaignDTO campaignDTO = new CampaignDTO();
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
	
	public static <T extends AbstractEntity> List<Long> getEntitiesAsList(List<T> list){
		List<Long> ids = new ArrayList<Long>();
		for (T t : list) {
			ids.add(t.getId());
		}
		return ids;
	}

	public static List<ProductDTO> mapProducts(List<Product> topProducts) {
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(topProducts.size());
		for (Product product : topProducts) {
			productDTOs.add(mapProduct(product));
		}
		return productDTOs;
	}

	public static Top5DTO mapTop5(List<Product> list) {
		if(list.size() > 0){
			Top5DTO top5dto = new Top5DTO();
			top5dto.setTop1(mapProduct(list.get(0)));
			top5dto.setTop2(mapProduct(list.get(1)));
			top5dto.setTop3(mapProduct(list.get(2)));
			top5dto.setTop4(mapProduct(list.get(3)));
			top5dto.setTop5(mapProduct(list.get(4)));
			return top5dto;			
		}
		return null;
		
	}

}
