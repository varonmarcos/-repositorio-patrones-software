package org.kallsonys.oms.commons.mail;

public interface MailTemplateConstants {

	static final String TEXT_HTML = "text/html; charset=ISO-8859-1";

	static final String PROPERTIES_PATH = "KallsonnysOMS-Mail";

	static final String SUPPORT_ADDRESSES = "support.addresses";
	
	static final String ORDER_CREATED_TEMPL = "Order_Created_Template.vm";
	
	static final String ORDER_APPROVED_TEMPL = "Order_Approved_Template.vm";
	
	static final String ORDER_DELETED_TEMPL = "Order_Deleted_Template.vm";
	
	static final String ORDER_SHIPPED_TEMPL = "Order_Shipped_Template.vm";
	
	static final String ORDER_ERROR_TEMPL = "Order_Error_Template.vm";
	
	static final String ORDERS_MAIL_ACCOUNT = "orders.mail.account";
	
	static final String UNIQUE_ALLOWED_ADDRESS = "unique.allowed.address";
	
	static final String UNIQUE_ALLOWED_FROM = "unique.allowed.from";
}
