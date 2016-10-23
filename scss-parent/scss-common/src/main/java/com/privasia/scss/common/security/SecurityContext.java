package com.privasia.scss.common.security;

public class SecurityContext {

  private String token = "";

  public String getToken() {
    return token;
  }

  private void setToken(String token) {
    this.token = token;
  }

  SecurityContext(String token) {
    this.setToken(token);
  }

  @Override
  public String toString() {
    return "SecurityContext [token=" + token + "]";
  }


}
