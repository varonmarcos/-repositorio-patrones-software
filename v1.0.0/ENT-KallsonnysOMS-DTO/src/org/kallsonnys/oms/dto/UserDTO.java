package org.kallsonnys.oms.dto;

import org.kallsonnys.oms.enums.StatusEnum;

public class UserDTO extends BaseDTO {
	

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	private StatusEnum  status;
	
	public UserDTO() {

	}

	public UserDTO(Long id, String email) {
		super();
		this.id = id;
		this.email = email;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	
	

}
