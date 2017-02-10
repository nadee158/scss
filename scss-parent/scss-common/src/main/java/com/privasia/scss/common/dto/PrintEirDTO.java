package com.privasia.scss.common.dto;

import java.io.Serializable;

public class PrintEirDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long printEIRID;

  private String timeIn;

  private String callCard;

  private String truckHeadNo;

  private String companyName;

  private String icNoOrPassport;

  private String truckNo;

  private String status;

  private String scuName;

  private String gateInNo;

  private String clientIp;

  private PrintEIRContainerInfoDTO container01;

  private PrintEIRContainerInfoDTO container02;

  @Override
  public String toString() {
    return "PrintEirDTO [printEIRID=" + printEIRID + ", timeIn=" + timeIn + ", callCard=" + callCard + ", truckHeadNo="
        + truckHeadNo + ", companyName=" + companyName + ", icNoOrPassport=" + icNoOrPassport + ", truckNo=" + truckNo
        + ", status=" + status + ", scuName=" + scuName + ", gateInNo=" + gateInNo + ", clientIp=" + clientIp
        + ", container01=" + container01 + ", container02=" + container02 + "]";
  }

  public Long getPrintEIRID() {
    return printEIRID;
  }

  public void setPrintEIRID(Long printEIRID) {
    this.printEIRID = printEIRID;
  }

  public String getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(String timeIn) {
    this.timeIn = timeIn;
  }

  public String getCallCard() {
    return callCard;
  }

  public void setCallCard(String callCard) {
    this.callCard = callCard;
  }

  public String getTruckHeadNo() {
    return truckHeadNo;
  }

  public void setTruckHeadNo(String truckHeadNo) {
    this.truckHeadNo = truckHeadNo;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getIcNoOrPassport() {
    return icNoOrPassport;
  }

  public void setIcNoOrPassport(String icNoOrPassport) {
    this.icNoOrPassport = icNoOrPassport;
  }

  public String getTruckNo() {
    return truckNo;
  }

  public void setTruckNo(String truckNo) {
    this.truckNo = truckNo;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getScuName() {
    return scuName;
  }

  public void setScuName(String scuName) {
    this.scuName = scuName;
  }

  public String getGateInNo() {
    return gateInNo;
  }

  public void setGateInNo(String gateInNo) {
    this.gateInNo = gateInNo;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public PrintEIRContainerInfoDTO getContainer01() {
    return container01;
  }

  public void setContainer01(PrintEIRContainerInfoDTO container01) {
    this.container01 = container01;
  }

  public PrintEIRContainerInfoDTO getContainer02() {
    return container02;
  }

  public void setContainer02(PrintEIRContainerInfoDTO container02) {
    this.container02 = container02;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
