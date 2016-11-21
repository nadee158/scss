package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.util.List;

import com.privasia.scss.common.enums.HpatReferStatus;

public class ReferRejectUpdateObjetDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private long referRejectID;

  // HpatReferStatus
  private String statusCode = HpatReferStatus.COMPLETE.toString();

  private List<ReferRejectDetailUpdateObjetDto> detailUpdateObjetDtos = null;


  public long getReferRejectID() {
    return referRejectID;
  }

  public void setReferRejectID(long referRejectID) {
    this.referRejectID = referRejectID;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public List<ReferRejectDetailUpdateObjetDto> getDetailUpdateObjetDtos() {
    return detailUpdateObjetDtos;
  }

  public void setDetailUpdateObjetDtos(List<ReferRejectDetailUpdateObjetDto> detailUpdateObjetDtos) {
    this.detailUpdateObjetDtos = detailUpdateObjetDtos;
  }



  //@formatter:off
//String sqlComp = "UPDATE SCSS_REFER_REJECT SET STATUS_CODE = 'COMP' WHERE REFER_ID = " + SQL.format(referId);



}
