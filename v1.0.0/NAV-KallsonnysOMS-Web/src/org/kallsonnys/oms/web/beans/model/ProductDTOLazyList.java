package org.kallsonnys.oms.web.beans.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.services.products.ProductsRemote;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class ProductDTOLazyList extends LazyDataModel<ProductDTO> {

	private static final long serialVersionUID = 1L;

	private List<ProductDTO> products;
	

	public ProductDTOLazyList(List<ProductDTO> products) {
		 this.products = products; 
	}

	@Override
	public List<ProductDTO> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, String> filters) {

		TableResultDTO<ProductDTO> result = null;
		TableFilterDTO filterDTO = new TableFilterDTO();
		filterDTO.setStart(startingAt/maxPerPage);
		filterDTO.setPageSize(maxPerPage);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		
		for (Entry<String, String> entryFilter : filters.entrySet()) {
			filterDTO.addFilter(entryFilter.getKey(), entryFilter.getValue());
		}

		ProductsRemote productsEJB = ServiceLocator.getInstance()
				.getRemoteObject("ProductsBean");
		result = productsEJB.getProductsList(filterDTO);

		products = result.getResult();
		
		this.setRowCount(result.getTotalOfRecords());  

		setPageSize(maxPerPage);

		return products;

	}

	@Override
	public Object getRowKey(ProductDTO productDTO) {

		return productDTO.getId();

	}

	@Override
	public ProductDTO getRowData(String prouctId) {

		Integer id = Integer.valueOf(prouctId);

		for (ProductDTO product : products) {

			if (id.equals(product.getId())) {

				return product;

			}

		}

		return null;

	}

}
