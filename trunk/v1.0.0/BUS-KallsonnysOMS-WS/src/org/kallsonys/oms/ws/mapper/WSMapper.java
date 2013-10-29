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
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.Top5DTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonys.oms.commons.DateUtil;

import com.integration.kallsonys.kallsonysschema.types.Address;
import com.integration.kallsonys.kallsonysschema.types.Campaign;
import com.integration.kallsonys.kallsonysschema.types.CampaignList;
import com.integration.kallsonys.kallsonysschema.types.Customer;
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
		List<Product> products = new ArrayList<Product>();
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
		List<Campaign> campaigns = new ArrayList<Campaign>();
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
		customerDTO.setPassword(customerDTO.getPassword());
		customerDTO.setCardType(customerDTO.getCardType());
		customerDTO.setCreditCardToken(customerDTO.getCreditCardToken());
		
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
		addressDTO.setZip(addressDTO.getZip());
		addressDTO.setCountry(addressDTO.getCountry());
		addressDTO.setStateName(address.getState());
		addressDTO.setCityName(address.getCity());
		
		return addressDTO;
	}

}
