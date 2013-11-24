package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CountryEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.utilities.Util;

import test.DAO;

@ManagedBean(name = "detailClient")
@ViewScoped
public class DetailClientBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private Long inputId;
	private String inputName;
	private String inputSname;
	private String inputPhone;
	private String inputEmail;
	private CustomerStatusEnum slState;
	
	private CountryEnum slCountryShip;
	private String inputZoneShip;
	private String inputAddrShip;
	
	private CountryEnum slCountryBill;
	private String inputZoneBill;
	private String inputAddrBill;
	
	private String subDptoShip;
	private String subCityShip;
	
	private String subDptoBill;
	private String subCityBill;
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	private CustomerDTO cliente;
	
	public DetailClientBean(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		if (req != null) {
			String idCli = req.getParameter("id");			
			if (idCli != null){					    	
			    			
				System.out.println("idPrd "+idCli);
				DAO d = new DAO();
				cliente = d.editCliente();
				
				inputId = cliente.getId();
				inputName = cliente.getName();
				inputSname = cliente.getSurname();
				inputPhone = cliente.getPhoneNumber();
				inputEmail = cliente.getEmail();
				slState = cliente.getStatus();
				
				List<AddressDTO> customerAddress = cliente.getCustomerAddress();
				
				for (AddressDTO addressDTO : customerAddress) {
					if (addressDTO.getAddresstype().equals(AddressTypeEnum.SHIPPING_ADDRESS)){
						slCountryShip = addressDTO.getCountry();
						subDptoShip = addressDTO.getStateName();
						subCityShip = addressDTO.getCityName();
						inputAddrShip = addressDTO.getStreet();
						inputZoneShip = addressDTO.getZip();
					}else
						if (addressDTO.getAddresstype().equals(AddressTypeEnum.BILLING_ADDRESS)){
							slCountryBill = addressDTO.getCountry();
							subDptoBill = addressDTO.getStateName();
							subCityBill = addressDTO.getCityName();
							inputAddrBill = addressDTO.getStreet();
							inputZoneBill = addressDTO.getZip();
					}
				}		    	
			}
			
		}
	}
	
	
	
	public Long getInputId() {
		return inputId;
	}
	public void setInputId(Long inputId) {
		this.inputId = inputId;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getInputSname() {
		return inputSname;
	}
	public void setInputSname(String inputSname) {
		this.inputSname = inputSname;
	}
	public String getInputPhone() {
		return inputPhone;
	}
	public void setInputPhone(String inputPhone) {
		this.inputPhone = inputPhone;
	}
	public String getInputEmail() {
		return inputEmail;
	}
	public void setInputEmail(String inputEmail) {
		this.inputEmail = inputEmail;
	}
	public CustomerStatusEnum getSlState() {
		return slState;
	}
	public void setSlState(CustomerStatusEnum slState) {
		this.slState = slState;
	}
	public CountryEnum getSlCountryShip() {
		return slCountryShip;
	}
	public void setSlCountryShip(CountryEnum slCountryShip) {
		this.slCountryShip = slCountryShip;
	}
	public String getInputZoneShip() {
		return inputZoneShip;
	}
	public void setInputZoneShip(String inputZoneShip) {
		this.inputZoneShip = inputZoneShip;
	}
	public String getInputAddrShip() {
		return inputAddrShip;
	}
	public void setInputAddrShip(String inputAddrShip) {
		this.inputAddrShip = inputAddrShip;
	}
	public CountryEnum getSlCountryBill() {
		return slCountryBill;
	}
	public void setSlCountryBill(CountryEnum slCountryBill) {
		this.slCountryBill = slCountryBill;
	}
	public String getInputZoneBill() {
		return inputZoneBill;
	}
	public void setInputZoneBill(String inputZoneBill) {
		this.inputZoneBill = inputZoneBill;
	}
	public String getInputAddrBill() {
		return inputAddrBill;
	}
	public void setInputAddrBill(String inputAddrBill) {
		this.inputAddrBill = inputAddrBill;
	}
	public String getSubDptoShip() {
		return subDptoShip;
	}
	public void setSubDptoShip(String subDptoShip) {
		this.subDptoShip = subDptoShip;
	}
	public String getSubCityShip() {
		return subCityShip;
	}
	public void setSubCityShip(String subCityShip) {
		this.subCityShip = subCityShip;
	}
	public String getSubDptoBill() {
		return subDptoBill;
	}
	public void setSubDptoBill(String subDptoBill) {
		this.subDptoBill = subDptoBill;
	}
	public String getSubCityBill() {
		return subCityBill;
	}
	public void setSubCityBill(String subCityBill) {
		this.subCityBill = subCityBill;
	}
	public CustomerDTO getCliente() {
		return cliente;
	}
	public void setCliente(CustomerDTO cliente) {
		this.cliente = cliente;
	}

}
