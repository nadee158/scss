package com.privasia.scss.common.service.export;

import org.apache.commons.lang3.StringUtils;

public class ExportUtilService {

  public static String getReferTempSign(Double value) {
    if (!(value == null)) {
      if (value > 0) {
        return "+";
      } else if (value < 0) {
        return "-";
      } else {
        return "";
      }
    }
    return "";
  }

  public static String getStringRepresentationOfBoolean(Boolean value) {

    if (value != null && value) {
      return "Y";
    } else {
      return "N";
    }

  }

  public static String getSolasVGMType(String vgmType) {

    if (vgmType != null) {
      if (StringUtils.equalsIgnoreCase("SHIPPER VGM", vgmType)) {
        return "S";
      } else if (StringUtils.equalsIgnoreCase("TERMINAL VGM", vgmType)) {
        return "T";
      } else {
        return "";
      }
    } else {
      return "";
    }
  }


  public static String getStringFromBoolean(Boolean bool) {
    if (!(bool == null)) {
      if (bool) {
        return "Y";
      } else {
        return "N";
      }
    }
    return null;
  }

  public static int getIntValueFromString(String stringVal) {
    if (StringUtils.isNotEmpty(stringVal)) {
      return Integer.parseInt(stringVal);
    }
    return 0;
  }

  public static String getStringValueFromInteger(Integer integerVal) {
    if (!(integerVal == null || integerVal == 0)) {
      return Integer.toString(integerVal);
    }
    return null;
  }

  public static int getIntegerValueFromString(String strValue) {
    if (StringUtils.isNotEmpty(strValue)) {
      try {
        return Integer.parseInt(strValue);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 0;
  }
  

  public static Boolean getBooleanFromString(String string) {
    if (StringUtils.isNotEmpty(string)) {
      if (StringUtils.equals(string, "N")) {
        return false;
      } else {
        return true;
      }
    }
    return null;
  }

  public static String getStringRepresentationOfBooleanTF(Boolean value) {
    if (value != null && value) {
      return "T";
    } else {
      return "F";
    }
  }

  public static Double getDoubleValueFromString(String strValue) {
    if (StringUtils.isNotEmpty(strValue)) {
      try {
        return Double.parseDouble(strValue);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 0d;
  }

}
