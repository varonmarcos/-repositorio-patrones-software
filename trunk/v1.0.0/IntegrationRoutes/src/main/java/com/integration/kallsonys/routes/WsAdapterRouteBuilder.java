package com.integration.kallsonys.routes;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.message.MessageContentsList;
import org.apache.log4j.Logger;

import com.integration.kallsonys.kallsonysschema.types.Customer;
import com.integration.kallsonys.kallsonysschema.types.InitialLoginDTO;
import com.integration.kallsonys.kallsonysschema.types.LoginDTO;
import com.integration.kallsonys.kallsonysschema.types.Product;
import com.integration.kallsonys.kallsonysschema.types.ProductList;

public class WsAdapterRouteBuilder extends RouteBuilder {
	
	private static final Logger logger = Logger.getLogger(WsAdapterRouteBuilder.class);

	@Override
	public void configure() throws Exception {
		
		from("cxf:bean:ProductServicesEndpoint")
		.routeId("ProductServicesWorkFlow")
		.process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				logger.info("INIT ROUTER");
				logger.info(exchange.getIn().getHeaders());
				logger.info(exchange.getIn().getBody());
				
				ProductList list = new ProductList();
				Product p1 = new Product();
				p1.setId("1");
				p1.setName("p1");
				p1.setDescription("p1");
				p1.setPrice("600000");
				p1.setProducer("SONY");
				p1.setUrlFull("https://googledrive.com/host/0B-NmAN9xQQ4SNmtLSmQzMk9oN3M/");
				p1.setUrlThumb("https://googledrive.com/host/0B-NmAN9xQQ4SN2t1MldiZlBFRGs/");
				list.getProductsResult().add(p1);
				
				exchange.getOut().setBody(list);
			}

		});
		
		from("cxf:bean:CustomerServicesEndpoint")
		.routeId("CustomerServicesWorkFlow")
		.choice().when(header("operationName").isEqualTo("getInitialLoginInfo")).process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				logger.info("INITIAL LOGIN INFO OPERATION");
				
				MessageContentsList content = (MessageContentsList) exchange.getIn().getBody();
				LoginDTO loginDTO = (LoginDTO) content.get(0);
				
				
				
				InitialLoginDTO initialLoginDTO = new InitialLoginDTO();
				initialLoginDTO.setTgt("");
				
				Customer customer = new Customer();
				customer.setEmail("rsmith@example.com");
				customer.setName("Robert Smith");
				
				initialLoginDTO.setCustomerInfo(customer);
				
				exchange.getOut().setBody(initialLoginDTO);
				
			}

		});
		
		

	}

}
