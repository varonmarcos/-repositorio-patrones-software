package org.kallsonys.oms.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.kallsonys.oms.ws.kallsonysschema.types.PagingDto;
import org.kallsonys.oms.ws.kallsonysschema.types.ProductList;
import org.kallsonys.oms.ws.productservices.ProductServices;

public class ProductServicesImpl implements ProductServices {

	@Override
	@WebResult(name = "infoProductList", targetNamespace = "http://ws.oms.kallsonys.org/kallsonysschema/types", partName = "getProductsResponse")
	@WebMethod
	public ProductList getProducts(
			@WebParam(partName = "getProductsRequest", name = "infoPaging", targetNamespace = "http://ws.oms.kallsonys.org/kallsonysschema/types") PagingDto getProductsRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
