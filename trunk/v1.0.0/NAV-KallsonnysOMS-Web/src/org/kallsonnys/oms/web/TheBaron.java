/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kallsonnys.oms.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Marco
 */
@ManagedBean(name = "editor")
@ViewScoped
public class TheBaron {
    
	private String value = "This editor is provided by PrimeFaces";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    
  
  
    
}


