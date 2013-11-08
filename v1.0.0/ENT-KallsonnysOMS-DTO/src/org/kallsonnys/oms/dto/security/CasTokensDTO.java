package org.kallsonnys.oms.dto.security;

public class CasTokensDTO {

	private String tgt;
	private String serviceTicket;

	public CasTokensDTO() {

	}

	public CasTokensDTO(String tgt, String serviceTicket) {
		this.tgt = tgt;
		this.serviceTicket = serviceTicket;
	}

	public String getTgt() {
		return tgt;
	}

	public void setTgt(String tgt) {
		this.tgt = tgt;
	}

	public String getServiceTicket() {
		return serviceTicket;
	}

	public void setServiceTicket(String serviceTicket) {
		this.serviceTicket = serviceTicket;
	}

}
