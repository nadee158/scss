package com.privasia.scss.hpat.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.privasia.scss.core.model.HPATBooking;

public class HpatDto implements Serializable {

  public static String DELETION_ACTIVE = "A";
  public static String DELETION_DELETE = "D";

  public static String ON_TIME = "ON-TIME";
  public static String ACTIVE = "ACTIVE";
  public static String LATE = "LATE";
  public static String COMPLETE = "COMP";
  public static String EXPIRED = "EXPIRED";
  public static String EARLY = "EARLY";
  public static String CANCEL = "CAN";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String bookingId;// (rs.getString("BOOKING_ID"));
  private String driverId;// (rs.getString("DRIVER_IC_PP"));
  private String buffer;// (rs.getString("BUFFER"));
  private String comId;// (rs.getString("HAULIER_CODE"));
  private String crdScardno;// (rs.getString("CRD_SCARDNO"));
  private String pmNo;// (rs.getString("PM_NO"));
  private String fromScss;// ("Y");
  private String status;// (rs.getString("STATUS_CODE"));
  private String trlrNo;// (rs.getString("TRAILER_NO"));
  private String trlrType;// (rs.getString("TRAILER_TYPE"));

  private String apptStart;// (this.convertDateToString(rs.getTimestamp("APPT_DATE_START")));
  private String apptEnd;// (this.convertDateToString(rs.getTimestamp("APPT_DATE_END")));

  private LocalDateTime apptStartDate;// (rs.getTimestamp("APPT_DATE_START")));
  private LocalDateTime apptEndDate;// (rs.getTimestamp("APPT_DATE_END")));

  private String OnTimeFlag = "N";
  private String containerAndGatepass;
  private String impExpScreen;
  private String menuId;

  public HpatDto(HPATBooking b) {
    this.bookingId = b.getBookingID();
    this.driverId = b.getDriverICNumber();
    this.buffer = b.getBuffer();
    this.comId = b.getHaulierCode();
    this.crdScardno = b.getCardNo();
    this.pmNo = b.getPmNumber();
    this.fromScss = "Y";
    this.status = b.getStatus().getValue();
    this.trlrNo = b.getTrailerNo();
    this.trlrType = b.getTrailerType();
    this.apptStartDate = b.getAppointmentStartDate();
    this.apptEndDate = b.getAppointmentEndDate();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    if (!(this.apptStartDate == null)) {
      this.apptStart = format.format(this.apptStartDate);
    }
    if (!(this.apptEndDate == null)) {
      this.apptEnd = format.format(this.apptEndDate);
    }

  }

  public HpatDto() {
    super();
  }


  public String getBookingId() {
    return bookingId;
  }

  public void setBookingId(String bookingId) {
    this.bookingId = bookingId;
  }

  public String getDriverId() {
    return driverId;
  }

  public void setDriverId(String driverId) {
    this.driverId = driverId;
  }

  public String getBuffer() {
    return buffer;
  }

  public void setBuffer(String buffer) {
    this.buffer = buffer;
  }

  public String getComId() {
    return comId;
  }

  public void setComId(String comId) {
    this.comId = comId;
  }

  public String getCrdScardno() {
    return crdScardno;
  }

  public void setCrdScardno(String crdScardno) {
    this.crdScardno = crdScardno;
  }

  public String getPmNo() {
    return pmNo;
  }

  public void setPmNo(String pmNo) {
    this.pmNo = pmNo;
  }

  public String getFromScss() {
    return fromScss;
  }

  public void setFromScss(String fromScss) {
    this.fromScss = fromScss;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTrlrNo() {
    return trlrNo;
  }

  public void setTrlrNo(String trlrNo) {
    this.trlrNo = trlrNo;
  }

  public String getTrlrType() {
    return trlrType;
  }

  public void setTrlrType(String trlrType) {
    this.trlrType = trlrType;
  }

  public String getApptStart() {
    return apptStart;
  }

  public void setApptStart(String apptStart) {
    this.apptStart = apptStart;
  }

  public String getApptEnd() {
    return apptEnd;
  }

  public void setApptEnd(String apptEnd) {
    this.apptEnd = apptEnd;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getOnTimeFlag() {
    return OnTimeFlag;
  }

  public void setOnTimeFlag(String onTimeFlag) {
    OnTimeFlag = onTimeFlag;
  }

  public String getContainerAndGatepass() {
    return containerAndGatepass;
  }

  public void setContainerAndGatepass(String containerAndGatepass) {
    this.containerAndGatepass = containerAndGatepass;
  }

  public String getImpExpScreen() {
    return impExpScreen;
  }

  public void setImpExpScreen(String impExpScreen) {
    this.impExpScreen = impExpScreen;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public LocalDateTime getApptStartDate() {
    return apptStartDate;
  }

  public void setApptStartDate(LocalDateTime apptStartDate) {
    this.apptStartDate = apptStartDate;
  }

  public LocalDateTime getApptEndDate() {
    return apptEndDate;
  }

  public void setApptEndDate(LocalDateTime apptEndDate) {
    this.apptEndDate = apptEndDate;
  }



}
