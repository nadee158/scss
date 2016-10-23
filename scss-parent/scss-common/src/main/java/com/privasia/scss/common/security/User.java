package com.privasia.scss.common.security;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  private long id = 0;

  private String staffNumber = StringUtils.EMPTY;

  private String name = StringUtils.EMPTY;

  private String gender = StringUtils.EMPTY;

  private LocalDateTime dateOfBirth = null;

  private String passportNo = StringUtils.EMPTY;

  private LocalDateTime passportExpireDate = null;

  private String nationality = StringUtils.EMPTY;

  private String department = StringUtils.EMPTY;

  private String userName = StringUtils.EMPTY;

  private String secret = StringUtils.EMPTY;

  private String userType = StringUtils.EMPTY;

  private Set<Authority> authorities = new HashSet<Authority>();

  private boolean accountExpired;

  private boolean accountLocked;

  private boolean credentialsExpired;

  private boolean accountEnabled;

  private List<String> activeSessions;

  private int activeSessionCount;


  public User(long id, String staffNumber, String name, String gender, LocalDateTime dateOfBirth, String passportNo,
      LocalDateTime passportExpireDate, String nationality, String department, String userName, String secret,
      String userType, Set<Authority> authorities, boolean accountExpired, boolean accountLocked,
      boolean credentialsExpired, boolean accountEnabled) {
    super();
    this.id = id;
    this.staffNumber = staffNumber;
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.passportNo = passportNo;
    this.passportExpireDate = passportExpireDate;
    this.nationality = nationality;
    this.department = department;
    this.userName = userName;
    this.secret = secret;
    this.userType = userType;
    this.authorities = authorities;
    this.accountExpired = accountExpired;
    this.accountLocked = accountLocked;
    this.credentialsExpired = credentialsExpired;
    this.accountEnabled = accountEnabled;
  }



  private User() {
    super();
  }



  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @JsonIgnore
  public long getId() {
    return id;
  }

  public String getSecret() {
    return secret;
  }

  @JsonIgnore
  public boolean isAccountExpired() {
    return accountExpired;
  }

  @JsonIgnore
  public boolean isAccountLocked() {
    return accountLocked;
  }

  @JsonIgnore
  public boolean isCredentialsExpired() {
    return credentialsExpired;
  }

  @JsonIgnore
  public boolean isAccountEnabled() {
    return accountEnabled;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public String getPassword() {
    return this.secret;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return (!(this.accountExpired));
  }

  @Override
  public boolean isAccountNonLocked() {
    return (!(this.accountLocked));
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return (!(this.credentialsExpired));
  }

  @Override
  public boolean isEnabled() {
    return this.accountEnabled;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + ": " + getUsername();
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  @JsonIgnore
  public List<String> getActiveSessions() {
    return activeSessions;
  }

  public void setActiveSessions(List<String> activeSessions) {
    this.activeSessions = activeSessions;
  }

  @JsonIgnore
  public int getActiveSessionCount() {
    return activeSessionCount;
  }

  public void setActiveSessionCount(int activeSessionCount) {
    this.activeSessionCount = activeSessionCount;
  }



  public String getStaffNumber() {
    return staffNumber;
  }



  public void setStaffNumber(String staffNumber) {
    this.staffNumber = staffNumber;
  }



  public String getName() {
    return name;
  }



  public void setName(String name) {
    this.name = name;
  }



  public String getGender() {
    return gender;
  }



  public void setGender(String gender) {
    this.gender = gender;
  }



  public LocalDateTime getDateOfBirth() {
    return dateOfBirth;
  }



  public void setDateOfBirth(LocalDateTime dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }



  public String getPassportNo() {
    return passportNo;
  }



  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }



  public LocalDateTime getPassportExpireDate() {
    return passportExpireDate;
  }



  public void setPassportExpireDate(LocalDateTime passportExpireDate) {
    this.passportExpireDate = passportExpireDate;
  }



  public String getNationality() {
    return nationality;
  }



  public void setNationality(String nationality) {
    this.nationality = nationality;
  }



  public String getDepartment() {
    return department;
  }



  public void setDepartment(String department) {
    this.department = department;
  }



  public void setId(long id) {
    this.id = id;
  }



  public void setAccountExpired(boolean accountExpired) {
    this.accountExpired = accountExpired;
  }



  public void setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
  }



  public void setCredentialsExpired(boolean credentialsExpired) {
    this.credentialsExpired = credentialsExpired;
  }



  public void setAccountEnabled(boolean accountEnabled) {
    this.accountEnabled = accountEnabled;
  }



  public String getUserType() {
    return userType;
  }



  public void setUserType(String userType) {
    this.userType = userType;
  }



}
