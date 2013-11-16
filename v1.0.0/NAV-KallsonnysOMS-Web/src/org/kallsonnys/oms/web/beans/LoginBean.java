package org.kallsonnys.oms.web.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.primefaces.context.RequestContext;

import test.DAO;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = -2152389656664659476L;
	private String inputUserName;
	private String inputPassword;
	private boolean logeado = false;
	
	public String getInputPassword() {
		return inputPassword;
	}

	public void setInputPassword(String inputPassword) {
		this.inputPassword = inputPassword;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public boolean estaLogeado() {
		return logeado;
	}

	public void login(ActionEvent actionEvent) {
		String messageHeader;
		String messageBody;
		Severity severity;
		
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		IntialUserLoginDTO ini = null;
		DAO d = new DAO();
		UserDTO userDto = null;
		logeado = true;	
		
		try {
			ini = d.getUser(inputUserName, inputPassword);
			userDto = ini.getUser();
			messageHeader = "Bienvenido";
			messageBody = userDto.getName();
			severity = FacesMessage.SEVERITY_INFO;
		} catch (OMSException e) {
			logeado = false;
			System.out.println("ERROR login: " + e.getMessage());
			messageHeader = "Login Error";
			messageBody = "Credenciales no válidas";
			severity = FacesMessage.SEVERITY_ERROR;
			e.printStackTrace();
		} catch (Exception e) {
			logeado = false;
			System.out.println("ERROR login: " + e.getMessage());
			messageHeader = "Login Error";
			messageBody = "Credenciales no válidas";
			severity = FacesMessage.SEVERITY_ERROR;
			e.printStackTrace();
		}
		
		
		Util.addMessage(severity, messageHeader, messageBody);
				
		context.addCallbackParam("estaLogeado", logeado);
		
		if (logeado){			
			Util.putinSession("userSesion", userDto);
			context.addCallbackParam("view", "pages/welcome.xhtml"); 
		}
		
		/*if (this.inputUserName != null && this.inputUserName.equals("pabloarturot@gmail.com") && this.inputPassword != null && this.inputPassword.equals("admin")) {
			validateUserPerfil(this.inputUserName, this.inputPassword);
			logeado = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.inputUserName);
		} else {
			logeado = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
		}*/	

	}
}