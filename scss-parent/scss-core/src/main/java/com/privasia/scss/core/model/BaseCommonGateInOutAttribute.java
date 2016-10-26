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

import com.privasia.scss.core.util.constant.TransactionStatus;

/**
 * @author Janaka
 *
 */
@Embeddable
public class BaseCommonGateInOutAttribute implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String pmHeadNo;

  private String pmPlateNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
  private Card card;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOKING_ID", nullable = true, referencedColumnName = "BOOKING_ID")
  private HPATBooking hpatBooking;

  @Enumerated(EnumType.STRING)
  private TransactionStatus eirStatus;
  
  @Type(type = "yes_no")
  private boolean transactionSlipPrinted;

  private String gateOutBoothNo;

  private LocalDateTime timeGateIn;

  private LocalDateTime timeGateInOk;

  private LocalDateTime timeGateOut;
  
  private LocalDateTime timeGateOutOk;

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

  public boolean isTransactionSlipPrinted() {
	return transactionSlipPrinted;
  }

  public void setTransactionSlipPrinted(boolean transactionSlipPrinted) {
	this.transactionSlipPrinted = transactionSlipPrinted;
  }

  public HPATBooking getHpatBooking() {
	return hpatBooking;
  }

  public void setHpatBooking(HPATBooking hpatBooking) {
	this.hpatBooking = hpatBooking;
  }
  
  
}
