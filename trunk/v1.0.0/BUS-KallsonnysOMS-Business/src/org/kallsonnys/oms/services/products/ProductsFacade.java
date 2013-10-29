package org.kallsonnys.oms.services.products;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;


public interface ProductsFacade {

	TableResultDTO<ProductDTO> getProductsList(TableFilterDTO filter);

}
