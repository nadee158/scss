package com.privasia.scss.common.dto;

import java.io.Serializable;

public class ReferRejectReasonDTO implements Serializable {

  private Long referRejectReasonID;

  private Long referReasonID;

  private String reasonDescription;


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

  public Long getReferReasonID() {
    return referReasonID;
  }

  public void setReferReasonID(Long referReasonID) {
    this.referReasonID = referReasonID;
  }

  public String getReasonDescription() {
    return reasonDescription;
  }

  public void setReasonDescription(String reasonDescription) {
    this.reasonDescription = reasonDescription;
  }

  @Override
  public String toString() {
    return "ReferRejectReasonDTO [referRejectReasonID=" + referRejectReasonID + ", referReasonID=" + referReasonID
        + ", reasonDescription=" + reasonDescription + "]";
  }

  public ReferRejectReasonDTO initializeWithDefaultValues() {
    this.referRejectReasonID = 50l;
    this.referReasonID = 10l;
    this.reasonDescription = "";
    return this;
  }

}
