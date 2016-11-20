/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.TransactionType;

/**
 * @author Janaka
 *
 */
public class TransactionTypeEnumUserType extends GeneralEnumMapUserType<TransactionType> implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   *
   */
  public TransactionTypeEnumUserType() {
    super(TransactionType.class);
  }

}
