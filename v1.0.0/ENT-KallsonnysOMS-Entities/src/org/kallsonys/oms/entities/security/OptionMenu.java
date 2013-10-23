package org.kallsonys.oms.entities.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.kallsonnys.oms.enums.StatusEnum;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class OptionMenu extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String options;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String url;
	
	@Enumerated
	private StatusEnum  status;
	
	/** The profile assigned to this user. */
	@ManyToMany(mappedBy = "optionsMenu")
	private List<Profile> profiles;
	

	public OptionMenu() {
		super();
	}

	public OptionMenu(Long id) {
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

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	/**
	 * Associate Profile with OptionMenu
	 */
	public void addProfile(Profile profile) {
		if (profile == null) return;
		getProfiles().add(profile);
		profile.getOptionsMenu().add(this);
	}
	
	/**
	 * Unassociate Profile with OptionMenu
	 */
	public void removeProfile(Profile profile) {
		if (profile == null) return;
		getProfiles().remove(profile);
		profile.getOptionsMenu().remove(this);
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
	
}
