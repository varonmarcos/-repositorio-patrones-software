package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.web.beans.model.ProductDTOLazyList;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "getsProducts")
@ViewScoped
public class GetsProductsBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private ProductDTO producto;
	private LazyDataModel<ProductDTO> productos;
	private List<ProductDTO> list;
	private List<ProductCategoryEnum> categorys;
	private List<ProducerTypeEnum> producers;
	
	public GetsProductsBean(){
		setProductos(new ProductDTOLazyList(list));
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

	public LazyDataModel<ProductDTO> getProductos() {
		return productos;
	}

	public void setProductos(LazyDataModel<ProductDTO> productos) {
		this.productos = productos;
	}
	
	

}
