package com.privasia.scss.core.security.model;

/**
 * @author Janaka
 *
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {

  private final String username;
  private final List<GrantedAuthority> authorities;
  private final List<Long> functions;

  private UserContext(String username, List<GrantedAuthority> authorities, List<Long> functions) {
    this.username = username;
    this.authorities = authorities;
    this.functions = functions;
  }

  public static UserContext create(String username, List<GrantedAuthority> authorities, List<Long> functions) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException("Username is blank: " + username);
    return new UserContext(username, authorities, functions);
  }

  public String getUsername() {
    return username;
  }

  public List<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public List<Long> getFunctions() {
    return functions;
  }

}
