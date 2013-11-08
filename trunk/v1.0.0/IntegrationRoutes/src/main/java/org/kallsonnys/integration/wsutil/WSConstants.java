package org.kallsonnys.integration.wsutil;

public interface WSConstants {

	public static final String WS_SCHEMA_PREFIX = "typ";
	public static final String CREDITCARD_SERVICE_URI = "http://localhost:8080/MockUpServices/CreditCardServices";
	public static final String CREDITCARD_SERVICES_OP = "VerifyCC";
	public static final String CREDITCARD_SERVICES_ENV = "creditCardEnvelope.vm";
	public static final String TEMPLATES_DIR = "/usr/local/conf/templates/";
	public static final String ENVELOPE_PARAM_BINDING = "param";
	public static final String CREDITCARD_SERVICES_RESP = "valid";
	public static final String SOAP_OPERATION = "Operation";

}
