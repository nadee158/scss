package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ReferRejectReasonDTO implements Serializable {

  private Long referRejectReasonID;

  private ReferReasonDTO referReason;

  //private String reasonDescription;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public Long getReferRejectReasonID() {
    return referRejectReasonID;
  }

  public void setReferRejectReasonID(Long referRejectReasonID) {
    this.referRejectReasonID = referRejectReasonID;
  }

  public ReferReasonDTO getReferReason() {
    return referReason;
  }

  public void setReferReason(ReferReasonDTO referReason) {
    this.referReason = referReason;
  }

  /*public String getReasonDescription() {
    return reasonDescription;
  }

  public void setReasonDescription(String reasonDescription) {
    this.reasonDescription = reasonDescription;
  }*/

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public ReferRejectReasonDTO initializeWithDefaultValues() {
    this.referRejectReasonID = 50l;
    // this.referReasonID = 10l;
    //this.reasonDescription = "";
    return this;
  }



}
