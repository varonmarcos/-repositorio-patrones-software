package org.kallsonnys.oms.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.services.orders.OrdersFacadeLocal;

@MessageDriven(name = "OrdersEngineMDB", mappedName = "queue/OrdersEngineQueue", messageListenerInterface = MessageListener.class, activationConfig = {
	@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/OrdersEngineQueue"),
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
 
public class OrdersEngineMDB {
	
	private static final Logger logger = Logger.getLogger(OrdersEngineMDB.class);
	
	@EJB
	OrdersFacadeLocal ordersFacadeEJB;

	public void onMessage(Message message) {
		logger.debug("onMessage(message)::started");

		try {

			if (!(message instanceof ObjectMessage)) {
				logger.error("Message must be instance of " +
						ObjectMessage.class +
						" but it is instance of " +
						message.getClass());
			}

			final Object objProp = ((ObjectMessage) message).getObject();
			if (objProp == null || !(objProp instanceof OrderDTO)) {
				logger.error("Object sent " + objProp +
						" was null or not instance of " + OrderDTO.class);
			}
			
			ordersFacadeEJB.processOrder((OrderDTO) objProp);

			
		} catch (Exception e) {
			logger.error("An unknown exception has occured processing the Order", e);
		} finally {
			logger.debug("onMessage(message)::finished");
		}
	}


}
