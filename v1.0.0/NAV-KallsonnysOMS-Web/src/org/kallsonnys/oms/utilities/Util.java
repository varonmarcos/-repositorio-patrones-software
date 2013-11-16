package org.kallsonnys.oms.utilities;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.kallsonnys.oms.enums.ProductCategoryEnum;

public class Util {
	
	public static void putinSession(String name, Object o) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                                                        .getExternalContext()
                                                        .getSession(true);
        session.setAttribute(name, o);
    }
	
	public static Object getfromSession(String name) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                                                        .getExternalContext()
                                                        .getSession(false);

        return session.getAttribute(name);
    }
	
	public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                                         .getExternalContext().getSession(true);
    }
	
	 public static void addMessage(Severity severity, String messageHeader, String messageBody) {  
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, messageHeader, messageBody));
	 }  

}
