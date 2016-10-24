/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.ImpExpFlagStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;

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

  private String pmHeadNo;

  private String pmPlateNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
  private Card card;

  @Enumerated(EnumType.STRING)
  private TransactionStatus eirStatus;

  private Long eirNumber;

  @Enumerated(EnumType.STRING)
  private ImpExpFlagStatus impExpFlag;

  @Type(type = "yes_no")
  private boolean transactionSlipPrinted;

  @Type(type = "yes_no")
  private boolean kioskCancelPickUp;

  @Type(type = "yes_no")
  private boolean kioskConfirmed;

  private String gateOutBoothNo;

  // @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timeGateIn;

  // @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timeGateInOk;

  // @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timeGateOut;

  // @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timeGateOutOk;

  // @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timeGateOutBooth;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser gateInClerk;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser gateOutClerk;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser gateOutBoothClerk;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client gateInClient;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client gateOutClient;

  private String rejectReason;

  @Enumerated(EnumType.STRING)
  private TransactionStatus gateInStatus;

  public String getPmHeadNo() {
    return pmHeadNo;
  }

  public void setPmHeadNo(String pmHeadNo) {
    this.pmHeadNo = pmHeadNo;
  }

  public String getPmPlateNo() {
    return pmPlateNo;
  }

  public void setPmPlateNo(String pmPlateNo) {
    this.pmPlateNo = pmPlateNo;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public TransactionStatus getEirStatus() {
    return eirStatus;
  }

  public void setEirStatus(TransactionStatus eirStatus) {
    this.eirStatus = eirStatus;
  }

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

  public boolean isTransactionSlipPrinted() {
    return transactionSlipPrinted;
  }

  public void setTransactionSlipPrinted(boolean transactionSlipPrinted) {
    this.transactionSlipPrinted = transactionSlipPrinted;
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

  public String getGateOutBoothNo() {
    return gateOutBoothNo;
  }

  public void setGateOutBoothNo(String gateOutBoothNo) {
    this.gateOutBoothNo = gateOutBoothNo;
  }

  public LocalDateTime getTimeGateIn() {
    return timeGateIn;
  }

  public void setTimeGateIn(LocalDateTime timeGateIn) {
    this.timeGateIn = timeGateIn;
  }

  public LocalDateTime getTimeGateInOk() {
    return timeGateInOk;
  }

  public void setTimeGateInOk(LocalDateTime timeGateInOk) {
    this.timeGateInOk = timeGateInOk;
  }

  public LocalDateTime getTimeGateOut() {
    return timeGateOut;
  }

  public void setTimeGateOut(LocalDateTime timeGateOut) {
    this.timeGateOut = timeGateOut;
  }

  public LocalDateTime getTimeGateOutOk() {
    return timeGateOutOk;
  }

  public void setTimeGateOutOk(LocalDateTime timeGateOutOk) {
    this.timeGateOutOk = timeGateOutOk;
  }

  public LocalDateTime getTimeGateOutBooth() {
    return timeGateOutBooth;
  }

  public void setTimeGateOutBooth(LocalDateTime timeGateOutBooth) {
    this.timeGateOutBooth = timeGateOutBooth;
  }

  public SystemUser getGateInClerk() {
    return gateInClerk;
  }

  public void setGateInClerk(SystemUser gateInClerk) {
    this.gateInClerk = gateInClerk;
  }

  public SystemUser getGateOutClerk() {
    return gateOutClerk;
  }

  public void setGateOutClerk(SystemUser gateOutClerk) {
    this.gateOutClerk = gateOutClerk;
  }

  public SystemUser getGateOutBoothClerk() {
    return gateOutBoothClerk;
  }

  public void setGateOutBoothClerk(SystemUser gateOutBoothClerk) {
    this.gateOutBoothClerk = gateOutBoothClerk;
  }

  public Client getGateInClient() {
    return gateInClient;
  }

  public void setGateInClient(Client gateInClient) {
    this.gateInClient = gateInClient;
  }

  public Client getGateOutClient() {
    return gateOutClient;
  }

  public void setGateOutClient(Client gateOutClient) {
    this.gateOutClient = gateOutClient;
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
