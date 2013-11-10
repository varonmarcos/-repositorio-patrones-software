package org.kallsonnys.oms.dto;

import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CountryEnum;

public class AddressDTO  extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	

	private Long id;
	
	private AddressTypeEnum addresstype;
	
	private String street;
	
	private String stateName;
	
	private String zip;
	
	private CountryEnum country;
	
	private String cityName;
	
	public AddressDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AddressTypeEnum getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(AddressTypeEnum addresstype) {
		this.addresstype = addresstype;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public CountryEnum getCountry() {
		return country;
	}

	public void setCountry(CountryEnum country) {
		this.country = country;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	

}
