package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.utilities.Util;

import test.DAO;

@ManagedBean(name = "createCampaing")
@ViewScoped
public class CreateCampaingBean implements Serializable{
	private static final long serialVersionUID = -2152389656664659476L;
	
	private ProductDTO producto; 
	private CampaignDTO campaing;
	private Long inputProId;
	private String inputName;
	private String inputProName;
	private String inputDesc;
	private String image_url_full;
	private Date dateInit;
	private Date dateEnd;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	public CreateCampaingBean (){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
		if (req != null) {
			String idPrd = req.getParameter("id");			
			if (idPrd != null){		    			
				System.out.println("idPrd "+idPrd);
				DAO d = new DAO();
				producto = d.getProduct();
				inputProId = producto.getProdId();
				inputName = producto.getName();
				inputDesc = producto.getDescription();
				image_url_full = producto.getImage_url_full();		    	
			}
			
		}
		
	}
	
	public void create(){
		
		try{
			campaing = new CampaignDTO();
			campaing.setName(inputName);
			campaing.setDescription(inputDesc);
			campaing.setProdId(inputProId);
			campaing.setProductName(inputProName);
			campaing.setStartDate(dateInit);
			campaing.setEndDate(dateEnd);
			
			//invocacion al EJB
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("manageCampaing.xhtml?id="+campaing.getId()+"&state=OK");
			
		}catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Error al crear la campaña ";
			messageBody =  campaing.getName();
			severity = FacesMessage.SEVERITY_ERROR;			
			Util.addMessage(severity, messageHeader, messageBody);
		}
		
	}
	
	
	public ProductDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductDTO producto) {
		this.producto = producto;
	}

	public Long getInputProId() {
		return inputProId;
	}

	public void setInputProId(Long inputProId) {
		this.inputProId = inputProId;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getInputDesc() {
		return inputDesc;
	}

	public void setInputDesc(String inputDesc) {
		this.inputDesc = inputDesc;
	}

	public String getImage_url_full() {
		return image_url_full;
	}

	public void setImage_url_full(String image_url_full) {
		this.image_url_full = image_url_full;
	}


	public String getInputProName() {
		return inputProName;
	}


	public void setInputProName(String inputProName) {
		this.inputProName = inputProName;
	}

	public Date getDateInit() {
		return dateInit;
	}

	public void setDateInit(Date dateInit) {
		this.dateInit = dateInit;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public CampaignDTO getCampaing() {
		return campaing;
	}

	public void setCampaing(CampaignDTO campaing) {
		this.campaing = campaing;
	}
	
	
	
}
