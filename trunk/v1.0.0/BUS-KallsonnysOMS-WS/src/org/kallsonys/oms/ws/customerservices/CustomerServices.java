package org.kallsonys.oms.ws.customerservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.3
 * 2013-11-04T21:25:20.549-05:00
 * Generated source version: 2.6.3
 * 
 */
@WebService(targetNamespace = "http://ws.oms.kallsonys.org/customerservices", name = "CustomerServices")
@XmlSeeAlso({com.integration.kallsonys.kallsonysschema.types.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CustomerServices {

    @WebResult(name = "infoCustomer", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types", partName = "getCustomerInfoResponse")
    @WebMethod
    public com.integration.kallsonys.kallsonysschema.types.Customer getCustomerInfo(
        @WebParam(partName = "getCustomerInfoRequest", name = "getCustomerInfoEmail", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types")
        com.integration.kallsonys.kallsonysschema.types.CustomerInfoEmail getCustomerInfoRequest
    ) throws GetCustomerInfoFault;

    @WebResult(name = "infoIntialLogin", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types", partName = "getInitialLoginResponse")
    @WebMethod
    public com.integration.kallsonys.kallsonysschema.types.InitialLoginDTO getInitialLoginInfo(
        @WebParam(partName = "getInitialLoginRequest", name = "infoLogin", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types")
        com.integration.kallsonys.kallsonysschema.types.LoginDTO getInitialLoginRequest
    ) throws GetInitialLoginInfoFault;

    @WebResult(name = "infoCustomerUpdated", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types", partName = "updateCustomerResponse")
    @WebMethod
    public com.integration.kallsonys.kallsonysschema.types.Customer updateCustomer(
        @WebParam(partName = "updateCustomerResquest", name = "infoCustomerUpdated", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types")
        com.integration.kallsonys.kallsonysschema.types.Customer updateCustomerResquest
    ) throws UpdateCustomerFault;

    @WebResult(name = "infoCustomer", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types", partName = "createCustomerResponse")
    @WebMethod
    public com.integration.kallsonys.kallsonysschema.types.Customer createCustomer(
        @WebParam(partName = "createCustomerRequest", name = "infoCustomer", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types")
        com.integration.kallsonys.kallsonysschema.types.Customer createCustomerRequest
    ) throws CreateCustomerFault;
}
