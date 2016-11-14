/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyStatus;
import com.privasia.scss.common.enums.CompanyType;
import com.privasia.scss.common.enums.Nationality;

/**
 * @author Janaka
 *
 */
public class SmartCardUserDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String personName;
  private String newNRICNO;
  private String oldNRICNO;
  private String passportNo;
  private String companyName;
  private Long companyID;
  private String companyAccountNo;
  private String companyStatus;
  private String companyType;
  private String companyCode;
  private String cardStatus;
  private String nationality;
  private Long cardNo;
  private Long smartCardUserID;
  private byte[] photo;
  private String clientUnitNo;

  public SmartCardUserDTO(String personName, String newNRICNO, String oldNRICNO, String passportNo,
      Long smartCardUserID, String companyName, Long companyID, String companyAccountNo, String companyCode,
      Long cardNo, byte[] photo, Enum<Nationality> nationality, Enum<CardStatus> cardStatus,
      Enum<CompanyType> companyType, Enum<CompanyStatus> companyStatus) {
    super();
    this.personName = personName;
    this.newNRICNO = newNRICNO;
    this.oldNRICNO = oldNRICNO;
    this.passportNo = passportNo;
    this.companyName = companyName;
    this.companyID = companyID;
    this.companyAccountNo = companyAccountNo;
    this.companyStatus = CompanyStatus.fromName(companyStatus.name()).getValue();
    this.companyType = CompanyType.fromName(companyType.name()).getValue();
    this.companyCode = companyCode;
    this.cardStatus = CardStatus.fromName(cardStatus.name()).getValue();
    this.nationality = Nationality.fromName(nationality.name()).getValue();
    this.cardNo = cardNo;
    this.smartCardUserID = smartCardUserID;
    this.photo = photo;
  }

  public SmartCardUserDTO() {
    super();
  }

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public String getNewNRICNO() {
    return newNRICNO;
  }

  public void setNewNRICNO(String newNRICNO) {
    this.newNRICNO = newNRICNO;
  }

  public String getOldNRICNO() {
    return oldNRICNO;
  }

  public void setOldNRICNO(String oldNRICNO) {
    this.oldNRICNO = oldNRICNO;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Long getCompanyID() {
    return companyID;
  }

  public void setCompanyID(Long companyID) {
    this.companyID = companyID;
  }

  public String getCompanyAccountNo() {
    return companyAccountNo;
  }

  public void setCompanyAccountNo(String companyAccountNo) {
    this.companyAccountNo = companyAccountNo;
  }

  public String getCompanyStatus() {
    return companyStatus;
  }

  public void setCompanyStatus(String companyStatus) {
    this.companyStatus = companyStatus;
  }

  public String getCompanyType() {
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public String getCardStatus() {
    return cardStatus;
  }

  public void setCardStatus(String cardStatus) {
    this.cardStatus = cardStatus;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public Long getCardNo() {
    return cardNo;
  }

  public void setCardNo(Long cardNo) {
    this.cardNo = cardNo;
  }

  public Long getSmartCardUserID() {
    return smartCardUserID;
  }

  public void setSmartCardUserID(Long smartCardUserID) {
    this.smartCardUserID = smartCardUserID;
  }

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

  public String getClientUnitNo() {
    return clientUnitNo;
  }

  public void setClientUnitNo(String clientUnitNo) {
    this.clientUnitNo = clientUnitNo;
  }



}
