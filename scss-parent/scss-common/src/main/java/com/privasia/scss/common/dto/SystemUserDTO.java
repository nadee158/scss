/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class SystemUserDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long systemUserID;

  public Long getSystemUserID() {
    return systemUserID;
  }

  public void setSystemUserID(Long systemUserID) {
    this.systemUserID = systemUserID;
  }


}
