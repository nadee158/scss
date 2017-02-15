/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.CommonUtil;


/**
 * @author Janaka
 *
 */
public class BaseCommonGateInOutDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String pmHeadNo;

  private String pmPlateNo;

  private String hpatBooking;

  private String eirStatus;

  private String gateOutBoothNo;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
  private LocalDateTime timeGateIn;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
  private LocalDateTime timeGateInOk;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
  private LocalDateTime timeGateOut;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
  private LocalDateTime timeGateOutOk;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
  private LocalDateTime timeGateOutBooth;

  private Long card;

  private SystemUserDTO gateInClerk;

  private SystemUserDTO gateOutClerk;

  private ClientDTO gateInClient;

  private ClientDTO gateOutClient;

  private SystemUserDTO gateOutBoothClerk;

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

  public String getHpatBooking() {
    return hpatBooking;
  }

  public void setHpatBooking(String hpatBooking) {
    this.hpatBooking = hpatBooking;
  }

  public String getEirStatus() {
    return eirStatus;
  }

  public void setEirStatus(String eirStatus) {
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

  public Long getCard() {
    return card;
  }

  public void setCard(Long card) {
    this.card = card;
  }

  public SystemUserDTO getGateInClerk() {
    return gateInClerk;
  }

  public void setGateInClerk(SystemUserDTO gateInClerk) {
    this.gateInClerk = gateInClerk;
  }

  public SystemUserDTO getGateOutClerk() {
    return gateOutClerk;
  }

  public void setGateOutClerk(SystemUserDTO gateOutClerk) {
    this.gateOutClerk = gateOutClerk;
  }

  public ClientDTO getGateInClient() {
    return gateInClient;
  }

  public void setGateInClient(ClientDTO gateInClient) {
    this.gateInClient = gateInClient;
  }

  public ClientDTO getGateOutClient() {
    return gateOutClient;
  }

  public void setGateOutClient(ClientDTO gateOutClient) {
    this.gateOutClient = gateOutClient;
  }

  public SystemUserDTO getGateOutBoothClerk() {
    return gateOutBoothClerk;
  }

  public void setGateOutBoothClerk(SystemUserDTO gateOutBoothClerk) {
    this.gateOutBoothClerk = gateOutBoothClerk;
  }



}