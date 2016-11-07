/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;




/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_LOGIN", uniqueConstraints = {@UniqueConstraint(columnNames = {"LGN_LOGINNAME"})})
public class Login extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_LOGIN")
  @SequenceGenerator(name = "SEQ_SCSS_LOGIN", sequenceName = "LGN_LOGINID_SEQ")
  @Column(name = "LGN_LOGINID_SEQ")
  private Long loginID;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "USR_USERID", nullable = false, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser systemUser;

  @Column(name = "LGN_LOGINNAME")
  private String userName;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ROL_ROLEID", nullable = false, referencedColumnName = "ROL_ROLEID_SEQ")
  private Role role;

  @Column(name = "LGN_USERPWD")
  private String password;

  @Column(name = "PASSWORD_EXPIRY_DATE")
  private LocalDateTime passportExpiryDate;

  public Long getLoginID() {
    return loginID;
  }

  public void setLoginID(Long loginID) {
    this.loginID = loginID;
  }

  public SystemUser getSystemUser() {
    return systemUser;
  }

  public void setSystemUser(SystemUser systemUser) {
    this.systemUser = systemUser;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getPassportExpiryDate() {
    return passportExpiryDate;
  }

  public void setPassportExpiryDate(LocalDateTime passportExpiryDate) {
    this.passportExpiryDate = passportExpiryDate;
  }

}
