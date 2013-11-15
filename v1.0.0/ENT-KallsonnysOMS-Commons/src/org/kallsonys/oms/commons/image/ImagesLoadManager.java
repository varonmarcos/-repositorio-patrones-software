package org.kallsonys.oms.commons.image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.ImageFileUtil;
import org.kallsonys.oms.commons.util.ResourceBundleManager;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

public class ImagesLoadManager {

	final static Logger log = Logger.getLogger(ImagesLoadManager.class);

	private static final String PROPERTIES_PATH = "KallsonnysOMS-GoogleDrive.properties";
	private static final String CLIENT_ID = "googeldrive.clientid";
	private static final String CERTIFICATE_PATH = "googeldrive.certificate.path";

	private final ResourceBundleManager bundleManager;

	private static ImagesLoadManager instance;

	private ImagesLoadManager() {
		bundleManager = new ResourceBundleManager(PROPERTIES_PATH);
	}

	public static ImagesLoadManager getInstance() {
		if (instance == null) {
			instance = new ImagesLoadManager();
		}
		return instance;
	}
	
	 public String uploadJPG(byte[] content, String productName) {
	        try {
	            java.io.File tempFile = ImageFileUtil.CreateTempImageFile(content);

	            Drive service = getDriveService();

	            File body = new File();
	            body.setShared(Boolean.TRUE);
	            body.setTitle(productName);
	            body.setDescription("Kallsonys Product Image");
	            body.setMimeType("image/jpeg");

	            java.io.File fileContent = tempFile;
	            FileContent mediaContent = new FileContent("image/jpeg", fileContent);
	            File file = service.files().insert(body, mediaContent).execute();

	            Permission newPermission = new Permission();

	            newPermission.setValue("me");
	            newPermission.setType("anyone");
	            newPermission.setRole("reader");


	            service.permissions().insert(file.getId(), newPermission).execute();

	            System.out.println("File ID: " + file.getId());
	            String url = file.getWebContentLink();
	            System.out.println("File url: " + url);
	            return url;
	        } catch (Exception ex) {
	        	 ex.printStackTrace();
	             throw new OMSException("0003", "ERROR INESPERADO EN LA CARGA DE LA IMAGEN", ex);
	        }
	    }


	private Drive getDriveService() throws GeneralSecurityException,
			IOException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(httpTransport)
				.setJsonFactory(jsonFactory)
				.setServiceAccountId(bundleManager.getProperty(CLIENT_ID))
				.setServiceAccountScopes(Arrays.asList(DriveScopes.DRIVE))
				.setServiceAccountPrivateKeyFromP12File(
						new java.io.File(bundleManager.getProperty(CERTIFICATE_PATH)))
				.build();
		Drive service = new Drive.Builder(httpTransport, jsonFactory, null)
				.setHttpRequestInitializer(credential).build();
		return service;
	}
}
