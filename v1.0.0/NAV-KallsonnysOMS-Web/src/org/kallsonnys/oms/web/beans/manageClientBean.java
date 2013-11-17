package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.utilities.Util;
import org.primefaces.event.RowEditEvent;

import test.DAO;

@ManagedBean(name = "manageClient")
@ViewScoped
public class manageClientBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private List<CustomerDTO> clientes;
	private List<CustomerStatusEnum> statuss;
	private int totalOfRecords; 
	private String ship;
	private String bill;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public manageClientBean(){
		
		DAO d = new DAO();
		TableResultDTO<CustomerDTO> result = null;
		result = d.getClients();
		clientes = result.getResult();
		setAddressShip(clientes);
		
		totalOfRecords = result.getTotalOfRecords();
		
	}

	private void setAddressShip(List<CustomerDTO> clientes){
		
		List<AddressDTO> listAddress = new ArrayList<AddressDTO>();
		
		for (CustomerDTO cliente : clientes) {
			listAddress = cliente.getCustomerAddress();
			for (AddressDTO address : listAddress) {
				if (address.getAddresstype().equals(AddressTypeEnum.SHIPPING_ADDRESS)){
					setShip(address.getCountry() + ", " + address.getStateName() + ", " + address.getCityName() +", " + address.getStreet());
				}
				
				if (address.getAddresstype().equals(AddressTypeEnum.BILLING_ADDRESS)){
					setBill(address.getCountry() + ", " + address.getStateName() + ", " + address.getCityName() +", " + address.getStreet());
				}
			}
		}
	}
	
	
	public List<CustomerStatusEnum> getStatuss() {
		statuss = new ArrayList<CustomerStatusEnum>();
		statuss.add(CustomerStatusEnum.PLATINUM);
		statuss.add(CustomerStatusEnum.GOLDEN);
		statuss.add(CustomerStatusEnum.SILVER);
		statuss.add(CustomerStatusEnum.DISABLED);
		return statuss;
	}

	public void setStatuss(List<CustomerStatusEnum> statuss) {
		this.statuss = statuss;
	}

	public List<CustomerDTO> getClientes() {
		return clientes;
	}

	public void setClientes(List<CustomerDTO> clientes) {
		this.clientes = clientes;
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

	public int getTotalOfRecords() {
		return totalOfRecords;
	}

	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}
	

}
