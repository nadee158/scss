package com.privasia.scss.common.dto;

import java.io.Serializable;

public class CardUsageDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long cardUsageID;

  // private CardDTO card;
  //
  // private ClientDTO client;
  //
  // private String usageStatus;

  public Long getCardUsageID() {
    return cardUsageID;
  }

  public void setCardUsageID(Long cardUsageID) {
    this.cardUsageID = cardUsageID;
  }

  public CardUsageDTO initializeWithDefaultValues() {
    this.cardUsageID = 9649030l;
    return this;
  }

  // public CardDTO getCard() {
  // return card;
  // }
  //
  // public void setCard(CardDTO card) {
  // this.card = card;
  // }
  //
  // public ClientDTO getClient() {
  // return client;
  // }
  //
  // public void setClient(ClientDTO client) {
  // this.client = client;
  // }
  //
  // public String getUsageStatus() {
  // return usageStatus;
  // }
  //
  // public void setUsageStatus(String usageStatus) {
  // this.usageStatus = usageStatus;
  // }
  //
  // @Override
  // public String toString() {
  // return "CardUsageDTO [cardUsageID=" + cardUsageID + ", card=" + card + ", client=" + client +
  // ", usageStatus="
  // + usageStatus + "]";
  // }



}
