package com.privasia.scss.common.util;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public final class CommonUtil {

  private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);
  private static Map<String, Properties> fileNamePropertiesMapping = new HashMap<String, Properties>();


  public static final String UPPER_CASE = "U";
  public static final String LOWER_CASE = "L";
  private static final AtomicLong LAST_TIME_MS = new AtomicLong();

 

  public static String formatMessageCode(String msgcode, Object[] args) {

    String filePath = "message_code.properties";
    String msg = (String) getValueFromFile(filePath, msgcode);

    if (msg != null) {
      msg = MessageFormat.format(msg, args);
    }

    return msg;
  }

  public static String getMessageCode(String msgcode) {
    return CommonUtil.formatMessageCode(msgcode, null);
  }

  /**
   * To get all the key value pairs from the property file
   * 
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
   * 
   * @param filePath
   * @param key
   * @return the value
   */
  public static String getValueFromFile(String filePath, String key) {
    return getPropertiesFromFile(filePath).getProperty(key);
  }

  // No initialization allowed
  private CommonUtil() {

  }



  public static String changeCase(String str, String caseStr) {
    if (StringUtils.isNotEmpty(str)) {
      switch (caseStr) {
        case LOWER_CASE:
          return StringUtils.lowerCase(str);
        case UPPER_CASE:
          return StringUtils.upperCase(str);
        default:
          break;
      }
    }
    return null;
  }



  /**
   * @return 13 character length unique long
   */
  public static long getUniqueID() {
    long now = System.currentTimeMillis();
    while (true) {
      long lastTime = LAST_TIME_MS.get();
      if (lastTime >= now)
        now = lastTime + 1;
      if (LAST_TIME_MS.compareAndSet(lastTime, now))
        return now;
    }
  }

}
