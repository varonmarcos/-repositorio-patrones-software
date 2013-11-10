package org.kallsonnys.oms.dto;

import java.util.ArrayList;
import java.util.List;

import org.kallsonnys.oms.ProfileDTO;
import org.kallsonnys.oms.enums.StatusEnum;

public class UserDTO extends BaseDTO {
	

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	private StatusEnum  status;
	
	private List<OptionMenuDTO> listamenu;
	
	private List<ProfileDTO> profiles;
	
	public UserDTO() {

	}

	public UserDTO(Long id, String email, List<OptionMenuDTO> listamenu) {
		super();
		this.id = id;
		this.email = email;
		this.listamenu = listamenu;
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

	public List<OptionMenuDTO> getListamenu() {
		if(listamenu == null)
			listamenu = new ArrayList<OptionMenuDTO>();
		return listamenu;
	}
	
	public void addListaMenu(OptionMenuDTO optionMenuDTO){
		if(optionMenuDTO==null)return;
		getListamenu().add(optionMenuDTO);
	}
	
	public void removeListaMenu(OptionMenuDTO optionMenuDTO){
		if(optionMenuDTO==null)return;
		getListamenu().remove(optionMenuDTO);
	}
	
	public void removeAllListaMenu(List<OptionMenuDTO> optionMenuDTOs){
		if(optionMenuDTOs==null)return;
		List<OptionMenuDTO> remove = new ArrayList<OptionMenuDTO>();
		remove.addAll(getListamenu());
		for (OptionMenuDTO optionMenuDTO : remove) {
			removeListaMenu(optionMenuDTO);
		}
	}

	public List<ProfileDTO> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<ProfileDTO> profiles) {
		if(profiles == null)
			profiles = new ArrayList<ProfileDTO>();
		this.profiles = profiles;
	}
	
	public void addProfile(ProfileDTO profile){
		if(profile==null)return;
		getProfiles().add(profile);
		
	}
	
	public void removeProfile(ProfileDTO profile){
		if(profile==null)return;
		getProfiles().remove(profile);
		
	}
	
	public void removeAllProfiles(List<ProfileDTO> profiles){
		if(profiles==null)return;
		List<ProfileDTO> remove = new ArrayList<ProfileDTO>();
		remove.addAll(getProfiles());
		for (ProfileDTO profileDTO : remove) {
			removeProfile(profileDTO);
		}
	}

	public void setListamenu(List<OptionMenuDTO> listamenu) {
		this.listamenu = listamenu;
	}
	
	
	

}
