package org.kallsonnys.integration.processors;

import static org.kallsonnys.integration.wsutil.WSConstants.CREDITCARD_SERVICES_ENV;
import static org.kallsonnys.integration.wsutil.WSConstants.CREDITCARD_SERVICES_OP;
import static org.kallsonnys.integration.wsutil.WSConstants.CREDITCARD_SERVICE_URI;
import static org.kallsonnys.integration.wsutil.WSConstants.ENVELOPE_PARAM_BINDING;
import static org.kallsonnys.integration.wsutil.WSConstants.SOAP_OPERATION;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.camel.converter.jaxp.XmlConverter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class testWS {

	public static void main(String[] args) {
		
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			
			String creditCardType = "Visa";
			String creditCardToken = "12345";
			
			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection
					.call(createSOAPRequest(CREDITCARD_SERVICES_ENV,
							CREDITCARD_SERVICES_OP, creditCardType, creditCardToken),
							CREDITCARD_SERVICE_URI);
			
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			Element el = new XmlConverter().toDOMElement(sourceContent);
			
			Node validationTC = el.getElementsByTagName("valid").item(0);
			
			System.out.println(validationTC.getTextContent());

            soapConnection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	 private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        Source sourceContent = soapResponse.getSOAPPart().getContent();
	        System.out.print("\nResponse SOAP Message = ");
	        StreamResult result = new StreamResult(System.out);
	        transformer.transform(sourceContent, result);
	 }
	 

		private static SOAPMessage createSOAPRequest(String envelopePath, String operation, Object...params) {

			try {

				MessageFactory messageFactory = MessageFactory.newInstance();
				SOAPMessage soapMessage = messageFactory.createMessage();
				SOAPPart soapPart = soapMessage.getSOAPPart();

				 /*  first, get and initialize an engine  */
		        VelocityEngine ve = new VelocityEngine();
		        ve.init();
		        /*  next, get the Template  */
		        Template t = ve.getTemplate(envelopePath);
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
}
