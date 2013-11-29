package org.kallsonnys.oms.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.enums.AddressTypeEnum;

import com.sun.faces.component.visit.FullVisitContext;

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
	
	public static void deleteSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
    }
	
	 public static void addMessage(Severity severity, String messageHeader, String messageBody) {  
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, messageHeader, messageBody));
	 }  
	 
	 public static String buildAdderss(CustomerDTO customerDTO,AddressTypeEnum addressType) {
			
			AddressDTO shipToAddressDTO = null;
			for (AddressDTO addressDTO : customerDTO.getCustomerAddress()) {
				if(addressDTO.getAddresstype() == addressType){
					shipToAddressDTO = addressDTO;
				}
			}
			
			String shipToAddress = "";
			if(shipToAddressDTO!=null){
				shipToAddress = shipToAddressDTO.getStreet() + "<br/>"
						+ shipToAddressDTO.getCountry().toString().toLowerCase()
						+ "," + shipToAddressDTO.getZip() + "<br/>"
						+ shipToAddressDTO.getCityName() + "<br/>"
						+ customerDTO.getPhoneNumber();
			}
			
			return shipToAddress;
		}
	 
	 public static UIComponent findComponent(final String id){
		    FacesContext context = FacesContext.getCurrentInstance(); 
		    UIViewRoot root = context.getViewRoot();
		    final UIComponent[] found = new UIComponent[1];
		    root.visitTree(new FullVisitContext(context), new VisitCallback() {     
		        @Override
		        public VisitResult visit(VisitContext context, UIComponent component) {
		            if(component.getId().equals(id)){
		                found[0] = component;
		                return VisitResult.COMPLETE;
		            }
		            return VisitResult.ACCEPT;              
		        }
		    });
		    return found[0];
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
