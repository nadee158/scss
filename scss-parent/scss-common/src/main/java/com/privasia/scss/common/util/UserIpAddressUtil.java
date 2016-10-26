package com.privasia.scss.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;



public class UserIpAddressUtil {

  public static synchronized String getUserIp(HttpServletRequest request) {
    String remoteAddress = request.getHeader("X-Forwarded-For");
    if (StringUtils.isBlank(remoteAddress)) {
      remoteAddress = request.getRemoteAddr();
    }

    return remoteAddress;
  }

}
