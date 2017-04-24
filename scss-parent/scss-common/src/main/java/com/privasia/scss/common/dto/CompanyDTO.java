package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.common.enums.CompanyAccountType;
import com.privasia.scss.common.enums.CompanyStatus;
import com.privasia.scss.common.enums.CompanyType;

public class CompanyDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private Long companyID;

  private String companyName;

  private String companyAccountNo;

  private String companyAccountType;

  private String companyCode;

  private String companyType;

  private String companyRegistrationNumber;

  private String companyStatus;

  private String nameOnCard;

  private CommonContactAttributeDTO commonContactAttribute;

  private String faxOffice;

  public Long getCompanyID() {
    return companyID;
  }

  public CommonContactAttributeDTO getCommonContactAttribute() {
    return commonContactAttribute;
  }

  public void setCommonContactAttribute(CommonContactAttributeDTO commonContactAttribute) {
    this.commonContactAttribute = commonContactAttribute;
  }

  public void setCompanyID(Long companyID) {
    this.companyID = companyID;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyAccountNo() {
    return companyAccountNo;
  }

  public void setCompanyAccountNo(String companyAccountNo) {
    this.companyAccountNo = companyAccountNo;
  }

  public String getCompanyAccountType() {
    return companyAccountType;
  }

  public void setCompanyAccountType(String companyAccountType) {
    this.companyAccountType = companyAccountType;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public String getCompanyType() {
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }

  public String getCompanyRegistrationNumber() {
    return companyRegistrationNumber;
  }

  public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
    this.companyRegistrationNumber = companyRegistrationNumber;
  }

  public String getCompanyStatus() {
    return companyStatus;
  }

  public void setCompanyStatus(String companyStatus) {
    this.companyStatus = companyStatus;
  }

  public String getNameOnCard() {
    return nameOnCard;
  }

  public void setNameOnCard(String nameOnCard) {
    this.nameOnCard = nameOnCard;
  }

  public String getFaxOffice() {
    return faxOffice;
  }

  public void setFaxOffice(String faxOffice) {
    this.faxOffice = faxOffice;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "CompanyDTO [companyID=" + companyID + ", companyName=" + companyName + ", companyAccountNo="
        + companyAccountNo + ", companyAccountType=" + companyAccountType + ", companyCode=" + companyCode
        + ", companyType=" + companyType + ", companyRegistrationNumber=" + companyRegistrationNumber
        + ", companyStatus=" + companyStatus + ", nameOnCard=" + nameOnCard + ", faxOffice=" + faxOffice + "]";
  }

  public CompanyDTO initializeWithDefaultValues() {
    this.companyID = 5l;
    this.companyName = StringUtils.EMPTY;
    this.companyAccountNo = StringUtils.EMPTY;
    this.companyAccountType = CompanyAccountType.CASH.getValue();
    this.companyCode = StringUtils.EMPTY;
    this.companyType = CompanyType.FOREIGN_WORKER.getValue();
    this.companyRegistrationNumber = StringUtils.EMPTY;
    this.companyStatus = CompanyStatus.ACTIVE.getValue();
    this.nameOnCard = StringUtils.EMPTY;
    this.commonContactAttribute = new CommonContactAttributeDTO().initializeWithDefaultValues();
    this.faxOffice = StringUtils.EMPTY;
    return this;
  }



}
