package com.privasia.scss.common.security;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class Session implements Authentication {

  // 30 mins
  public static final long EXPIRY_PERIOD = ((1000 * 60) * 30);

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private UUID id = null;

  private String token = StringUtils.EMPTY;

  private long ownerId = 0;

  private Date startTimestamp = null;

  private Date lastRequestTimestamp = null;

  private boolean isAuthenticated = false;

  private long expires;
  
  private User user;


  public Session(long ownerId) {
    this.id = UUID.randomUUID();
    this.ownerId = ownerId;
    this.startTimestamp = Calendar.getInstance().getTime();
    this.lastRequestTimestamp = Calendar.getInstance().getTime();
    this.expires = EXPIRY_PERIOD;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public Date getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(Date startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public Date getLastRequestTimestamp() {
    return lastRequestTimestamp;
  }

  public void setLastRequestTimestamp(Date lastRequestTimestamp) {
    this.lastRequestTimestamp = lastRequestTimestamp;
  }

  @Override
  public String getName() {
    return this.user.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return this.getToken();
  }

  @Override
  public Object getDetails() {
    return this.id.toString();
  }

  @Override
  public Object getPrincipal() {
    return this.ownerId;
  }

  @Override
  public boolean isAuthenticated() {
    return this.isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  public long getExpires() {
    return expires;
  }

  public void setExpires(long expires) {
    this.expires = expires;
  }

  @Override
  public String toString() {
    return "Session [id=" + id + ", token=" + token + ", ownerId=" + ownerId + ", startTimestamp=" + startTimestamp
        + ", lastRequestTimestamp=" + lastRequestTimestamp + ", isAuthenticated=" + isAuthenticated + ", expires="
        + expires + "]";
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }



}
