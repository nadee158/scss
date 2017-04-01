package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ReadWriteStatus;
import com.privasia.scss.common.enums.TransactionType;

@Entity
@Table(name = "OPUS_REQUEST_RESPONSE")
public class OpusRequestResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(name = "TRANSACTION_TYPE")
  @Type(type = "com.privasia.scss.common.enumusertype.TransactionTypeEnumUserType")
  private TransactionType transactionType;

  @Column(name = "GATE_IN_OUT")
  @Type(type = "com.privasia.scss.common.enumusertype.GateInOutStatusEnumUserType")
  private GateInOutStatus gateInOut;

  @Column(name = "CONTAINER_NUMBER")
  private String containerNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CARD_ID", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
  private Card card;

  @Column(name = "GATE_IN_TIME")
  private LocalDateTime gateinTime;

  @Column(name = "READ_WRITE")
  @Type(type = "com.privasia.scss.common.enumusertype.ReadWriteStatusEnumUserType")
  private ReadWriteStatus readWrite;

  @Column(name = "REQUEST", length = 5000)
  private String request;

  @Column(name = "RESPONSE", length = 5000)
  private String response;

  @Column(name = "SEND_TIME")
  private LocalDateTime sendTime;

  @Column(name = "RECEIVED_TIME")
  private LocalDateTime receivedTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public GateInOutStatus getGateInOut() {
    return gateInOut;
  }

  public void setGateInOut(GateInOutStatus gateInOut) {
    this.gateInOut = gateInOut;
  }

  public String getContainerNumber() {
    return containerNumber;
  }

  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public LocalDateTime getGateinTime() {
    return gateinTime;
  }

  public void setGateinTime(LocalDateTime gateinTime) {
    this.gateinTime = gateinTime;
  }

  public ReadWriteStatus getReadWrite() {
    return readWrite;
  }

  public void setReadWrite(ReadWriteStatus readWrite) {
    this.readWrite = readWrite;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public LocalDateTime getSendTime() {
    return sendTime;
  }

  public void setSendTime(LocalDateTime sendTime) {
    this.sendTime = sendTime;
  }

  public LocalDateTime getReceivedTime() {
    return receivedTime;
  }

  public void setReceivedTime(LocalDateTime receivedTime) {
    this.receivedTime = receivedTime;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
