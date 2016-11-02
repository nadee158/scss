package com.privasia.scss.common.util;

public class ReturnMsg {

  public static String trim(String returnmsg) {
    if (returnmsg.endsWith(SEPARATOR)) {
      returnmsg = returnmsg.substring(0, returnmsg.length() - SEPARATOR.length());
    }
    return returnmsg;
  }

  public final static String SEPARATOR = "<br>";
}
