package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransactionDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String bookingID;

  private String driverICNumber;

  private String buffer;

  private String haulierCode;

  private String cardNo;

  private String pmNumber;

  private String status;

  private String trailerNo;

  private String trailerPlate;

  private Boolean pmVerified;

  private Boolean axleVerified;

  private String trailerType;

  private LocalDateTime appointmentStartDate;

  private LocalDateTime appointmentEndDate;

  private String axleWeight;

  private String pmWeight;

  private LocalDateTime hpatCreationDate;

  private ExportContainer exportContainer01;
  private ExportContainer exportContainer02;

  private ImportContainer importContainer01;
  private ImportContainer importContainer02;

  // TruckHeadNo
  private String pmHeadNo;

  // TruckPlateNo
  private String pmPlateNo;

  private String unitNo;

  private int cosmosPortNo;

  //////////////////////////// TO PRINT EIR

  private String nationality;

  private String newICNo;

  private String oldICNo;

  private String passportNo;

  private Boolean shipperVGM;

  private String timeIn;

  private String compNamePrint;

  private long callCard;

  private String gateInNo;

  private String scuName;

  //////////////////////////// TO createImpRequestXML

  private String gateImpOrExp;
  // Truck Com. Code : To capture Truck Com. Code
  private String compCode;
  // Lane : To capture gate lane no
  private String laneNo;

  private String requestXML;

  private int portNo;

  private String replyXML;

  private String msg;

  public String getReplyXML() {
    return replyXML;
  }

  public void setReplyXML(String replyXML) {
    this.replyXML = replyXML;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getPortNo() {
    return portNo;
  }

  public void setPortNo(int portNo) {
    this.portNo = portNo;
  }

  public String getRequestXML() {
    return requestXML;
  }

  public void setRequestXML(String requestXML) {
    this.requestXML = requestXML;
  }

  public String getCompCode() {
    return compCode;
  }

  public void setCompCode(String compCode) {
    this.compCode = compCode;
  }

  public String getLaneNo() {
    return laneNo;
  }

  public void setLaneNo(String laneNo) {
    this.laneNo = laneNo;
  }

  public String getScuName() {
    return scuName;
  }

  public String getGateImpOrExp() {
    return gateImpOrExp;
  }

  public void setGateImpOrExp(String gateImpOrExp) {
    this.gateImpOrExp = gateImpOrExp;
  }

  public void setScuName(String scuName) {
    this.scuName = scuName;
  }

  public String getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(String timeIn) {
    this.timeIn = timeIn;
  }

  public String getCompNamePrint() {
    return compNamePrint;
  }

  public void setCompNamePrint(String compNamePrint) {
    this.compNamePrint = compNamePrint;
  }

  public long getCallCard() {
    return callCard;
  }

  public void setCallCard(long callCard) {
    this.callCard = callCard;
  }

  public String getGateInNo() {
    return gateInNo;
  }

  public void setGateInNo(String gateInNo) {
    this.gateInNo = gateInNo;
  }

  public TransactionDTO() {
    super();
  }

  public Boolean isShipperVGM() {
    return shipperVGM;
  }

  public void setShipperVGM(Boolean shipperVGM) {
    this.shipperVGM = shipperVGM;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getNewICNo() {
    return newICNo;
  }

  public void setNewICNo(String newICNo) {
    this.newICNo = newICNo;
  }

  public String getOldICNo() {
    return oldICNo;
  }

  public void setOldICNo(String oldICNo) {
    this.oldICNo = oldICNo;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

  public String getBookingID() {
    return bookingID;
  }

  public void setBookingID(String bookingID) {
    this.bookingID = bookingID;
  }

  public String getDriverICNumber() {
    return driverICNumber;
  }

  public void setDriverICNumber(String driverICNumber) {
    this.driverICNumber = driverICNumber;
  }

  public String getBuffer() {
    return buffer;
  }

  public void setBuffer(String buffer) {
    this.buffer = buffer;
  }

  public String getHaulierCode() {
    return haulierCode;
  }

  public void setHaulierCode(String haulierCode) {
    this.haulierCode = haulierCode;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public String getPmNumber() {
    return pmNumber;
  }

  public void setPmNumber(String pmNumber) {
    this.pmNumber = pmNumber;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTrailerNo() {
    return trailerNo;
  }

  public void setTrailerNo(String trailerNo) {
    this.trailerNo = trailerNo;
  }

  public String getTrailerPlate() {
    return trailerPlate;
  }

  public void setTrailerPlate(String trailerPlate) {
    this.trailerPlate = trailerPlate;
  }

  public Boolean isPmVerified() {
    return pmVerified;
  }

  public void setPmVerified(Boolean pmVerified) {
    this.pmVerified = pmVerified;
  }

  public Boolean isAxleVerified() {
    return axleVerified;
  }

  public void setAxleVerified(Boolean axleVerified) {
    this.axleVerified = axleVerified;
  }

  public String getTrailerType() {
    return trailerType;
  }

  public void setTrailerType(String trailerType) {
    this.trailerType = trailerType;
  }

  public LocalDateTime getAppointmentStartDate() {
    return appointmentStartDate;
  }

  public void setAppointmentStartDate(LocalDateTime appointmentStartDate) {
    this.appointmentStartDate = appointmentStartDate;
  }

  public LocalDateTime getAppointmentEndDate() {
    return appointmentEndDate;
  }

  public void setAppointmentEndDate(LocalDateTime appointmentEndDate) {
    this.appointmentEndDate = appointmentEndDate;
  }

  public String getAxleWeight() {
    return axleWeight;
  }

  public void setAxleWeight(String axleWeight) {
    this.axleWeight = axleWeight;
  }

  public String getPmWeight() {
    return pmWeight;
  }

  public void setPmWeight(String pmWeight) {
    this.pmWeight = pmWeight;
  }

  public LocalDateTime getHpatCreationDate() {
    return hpatCreationDate;
  }

  public void setHpatCreationDate(LocalDateTime hpatCreationDate) {
    this.hpatCreationDate = hpatCreationDate;
  }

  public ExportContainer getExportContainer01() {
    return exportContainer01;
  }

  public void setExportContainer01(ExportContainer exportContainer01) {
    this.exportContainer01 = exportContainer01;
  }

  public ExportContainer getExportContainer02() {
    return exportContainer02;
  }

  public void setExportContainer02(ExportContainer exportContainer02) {
    this.exportContainer02 = exportContainer02;
  }

  public ImportContainer getImportContainer01() {
    return importContainer01;
  }

  public void setImportContainer01(ImportContainer importContainer01) {
    this.importContainer01 = importContainer01;
  }

  public ImportContainer getImportContainer02() {
    return importContainer02;
  }

  public void setImportContainer02(ImportContainer importContainer02) {
    this.importContainer02 = importContainer02;
  }

  @Override
  public String toString() {
    return "TransactionDTO [bookingID=" + bookingID + ", driverICNumber=" + driverICNumber + ", buffer=" + buffer
        + ", haulierCode=" + haulierCode + ", cardNo=" + cardNo + ", pmNumber=" + pmNumber + ", status=" + status
        + ", trailerNo=" + trailerNo + ", trailerPlate=" + trailerPlate + ", pmVerified=" + pmVerified
        + ", axleVerified=" + axleVerified + ", trailerType=" + trailerType + ", appointmentStartDate="
        + appointmentStartDate + ", appointmentEndDate=" + appointmentEndDate + ", axleWeight=" + axleWeight
        + ", pmWeight=" + pmWeight + ", hpatCreationDate=" + hpatCreationDate + ", exportContainer01="
        + exportContainer01 + ", exportContainer02=" + exportContainer02 + ", importContainer01=" + importContainer01
        + ", importContainer02=" + importContainer02 + ", pmHeadNo=" + pmHeadNo + ", pmPlateNo=" + pmPlateNo
        + ", unitNo=" + unitNo + ", cosmosPortNo=" + cosmosPortNo + "]";
  }

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

  public String getUnitNo() {
    return unitNo;
  }

  public void setUnitNo(String unitNo) {
    this.unitNo = unitNo;
  }

  public int getCosmosPortNo() {
    return cosmosPortNo;
  }

  public void setCosmosPortNo(int cosmosPortNo) {
    this.cosmosPortNo = cosmosPortNo;
  }



}
