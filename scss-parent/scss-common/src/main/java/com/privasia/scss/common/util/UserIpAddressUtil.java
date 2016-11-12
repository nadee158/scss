package com.privasia.scss.common.util;

import java.net.MalformedURLException;
import java.net.URL;

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

  public static String getBaseUrl(HttpServletRequest request) throws MalformedURLException  {
    System.out.println("request.getRequestURL() " + request.getRequestURL());
    System.out.println("request.getLocalPort() " + request.getLocalPort());
    System.out.println("request.getremPort() " + request.getRemotePort());
    System.out.println("request.getserPort() " + request.getServerPort());
    URL requestURL = new URL(request.getRequestURL().toString());
	String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
	return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
	
  }
}
