package org.kallsonnys.integration.processors;

import java.util.List;

import javax.xml.transform.Source;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfPayload;
import org.apache.camel.converter.jaxp.XmlConverter;
import org.apache.cxf.binding.soap.SoapHeader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LoadTcParameters implements Processor {


	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		 
		CxfPayload<SoapHeader> payload = exchange.getIn().getBody(CxfPayload.class);
		List<Source> elements = payload.getBodySources();
		
		Element el = new XmlConverter().toDOMElement(elements.get(0));
		
		Node creditCardType = el.getElementsByTagName("typ:creditCardType").item(0);
		Node creditCardToken = el.getElementsByTagName("typ:creditCardToken").item(0);
		
		
		exchange.getIn().setHeader("creditCardType", creditCardType.getTextContent());
		exchange.getIn().setHeader("creditCardToken", creditCardToken.getTextContent());
		
	}
}
