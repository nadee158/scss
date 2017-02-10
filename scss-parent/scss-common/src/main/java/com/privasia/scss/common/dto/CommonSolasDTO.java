package com.privasia.scss.common.dto;

import java.io.Serializable;

public class CommonSolasDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Integer mgw;

  private String faLedgerCode;

  private String solasRefNumber;

  private String solasDetailID;

  private String solasInstruction;

  private String shipperVGM;



  public Integer getMgw() {
    return mgw;
  }



  public void setMgw(Integer mgw) {
    this.mgw = mgw;
  }



  public String getFaLedgerCode() {
    return faLedgerCode;
  }



  public void setFaLedgerCode(String faLedgerCode) {
    this.faLedgerCode = faLedgerCode;
  }



  public String getSolasRefNumber() {
    return solasRefNumber;
  }



  public void setSolasRefNumber(String solasRefNumber) {
    this.solasRefNumber = solasRefNumber;
  }



  public String getSolasDetailID() {
    return solasDetailID;
  }



  public void setSolasDetailID(String solasDetailID) {
    this.solasDetailID = solasDetailID;
  }



  public String getSolasInstruction() {
    return solasInstruction;
  }



  public void setSolasInstruction(String solasInstruction) {
    this.solasInstruction = solasInstruction;
  }



  public String getShipperVGM() {
    return shipperVGM;
  }



  public void setShipperVGM(String shipperVGM) {
    this.shipperVGM = shipperVGM;
  }



  @Override
  public String toString() {
    return "CommonSolasDTO [mgw=" + mgw + ", faLedgerCode=" + faLedgerCode + ", solasRefNumber=" + solasRefNumber
        + ", solasDetailID=" + solasDetailID + ", solasInstruction=" + solasInstruction + ", shipperVGM=" + shipperVGM
        + "]";
  }



}
