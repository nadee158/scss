package com.privasia.scss.core.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.privasia.scss.core.model.CommonSolasAttribute;

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

  public SolasInfo(CommonSolasAttribute solas) {
    super();
    if (!(solas == null)) {
      BeanUtils.copyProperties(solas, this, "solasInstruction");
      if (!(solas.getSolasInstruction() == null)) {
        this.solasInstruction = solas.getSolasInstruction().getValue();
      }
    }
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
