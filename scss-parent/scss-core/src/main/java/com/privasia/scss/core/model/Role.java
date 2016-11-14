/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.RoleType;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_ROLE", uniqueConstraints = {@UniqueConstraint(columnNames = "ROL_NAME")})
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE"))})
public class Role extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_ROLE")
  @SequenceGenerator(name = "SEQ_SCSS_ROLE", sequenceName = "ROL_ROLEID_SEQ")
  @Column(name = "ROL_ROLEID_SEQ")
  private Long roleID;

  @Column(name = "ROL_NAME")
  private String roleName;

  @Column(name = "ROL_TYPE")
  @Type(type = "com.privasia.scss.core.util.enumusertype.RoleTypeEnumUserType")
  private RoleType roleType;

  @Column(name = "ROL_SUPERVISOR_FLAG")
  @Type(type = "yes_no")
  private boolean supervisorFlag;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "roleRightsID.role")
  private Set<RoleRights> roleRights;

  public Long getRoleID() {
    return roleID;
  }

  public void setRoleID(Long roleID) {
    this.roleID = roleID;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }

  public boolean isSupervisorFlag() {
    return supervisorFlag;
  }

  public void setSupervisorFlag(boolean supervisorFlag) {
    this.supervisorFlag = supervisorFlag;
  }

public Set<RoleRights> getRoleRights() {
	return roleRights;
}

public void setRoleRights(Set<RoleRights> roleRights) {
	this.roleRights = roleRights;
}

}
