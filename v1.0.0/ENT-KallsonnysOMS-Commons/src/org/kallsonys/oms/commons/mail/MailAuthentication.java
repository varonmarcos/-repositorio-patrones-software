package org.kallsonys.oms.commons.mail;

import java.io.Serializable;

public class MailAuthentication implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String user;
	
	private String password;
	
	private String host;
	private String starttls = null;
	private String port = null;
	private String auth = null;
	private String socketclass = null;


	public MailAuthentication() {
	}

	
	public MailAuthentication(String user, String password, String host) {
		setUser(user);
		setPassword(password);
		setHost(host);
	}

	
	public void setUser(String user) {
		this.user = user;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	
	public void setHost(String hostName) {
		this.host = hostName;
	}

	
	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

	public String getHost() {
		return this.host;
	}

	public String getStarttls() {
		return starttls;
	}

	public void setStarttls(String starttls) {
		this.starttls = starttls;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getSocketclass() {
		return socketclass;
	}

	public void setSocketclass(String socketclass) {
		this.socketclass = socketclass;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("== Propiedades de autenticacion de correo");
		buf.append("\t+ Host:" + host);
		buf.append("\t+ User:" + user);
		buf.append("\t+ Password:" + password);
		buf.append("\t+ Starttls:" + starttls);
		buf.append("\t+ Socket Class:" + socketclass);
		buf.append("\t+ Port:" + port);
		buf.append("\t+ Auth:" + auth);
		return buf.toString();
	}
}
