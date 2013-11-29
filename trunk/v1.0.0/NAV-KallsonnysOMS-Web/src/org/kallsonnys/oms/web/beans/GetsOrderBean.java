package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.web.beans.model.OrderDTOLazyList;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "getsOrder")
@ViewScoped
public class GetsOrderBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private OrderDTO orden;
	private LazyDataModel<OrderDTO> ordenes;
	private List<OrderDTO> list; 

	
	public GetsOrderBean(){
		setOrdenes(new OrderDTOLazyList(list));		
	}

	public OrderDTO getOrden() {
		return orden;
	}


	public void setOrden(OrderDTO orden) {
		this.orden = orden;
	}


	public LazyDataModel<OrderDTO> getOrdenes() {
		return ordenes;
	}


	public void setOrdenes(LazyDataModel<OrderDTO> ordenes) {
		this.ordenes = ordenes;
	}


	public List<OrderDTO> getList() {
		return list;
	}


	public void setList(List<OrderDTO> list) {
		this.list = list;
	}

}
