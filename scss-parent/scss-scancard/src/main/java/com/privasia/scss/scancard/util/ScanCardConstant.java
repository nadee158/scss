/**
 * 
 */
package com.privasia.scss.scancard.util;

/**
 * @author Janaka
 *
 */
public final class ScanCardConstant {

  public static final int CARD_NO_14_DIGITS_LENGTH = 14;
  public static final int CARD_NO_16_DIGITS_LENGTH = 16;

  // card error code
  public static final String CARD_ERR_BLACKLIST = "CRD BLK";
  public static final String CARD_ERR_SUSPENDED = "CRD SUS";
  public static final String CARD_ERR_TERMINATED = "CRD TER";
  public static final String CARD_ERR_EXPIRED = "CRD EXP";
  public static final String CARD_ERR_NOT_ISSUED = "CRD NIS";
  public static final String CARD_ERR_PENDING = "CRD PEN";
  public static final String CARD_ERR_NOT_FOUND = "NO CARD ";

  // company error code
  public static final String COMP_ERR_SUSPENDED = "COM SUS";
  public static final String COMP_ERR_TERMINATED = "COM TER";


  // unknown error code
  public static final String RET_UNKNOWN = "UNKNOWN";
  // alignment to the center
  public static final String RET_OK = "   OK   ";

}
