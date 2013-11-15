package org.kallsonnys.oms.services.products;

import javax.ejb.Local;

import org.kallsonnys.oms.dto.ProductDTO;

@Local
public interface ProductsLocal extends ProductsFacade{

	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO updateProduct(ProductDTO productDTO);

}
