
package org.kallsonys.oms.ws.customerservices;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.3
 * 2013-10-22T03:16:44.799-05:00
 * Generated source version: 2.6.3
 */

@WebFault(name = "CustomerException", targetNamespace = "http://ws.oms.kallsonys.org/kallsonysschema/types")
public class GetInitialLoginInfoFault extends Exception {
    
    private org.kallsonys.oms.ws.kallsonysschema.types.GlobalException customerException;

    public GetInitialLoginInfoFault() {
        super();
    }
    
    public GetInitialLoginInfoFault(String message) {
        super(message);
    }
    
    public GetInitialLoginInfoFault(String message, Throwable cause) {
        super(message, cause);
    }

    public GetInitialLoginInfoFault(String message, org.kallsonys.oms.ws.kallsonysschema.types.GlobalException customerException) {
        super(message);
        this.customerException = customerException;
    }

    public GetInitialLoginInfoFault(String message, org.kallsonys.oms.ws.kallsonysschema.types.GlobalException customerException, Throwable cause) {
        super(message, cause);
        this.customerException = customerException;
    }

    public org.kallsonys.oms.ws.kallsonysschema.types.GlobalException getFaultInfo() {
        return this.customerException;
    }
}
