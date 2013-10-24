package org.kallsonnys.oms.dto.security;

import org.kallsonnys.oms.dto.BaseDTO;
import org.kallsonnys.oms.dto.UserDTO;

public class IntialUserLoginDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private UserDTO  user;
	
	private String tgt;
	
	private String serviceTicket;

	public IntialUserLoginDTO() {
		
	}

	public IntialUserLoginDTO(UserDTO user, String tgt, String serviceTicket) {
		super();
		this.user = user;
		this.tgt = tgt;
		this.serviceTicket = serviceTicket;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
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
