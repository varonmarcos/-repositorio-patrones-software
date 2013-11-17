package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.utilities.Util;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import test.DAO;

@ManagedBean(name = "manageProduct")
@ViewScoped
public class ManageProductBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private List<ProductDTO> productos; 
	private ProductDTO producto;
	private int totalOfRecords; 
	private List<ProductCategoryEnum> categorys;
	private List<ProducerTypeEnum> producers;
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	public ManageProductBean(){
		DAO d = new DAO();
		TableResultDTO<ProductDTO> result = null;
		result = d.getProducts();
		productos = result.getResult();
		totalOfRecords = result.getTotalOfRecords();
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req != null) {
			String idPrd = req.getParameter("id");
			String status = req.getParameter("state");
			if (idPrd != null && status != null){
				if (status.equals("OK")){
		    		messageHeader = "Producto con Id ";
					messageBody = idPrd + " creado correctamente";
					severity = FacesMessage.SEVERITY_INFO;
		    	}else{
		    		messageHeader = "Error al crear el producto ";
					messageBody =  idPrd;
					severity = FacesMessage.SEVERITY_ERROR;
		    	}
		    	Util.addMessage(severity, messageHeader, messageBody);
			}			
		}
		
		
	}
	
	public List<ProductCategoryEnum> getCategorys() {
		categorys = new ArrayList<ProductCategoryEnum>();
		categorys.add(ProductCategoryEnum.CAT1);
		categorys.add(ProductCategoryEnum.CAT2);
		categorys.add(ProductCategoryEnum.CAT3);
		categorys.add(ProductCategoryEnum.CAT4);
		return categorys;
	}


	public List<ProducerTypeEnum> getProducers() {
		producers = new ArrayList<ProducerTypeEnum>();
		producers.add(ProducerTypeEnum.SONY);
		producers.add(ProducerTypeEnum.RAPID_SERVICE);
		return producers;
	}
	
	public void onEdit(RowEditEvent event) {  
        messageHeader = "Producto Editado";
		messageBody = ((ProductDTO) event.getObject()).getProdId().toString();
		severity = FacesMessage.SEVERITY_INFO;
        
		Util.addMessage(severity, messageHeader, messageBody); 
    }  
      
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Producto Eliminado", ((ProductDTO) event.getObject()).getProdId().toString()); 
        messageHeader = "Producto Eliminado";
		messageBody = ((ProductDTO) event.getObject()).getProdId().toString();
		severity = FacesMessage.SEVERITY_INFO;
		
		Util.addMessage(severity, messageHeader, messageBody);  
    } 
	
	public List<ProductDTO> getProductos() {
		return productos;
	}


	public void setProductos(List<ProductDTO> productos) {
		this.productos = productos;
	}


	public ProductDTO getProducto() {
		return producto;
	}


	public void setProducto(ProductDTO producto) {
		this.producto = producto;
	}


	public int getTotalOfRecords() {
		return totalOfRecords;
	}


	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}
	
	

}
