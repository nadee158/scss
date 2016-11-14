/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

import com.privasia.scss.common.dto.SolasInfo;
import com.privasia.scss.common.enums.SolasInstructionType;


/**
 * @author Janaka
 *
 */
@Embeddable
public class CommonSolasAttribute implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "MGW")
  private int mgw;

  @Column(name = "FA_LEDGER_CODE")
  private String faLedgerCode;

  @Column(name = "SOLAS_REF_NO")
  private String solasRefNumber;

  @Column(name = "SOLAS_DET_ID")
  private String solasDetailID;

  @Column(name = "SOLAS_INSTRUCTION")
  @Type(type = "com.privasia.scss.core.util.enumusertype.SolasInstructionEnumUserType")
  private SolasInstructionType solasInstruction;

  @Column(name = "SHIPPER_VGM")
  private String shipperVGM;

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

  public SolasInstructionType getSolasInstruction() {
    return solasInstruction;
  }

  public void setSolasInstruction(SolasInstructionType solasInstruction) {
    this.solasInstruction = solasInstruction;
  }

  public String getShipperVGM() {
    return shipperVGM;
  }

  public void setShipperVGM(String shipperVGM) {
    this.shipperVGM = shipperVGM;
  }

  public SolasInfo constructSolasInfo() {
    SolasInfo solasInfo = new SolasInfo();
    BeanUtils.copyProperties(this, solasInfo, "solasInstruction");
    if (!(this.getSolasInstruction() == null)) {
      solasInfo.setSolasInstruction(this.getSolasInstruction().getValue());
    }
    return solasInfo;
  }



}
