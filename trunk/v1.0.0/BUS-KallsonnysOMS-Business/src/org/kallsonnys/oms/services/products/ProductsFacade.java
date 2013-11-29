package org.kallsonnys.oms.services.products;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;


public interface ProductsFacade {

	TableResultDTO<ProductDTO> getProductsList(TableFilterDTO filter);

	ProductDTO getProductDetail(Long prodId);
	
	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO updateProduct(ProductDTO productDTO);
	
	void removeProduct(ProductDTO productDTO);

}
