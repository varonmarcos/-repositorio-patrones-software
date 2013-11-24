package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.CampaingDTOLazyList;
import org.kallsonnys.oms.web.beans.model.ProductDTOLazyList;
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
        messageHeader = "Cmapaña Editada";
		messageBody = ((CampaignDTO) event.getObject()).getProdId().toString();
		severity = FacesMessage.SEVERITY_INFO;
        
		Util.addMessage(severity, messageHeader, messageBody); 
    }  
      
    public void onCancel(RowEditEvent event) {  
        messageHeader = "Cmapaña Eliminada";
		messageBody = ((CampaignDTO) event.getObject()).getProdId().toString();
		severity = FacesMessage.SEVERITY_INFO;
		
		Util.addMessage(severity, messageHeader, messageBody);  
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
