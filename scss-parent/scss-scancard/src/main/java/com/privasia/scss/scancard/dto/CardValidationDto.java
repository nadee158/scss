package com.privasia.scss.scancard.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * @author nadee158
 *
 */
public class CardValidationDto {

  long cardId = 0;
  boolean isValid = false;
  String validationMessage = StringUtils.EMPTY;
  String cardStatus = StringUtils.EMPTY;

  public CardValidationDto(long cardId, boolean isValid, String validationMessage, String cardStatus) {
    super();
    this.cardId = cardId;
    this.isValid = isValid;
    this.validationMessage = validationMessage;
    this.cardStatus = cardStatus;
  }

  public String getValidationMessage() {
    return validationMessage;
  }

  public void setValidationMessage(String validationMessage) {
    this.validationMessage = validationMessage;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public String getCardStatus() {
    return cardStatus;
  }

  public void setCardStatus(String cardStatus) {
    this.cardStatus = cardStatus;
  }

  public long getCardId() {
    return cardId;
  }

  public void setCardId(long cardId) {
    this.cardId = cardId;
  }


}
