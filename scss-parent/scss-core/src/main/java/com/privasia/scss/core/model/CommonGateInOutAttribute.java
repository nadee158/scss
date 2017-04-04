
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;

/**
 * @author Janaka
 *
 */
@Embeddable
public class CommonGateInOutAttribute implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long eirNumber;

  @Type(type = "com.privasia.scss.common.enumusertype.ImpExpFlagEnumUserType")
  private ImpExpFlagStatus impExpFlag;

  @Type(type = "yes_no")
  private boolean kioskCancelPickUp;

  @Type(type = "yes_no")
  private boolean kioskConfirmed;

  private String rejectReason;

  @Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
  private TransactionStatus gateInStatus;

  private String zipFileNo;

  private String trxSlipNo;

  public Long getEirNumber() {
    return eirNumber;
  }

  public void setEirNumber(Long eirNumber) {
    this.eirNumber = eirNumber;
  }

  public ImpExpFlagStatus getImpExpFlag() {
    return impExpFlag;
  }

  public void setImpExpFlag(ImpExpFlagStatus impExpFlag) {
    this.impExpFlag = impExpFlag;
  }

  public boolean isKioskCancelPickUp() {
    return kioskCancelPickUp;
  }

  public void setKioskCancelPickUp(boolean kioskCancelPickUp) {
    this.kioskCancelPickUp = kioskCancelPickUp;
  }

  public boolean isKioskConfirmed() {
    return kioskConfirmed;
  }

  public void setKioskConfirmed(boolean kioskConfirmed) {
    this.kioskConfirmed = kioskConfirmed;
  }

  public String getRejectReason() {
    return rejectReason;
  }

  public void setRejectReason(String rejectReason) {
	  if(StringUtils.isNotEmpty(rejectReason)){
		  rejectReason = rejectReason.toUpperCase();
	  }
    this.rejectReason = rejectReason;
  }

  public TransactionStatus getGateInStatus() {
    return gateInStatus;
  }

  public void setGateInStatus(TransactionStatus gateInStatus) {
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
