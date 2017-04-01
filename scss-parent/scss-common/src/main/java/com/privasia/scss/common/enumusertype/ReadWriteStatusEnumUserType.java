/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.ReadWriteStatus;

/**
 * @author Janaka
 *
 */
public class ReadWriteStatusEnumUserType extends GeneralEnumMapUserType<ReadWriteStatus>implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   */
  public ReadWriteStatusEnumUserType() {
    super(ReadWriteStatus.class);
  }

}
