/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.KioskLockStatus;

/**
 * @author Janaka
 *
 */
public class KioskLockStatusEnumUserType extends GeneralEnumMapUserType<KioskLockStatus> implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   */
  public KioskLockStatusEnumUserType() {
    super(KioskLockStatus.class);
  }

}
