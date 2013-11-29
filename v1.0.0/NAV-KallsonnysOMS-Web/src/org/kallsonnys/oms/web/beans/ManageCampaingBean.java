package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.services.campaigns.CampaignFacadeRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.CampaingDTOLazyList;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "manageCampaing")
@ViewScoped
public class ManageCampaingBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private CampaignDTO campaña;
	private LazyDataModel<CampaignDTO> camapañas;
	private List<CampaignDTO> list;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public ManageCampaingBean(){
		setCamapañas(new CampaingDTOLazyList(list));
	}
	
	public void onEdit(RowEditEvent event) {  
		CampaignDTO campaignDTO = (CampaignDTO) event.getObject();
		try {
			CampaignFacadeRemote campaignFacadeEJB = ServiceLocator.getInstance().getRemoteObject("CampaignBean");
			campaignFacadeEJB.updateCampaign(campaignDTO);
			
	        messageHeader = "La Campaña ha sido Editada "+campaignDTO.getName()+"con exito";
			messageBody = campaignDTO.getProdId().toString();
			severity = FacesMessage.SEVERITY_INFO;
	        
			Util.addMessage(severity, messageHeader, messageBody);
			
			DataTable dataTable = (DataTable) Util.findComponent("dataTableCampaing");
			dataTable.loadLazyData();
		} catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Ocurrio un error al Editar la Campaña "+ campaignDTO.getName();
			messageBody = campaignDTO.getProdId().toString();
			severity = FacesMessage.SEVERITY_ERROR;
			Util.addMessage(severity, messageHeader, messageBody);
		}
		
    }  
      
    public void onCancel(RowEditEvent event) {  
    	CampaignDTO campaignDTO = (CampaignDTO) event.getObject();
    	try {
    		CampaignFacadeRemote campaignFacadeEJB = ServiceLocator.getInstance().getRemoteObject("CampaignBean");
    		campaignFacadeEJB.removeCampaign(campaignDTO);
        	
    		messageHeader = "La Campaña ha sido Elimanada "+campaignDTO.getName()+"con exito";
    		messageBody = campaignDTO.getProdId().toString();
    		severity = FacesMessage.SEVERITY_INFO;
    		
    		Util.addMessage(severity, messageHeader, messageBody);  
    		
    		DataTable dataTable = (DataTable) Util.findComponent("dataTableCampaing");
    		dataTable.loadLazyData();
    	
		} catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Ocurrio un error al Eliminar la Campaña "+ campaignDTO.getName();
			messageBody = campaignDTO.getProdId().toString();
			severity = FacesMessage.SEVERITY_ERROR;
			Util.addMessage(severity, messageHeader, messageBody);
		}
    	
    }

	public CampaignDTO getCampaña() {
		return campaña;
	}

	public void setCampaña(CampaignDTO campaña) {
		this.campaña = campaña;
	}

	public LazyDataModel<CampaignDTO> getCamapañas() {
		return camapañas;
	}

	public void setCamapañas(LazyDataModel<CampaignDTO> camapañas) {
		this.camapañas = camapañas;
	}

	public List<CampaignDTO> getList() {
		return list;
	}

	public void setList(List<CampaignDTO> list) {
		this.list = list;
	} 	

}
