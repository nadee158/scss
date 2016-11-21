package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ReferRejectDetailUpdateObjetDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private long referRejectDetailID = 0;

  // for saveReferReject method
  private String status = StringUtils.EMPTY;

  private String supervisorRemarks = StringUtils.EMPTY;

  private String containerNo = StringUtils.EMPTY;

  private List<Long> referReasonIdList = null;


  // for updateLineCodeAndGateInDateForReferRejectDetail method
  private long referRejectID = 0;

  private String lineCode = StringUtils.EMPTY;

  private String gateInTime = StringUtils.EMPTY;


  public long getReferRejectDetailID() {
    return referRejectDetailID;
  }

  public void setReferRejectDetailID(long referRejectDetailID) {
    this.referRejectDetailID = referRejectDetailID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSupervisorRemarks() {
    return supervisorRemarks;
  }

  public void setSupervisorRemarks(String supervisorRemarks) {
    this.supervisorRemarks = supervisorRemarks;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public List<Long> getReferReasonIdList() {
    return referReasonIdList;
  }

  public void setReferReasonIdList(List<Long> referReasonIdList) {
    this.referReasonIdList = referReasonIdList;
  }


  public long getReferRejectID() {
    return referRejectID;
  }

  public void setReferRejectID(long referRejectID) {
    this.referRejectID = referRejectID;
  }

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }

  public String getGateInTime() {
    return gateInTime;
  }

  public void setGateInTime(String gateInTime) {
    this.gateInTime = gateInTime;
  }



  //@formatter:off
//if (StringUtils.isNotBlank(f.getReferId())) {
//String sqlReferUpdate = "UPDATE SCSS_REFER_REJECT_DET SET"
//                    + " LINE_CODE = " + SQL.format(f.getLineC1())
//                    + " , GATE_IN_DATETIME = " + SQL.TO_DATETIME(timeGateIn)
//                    + " WHERE REFER_ID = " + SQL.format(f.getReferId())
//                    + " AND CONT_NO = " + SQL.format(f.getContainerNoC1());
  
  
//sqlUpdate = "UPDATE SCSS_REFER_REJECT_DET SET" //
//    + "  STATUS_CODE = " //
//    + SQL.format("R") //
//    + ", SUP_REMARKS = " //
//    + SQL.format(f.getSupervisorRemarksC1()) //
//    + ", REJECT_BY = " + SQL.format(userId) //
//    + " WHERE REFER_ID=" //
//    + SQL.format(f.getReferId()) //
//    + " AND CONT_NO=" //
//    + SQL.format(f.getContNo1());


//String sqlIns = "INSERT INTO SCSS_REJECT_SUP_REASON  (" //
//    + " REJECT_SUP_REASON_ID " //
//    + ", REFER_REJECT_DET_ID " //
//    + ", REF_REASON_ID " //
//    + ", ADD_BY " //
//    + ", DATETIME_ADD " //
//    + ", UPDATE_BY " //
//    + ", DATETIME_UPDATE " //
//    + ") VALUES (" //
//    + " SEQ_REJECT_SUP_REASON.NEXTVAL" 
//    + ", "
//    + SQL.format(f.getReferDet1())
//    + ", "
//    + SQL.format(str) // reason id
//    + ", " 
//    + SQL.format(userId) 
//    + ", " 
//    + SQL.TO_DATETIME()
//    + ", "
//    + SQL.format(userId) 
//    + ", " 
//    + SQL.TO_DATETIME() 
//    + ")";

}
