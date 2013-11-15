package org.kallsonys.oms.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.kallsonys.oms.commons.Exception.OMSException;

public class ImageFileUtil {

	public static File CreateTempImageFile(byte[] content) {
        File tempfile = null;
        try {
            tempfile = java.io.File.createTempFile("productImage", ".tmp");
            FileOutputStream output = new FileOutputStream(tempfile);
            IOUtils.write(content, output);
        } catch (IOException ex) {
           ex.printStackTrace();
           throw new OMSException("0003", "ERROR CREADO EL ARCHIVO TEMPORAL DE LA IMAGEN", ex);
        }
        return tempfile;
    }

}
