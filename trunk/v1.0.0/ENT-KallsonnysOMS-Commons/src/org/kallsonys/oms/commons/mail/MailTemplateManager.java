package org.kallsonys.oms.commons.mail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapterImpl;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.kallsonnys.oms.dto.MailDTO;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.ResourceBundleManager;
import org.kallsonys.oms.commons.util.TemplatesLoaderUtil;

public class MailTemplateManager {

	final static Logger log = Logger.getLogger(MailTemplateManager.class);

	

	private final ResourceBundleManager bundleManager;

	public MailTemplateManager() {
		bundleManager = new ResourceBundleManager(MailTemplateConstants.PROPERTIES_PATH);
	}

	public boolean sendMail(final MailDTO mail) throws AddressException,
			MessagingException, IOException {

		// Obtiene los datos para realizar autenticaciÛn
		final MailAuthentication mauth = getMailAuthentication();

		// Crea la sesiÛn java mail
		final Session session = createSession(mauth);

		// Prepara el header del mensaje
		final Message message = prepareHeader(session, mail);

		// Se modifican los parametros para colocar las Otildes en el caso de los
		// parametros en español que lo requieran.
		final Map<String, Object> params = parseParameters(mail.getParamValues());
		
		String mailContent = processContent(mail.getTemplateName(), params);

		message.setSubject(mail.getSubject());

		// Se crea el contenido principal del mensaje
		final MimeMultipart multipart = new MimeMultipart();

		// Adhiere el contenido al menaje
		final MimeBodyPart textpart = new MimeBodyPart();
		textpart.setContent(mailContent, MailTemplateConstants.TEXT_HTML);
		multipart.addBodyPart(textpart);

		// Se setea el contenido del mensaje
		message.setContent(multipart);

		// Setea la fecha de envÌo del correo.
		message.setSentDate(new Date());
		message.saveChanges();

		// EnvÌa el mail
		// Transport.send(message);
		transportMessage(session, message, mauth);

		return true;
	}

	private String processContent(String templateName,
			Map<String, Object> params) {
		
		
        try {
        	
        	RuntimeInstance runtimeInstance = new RuntimeInstance();
			runtimeInstance.init();
			SimpleNode simpleNode = runtimeInstance.parse(TemplatesLoaderUtil.readFileAsString(templateName), "mailTemplate");

			Template t = new Template();
			simpleNode.init(new InternalContextAdapterImpl(new VelocityContext()), runtimeInstance);
			t.setData(simpleNode);
			
			 /*  first, get and initialize an engine  */
	        VelocityEngine ve = new VelocityEngine();
	        ve.init();
	
	        /*  create a context and add data */
	        VelocityContext context = new VelocityContext();
	        for (Entry<String, Object> entry : params.entrySet()) {
	        	 context.put(entry.getKey(), entry.getValue());
			}
	       
	        StringWriter writer = new StringWriter();
	        
	        t.merge( context, writer );
	        
	        return writer.toString();
			
			
		} catch (Exception e) {
			log.error("- Exception trying to send email", e);
			throw new OMSException("");
		}
	
	}

