package org.kallsonnys.oms.web.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.utilities.Util;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import test.DAO;

@ManagedBean(name = "createProduct")
@ViewScoped
public class CreateProductBean implements Serializable{
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private ProductDTO producto;
	private Long inputProId;
	private String inputName;
	private String inputDesc;
	private Double inputPrice;
	private String slCategory;
	private String slProveedor;
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
	
	public CreateProductBean(){
		
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
    
    
    public void create(ActionEvent actionEvent){
    	RequestContext context = RequestContext.getCurrentInstance();   	
    	
    	System.out.println("inputProId " + inputProId);
    	System.out.println("inputName " + inputName);
    	System.out.println("inputDesc " + inputDesc);
    	System.out.println("inputPrice " + inputPrice);
    	System.out.println("slCategory " + slCategory);
    	System.out.println("slProveedor " + slProveedor);
    	System.out.println("image_full_bytes " + image_full_bytes.length);
    	System.out.println("image_thumb_bytes " + image_thumb_bytes.length);
    	
    	producto = new  ProductDTO();
    	producto.setProdId(inputProId);
    	producto.setName(inputName);
    	producto.setDescription(inputDesc);
    	producto.setPrice(inputPrice);
    	producto.setCategory(verifyEnumCategory(categorys, slCategory));
    	producto.setProducer(verifyEnumProducer(producers,slProveedor));
    	producto.setImage_full_bytes(image_full_bytes);
    	producto.setImage_thumb_bytes(image_thumb_bytes);
    	
    	DAO d = new DAO();
    	producto = d.createProduct(producto);
    	
    	System.out.println("recibido " + producto.getId());
    	
    	if (producto.getId() != null){
    		messageHeader = "Producto con Id ";
			messageBody = producto.getId() + " creado correctamente";
			severity = FacesMessage.SEVERITY_INFO;
    	}else{
    		messageHeader = "Error al crear el producto ";
			messageBody =  producto.getProdId().toString();
			severity = FacesMessage.SEVERITY_ERROR;
    	}
    	Util.addMessage(severity, messageHeader, messageBody); 
    }    
    
    private ProductCategoryEnum verifyEnumCategory(List<ProductCategoryEnum> list, String value){
    	for (ProductCategoryEnum productCategoryEnum : list) {
    		if (productCategoryEnum.toString().equals(value)){
    			System.out.println("productCategoryEnum " + productCategoryEnum);
    			return productCategoryEnum;
    		}
		}    	
    	return null;
    }
    
    private ProducerTypeEnum verifyEnumProducer(List<ProducerTypeEnum> list, String value){
    	for (ProducerTypeEnum producerTypeEnum : list) {
    		if (producerTypeEnum.toString().equals(value)){
    			System.out.println("producerTypeEnum " + producerTypeEnum);
    			return producerTypeEnum;
    		}
		}    	
    	return null;
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

	public Double getInputPrice() {
		return inputPrice;
	}

	public void setInputPrice(Double inputPrice) {
		this.inputPrice = inputPrice;
	}

	public UploadedFile getFileFull() {
		return fileFull;
	}

	public void setFileFull(UploadedFile fileFull) {
		this.fileFull = fileFull;
	}


	public ProductDTO getProducto() {
		return producto;
	}


	public void setProducto(ProductDTO producto) {
		this.producto = producto;
	}

	public String getSlCategory() {
		return slCategory;
	}

	public void setSlCategory(String slCategory) {
		this.slCategory = slCategory;
	}

	public String getSlProveedor() {
		return slProveedor;
	}

	public void setSlProveedor(String slProveedor) {
		this.slProveedor = slProveedor;
	}
	
	

}
