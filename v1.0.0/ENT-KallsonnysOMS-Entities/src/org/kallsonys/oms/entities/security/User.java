package org.kallsonys.oms.entities.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.kallsonnys.oms.enums.StatusEnum;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String name;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String surname;
	
	@NotNull
	@Email
	@Length(min = 8, max = 250)
	@Column(unique = true, precision = 0, scale = 0, nullable = false, insertable = true, updatable = true)
	private String email;
	
	@NotNull
	@Length(min = 0, max = 255)
	private String password;
	
	/** The profile assigned to this user. */
	@ManyToMany(mappedBy = "users")
	private List<Profile> profiles;
	
	@Enumerated
	private StatusEnum  status;
	
	
	public User() {
		super();
	}

	public User(Long id) {
		super(id);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public List<Profile> getProfiles() {
		if(profiles == null){
			profiles = new ArrayList<Profile>();
		}
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	/**
	 * Associate Profile with user
	 */
	public void addProfile(Profile profile) {
		if (profile == null) return;
		getProfiles().add(profile);
		profile.getUsers().add(this);
	}
	
	/**
	 * Unassociate Profile with user
	 */
	public void removeProfile(Profile profile) {
		if (profile == null) return;
		getProfiles().remove(profile);
		profile.getUsers().remove(this);
	}
	
	/**
	 * Remove All Profiles
	 */
	public void removeAllProfiles() {
		List<Profile> remove = new java.util.ArrayList<Profile>();
		remove.addAll(getProfiles());
		for (Profile element : remove) {
			removeProfile(element);
		}
	}
	
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
