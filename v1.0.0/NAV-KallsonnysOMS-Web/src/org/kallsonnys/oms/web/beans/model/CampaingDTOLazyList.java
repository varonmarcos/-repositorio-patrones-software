package org.kallsonnys.oms.web.beans.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.services.campaigns.CampaignFacadeRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
//import org.kallsonnys.oms.services.products.ProductsRemote;

public class CampaingDTOLazyList extends LazyDataModel<CampaignDTO> {

	private static final long serialVersionUID = 1L;

	private List<CampaignDTO> campa�as;
	private int totalOfRecords;
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	

	public CampaingDTOLazyList(List<CampaignDTO> campa�as) {
		 this.campa�as = campa�as; 
	}

	@Override
	public List<CampaignDTO> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters) {

		TableResultDTO<CampaignDTO> result = null;
		TableFilterDTO filterDTO = new TableFilterDTO();
		filterDTO.setStart(startingAt/maxPerPage);
		filterDTO.setPageSize(maxPerPage);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		
		for (Entry<String, String> entryFilter : filters.entrySet()) {
			filterDTO.addFilter(entryFilter.getKey(), entryFilter.getValue());
		}
		
		
		CampaignFacadeRemote  campaignFacadeEJB =  ServiceLocator.getInstance().getRemoteObject("CampaignBean");
		result = campaignFacadeEJB.getCampaigns(filterDTO);
		
		campa�as = result.getResult();
		totalOfRecords = result.getTotalOfRecords();	

		if (getRowCount() <= 0) {

			setRowCount(result.getTotalOfRecords());

		}

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
		
		return campa�as;

	}

	@Override
	public Object getRowKey(CampaignDTO campaignDTO) {

		return campaignDTO.getId();

	}

	@Override
	public CampaignDTO getRowData(String playerId) {

		Integer id = Integer.valueOf(playerId);

		for (CampaignDTO campa�a : campa�as) {

			if (id.equals(campa�a.getId())) {

				return campa�a;

			}

		}

		return null;

	}

}
