package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class GateInOutODDDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String pmHeadNo = StringUtils.EMPTY;

  private String pmPlateNo = StringUtils.EMPTY;

  private long cardId;

  private String gateInStatus = StringUtils.EMPTY;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  private LocalDateTime timeGateIn;

  private List<WHODDDTO> whoddds;

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

  public long getCardId() {
    return cardId;
  }

  public void setCardId(long cardId) {
    this.cardId = cardId;
  }

  public String getGateInStatus() {
    return gateInStatus;
  }

  public void setGateInStatus(String gateInStatus) {
    this.gateInStatus = gateInStatus;
  }

  public LocalDateTime getTimeGateIn() {
    return timeGateIn;
  }

  public void setTimeGateIn(LocalDateTime timeGateIn) {
    this.timeGateIn = timeGateIn;
  }

  public List<WHODDDTO> getWhoddds() {
    return whoddds;
  }

  public void setWhoddds(List<WHODDDTO> whoddds) {
    this.whoddds = whoddds;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "GateInOutODDDTO [pmHeadNo=" + pmHeadNo + ", pmPlateNo=" + pmPlateNo + ", cardId=" + cardId
        + ", gateInStatus=" + gateInStatus + ", timeGateIn=" + timeGateIn + ", whoddds=" + whoddds + "]";
  }

  public static GateInOutODDDTO emptyGateInOutODDRequest() {
    GateInOutODDDTO gateInOutODDDTO = new GateInOutODDDTO();
    gateInOutODDDTO.setTimeGateIn(LocalDateTime.now());
    List<WHODDDTO> whoddds = new ArrayList<WHODDDTO>();
    whoddds.add(constructWHODDDTO());
    gateInOutODDDTO.setWhoddds(whoddds);
    return gateInOutODDDTO;
  }

  private static WHODDDTO constructWHODDDTO() {
    WHODDDTO whodddto = new WHODDDTO();
    whodddto.setContainer01(constructContainer());
    whodddto.setContainer02(constructContainer());
    return whodddto;
  }

  private static ODDContainerDetailsDTO constructContainer() {
    ODDContainerDetailsDTO dto = new ODDContainerDetailsDTO();
    return dto;
  }



}
