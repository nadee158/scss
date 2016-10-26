/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.ImpExpFlagStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;

/**
 * @author Janaka
 *
 */
@Embeddable
public class CommonGateInOutAttribute extends BaseCommonGateInOutAttribute implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long eirNumber;

  @Enumerated(EnumType.STRING)
  private ImpExpFlagStatus impExpFlag;
  
  @Type(type = "yes_no")
  private boolean kioskCancelPickUp;

  @Type(type = "yes_no")
  private boolean kioskConfirmed;

  private String rejectReason;

  @Enumerated(EnumType.STRING)
  private TransactionStatus gateInStatus;

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
    this.rejectReason = rejectReason;
  }

  public TransactionStatus getGateInStatus() {
    return gateInStatus;
  }

  public void setGateInStatus(TransactionStatus gateInStatus) {
    this.gateInStatus = gateInStatus;
  }

  
}
