package org.kallsonnys.oms.web.beans.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.utilities.Util;
//import org.kallsonnys.oms.services.products.ProductsRemote;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import test.DAO;

public class ProductDTOLazyList extends LazyDataModel<ProductDTO> {

	private static final long serialVersionUID = 1L;

	private List<ProductDTO> products;
	private int totalOfRecords;
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	

	public ProductDTOLazyList(List<ProductDTO> products) {
		 this.products = products; 
	}

	@Override
	public List<ProductDTO> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters) {

		TableResultDTO<ProductDTO> result = null;
		TableFilterDTO filterDTO = new TableFilterDTO();
		filterDTO.setStart(startingAt/maxPerPage);
		filterDTO.setPageSize(maxPerPage);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		
		for (Entry<String, String> entryFilter : filters.entrySet()) {
			filterDTO.addFilter(entryFilter.getKey(), entryFilter.getValue());
		}
		
		DAO d = new DAO();
		//ProductsRemote productsEJB = ServiceLocator.getInstance().getRemoteObject("ProductsBean");
		//result = productsEJB.getProductsList(filterDTO);
		result = d.getProducts(filterDTO);

		products = result.getResult();
		totalOfRecords = result.getTotalOfRecords();	

		if (getRowCount() <= 0) {

			setRowCount(result.getTotalOfRecords());

		}

		setPageSize(maxPerPage);		
		
		if (totalOfRecords > 0){
			messageHeader = "Registros Encontrados ";
			messageBody = "";
			severity = FacesMessage.SEVERITY_INFO;
		}else{
			messageHeader = "No se Encontraron Registros ";
			messageBody = "";
			severity = FacesMessage.SEVERITY_WARN;
		}

		Util.addMessage(severity, messageHeader, messageBody);
		
		return products;

	}

	@Override
	public Object getRowKey(ProductDTO productDTO) {

		return productDTO.getId();

	}

	@Override
	public ProductDTO getRowData(String playerId) {

		Integer id = Integer.valueOf(playerId);

		for (ProductDTO product : products) {

			if (id.equals(product.getId())) {

				return product;

			}

		}

		return null;

	}

}
