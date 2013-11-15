package org.kallsonnys.oms.services.products;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.ProductDAO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonys.oms.entities.products.Product;
import org.kallsonys.oms.entities.products.Top5;


@Stateless
public class ProductsBean implements ProductsRemote, ProductsLocal {
	
	
	@PersistenceContext(unitName = "kallsonnysProducts")
	private EntityManager em;
	
	ProductDAO productDAO  = new ProductDAO();
	
	public ProductsBean() {
		
	}
	
	public ProductDTO getProductDetail(Long prodId){
		productDAO.setEm(em);
		Product product = productDAO.getProduct(prodId);
		Top5 productTop5 = productDAO.getProductTop5(prodId);
		
		ProductDTO productDTO = OMSMapper.mapProduct(product);
		if(productTop5!=null){
			productDTO.setTop5(OMSMapper.mapTop5(productDAO.getTopProducts(productTop5)));			
		}
		
		return productDTO;
		
	}
	
	public ProductDTO createProduct(ProductDTO productDTO){
		
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setCategory(productDTO.getCategory());
		product.setPrice(productDTO.getPrice());
		product.setProducer(productDTO.getProducer());
		
		if(productDTO.getProduct_image_full().length>0){
			
		}
		
		em.persist(product);
		
		
		return productDTO;
		
	}

    public TableResultDTO<ProductDTO> getProductsList(final TableFilterDTO filter){
    	
    	productDAO.setEm(em);
    	final TableResultDTO<Product> products = productDAO.getProducts(filter);
    	
    	final TableResultDTO<ProductDTO> tableResultDTO = new TableResultDTO<ProductDTO>();
    	tableResultDTO.setTotalOfRecords(products.getTotalOfRecords());
    	
    	final List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(products.getResult().size());
    
    	for (final Product product : products.getResult()) {
    		productDTOs.add(OMSMapper.mapProduct(product));
		}
    	
    	tableResultDTO.setResult(productDTOs);
    	
    	return tableResultDTO;
    	
    }
    
}
