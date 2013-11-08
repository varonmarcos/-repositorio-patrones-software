package org.kallsonnys.oms.utilities;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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

}
