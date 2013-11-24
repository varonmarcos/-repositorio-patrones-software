package org.kallsonnys.oms.web.beans.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.utilities.Util;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import test.DAO;

public class OrderDTOLazyList extends LazyDataModel<OrderDTO> {
	
	private static final long serialVersionUID = 1L;
	
	private List<OrderDTO> ordenes;
	private int totalOfRecords; 
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	public OrderDTOLazyList(List<OrderDTO> ordenes){
		this.ordenes = ordenes;
	}
	
	@Override
	public List<OrderDTO> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters){
		
		TableResultDTO<OrderDTO> result = null;		
		TableFilterDTO filterDTO = new  TableFilterDTO();
			
		filterDTO.setStart(startingAt/maxPerPage);
		filterDTO.setPageSize(maxPerPage);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		
		for (Entry<String, String> entryFilter : filters.entrySet()) {
			filterDTO.addFilter(entryFilter.getKey(), entryFilter.getValue());
		}	
		
		//invocacion EJB
		//result = d.getClients(filterDTO);
		ordenes = result.getResult();
		
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
		
		return ordenes;
	}
	
	
	public int getTotalOfRecords() {
		return totalOfRecords;
	}
	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}

}
