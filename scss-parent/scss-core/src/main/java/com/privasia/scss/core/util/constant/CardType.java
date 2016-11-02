/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author nadee158
 *
 */
public enum CardType {

  MASTER_CARD("M"), NORMAL_CARD("N");


  private final String cardType;

  private CardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCardType() {
    return cardType;
  }



}
