package org.kallsonnys.oms.dto.security;

public class UserDTO {
	
	private String uid;
	private String password;
	
	public UserDTO() {
	
	}
	
	public UserDTO(String uid, String password) {
		super();
		this.uid = uid;
		this.password = password;
	}

	public UserDTO(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
