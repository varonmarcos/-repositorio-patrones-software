package org.kallsonys.oms.commons.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ResourceBundleManager {

	/**
	 * Constante que almacena el nombre calificado del archivo de propiedades.
	 */
	private String propertiesPath;

	/**
	 * Mapa que almacena las propiedades del archvio.
	 */
	private static Map<String, String> properties = new HashMap<String, String>();
	

	public ResourceBundleManager(String propertiesPath) {
		this.propertiesPath = propertiesPath;
		loadProperties();
	}

	/**
	 * Metodo que permite obtener el valor de una propiedad dado su
	 * identificador.
	 * 
	 * @param key -
	 *            id asociado al valor.
	 * @return valor asociado al key
	 */
	public String getProperty(String key) {
		
		if (properties.get(key) == null) {
			loadProperties();
		}
		return properties.get(key);

	}

	/**
	 * Metodo que establece las propiedades del archvio en el mapa
	 * <code>properties</code>
	 */
	private void loadProperties() {

		properties = new HashMap<String, String>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle(propertiesPath);

		if (resourceBundle != null) {
			Enumeration<String> keys = resourceBundle.getKeys();

			String key = null;
			String value = null;
			while (keys.hasMoreElements()) {
				key = keys.nextElement();
				value = resourceBundle.getString(key);
				properties.put(key, value);
			}
		}
	}

	public  String getPropertiesPath() {
		return propertiesPath;
	}

	public  void setPropertiesPath(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}
}