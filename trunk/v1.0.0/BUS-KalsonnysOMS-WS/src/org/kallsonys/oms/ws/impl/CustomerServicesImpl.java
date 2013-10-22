package org.kallsonys.oms.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.kallsonys.oms.ws.customerservices.CustomerServices;
import org.kallsonys.oms.ws.customerservices.GetInitialLoginInfoFault;
import org.kallsonys.oms.ws.kallsonysschema.types.InitialLoginDTO;
import org.kallsonys.oms.ws.kallsonysschema.types.LoginDTO;

public class CustomerServicesImpl implements CustomerServices {

	@Override
	@WebResult(name = "infoIntialLogin", targetNamespace = "http://ws.oms.kallsonys.org/kallsonysschema/types", partName = "getInitialLoginResponse")
	@WebMethod
	public InitialLoginDTO getInitialLoginInfo(
			@WebParam(partName = "getInitialLoginRequest", name = "infoLogin", targetNamespace = "http://ws.oms.kallsonys.org/kallsonysschema/types") LoginDTO getInitialLoginRequest)
			throws GetInitialLoginInfoFault {
		// TODO Auto-generated method stub
		return null;
	}

}
