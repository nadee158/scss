package com.privasia.scss.hpat.dto;

import java.io.Serializable;

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

  public static String IMPORT = "I";
  public static String EXPORT = "E";
  public static String EMPTY_PICKUP = "EP";
  public static String EMPTY_RETURN = "ER";
  public static String IMPORT_ITT = "II";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String seqId;// (rs.getString("BOOKING_ID"));
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

  public String getSeqId() {
    return seqId;
  }

  public void setSeqId(String seqId) {
    this.seqId = seqId;
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



}
