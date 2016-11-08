package com.privasia.scss.core.request;

import org.apache.commons.lang3.StringUtils;

public class ResetPasswordRequest {


  private String userName = StringUtils.EMPTY;

  private String email = StringUtils.EMPTY;

  private String language = StringUtils.EMPTY;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }



}
