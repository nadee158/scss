package com.privasia.scss.common.util;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

public class COSMOSMsgCode {

  private COSMOSMsgCode() {
    String filename = "COSMOSMsgCode.properties";

    hashMsgCode = new Hashtable();

    Properties p = new Properties();
    InputStream is = getClass().getResourceAsStream(filename);
    try {
      p.load(is);
    } catch (Exception e) {
      // logger.warning(e.getMessage());
    }

    Enumeration e = p.propertyNames();
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      String value = p.getProperty(key);
      hashMsgCode.put(key, value);
    }

    /*
     * for debug Enumeration e2 = hashMsgCode.keys(); while (e2.hasMoreElements()) { String key =
     * (String) e2.nextElement(); String value = (String) hashMsgCode.get(key); logger.config(key +
     * "=" + value); }
     */

  }


  public static void main(String[] args) {
    Object[] objs = new Object[] {"YONG", "Kian On"};
  }

  public static String format(String msgcode, Object[] args) {
    if (_instance == null) {
      _instance = new COSMOSMsgCode();
    }
    String msg = (String) _instance.hashMsgCode.get(msgcode);

    if (msg != null) {
      msg = MessageFormat.format(msg, args);
    }

    return msg;
  }

  public static String get(String msgcode) {
    return COSMOSMsgCode.format(msgcode, null);
  }


  static private COSMOSMsgCode _instance = null;

  private Logger logger = Logger.getLogger(this.getClass().getName());

  private Hashtable hashMsgCode;

}
