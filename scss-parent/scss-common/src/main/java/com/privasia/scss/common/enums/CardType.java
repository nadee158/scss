/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author nadee158
 *
 */
public enum CardType implements Enumable {

  MASTER_CARD("M"), NORMAL_CARD("N");


  private final String cardType;

  private CardType(String cardType) {
    this.cardType = cardType;
  }

  public String getValue() {
    return cardType;
  }
  
  public Enum<?> getEnumFromValue(String value) {
      return EnumableHelper.getEnumFromValue(this, value, null);
  }



}
