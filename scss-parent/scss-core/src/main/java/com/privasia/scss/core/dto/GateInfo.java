package com.privasia.scss.core.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.core.model.CardUsage;
import com.privasia.scss.core.util.constant.CardType;

public class GateInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  String cardIdSeq = StringUtils.EMPTY;
  String clientId = StringUtils.EMPTY;
  String timeGateIn = StringUtils.EMPTY;
  boolean isMCByPass = false;
  String weightBridge = StringUtils.EMPTY;
  String cugIdSeq = StringUtils.EMPTY;

  public GateInfo(CardUsage cardUsage) {
    // String cardId = rs.getString("crd_cardid");
    // String timeInOut = rs.getString("cug_time_start");
    // String mcFlag = rs.getString("cug_mc_flag");
    // mcFlag = mcFlag.equals(Card.MASTER_CARD) ? mcFlag = "true" : "false";
    // String weightBridge = rs.getString("exp_weight_bridge");
    // String cugIdSeq = rs.getString("cug_id_seq");

    if (!(cardUsage == null)) {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
      this.cardIdSeq = Long.toString(cardUsage.getCard().getCardID());
      this.clientId = Long.toString(cardUsage.getClient().getClientID());
      ZonedDateTime dateTime = cardUsage.getDateTimeAdd();
      if (!(dateTime == null)) {
        this.timeGateIn = format.format(dateTime);
      }
      CardType cardType = cardUsage.getMcFlag();
      this.isMCByPass = cardType.equals(CardType.MASTER_CARD) ? true : false;

      this.weightBridge = Integer.toString(cardUsage.getExpWeightBridge());
      this.cugIdSeq = Long.toString(cardUsage.getCardUsageID());
    }

  }

  public String getCardIdSeq() {
    return cardIdSeq;
  }

  public void setCardIdSeq(String cardIdSeq) {
    this.cardIdSeq = cardIdSeq;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getTimeGateIn() {
    return timeGateIn;
  }

  public void setTimeGateIn(String timeGateIn) {
    this.timeGateIn = timeGateIn;
  }

  public boolean isMCByPass() {
    return isMCByPass;
  }

  public void setMCByPass(boolean isMCByPass) {
    this.isMCByPass = isMCByPass;
  }


  public String getWeightBridge() {
    return weightBridge;
  }

  public void setWeightBridge(String weightBridge) {
    this.weightBridge = weightBridge;
  }

  public String getCugIdSeq() {
    return cugIdSeq;
  }

  public void setCugIdSeq(String cugIdSeq) {
    this.cugIdSeq = cugIdSeq;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
