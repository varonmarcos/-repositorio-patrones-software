package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.utilities.Util;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "detailProduct")
@ViewScoped
public class DetailProductBean implements Serializable{
	private static final long serialVersionUID = -2152389656664659476L;
	
	private ProductDTO producto; 
	private UploadedFile fileFull; 	
	private UploadedFile fileThumb;
	byte[] image_full_bytes;
	byte[] image_thumb_bytes;
	private List<ProductCategoryEnum> categorys;
	private List<ProducerTypeEnum> producers;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	private final static String  PREFIJO_FULL_IMG = "full";
	private final static String  PREFIJO_THUMB_IMG = "thumb";
	
	public DetailProductBean (){
		
	}
	
	public void uploadFile(FileUploadEvent event) {    	
    	
    	if (event.getFile().getFileName().contains(PREFIJO_FULL_IMG)){
    		fileFull = event.getFile();
    		image_full_bytes = event.getFile().getContents();
            System.out.println("full " + fileFull.getFileName());
    	}else if (event.getFile().getFileName().contains(PREFIJO_THUMB_IMG)){
    		fileThumb = event.getFile();
    		image_thumb_bytes = event.getFile().getContents();
            System.out.println("thumb " + fileThumb.getFileName());
    	} 
    	messageHeader = "Imagen ";
		messageBody = event.getFile().getFileName() + " se cargo correctamente.";
		severity = FacesMessage.SEVERITY_INFO;
       
		Util.addMessage(severity, messageHeader, messageBody);
    }
	
	public List<ProducerTypeEnum> getProducers() {
		producers = new ArrayList<ProducerTypeEnum>();
		producers.add(ProducerTypeEnum.SONY);
		producers.add(ProducerTypeEnum.RAPID_SERVICE);
		return producers;
	}
    
    public List<ProductCategoryEnum> getCategorys() {
		categorys = new ArrayList<ProductCategoryEnum>();
		categorys.add(ProductCategoryEnum.CAT1);
		categorys.add(ProductCategoryEnum.CAT2);
		categorys.add(ProductCategoryEnum.CAT3);
		categorys.add(ProductCategoryEnum.CAT4);
		return categorys;
	}
	
    public ProductDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductDTO producto) {
		this.producto = producto;
	}
	
	
	
	

}
