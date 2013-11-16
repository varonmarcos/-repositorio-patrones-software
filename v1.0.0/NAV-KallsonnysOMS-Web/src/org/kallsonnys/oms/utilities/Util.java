package org.kallsonnys.oms.utilities;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.kallsonnys.oms.dto.FilterConstants;
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
	 
	 public static String mapCriterioFilter(Integer criterio){
		 String typeFilter;
		 switch (criterio) {
		 	case 1:
		 		typeFilter = FilterConstants.PROD_ID;
		 		break;
		 	case 2:
		 		typeFilter = FilterConstants.NAME;
		 		break;
		 	case 3:
		 		typeFilter = FilterConstants.DESCRIPTION;		
			break;

		default:
			typeFilter = FilterConstants.NAME;
			break;
		}
		 
		 return typeFilter;
	 }

}
