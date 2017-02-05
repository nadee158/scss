/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.ShipStatus;

/**
 * @author Janaka
 *
 */
public class ShipStatusEnumUserType extends GeneralEnumMapUserType<ShipStatus> implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   */
  public ShipStatusEnumUserType() {
    super(ShipStatus.class);
  }

}
