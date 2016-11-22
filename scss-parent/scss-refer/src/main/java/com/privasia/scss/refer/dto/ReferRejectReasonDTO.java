package com.privasia.scss.refer.dto;

import java.io.Serializable;

import com.privasia.scss.core.model.ReferRejectReason;

public class ReferRejectReasonDTO implements Serializable {

  private Long referRejectReasonID;

  private Long referReasonID;

  private String reasonDescription;

  public ReferRejectReasonDTO(ReferRejectReason reason) {
    this.referRejectReasonID = reason.getReferRejectReasonID();
    if (!(reason.getReferReason() == null)) {
      this.setReferReasonID(reason.getReferReason().getReferReasonID());
      this.setReasonDescription(reason.getReferReason().getReasonDescription());
    }
  }

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

}
