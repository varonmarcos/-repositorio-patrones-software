package org.kallsonys.oms.commons.Exception;

import java.util.Arrays;
import java.util.List;

/**
 * Error codes listing.
 */
public enum ErrorCodesEnum {

	ENTITY_NOT_FOUND("ERR001",""),
	SECURITY_INITIAL_ERROR("ERR002","Ocurrio un error al obtener el contexto inicial de seguridad");

	private String code;
	private String msg;

	ErrorCodesEnum(String code, String msg) {
		this.code = new String(code);
		this.msg = new String(msg);
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static List<ErrorCodesEnum> asList() {
		return Arrays.asList(values());
	}

}
