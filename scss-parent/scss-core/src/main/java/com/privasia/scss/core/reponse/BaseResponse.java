package com.privasia.scss.core.reponse;

import java.io.Serializable;

public class BaseResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected int status;

  protected String message;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }



}
