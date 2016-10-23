package com.privasia.scss.common.security;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class AuditContext {

  private Date timestamp = null;

  private String clientIPAddress = StringUtils.EMPTY;;

  private String serverIPAddress = StringUtils.EMPTY;;

  private String userAgent = StringUtils.EMPTY;;

  private boolean isMobile = false;

  private String os = StringUtils.EMPTY;

  private String browser = StringUtils.EMPTY;


  AuditContext() {

  }


  public Date getTimestamp() {
    return timestamp;
  }


  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }


  public String getClientIPAddress() {
    return clientIPAddress;
  }


  public void setClientIPAddress(String clientIPAddress) {
    this.clientIPAddress = clientIPAddress;
  }


  public String getServerIPAddress() {
    return serverIPAddress;
  }


  public void setServerIPAddress(String serverIPAddress) {
    this.serverIPAddress = serverIPAddress;
  }


  public String getUserAgent() {
    return userAgent;
  }


  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }


  public boolean isMobile() {
    return isMobile;
  }


  public void setMobile(boolean isMobile) {
    this.isMobile = isMobile;
  }


  public String getOs() {
    return os;
  }


  public void setOs(String os) {
    this.os = os;
  }


  public String getBrowser() {
    return browser;
  }


  public void setBrowser(String browser) {
    this.browser = browser;
  }


  @Override
  public String toString() {
    return "AuditContext [timestamp=" + timestamp + ", clientIPAddress=" + clientIPAddress
        + ", serverIPAddress=" + serverIPAddress + ", userAgent=" + userAgent + ", isMobile="
        + isMobile + ", os=" + os + ", browser=" + browser + "]";
  }


}
