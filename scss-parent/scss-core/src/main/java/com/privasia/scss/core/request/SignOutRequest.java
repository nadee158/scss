package com.privasia.scss.core.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class SignOutRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userName = StringUtils.EMPTY;
  private String securityUserId = StringUtils.EMPTY;


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSecurityUserId() {
    return securityUserId;
  }

  public void setSecurityUserId(String securityUserId) {
    this.securityUserId = securityUserId;
  }

  @Override
  public String toString() {
    return "SignOutRequest [userName=" + userName + ", securityUserId=" + securityUserId + "]";
  }



}
