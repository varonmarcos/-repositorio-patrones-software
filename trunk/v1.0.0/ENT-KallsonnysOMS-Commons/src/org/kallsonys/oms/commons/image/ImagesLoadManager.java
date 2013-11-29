package org.kallsonys.oms.commons.image;

import org.apache.log4j.Logger;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.commons.util.ImageFileUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ImagesLoadManager {

	final static Logger log = Logger.getLogger(ImagesLoadManager.class);

//	private static final String PROPERTIES_PATH = "KallsonnysOMS-GoogleDrive";
//	private static final String CLIENT_ID = "googeldrive.clientid";
//	private static final String CERTIFICATE_PATH = "googeldrive.certificate.path";

	//private final ResourceBundleManager bundleManager;

	private static ImagesLoadManager instance;
	
	List<String> url_full_List;
	List<String> url_thumb_List;

	private ImagesLoadManager() {
		//bundleManager = new ResourceBundleManager(PROPERTIES_PATH);
		
		url_full_List = new ArrayList<String>();
		url_full_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SUmQ5OGZOTlBTQnc/");
		url_full_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SNmtLSmQzMk9oN3M/");
		url_full_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SZ25wWUlUc3NGd28/");
		url_full_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SZEY2SGgzZ1NQS3M/");
		url_full_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SSmlGeU1QUmJYNm8/");
		
		url_thumb_List = new ArrayList<String>();
		url_thumb_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SOHhvYWRCckRiQ0U/");
		url_thumb_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SN2t1MldiZlBFRGs/");
		url_thumb_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SaFRIdXpjMmNtN00/");
		url_thumb_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SM0lYMjFDaVlJVms/");
		url_thumb_List.add("https://googledrive.com/host/0B-NmAN9xQQ4SOG4zcXRtczZobkU/");
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
	            return url.substring(0,url.indexOf('&'));
	        } catch (Exception ex) {
	        	 ex.printStackTrace();
	             throw new OMSException("0003", "ERROR INESPERADO EN LA CARGA DE LA IMAGEN", ex);
	        }
	    }

	public String loadRadomImage(boolean tumbnail) {

		String url;

		int radomNum = (int) (Math.random() * 4 + 1);

		if (tumbnail) {
			url = url_thumb_List.get(radomNum);
		} else {
			url = url_full_List.get(radomNum);
		}

		return url;

	}

	private Drive getDriveService() throws GeneralSecurityException,
			IOException, URISyntaxException {
		  HttpTransport httpTransport = new NetHttpTransport();
	        JacksonFactory jsonFactory = new JacksonFactory();
	        GoogleCredential credential = new GoogleCredential.Builder()
	                .setTransport(httpTransport)
	                .setJsonFactory(jsonFactory)
	                .setServiceAccountId("92336941260-pqo23pavqkh4cbrd6jkthl8dcnvteaq9@developer.gserviceaccount.com")
	                .setServiceAccountScopes(Arrays.asList(DriveScopes.DRIVE))
	                .setServiceAccountPrivateKeyFromP12File(
	                new java.io.File("/usr/local/conf/2929786402b0915386897966b034e6cce287cfcb-privatekey.p12"))
	                .build();
	        Drive service = new Drive.Builder(httpTransport, jsonFactory, null)
	                .setHttpRequestInitializer(credential).setApplicationName("KallsonysOMS").build();
	        return service;
	}
}
