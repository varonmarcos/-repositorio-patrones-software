
package org.kallsonys.oms.ws.campaignservices;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.3
 * 2013-10-28T22:25:57.896-05:00
 * Generated source version: 2.6.3
 */

@WebFault(name = "CampaignException", targetNamespace = "http://kallsonys.integration.com/kallsonysschema/types")
public class CampaignsFault extends Exception {
    
    private com.integration.kallsonys.kallsonysschema.types.GlobalException campaignException;

    public CampaignsFault() {
        super();
    }
    
    public CampaignsFault(String message) {
        super(message);
    }
    
    public CampaignsFault(String message, Throwable cause) {
        super(message, cause);
    }

    public CampaignsFault(String message, com.integration.kallsonys.kallsonysschema.types.GlobalException campaignException) {
        super(message);
        this.campaignException = campaignException;
    }

    public CampaignsFault(String message, com.integration.kallsonys.kallsonysschema.types.GlobalException campaignException, Throwable cause) {
        super(message, cause);
        this.campaignException = campaignException;
    }

    public com.integration.kallsonys.kallsonysschema.types.GlobalException getFaultInfo() {
        return this.campaignException;
    }
}