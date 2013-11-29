package org.kallsonys.oms.commons.Exception;

import java.util.Arrays;
import java.util.List;

/**
 * Error codes listing.
 */
public enum ErrorCodesEnum {

	ENTITY_NOT_FOUND("ERR001",""),
	SECURITY_INITIAL_ERROR("ERR002","Ocurrio un error al obtener el contexto inicial de seguridad"),
	PARSING_DATE_ERROR("ERR003","Ocurrio un error al intentar parsea una fecha formato aceptado: yyyy-MM-dd"),
	USER_ALREADY_EXISTS("ERR004","EL cliente que se desea registrar ya se encuentra registrado en el sistema"),
	PASSWORD_ERROR("ERR005","Ocurrio un error al crear el hash del password"),
	ERROR_PROCESSING_ORDER("ERR006","Ocurrio un error inesperado en el procesamiento de la orden"),
	SECURITY_INITIAL_INVALID("ERR005","La contraseña o el login estan invalidos ");

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
