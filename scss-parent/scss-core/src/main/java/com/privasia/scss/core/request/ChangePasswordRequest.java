package com.privasia.scss.core.request;

import org.apache.commons.lang3.StringUtils;

public class ChangePasswordRequest {

  private long userId = 0;

  private String secret = StringUtils.EMPTY;

  private String newSecret = StringUtils.EMPTY;

  private String confirmSecret = StringUtils.EMPTY;

  private String language = StringUtils.EMPTY;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getConfirmSecret() {
    return confirmSecret;
  }

  public void setConfirmSecret(String confirmSecret) {
    this.confirmSecret = confirmSecret;
  }

  public String getNewSecret() {
    return newSecret;
  }

  public void setNewSecret(String newSecret) {
    this.newSecret = newSecret;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }


}