	private void transportMessage(final Session session, final Message msg,
			final MailAuthentication mauth) {

		try {
			final Transport transport = session.getTransport("smtp");
			transport.connect(mauth.getHost(), mauth.getUser(),
					mauth.getPassword());
			transport.sendMessage(msg, msg.getAllRecipients());
			log.info("- '" + msg.getSubject() + "' email sent to "
					+ msg.getAllRecipients().toString());
			transport.close();
		} catch (final MessagingException me) {
			log.error("- Exception trying to send email", me);
			throw new OMSException("");
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> parseParameters(final Map<String, Object> param) {
		final Map<String, Object> e = new HashMap<String, Object>();

		final Iterator iterator = param.entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry entry = (Map.Entry) iterator.next();
			if(entry.getValue() instanceof String){
				e.put((String) entry.getKey(), parse((String) entry.getValue()));				
			}else{
				e.put((String) entry.getKey(), entry.getValue());	
			}
		}

		return e;
	}

	private String parse(final String message) {
		final String[] letters = new String[] { "·", "È", "Ì", "Û", "˙", "¡",
				"…", "Õ", "”", "⁄", "ø", "Ä", "—", "Ò" };
		final String[] le = new String[] { "&aacute;", "&eacute;", "&iacute;",
				"&oacute;", "&uacute;", "&Aacute;", "&Eacute;", "&Iacute;",
				"&Oacute;", "&Uacute;", "&iquest;", "&euro;", "&Ntilde;",
				"&ntilde;" };

		if (message == null) {
			return "";
		}

		final StringBuilder msg = new StringBuilder(message);

		int i = 0;
		for (final String ch : letters) {
			int pos = msg.indexOf(ch);

			while (pos != -1) {
				msg.replace(pos, pos + 1, le[i]);

				pos = msg.indexOf(ch);
			}
			i++;
		}

		return msg.toString();
	}

	private MailAuthentication getMailAuthentication() {
		final String user = bundleManager.getProperty("user");
		final String password = bundleManager.getProperty("password");
		final String host = bundleManager.getProperty("host");
		final String starttls = bundleManager.getProperty("starttls");
		final String port = bundleManager.getProperty("port");
		final String auth = bundleManager.getProperty("auth");
		final String socketclass = bundleManager.getProperty("socketclass");

		final MailAuthentication mauth = new MailAuthentication(user, password, host);
		if (null != starttls)
			mauth.setStarttls(starttls);
		if (null != port)
			mauth.setStarttls(port);
		if (null != auth)
			mauth.setStarttls(auth);
		if (null != socketclass)
			mauth.setStarttls(socketclass);

		return mauth;
	}

	private Message prepareHeader(final Session session, final MailDTO mail)
			throws AddressException, MessagingException {

		final Message message = new MimeMessage(session);
		InternetAddress[] addresses = null;
		List<String> list = null;
		
		String allowedadd = bundleManager.getProperty(MailTemplateConstants.UNIQUE_ALLOWED_ADDRESS);
		if (null != allowedadd && allowedadd.indexOf('@') != -1) {
			
			//se agrega un unico destinatario del correo.
			InternetAddress address = new InternetAddress(allowedadd.trim());
			message.addRecipient(Message.RecipientType.TO, address);
			
			//Se agrega el emisario por defecto para correos restringidos
			String allowedfrom = bundleManager.getProperty(MailTemplateConstants.UNIQUE_ALLOWED_FROM);
			InternetAddress from = new InternetAddress(allowedfrom.trim());
			message.setFrom(from);
			
			//Se agrega el emisor del correo por defecto 
		}else{
			
			// Destinos del correo electrÛnico
			list = mail.getRecipients();

			if (list != null) {
				int i = 0;
				addresses = new InternetAddress[list.size()];

				for (final String recipient : list) {
					final InternetAddress address = new InternetAddress(recipient.trim());
					addresses[i++] = address;
				}
				if (addresses != null && addresses.length > 0) {
					message.addRecipients(Message.RecipientType.TO, addresses);
				}
			}
			// Direcciones email que requieren Copia a (CC)
			list = mail.getCopyTo();

			if (list != null) {
				int i = 0;
				addresses = new InternetAddress[list.size()];

				for (final String copyTo : list) {
					final InternetAddress address = new InternetAddress(copyTo.trim());
					addresses[i++] = address;
				}
				if (addresses != null && addresses.length > 0) {
					message.addRecipients(Message.RecipientType.CC, addresses);
				}
			}
			// Direcciones email que requieren Segunda Copia (BCC)
			list = mail.getSecondCopyTo();

			if (list != null) {
				int i = 0;
				addresses = new InternetAddress[list.size()];

				for (final String secondCopyTo : list) {
					final InternetAddress address = new InternetAddress(
							secondCopyTo.trim());
					addresses[i++] = address;
				}
				if (addresses != null && addresses.length > 0) {
					message.addRecipients(Message.RecipientType.BCC, addresses);
				}
			}
			// Se verifica si hay que enviar copia oculta a los mails de soporte
			// de etask
			if (mail.getBccToSupport()) {
				final String supaddrs = bundleManager.getProperty(MailTemplateConstants.SUPPORT_ADDRESSES);
				if (null != supaddrs && 0 != supaddrs.trim().length()) {

					final StringTokenizer tok = new StringTokenizer(supaddrs, ", ");
					while (tok.hasMoreTokens()) {
						final String supaddr = tok.nextToken();
						message.addRecipient(Message.RecipientType.BCC,
								new InternetAddress(supaddr.trim()));
					}
				}
			}
			// Direcciones email donde se enviar· respuesta
			list = mail.getReplyTo();

			if (list != null) {
				int i = 0;
				addresses = new InternetAddress[list.size()];

				for (final String replyTo : list) {
					final InternetAddress address = new InternetAddress(replyTo.trim());
					addresses[i++] = address;
				}
				if (addresses != null && addresses.length > 0) {
					message.setReplyTo(addresses);
				}
			}
			
			// DirecciÛn de correo electrÛnico desde donde se envÌa el mail.
			if (mail.getFrom() != null) {
				final InternetAddress address = new InternetAddress(bundleManager.getProperty(mail.getFrom()));
				message.setFrom(address);
			}
			
		}

		return message;
	}

	private Session createSession(final MailAuthentication mauth)
			throws NoSuchProviderException {

		Session session = null;
		try {
			final Properties properties = new Properties();

			properties.put("mail.smtp.host", mauth.getHost());
			properties.put("mail.smtp.user", mauth.getUser());
			properties.put("mail.smtp.debug", "true");
			if (null != mauth.getPort())
				properties.put("mail.smtp.port", mauth.getPort());
			if (null != mauth.getAuth())
				properties.put("mail.smtp.auth", mauth.getAuth());
			if (null != mauth.getStarttls())
				properties
						.put("mail.smtp.starttls.enable", mauth.getStarttls());
			if (null != mauth.getSocketclass()) {
				properties.put("mail.smtp.socketFactory.port", mauth.getPort());
				properties.put("mail.smtp.socketFactory.class",
						mauth.getSocketclass());
				properties.put("mail.smtp.socketFactory.fallback", "false");
			}

			session = Session.getDefaultInstance(properties, null);
			session.setDebug(Level.DEBUG.isGreaterOrEqual(log
					.getEffectiveLevel()));

		} catch (final Exception e) {
			log.error("- Error creando la sesiÛn para enviar el mensaje ->"
					+ mauth, e);
			throw new OMSException("");
		}
		return session;
	}

}