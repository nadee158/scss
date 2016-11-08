package com.privasia.scss.core.reponse;

import java.io.Serializable;



public class SignOutResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private int statusCode;

  private String message;

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "SignOutResponse [statusCode=" + statusCode + ", message=" + message + "]";
  }



}
