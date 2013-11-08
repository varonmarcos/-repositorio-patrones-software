package org.kallsonnys.integration.processors;

import static org.kallsonnys.integration.wsutil.WSConstants.CREDITCARD_SERVICES_ENV;
import static org.kallsonnys.integration.wsutil.WSConstants.CREDITCARD_SERVICES_OP;
import static org.kallsonnys.integration.wsutil.WSConstants.CREDITCARD_SERVICE_URI;
import static org.kallsonnys.integration.wsutil.WSConstants.ENVELOPE_PARAM_BINDING;
import static org.kallsonnys.integration.wsutil.WSConstants.SOAP_OPERATION;
import static org.kallsonnys.integration.wsutil.WSConstants.TEMPLATES_DIR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapterImpl;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class VerifyTcProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		
		String creditCardType = (String) exchange.getIn().getHeader("creditCardType");
		String creditCardToken = (String) exchange.getIn().getHeader("creditCardToken");
		
		// Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection
				.call(createSOAPRequest(CREDITCARD_SERVICES_ENV,
						CREDITCARD_SERVICES_OP, creditCardType, creditCardToken),
						CREDITCARD_SERVICE_URI);
		
		String validationTC = soapResponse.getSOAPBody().getTextContent();

		if(validationTC != null){
			exchange.getIn().setHeader("validationTC", Boolean.valueOf(validationTC));
		}
	
		soapConnection.close();
	}
	
	private static SOAPMessage createSOAPRequest(String envelopePath, String operation, Object...params) {

		try {

			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();

			
			RuntimeInstance runtimeInstance = new RuntimeInstance();
			runtimeInstance.init();
			SimpleNode simpleNode = runtimeInstance.parse(readFileAsString(TEMPLATES_DIR+envelopePath), "nameOfYourTemplateResource");

			Template t = new Template();
			simpleNode.init(new InternalContextAdapterImpl(new VelocityContext()), runtimeInstance);
			t.setData(simpleNode);
			
			 /*  first, get and initialize an engine  */
	        VelocityEngine ve = new VelocityEngine();
	        ve.init();
	
	        /*  create a context and add data */
	        VelocityContext context = new VelocityContext();
	        
	        if(params != null){
	        	int i = 0;
	        	  for (Object object : params) {
	 	        	 context.put(ENVELOPE_PARAM_BINDING+i, object);
	 	        	 i++;
	 			}
	        }else{
				throw new RuntimeException("NO HAY PARAMETROS PARA MAPEAR");
	        }
	      
	        StringWriter writer = new StringWriter();
	        t.merge(context, writer);
	     
	        soapPart.setContent(new DOMSource(DOMUtils.readXml(new StringReader(writer.toString()))));

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader(SOAP_OPERATION, operation);

			soapMessage.saveChanges();

			return soapMessage;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private static String readFileAsString(String filePath) {
		
		try {
			StringBuffer fileData = new StringBuffer();
			BufferedReader reader = new BufferedReader(
					new FileReader(filePath));
			char[] buf = new char[1024];
			int numRead=0;
			while((numRead=reader.read(buf)) != -1){
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
			}
			reader.close();
			return fileData.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }

}
