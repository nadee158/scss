package com.privasia.scss.common.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {


  private static final long serialVersionUID = 1L;


  private String authority;



  public Authority(String authority) {
    super();
    this.authority = authority;
  }

  public Authority() {
    super();
  }

  @Override
  public String getAuthority() {
    return this.authority;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Authority))
      return false;

    Authority ua = (Authority) obj;
    return ua.getAuthority() == this.getAuthority()
        || ua.getAuthority().equals(this.getAuthority());
  }

  @Override
  public int hashCode() {
    return getAuthority() == null ? 0 : getAuthority().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + ": " + getAuthority();
  }



}
