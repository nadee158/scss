package com.privasia.scss.core.reponse;

import java.io.Serializable;

import com.privasia.scss.common.security.User;



public class SaveSessionDetailsResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }



}
