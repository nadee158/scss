/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.KioskHLTCheckStatus;

/**
 * @author Janaka
 *
 */
public class KioskHLTCheckStatusEnumUserType extends GeneralEnumMapUserType<KioskHLTCheckStatus>
    implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   */
  public KioskHLTCheckStatusEnumUserType() {
    super(KioskHLTCheckStatus.class);
  }

}
