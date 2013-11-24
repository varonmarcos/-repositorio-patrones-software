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
	
	private CampaignDTO campa�a;
	private LazyDataModel<CampaignDTO> camapa�as;
	private List<CampaignDTO> list;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public ManageCampaingBean(){
		setCamapa�as(new CampaingDTOLazyList(list));
	}
	
	public void onEdit(RowEditEvent event) {  
        messageHeader = "Cmapa�a Editada";
		messageBody = ((CampaignDTO) event.getObject()).getProdId().toString();
		severity = FacesMessage.SEVERITY_INFO;
        
		Util.addMessage(severity, messageHeader, messageBody); 
    }  
      
    public void onCancel(RowEditEvent event) {  
        messageHeader = "Cmapa�a Eliminada";
		messageBody = ((CampaignDTO) event.getObject()).getProdId().toString();
		severity = FacesMessage.SEVERITY_INFO;
		
		Util.addMessage(severity, messageHeader, messageBody);  
    }

	public CampaignDTO getCampa�a() {
		return campa�a;
	}

	public void setCampa�a(CampaignDTO campa�a) {
		this.campa�a = campa�a;
	}

	public LazyDataModel<CampaignDTO> getCamapa�as() {
		return camapa�as;
	}

	public void setCamapa�as(LazyDataModel<CampaignDTO> camapa�as) {
		this.camapa�as = camapa�as;
	}

	public List<CampaignDTO> getList() {
		return list;
	}

	public void setList(List<CampaignDTO> list) {
		this.list = list;
	} 	

}
