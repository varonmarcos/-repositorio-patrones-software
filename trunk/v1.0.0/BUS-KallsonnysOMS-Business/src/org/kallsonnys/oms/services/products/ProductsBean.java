package org.kallsonnys.oms.services.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public TableResultDTO<ProductDTO> getProductsList(final TableFilterDTO filter){
    	
    	productDAO.setEm(em);
    	final TableResultDTO<Product> products = productDAO.getProducts(filter);
    	
    	final TableResultDTO<ProductDTO> tableResultDTO = new TableResultDTO<ProductDTO>();
    	tableResultDTO.setTotalOfRecords(products.getTotalOfRecords());
    	
    	final List<ProductDTO> productDTOs = new ArrayList<ProductDTO>(products.getResult().size());
    	
    	Map<Long, Top5> productsTop5 = productDAO.getProductsTop5(OMSMapper.getEntitiesAsList(products.getResult()));
    	Top5 top5;
    	ProductDTO productDTO;
    	for (final Product product : products.getResult()) {
    		
    		productDTO = OMSMapper.mapProduct(product);
    		top5 = productsTop5.get(product.getProdId());
    		if(top5!=null){
    			productDTO.setTop5(OMSMapper.mapTop5(productDAO.getTopProducts(top5)));    			
    		}
    		
    		productDTOs.add(productDTO);
		}
    	
    	tableResultDTO.setResult(productDTOs);
    	
    	return tableResultDTO;
    	
    }
    
}
