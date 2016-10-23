package com.privasia.scss.common.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;


import org.apache.log4j.Logger;

public class PropFileUtil {
	
	private static final Logger LOGGER = Logger.getLogger(PropFileUtil.class);
	private static Map<String, Properties> fileNamePropertiesMapping = new HashMap<String, Properties>();
	
	
	
	/**
	 * To get all the key value pairs from the property file
	 * @param filePath
	 * @return Properties
	 */
	public static Properties getPropertiesFromFile(String filePath) {

		Properties properties = new Properties();
		
		if (fileNamePropertiesMapping.containsKey(filePath)) {
			properties = fileNamePropertiesMapping.get(filePath);
		} else {
			try {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(filePath);
				LOGGER.debug("loaded local Resource Bundle File:" + filePath);
				String key = null;
				if (resourceBundle != null) {
					Enumeration<String> localenum = resourceBundle.getKeys();
					while (localenum.hasMoreElements()) {
						key = localenum.nextElement();
						properties.put(key, resourceBundle.getString(key));
					}
				}
				
				fileNamePropertiesMapping.put(filePath, properties);
				
			} catch (MissingResourceException ex) {
				LOGGER.fatal("could not find file:" + ex);
			}
		}
		
		return properties;
	}
	
	/**
	 * To get the value of a particular key from a specified properties file
	 * @param filePath
	 * @param key
	 * @return the value
	 */
	public static String getValueFromFile(String filePath, String key) {
		return getPropertiesFromFile(filePath).getProperty(key);
	}
}
