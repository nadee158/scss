package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Nadeeshani Senevirathna
 *
 */
public class SolasInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int mgw;

  private String faLedgerCode;

  private String solasRefNumber;

  private String solasDetailID;

  private String solasInstruction;

  private String shipperVGM;


  public SolasInfo() {
    super();
  }


  public int getMgw() {
    return mgw;
  }

  public void setMgw(int mgw) {
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



}
