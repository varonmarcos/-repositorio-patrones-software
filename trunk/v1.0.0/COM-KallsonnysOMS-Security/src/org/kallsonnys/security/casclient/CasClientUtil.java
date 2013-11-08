package org.kallsonnys.security.casclient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.kallsonnys.oms.dto.security.CasTokensDTO;
import org.kallsonys.oms.commons.util.ResourceBundleManager;

public class CasClientUtil {
	
	private static final Logger logger = Logger.getLogger(CasClientUtil.class);
	
	String cas_base_url;
	
	String cas_rest_url;
	
	String service_url;
	
	String cas_validate_url;
	
	private ResourceBundleManager bundleManager = new ResourceBundleManager("KallsonnysOMS-Security");
	
	public CasClientUtil() {
		this.cas_base_url = bundleManager.getProperty("cas_base_url");
		this.cas_rest_url = cas_base_url + bundleManager.getProperty("cas_rest_url");
		this.cas_validate_url = cas_base_url + bundleManager.getProperty("cas_validate_url");
		this.service_url = bundleManager.getProperty("service_url");
	}
	
	public Boolean validateUserSesion(String srvTicket){
		
		final HttpClient client = new HttpClient();
		
		final PostMethod post = new PostMethod(cas_validate_url);
		
		post.setRequestBody(new NameValuePair[] {
				new NameValuePair("ticket", srvTicket),
				new NameValuePair("service", cas_validate_url) });
		
		Boolean validated = null;
		try {
			client.executeMethod(post);

			final String response = post.getResponseBodyAsString();

			switch (post.getStatusCode()) {
			case 200: 
				validated = Boolean.TRUE;
				logger.info("Successful ticket validating request");
				logger.info("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
				
			case 400:
				validated = Boolean.FALSE;
				logger.info("UnSuccessful ticket validating request");
				logger.info("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));

			default:
				
				validated = Boolean.FALSE;
				
				logger.info("Invalid response code (" + post.getStatusCode()
						+ ") from CAS server!");
				logger.info("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}
		} catch (final IOException e) {
			logger.error("Un error ha ocurrido al validar el ticket de sesión");
		} finally {
			post.releaseConnection();
		}
			
		return validated;
	}

	public CasTokensDTO grantingUserSessionTicket(String username, String password) {
		
		final HttpClient client = new HttpClient();
	
		PostMethod post = new PostMethod(cas_rest_url);

		post.setRequestBody(new NameValuePair[] {
				new NameValuePair("username", username),
				new NameValuePair("password", password) });

		String tgt = "";
		try {
			client.executeMethod(post);

			final String response = post.getResponseBodyAsString();

			switch (post.getStatusCode()) {
			case 201: {
				final Matcher matcher = Pattern.compile(
						".*action=\".*/(.*?)\".*").matcher(response);

				if (matcher.matches()) {
					tgt = matcher.group(1);
				}
				logger.info("Successful ticket granting request, but no ticket found!");
				logger.info("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}

			default:
				logger.info("Invalid response code (" + post.getStatusCode()
						+ ") from CAS server!");
				logger.info("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}
		} catch (final IOException e) {
			logger.error("Un error ha ocurrido al obtener el ticket de sesión");
		} finally {
			post.releaseConnection();
		}
		
		
		post = new PostMethod(cas_rest_url + tgt);

		post.setRequestBody(new NameValuePair[] { new NameValuePair("service",
				service_url) });

		String srvTicket = "";
		try {
			client.executeMethod(post);

			final String response = post.getResponseBodyAsString();

			switch (post.getStatusCode()) {
			case 200:
				srvTicket = response;
				
				System.out
						.println("Successful service ticket granting request, but no ticket found!");
				System.out.println("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;

			case 400:
				System.out
						.println("UnSuccessful service ticket granting request, but no ticket found!");
				System.out.println("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;

			default:
				System.out.println("Invalid response code ("
						+ post.getStatusCode() + ") from CAS server!");
				System.out.println("Response (1k): "
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}
		} catch (final IOException e) {
			System.err.println(e.getMessage());
		} finally {
			post.releaseConnection();
		}

		return new CasTokensDTO(tgt, srvTicket);
	}

}
