package org.kallsonnys.oms.dto.agent;

import java.util.HashMap;
import java.util.Map;

import org.kallsonnys.oms.dto.BaseDTO;

public class SoapMessageDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	private String serviceDir;
	private String serviceOp;
	private String serviceTempl;
	private Map<String, Object> params;
	
	public SoapMessageDTO() {
		
	}

	public String getServiceDir() {
		return serviceDir;
	}

	public void setServiceDir(String serviceDir) {
		this.serviceDir = serviceDir;
	}

	public String getServiceOp() {
		return serviceOp;
	}

	public void setServiceOp(String serviceOp) {
		this.serviceOp = serviceOp;
	}

	public Map<String, Object> getParams() {
		if(params == null)
			params = new HashMap<String, Object>();
		return params;
	}
	
	public void addParam(String key, Object val){
		getParams().put(key, val);
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getServiceTempl() {
		return serviceTempl;
	}

	public void setServiceTempl(String serviceTempl) {
		this.serviceTempl = serviceTempl;
	}

}
