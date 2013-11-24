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

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.CustomerDTOLazyList;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import test.DAO;

@ManagedBean(name = "manageClient")
@ViewScoped
public class ManageClientBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private CustomerDTO cliente;
	private List<CustomerStatusEnum> statuss;
	private LazyDataModel<CustomerDTO> clientes;
	private List<CustomerDTO> list; 
	private String ship;
	private String bill;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public ManageClientBean(){
		setClientes(new CustomerDTOLazyList(list));		
		setAddressShip(list);
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req != null) {
			String idCli = req.getParameter("id");
			String status = req.getParameter("state");
			if (idCli != null && status != null){
				if (status.equals("OK")){
		    		messageHeader = "Cliente con Id ";
					messageBody = idCli + " creado correctamente";
					severity = FacesMessage.SEVERITY_INFO;
		    	}else{
		    		messageHeader = "Error al crear el Cliente ";
					messageBody =  idCli;
					severity = FacesMessage.SEVERITY_ERROR;
		    	}
		    	Util.addMessage(severity, messageHeader, messageBody);
			}			
		}
		
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

	public CustomerDTO getCliente() {
		return cliente;
	}

	public void setCliente(CustomerDTO cliente) {
		this.cliente = cliente;
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
	
	

}
