package com.privasia.scss.common.util;



import org.apache.commons.lang3.StringUtils;


public class LPS {

  public static String parseResponseByWeb(String res) {
    if (res.equalsIgnoreCase(LPS.RES_OPEN)) {
      return CommonUtil.getMessageCode("CON_MSG_006");
    } else if (res.equalsIgnoreCase(LPS.RES_MANUAL_CONTROL_MODE)) {
      return CommonUtil.getMessageCode("ERR_MSG_058");
    } else if (res.equalsIgnoreCase(LPS.RES_TIME_OUT)) {
      return CommonUtil.getMessageCode("ERR_MSG_059");
    } else if (res.equalsIgnoreCase(LPS.RES_FAIL)) {
      return CommonUtil.getMessageCode("ERR_MSG_057");
    }
    return "???";
  }

  public static String parseResponseByCustom(String res) {
    if (StringUtils.isNotBlank(res)) {
      if (res.equalsIgnoreCase(LPS.RES_CHECK)) {
        return "CHECKED";
      } else if (res.equalsIgnoreCase(LPS.RES_OPEN)) {
        return "OPENED";
      } else if (res.equalsIgnoreCase(LPS.RES_MANUAL_CONTROL_MODE)) {
        return "MANUAL CONTROL MODE";
      } else if (res.equalsIgnoreCase(LPS.RES_TIME_OUT)) {
        return "TIME OUT";
      } else if (res.equalsIgnoreCase(LPS.RES_FAIL)) {
        return "FAILED";
      }
    }
    return "PENDING";
  }

  public static final String RES_CHECK = "C"; // WPTSCSSSUP-464 To add checked status
  public static final String RES_CHECK_SUPERVISOR = "S"; // WPTSCSSSUP-464 To add checked status
  public static final String RES_OPEN = "O";
  public static final String RES_MANUAL_CONTROL_MODE = "M"; // Manual Control Mode
  public static final String RES_TIME_OUT = "T";
  public static final String RES_FAIL = "F";
  public static final String RES_WAIT = "W";
  public static final String RES_CUS_CHECKED = "A"; // WPTSCSSSUP-464 To add checked status
}
