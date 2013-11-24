package org.kallsonnys.oms.web.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.primefaces.context.RequestContext;

import test.DAO;

@ManagedBean(name = "loogOutBean")
@ViewScoped
public class LogOutBean implements Serializable {
	private static final long serialVersionUID = -2152389656664659476L;
	
	public LogOutBean(){
		
	}

	public void logout(ActionEvent actionEvent) {		
		
		try {
			Util.deleteSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}