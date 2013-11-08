package org.kallsonys.oms.commons.util;

import java.util.Properties;

import javax.naming.Context;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;

import org.apache.log4j.Logger;
import org.hibernate.transaction.JBossTransactionManagerLookup;
import org.kallsonnys.oms.dto.MailDTO;
import org.kallsonys.oms.commons.mail.MailTemplateManager;

public class SendMailTxListener {

	static final Logger log = Logger.getLogger(SendMailTxListener.class);

	private ResourceBundleManager bundleManager;

	private Properties properties;
	
	private static SendMailTxListener instance;
	
	private static final String PROPERTIES_PATH = "KallsonnysOMS-Enterprise";

	private SendMailTxListener() {
		bundleManager = new ResourceBundleManager(PROPERTIES_PATH);
		properties = loadProperties();
	}
	
	public static SendMailTxListener getInstance() {
		if (instance == null) {
			instance = new SendMailTxListener();
		}
		return instance;
	}

	private Properties loadProperties() {
		final Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, bundleManager.getProperty(Context.INITIAL_CONTEXT_FACTORY));
		properties.put(Context.URL_PKG_PREFIXES, bundleManager.getProperty(Context.URL_PKG_PREFIXES));
		properties.put(Context.PROVIDER_URL, bundleManager.getProperty(Context.PROVIDER_URL));
		return properties;
	}

	public void sendAsyncMailOnCommit(final MailDTO mail) {
		if (mail == null) {
			throw new IllegalArgumentException();
		}

		log.debug("sendAsyncMail(Mail mail)::started");

		try {
			final Transaction tx = new JBossTransactionManagerLookup()
					.getTransactionManager(getProperties()).getTransaction();

			if (null == tx) {
				return;
			}

			tx.registerSynchronization(new Synchronization() {

				public void beforeCompletion() {

				}

				public void afterCompletion(int status) {
					MailTemplateManager mailTemplateManager = new MailTemplateManager();
					try {
						mailTemplateManager.sendMail(mail);
					} catch (Exception e) {
						log.error(" - Could not send the message: ", e);
					} 
				}
			});
		} catch (Exception e) {
			log.error(" - Could not send the message: ", e);
		}
	}

	private Properties getProperties() {
		return properties;
	}
}
