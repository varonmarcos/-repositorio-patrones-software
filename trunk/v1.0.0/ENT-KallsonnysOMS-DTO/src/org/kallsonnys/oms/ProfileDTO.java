package org.kallsonnys.oms;

import org.kallsonnys.oms.dto.BaseDTO;
import org.kallsonnys.oms.enums.StatusEnum;

public class ProfileDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private StatusEnum  status;  
	
	public ProfileDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
