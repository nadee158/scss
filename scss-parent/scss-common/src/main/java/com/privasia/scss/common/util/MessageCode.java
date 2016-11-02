package com.privasia.scss.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

public class MessageCode {

  private MessageCode() {
    String filename = "MessageCode.properties";

    hashMsgCode = new Hashtable();

    Properties p = new Properties();
    // loading from current directory of this class
    InputStream is = getClass().getResourceAsStream(filename);
    try {
      p.load(is);
    } catch (IOException ioe) {
      // logger.warning("Could not load " + filename + ": " + ioe.getMessage());
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


  public static String format(String msgcode, Object[] args) {
    if (_instance == null) {
      _instance = new MessageCode();
    }
    String msg = (String) _instance.hashMsgCode.get(msgcode);

    if (msg != null) {
      msg = MessageFormat.format(msg, args);
    }

    return msg;
  }

  public static String get(String msgcode) {
    return MessageCode.format(msgcode, null);
  }


  static private MessageCode _instance = null;

  private Logger logger = Logger.getLogger(this.getClass().getName());
  private Hashtable hashMsgCode;

}
