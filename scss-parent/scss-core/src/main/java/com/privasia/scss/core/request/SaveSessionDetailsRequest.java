package com.privasia.scss.core.request;

import java.io.Serializable;

import com.privasia.scss.common.security.Session;



public class SaveSessionDetailsRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Session session;

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }


}
