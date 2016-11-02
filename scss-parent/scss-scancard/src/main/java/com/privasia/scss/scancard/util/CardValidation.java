package com.privasia.scss.scancard.util;

import org.apache.commons.lang3.StringUtils;

public class CardValidation {

  boolean isValid = false;
  String validationMessage = StringUtils.EMPTY;
  String cardStatus = StringUtils.EMPTY;

  public CardValidation(boolean isValid, String validationMessage, String cardStatus) {
    super();
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


}
