/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class CommonGateInOutDTO implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  private Long eirNumber;

  private String impExpFlag;

  private String rejectReason;

  private String gateInStatus;

  private String zipFileNo;

  private String trxSlipNo;

  public Long getEirNumber() {
    return eirNumber;
  }

  public void setEirNumber(Long eirNumber) {
    this.eirNumber = eirNumber;
  }

  public String getImpExpFlag() {
    return impExpFlag;
  }

  public void setImpExpFlag(String impExpFlag) {
    this.impExpFlag = impExpFlag;
  }

  public String getRejectReason() {
    return rejectReason;
  }

  public void setRejectReason(String rejectReason) {
    this.rejectReason = rejectReason;
  }

  public String getGateInStatus() {
    return gateInStatus;
  }

  public void setGateInStatus(String gateInStatus) {
    this.gateInStatus = gateInStatus;
  }

  public String getZipFileNo() {
    return zipFileNo;
  }

  public void setZipFileNo(String zipFileNo) {
    this.zipFileNo = zipFileNo;
  }

  public String getTrxSlipNo() {
    return trxSlipNo;
  }

  public void setTrxSlipNo(String trxSlipNo) {
    this.trxSlipNo = trxSlipNo;
  }



}
