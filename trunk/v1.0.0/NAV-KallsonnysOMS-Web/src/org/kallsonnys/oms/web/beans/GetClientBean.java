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
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.utilities.Util;

import test.DAO;

@ManagedBean(name = "getClient")
@ViewScoped
public class GetClientBean implements Serializable{
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private List<CustomerDTO> clientes;
	private int totalOfRecords; 
	private String ship;
	private String bill;
	
	private String inputFindValue;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public GetClientBean(){

	}
	
	public void find(){
		DAO d = new DAO();
		TableResultDTO<CustomerDTO> result = null;		
		TableFilterDTO filterDTO = new  TableFilterDTO();
		
		filterDTO.setStart(0);
		filterDTO.setPageSize(20);
		filterDTO.setSorterType(TableFilterDTO.ASC);
		filterDTO.addFilter("Id", inputFindValue);		
		
		result = d.getClients(filterDTO);
		clientes = result.getResult();
		setAddressShip(clientes);
		totalOfRecords = result.getTotalOfRecords();	
		
		if (totalOfRecords > 0){
			messageHeader = "Registros Encontrados ";
			messageBody = "";
			severity = FacesMessage.SEVERITY_INFO;
		}else{
			messageHeader = "No se Encontraron Registros ";
			messageBody = " input " + inputFindValue;
			severity = FacesMessage.SEVERITY_WARN;
		}

		Util.addMessage(severity, messageHeader, messageBody);
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
	
	public List<CustomerDTO> getClientes() {
		return clientes;
	}
	
	public void setClientes(List<CustomerDTO> clientes) {
		this.clientes = clientes;
	}
	
	public int getTotalOfRecords() {
		return totalOfRecords;
	}
	
	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
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

	public String getInputFindValue() {
		return inputFindValue;
	}

	public void setInputFindValue(String inputFindValue) {
		this.inputFindValue = inputFindValue;
	}
	
	
}
