package org.kallsonnys.oms.web.beans.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.services.customers.CustomersFacadeRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class CustomerDTOLazyList extends LazyDataModel<CustomerDTO> {
	
	private static final long serialVersionUID = 1L;
	
	private List<CustomerDTO> clientes;
	private int totalOfRecords; 
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	public CustomerDTOLazyList(List<CustomerDTO> clientes){
		this.clientes = clientes;
	}
	
	@Override
	public List<CustomerDTO> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters){
		
		TableResultDTO<CustomerDTO> result = null;		
		TableFilterDTO filterDTO = new  TableFilterDTO();
			
		filterDTO.setStart(startingAt/maxPerPage);
		filterDTO.setPageSize(maxPerPage);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		
		for (Entry<String, String> entryFilter : filters.entrySet()) {
			if(entryFilter.getKey().equals("id")){
				filterDTO.addFilter(FilterConstants.CUSTOMER_ID, entryFilter.getValue());
			}else if(entryFilter.getKey().equals("status")){
				filterDTO.addFilter(FilterConstants.CUSTOMER_STATUS, mapCustomerStatusVal(entryFilter.getValue()));
			}else{
				filterDTO.addFilter(entryFilter.getKey(), entryFilter.getValue());
			}
		}	
		
		CustomersFacadeRemote customersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("CustomersBean");
		result = customersFacadeEJB.getCustomers(filterDTO);
		totalOfRecords = result.getTotalOfRecords();	
		
		
		clientes = result.getResult();

		setPageSize(maxPerPage);
		
		if (totalOfRecords > 0){
			messageHeader = "Registros Encontrados ";
			messageBody = "";
			severity = FacesMessage.SEVERITY_INFO;
			
			this.setRowCount(totalOfRecords);  

		}else{
			messageHeader = "No se Encontraron Registros ";
			messageBody = "";
			severity = FacesMessage.SEVERITY_WARN;
		}

		Util.addMessage(severity, messageHeader, messageBody);
		
		return clientes;
	}
	
	private CustomerStatusEnum mapCustomerStatusVal(String value) {
		try {
			CustomerStatusEnum valueOf = CustomerStatusEnum.valueOf(value.toUpperCase());
			return valueOf;
		} catch (Exception e) {
		}
		
		return null;
	}

	
	
	public List<CustomerDTO> getClientes() {
		return clientes;
	}
	public void setClients(List<CustomerDTO> clientes) {
		this.clientes = clientes;
	}
	public int getTotalOfRecords() {
		return totalOfRecords;
	}
	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}

}
