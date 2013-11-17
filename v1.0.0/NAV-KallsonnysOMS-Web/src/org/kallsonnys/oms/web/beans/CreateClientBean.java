package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CountryEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;

@ManagedBean(name = "createClient")
@ViewScoped
public class CreateClientBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private Long inputId;
	private String inputName;
	private String inputSname;
	private String inputPhone;
	private String inputEmail;
	private CustomerStatusEnum slState;
	private List<CustomerStatusEnum> status;
	private List<CountryEnum> countrys;
	
	private CountryEnum slCountryShip;
	private String inputDptoShip;
	private String inputCityShip;
	private String inputZoneShip;
	private String inputAddrShip;
	
	private CountryEnum slCountryBill;
	private String inputDptoBill;
	private String inputCityBill;
	private String inputZoneBill;
	private String inputAddrBill;
	
	private List<String> dptos;
	private List<String> citys;
	private String subDptoShip;
	private String subCityShip;
	
	private String subDptoBill;
	private String subCityBill;

	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	public CreateClientBean(){
	}

	public void create(ActionEvent actionEvent){
		CustomerDTO customer = new CustomerDTO();
		customer.setId(inputId);
		customer.setName(inputName);
		customer.setSurname(inputSname);
		customer.setPhoneNumber(inputPhone);
		customer.setEmail(inputEmail);
		customer.setStatus(slState);
		
		List<AddressDTO> customerAddress = new ArrayList<AddressDTO>();
		AddressDTO adrShip = new AddressDTO();
		adrShip.setAddresstype(AddressTypeEnum.SHIPPING_ADDRESS);
		adrShip.setCountry(slCountryShip);
		adrShip.setStateName(inputDptoShip);
		adrShip.setCityName(inputCityShip);
		adrShip.setZip(inputZoneShip);
		adrShip.setStreet(inputAddrShip);		
		customerAddress.add(adrShip);
		AddressDTO adrBill = new AddressDTO();
		adrBill.setAddresstype(AddressTypeEnum.BILLING_ADDRESS);
		adrBill.setCountry(slCountryBill);
		adrBill.setStateName(inputDptoBill);
		adrBill.setCityName(inputCityBill);
		adrBill.setZip(inputZoneBill);
		adrBill.setStreet(inputAddrBill);	
		customerAddress.add(adrBill);
		
		customer.setCustomerAddress(customerAddress);
	}
	
	public void handleDptoChange() {  
		System.out.println("subDptoShip " + subDptoShip);
        if(subDptoShip !=null && !subDptoShip.equals(""))  
        	citys = loadCitys(subDptoShip); 
    }
	
	public void handleCountryChange() {  
		System.out.println("slCountryShip " + slCountryShip);
        if(slCountryShip !=null && !slCountryShip.equals(""))  
        	dptos = loadDptos(slCountryShip); 
    }
	
	public void handleDptoChangeB() {  
		System.out.println("subDptoBill " + subDptoBill);
        if(subDptoBill !=null && !subDptoBill.equals(""))  
        	citys = loadCitys(subDptoBill); 
    }
	
	public void handleCountryChangeB() {  
		System.out.println("slCountryBill " + slCountryBill);
        if(slCountryBill !=null && !slCountryBill.equals(""))  
        	dptos = loadDptos(slCountryBill); 
    }
	
	
	private List<String> loadDptos(CountryEnum slCountryShip){
		dptos = new ArrayList<String>();
		
		if (slCountryShip.equals(CountryEnum.COLOMBIA)){
			dptos.add("Cundinamarca");
			dptos.add("Antioquia");
			dptos.add("Tolima");
			dptos.add("Boyaca");
		}
		
		if (slCountryShip.equals(CountryEnum.ENGLAND)){
			dptos.add("Gran Manchester");
			dptos.add("Cheshire");
			dptos.add("Ruteoland");
			dptos.add("Derbyshire");
		}
		
		if (slCountryShip.equals(CountryEnum.USA)){
			dptos.add("Colorado");
			dptos.add("California");
			dptos.add("Texas");
			dptos.add("Florida");
		}
		
		return dptos;		
	}
	
	private List<String> loadCitys(String subDpto){
		citys = new ArrayList<String>();
		
		if (subDpto.equals("Cundinamarca")){
			citys.add("Soacha");
			citys.add("Girardot");
		}
		
		if (subDpto.equals("Antioquia")){
			citys.add("Medellin");
			citys.add("Envigado");
		}
		
		if (subDpto.equals("Tolima")){
			citys.add("Fresno");
			citys.add("Ibague");
		}
		
		if (subDpto.equals("Boyaca")){
			citys.add("Duitama");
			citys.add("Sogamoso");
		}
		
		if (subDpto.equals("Gran Manchester")){
			citys.add("Derby");
			citys.add("Ripley");
		}
		
		if (subDpto.equals("Cheshire")){
			citys.add("Bolton");
			citys.add("Wigan");
		}
		
		if (subDpto.equals("Ruteoland")){
			citys.add("Chester");
			citys.add("Northwich");
		}
		
		if (subDpto.equals("Derbyshire")){
			citys.add("Bridge");
			citys.add("Tottenham");
		}

		if (subDpto.equals("Colorado")){
			citys.add("Denver");
			citys.add("Washington");
		}
			
		if (subDpto.equals("California")){
			citys.add("San Francisco");
			citys.add("Los Angeles");
		}
		
		if (subDpto.equals("Texas")){
			citys.add("Dallas");
			citys.add("Houston");
		}
		
		if (subDpto.equals("Florida")){
			citys.add("Miami");
			citys.add("Atlantis");
		}
		
		return citys;		
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
	
	public List<CustomerStatusEnum> getStatus() {
		status = new ArrayList<CustomerStatusEnum>();
		status.add(CustomerStatusEnum.PLATINUM);
		status.add(CustomerStatusEnum.GOLDEN);
		status.add(CustomerStatusEnum.SILVER);
		status.add(CustomerStatusEnum.DISABLED);
		return status;
	}

	public void setStatus(List<CustomerStatusEnum> status) {
		this.status = status;
	}

	public List<CountryEnum> getCountrys() {
		countrys = new ArrayList<CountryEnum>();
		countrys.add(CountryEnum.COLOMBIA);
		countrys.add(CountryEnum.ENGLAND);
		countrys.add(CountryEnum.USA);
		return countrys;
	}

	public void setCountrys(List<CountryEnum> countrys) {
		this.countrys = countrys;
	}

	public String getInputDptoShip() {
		return inputDptoShip;
	}

	public void setInputDptoShip(String inputDptoShip) {
		this.inputDptoShip = inputDptoShip;
	}

	public String getInputCityShip() {
		return inputCityShip;
	}

	public void setInputCityShip(String inputCityShip) {
		this.inputCityShip = inputCityShip;
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

	public String getInputDptoBill() {
		return inputDptoBill;
	}

	public void setInputDptoBill(String inputDptoBill) {
		this.inputDptoBill = inputDptoBill;
	}

	public String getInputCityBill() {
		return inputCityBill;
	}

	public void setInputCityBill(String inputCityBill) {
		this.inputCityBill = inputCityBill;
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

	public CountryEnum getSlCountryBill() {
		return slCountryBill;
	}

	public void setSlCountryBill(CountryEnum slCountryBill) {
		this.slCountryBill = slCountryBill;
	}

	public List<String> getDptos() {
		return dptos;
	}

	public void setDptos(List<String> dptos) {
		this.dptos = dptos;
	}

	public List<String> getCitys() {
		return citys;
	}

	public void setCitys(List<String> citys) {
		
		this.citys = citys;
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
	
	
}
