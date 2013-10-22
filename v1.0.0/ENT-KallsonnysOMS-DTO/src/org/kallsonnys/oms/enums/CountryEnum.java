package org.kallsonnys.oms.enums;

public enum CountryEnum {

	COLOMBIA(Boolean.FALSE), USA(Boolean.TRUE), ENGLAND(Boolean.TRUE);
	
	private Boolean international;
	
	CountryEnum(boolean international){
		this.international = international;
	}

	public Boolean isInternational() {
		return international;
	}

	public void setInternational(Boolean international) {
		this.international = international;
	}
}
