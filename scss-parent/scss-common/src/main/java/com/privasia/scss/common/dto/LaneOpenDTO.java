package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LaneOpenDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long laneOpenID;

  private String container01;

  private String container02;

  private String laneOpenFlag;

  private String remarks;

  private Long laneOpenBy;

  private Long laneID;



  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public Long getLaneOpenID() {
    return laneOpenID;
  }

  public void setLaneOpenID(Long laneOpenID) {
    this.laneOpenID = laneOpenID;
  }

  public String getContainer01() {
    return container01;
  }

  public void setContainer01(String container01) {
    this.container01 = container01;
  }

  public String getContainer02() {
    return container02;
  }

  public void setContainer02(String container02) {
    this.container02 = container02;
  }

  public String getLaneOpenFlag() {
    return laneOpenFlag;
  }

  public void setLaneOpenFlag(String laneOpenFlag) {
    this.laneOpenFlag = laneOpenFlag;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Long getLaneOpenBy() {
    return laneOpenBy;
  }

  public void setLaneOpenBy(Long laneOpenBy) {
    this.laneOpenBy = laneOpenBy;
  }

  public Long getLaneID() {
    return laneID;
  }

  public void setLaneID(Long laneID) {
    this.laneID = laneID;
  }



}
