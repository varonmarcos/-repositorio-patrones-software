package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.services.products.ProductsRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.ProductDTOLazyList;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "manageProduct")
@ViewScoped
public class ManageProductBean implements Serializable {

	private static final long serialVersionUID = -2152389656664659476L;

	private LazyDataModel<ProductDTO> productos;
	private List<ProductDTO> list;
	private ProductDTO producto;
	private int totalOfRecords;
	private List<ProductCategoryEnum> categorys;
	private List<ProducerTypeEnum> producers;
	private String messageHeader;
	private String messageBody;
	private Severity severity;

	public LazyDataModel<ProductDTO> getProductos(){
		return productos;
	}

	public ManageProductBean() {
		
		productos = new ProductDTOLazyList(list);

		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		if (req != null) {
			String idPrd = req.getParameter("id");
			String status = req.getParameter("state");
			if (idPrd != null && status != null) {
				if (status.equals("OK")) {
					messageHeader = "Producto con Id ";
					messageBody = idPrd + " creado correctamente";
					severity = FacesMessage.SEVERITY_INFO;
				} else {
					messageHeader = "Error al crear el producto ";
					messageBody = idPrd;
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
		
		ProductDTO productDTO = (ProductDTO) event.getObject();
		
		try {
			ProductsRemote productsEJB = ServiceLocator.getInstance().getRemoteObject("ProductsBean");
			productsEJB.updateProduct(productDTO);
			
			messageHeader = "El Producto ha sido Editado "+productDTO.getName()+"con exito";
			messageBody = ((ProductDTO) event.getObject()).getProdId().toString();
			severity = FacesMessage.SEVERITY_INFO;

			Util.addMessage(severity, messageHeader, messageBody);
			
		} catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Ocurrio un error al Editar el Producto "+ productDTO.getName();
			messageBody = productDTO.getProdId().toString();
			severity = FacesMessage.SEVERITY_ERROR;
			Util.addMessage(severity, messageHeader, messageBody);
		}
	
	}

	public void onCancel(RowEditEvent event) {
		
		ProductDTO productDTO = (ProductDTO) event.getObject();
		
		
		try {
			
			ProductsRemote productsEJB = ServiceLocator.getInstance().getRemoteObject("ProductsBean");
			productsEJB.removeProduct(productDTO);
			
			messageHeader = "El Producto ha sido Elimanado "+productDTO.getName()+"con exito";
			messageBody = ((ProductDTO) event.getObject()).getProdId().toString();
			severity = FacesMessage.SEVERITY_INFO;

			Util.addMessage(severity, messageHeader, messageBody);
			
			DataTable dataTable = (DataTable) Util.findComponent("dataTableProduct");
			dataTable.loadLazyData();
		} catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Ocurrio un error al Eliminar el Producto "+ productDTO.getName();
			messageBody = productDTO.getProdId().toString();
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

	public int getTotalOfRecords() {
		return totalOfRecords;
	}

	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}

	public List<ProductDTO> getList() {
		return list;
	}

	public void setList(List<ProductDTO> list) {
		this.list = list;
	}

}
