package org.kallsonys.oms.commons.Exception;


public class OMSException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private String code;

	public OMSException(String code) {
		super();
		this.code = code;
	}

	public OMSException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public OMSException(String code, String string, Throwable throwable) {
		super(string, throwable);
		this.code = code;
	}

	public OMSException(String code, Throwable throwable) {
		super(throwable);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}