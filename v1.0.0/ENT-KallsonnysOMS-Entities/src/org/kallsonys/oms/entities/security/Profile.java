package org.kallsonys.oms.entities.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.kallsonnys.oms.enums.StatusEnum;
import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Profile extends AbstractEntity {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Field(store = Store.NO, index = Index.TOKENIZED)
	@Column(nullable = false, length = 120)
	private String name;
	
	@Enumerated
	private StatusEnum  status;

	@ManyToMany
	@JoinTable(name="user_profile", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	@ManyToMany
	@JoinTable(name = "optionsMenu_profile", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "optionsMenu_id"))
	private List<OptionMenu> optionsMenu;

	public Profile() {
		super();
	}

	public Profile(Long id) {
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<User> getUsers() {
		if(users == null)
			users = new ArrayList<User>();
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	/**
	 * Associate User with profile
	 */
	public void addUser(User user) {
		if (user == null) return;
		getUsers().add(user);
		user.getProfiles().add(this);
	}
	
	/**
	 * Unassociate User with profile
	 */
	public void removeUser(User user) {
		if (user == null) return;
		getUsers().remove(user);
		user.getProfiles().remove(this);
	}
	
	/**
	 * Remove All users
	 */
	public void removeAllProfiles() {
		List<User> remove = new java.util.ArrayList<User>();
		remove.addAll(getUsers());
		for (User element : remove) {
			removeUser(element);
		}
	}

	public List<OptionMenu> getOptionsMenu() {
		if(optionsMenu == null){
			optionsMenu = new ArrayList<OptionMenu>();
		}
		return optionsMenu;
	}

	public void setOptionsMenu(List<OptionMenu> optionsMenu) {
		this.optionsMenu = optionsMenu;
	}
	
	/**
	 * Unassociate OptionMenu with profile
	 */
	public void addOptionMenu(OptionMenu optionMenu) {
		if (optionMenu == null) return;
		getOptionsMenu().add(optionMenu);
		optionMenu.getProfiles().add(this);
		
	}
	
	/**
	 * Unassociate OptionMenu with profile
	 */
	public void removeOptionMenu(OptionMenu optionMenu) {
		if (optionMenu == null) return;
		getOptionsMenu().remove(optionMenu);
		optionMenu.getProfiles().remove(this);
		
	}
	
	/**
	 * Remove All OptionMenu
	 */
	public void removeAllOptionsMenu() {
		List<OptionMenu> remove = new java.util.ArrayList<OptionMenu>();
		remove.addAll(getOptionsMenu());
		for (OptionMenu element : remove) {
			removeOptionMenu(element);
		}
	}


}
