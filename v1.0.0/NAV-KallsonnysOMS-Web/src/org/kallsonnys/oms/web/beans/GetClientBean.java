package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.web.beans.model.CustomerDTOLazyList;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "getClient")
@ViewScoped
public class GetClientBean implements Serializable{
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private LazyDataModel<CustomerDTO> clientes;
	private List<CustomerDTO> list;
	private String ship;
	private String bill;
	
	public GetClientBean(){
		setClientes(new CustomerDTOLazyList(list));
	}
	
	public LazyDataModel<CustomerDTO> getClientes() {
		return clientes;
	}

	public void setClientes(LazyDataModel<CustomerDTO> clientes) {
		this.clientes = clientes;
	}

	public List<CustomerDTO> getList() {
		return list;
	}

	public void setList(List<CustomerDTO> list) {
		this.list = list;
	}

	public String getShip() {
		return ship;
	}

	public void setShip(String ship) {
		this.ship = ship;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}	
	
	
	
}
