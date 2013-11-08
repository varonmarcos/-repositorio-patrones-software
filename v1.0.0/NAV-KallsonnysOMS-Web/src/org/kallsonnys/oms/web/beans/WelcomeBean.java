package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.OptionMenuDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.kallsonnys.oms.utilities.Util;

@ManagedBean(name = "welcomeBean")
@ViewScoped
public class WelcomeBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	private MenuModel model; 
	private String userLogin;
	
	
	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public WelcomeBean(){
		UserDTO userDto = null;
		List<OptionMenuDTO> listMenu = new ArrayList<OptionMenuDTO>();
		
		model = new DefaultMenuModel(); 
		
		try {
			userDto = (UserDTO) Util.getfromSession("userSesion");
			listMenu = userDto.getListamenu();			
			Submenu submenu = new Submenu();  
	        submenu.setLabel("Menu Opciones");
			for (OptionMenuDTO optionMenuDTO : listMenu) {			  
		        
		        MenuItem item = new MenuItem();  
		        item.setValue(optionMenuDTO.getOption());  
		        item.setUrl(optionMenuDTO.getUrl());  
		        submenu.getChildren().add(item);
		        
			}
			model.addSubmenu(submenu);	
			this.setUserLogin(userDto.getName() + " " + userDto.getSurname());
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		
		 
          
	}

}
