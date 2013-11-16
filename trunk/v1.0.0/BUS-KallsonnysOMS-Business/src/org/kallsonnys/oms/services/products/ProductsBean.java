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
import org.kallsonys.oms.commons.image.ImagesLoadManager;
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
		
		String image_full_url = null;
		if(productDTO.getImage_full_bytes().length>0){
			image_full_url = ImagesLoadManager.getInstance().uploadJPG(productDTO.getImage_full_bytes(), productDTO.getName());
		}
		
		product.setImage_url_full(image_full_url);
		
		String image_full_thumbl = null;
		if(productDTO.getImage_thumb_bytes().length>0){
			image_full_thumbl = ImagesLoadManager.getInstance().uploadJPG(productDTO.getImage_thumb_bytes(), productDTO.getName());
		}
		
		product.setImage_url_thumb(image_full_thumbl);
		
		em.persist(product);
		
		
		return productDTO;
		
	}
	
	public ProductDTO updateProduct(ProductDTO productDTO){
		
		Product product = em.find(Product.class, productDTO.getId());
		
		
		if(!product.getName().equals(productDTO.getName())){
			
		}
		
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setCategory(productDTO.getCategory());
		product.setPrice(productDTO.getPrice());
		product.setProducer(productDTO.getProducer());
		
		String image_full_url = null;
		if(productDTO.getImage_full_bytes().length>0){
			image_full_url = ImagesLoadManager.getInstance().uploadJPG(productDTO.getImage_full_bytes(), productDTO.getName());
		}
		
		product.setImage_url_full(image_full_url);
		productDTO.setImage_url_full(image_full_url);
		productDTO.setImage_url_full(null);
		
		String image_full_thumbl = null;
		if(productDTO.getImage_thumb_bytes().length>0){
			image_full_thumbl = ImagesLoadManager.getInstance().uploadJPG(productDTO.getImage_thumb_bytes(), productDTO.getName());
		}
		
		product.setImage_url_thumb(image_full_thumbl);
		productDTO.setImage_url_full(image_full_thumbl);
		productDTO.setImage_thumb_bytes(null);
		
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
