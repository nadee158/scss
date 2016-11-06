package com.privasia.scss.hpat.dto;

import com.privasia.scss.core.model.Card;

public class CardDto {
  private String crdScardNo;
  private String compId;
  private String compCode;
  private String newICNo;
  private String oldICNo;
  private String passportNo;

  public CardDto(Card card) {
    if (!(card == null)) {
      this.crdScardNo = Long.toString(card.getCardID());
      this.compId = Long.toString(card.getCompany().getCompanyID());
      this.compCode = card.getCompany().getCompanyCode();
      this.newICNo = card.getSmartCardUser().getCommonContactAttribute().getNewNRICNO();
      this.oldICNo = card.getSmartCardUser().getCommonContactAttribute().getOldNRICNO();
      this.passportNo = card.getSmartCardUser().getPassportNo();
    }
  }

  public String getCrdScardNo() {
    return crdScardNo;
  }

  public void setCrdScardNo(String crdScardNo) {
    this.crdScardNo = crdScardNo;
  }

  public String getCompId() {
    return compId;
  }

  public void setCompId(String compId) {
    this.compId = compId;
  }

  public String getCompCode() {
    return compCode;
  }

  public void setCompCode(String compCode) {
    this.compCode = compCode;
  }

  public String getNewICNo() {
    return newICNo;
  }

  public void setNewICNo(String newICNo) {
    this.newICNo = newICNo;
  }

  public String getOldICNo() {
    return oldICNo;
  }

  public void setOldICNo(String oldICNo) {
    this.oldICNo = oldICNo;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

}
