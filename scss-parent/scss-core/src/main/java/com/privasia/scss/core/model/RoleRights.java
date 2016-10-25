/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;



/**
 * @author Janaka
 *
 */
//@Entity
//@Table(name = "SCSS_ROLE_RIGHTS")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class RoleRights extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @EmbeddedId
  private RoleRightsPK roleRightsID;


  public RoleRightsPK getRoleRightsID() {
    return roleRightsID;
  }


  public void setRoleRightsID(RoleRightsPK roleRightsID) {
    this.roleRightsID = roleRightsID;
  }



}
