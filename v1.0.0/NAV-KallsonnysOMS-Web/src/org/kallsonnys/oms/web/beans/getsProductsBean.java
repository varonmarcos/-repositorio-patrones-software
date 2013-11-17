package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.utilities.Util;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import test.DAO;

@ManagedBean(name = "getsProducts")
@ViewScoped
public class getsProductsBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private Integer criterio;
	private String inputFindValue;	
	
	private ProductDTO producto;
	private List<ProductDTO> productos;
	private int totalOfRecords; 
	private List<ProductCategoryEnum> categorys;
	private List<ProducerTypeEnum> producers;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public getsProductsBean(){
		
	}
	
	public void find(ActionEvent actionEvent){
		RequestContext context = RequestContext.getCurrentInstance(); 
		DAO d = new DAO();
		TableResultDTO<ProductDTO> result = null;		
		TableFilterDTO filterDTO = new  TableFilterDTO();
		
		filterDTO.setStart(0);
		filterDTO.setPageSize(20);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		filterDTO.addFilter(Util.mapCriterioFilter(criterio), inputFindValue);		
		
		result = d.getProducts(filterDTO);
		productos = result.getResult();
		totalOfRecords = result.getTotalOfRecords();	
		
		if (totalOfRecords > 0){
			messageHeader = "Registros Encontrados ";
			messageBody = "";
			severity = FacesMessage.SEVERITY_INFO;
		}else{
			messageHeader = "No se Encontraron Registros ";
			messageBody = criterio.toString() + " input " + inputFindValue;
			severity = FacesMessage.SEVERITY_WARN;
		}

		Util.addMessage(severity, messageHeader, messageBody);
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

	public Integer getCriterio() {
		return criterio;
	}

	public void setCriterio(Integer criterio) {
		this.criterio = criterio;
	}

	public String getInputFindValue() {
		return inputFindValue;
	}

	public void setInputFindValue(String inputFindValue) {
		this.inputFindValue = inputFindValue;
	}

	public List<ProductDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductDTO> productos) {
		this.productos = productos;
	}

	public int getTotalOfRecords() {
		return totalOfRecords;
	}

	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}

	public List<ProducerTypeEnum> getProducers() {
		producers = new ArrayList<ProducerTypeEnum>();
		producers.add(ProducerTypeEnum.SONY);
		producers.add(ProducerTypeEnum.RAPID_SERVICE);
		return producers;
	}

	public void setProducers(List<ProducerTypeEnum> producers) {
		this.producers = producers;
	}

	public List<ProductCategoryEnum> getCategorys() {
		categorys = new ArrayList<ProductCategoryEnum>();
		categorys.add(ProductCategoryEnum.CAT1);
		categorys.add(ProductCategoryEnum.CAT2);
		categorys.add(ProductCategoryEnum.CAT3);
		categorys.add(ProductCategoryEnum.CAT4);
		return categorys;
	}

	public void setCategorys(List<ProductCategoryEnum> categorys) {
		this.categorys = categorys;
	}

	public ProductDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductDTO producto) {
		this.producto = producto;
	}
	
	

}
