package org.kallsonys.oms.commons.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TemplatesLoaderUtil {
	
	private static final String TEMPLATES_DIR = "/usr/local/conf/templates/";
	

	public static String readFileAsString(String filePath) {

		try {
			StringBuffer fileData = new StringBuffer();
			BufferedReader reader = new BufferedReader(new FileReader(TEMPLATES_DIR+filePath));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
			}
			reader.close();
			return fileData.toString();

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
