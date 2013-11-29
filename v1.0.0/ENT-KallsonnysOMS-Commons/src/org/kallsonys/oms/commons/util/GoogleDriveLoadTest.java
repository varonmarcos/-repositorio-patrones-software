package org.kallsonys.oms.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.kallsonys.oms.commons.image.ImagesLoadManager;

public class GoogleDriveLoadTest {

	public static void main(String[] args) {

		File archivo = null;
	

		try {
		
			archivo= new File("/Users/macbook/Desktop/estabilizador_full.jpg");
			InputStream inputStream = new FileInputStream(archivo);
			byte[] bytes = new byte[(int) archivo.length()];
			inputStream.read(bytes);
			
			ImagesLoadManager.getInstance().uploadJPG(bytes, "test1");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

}
