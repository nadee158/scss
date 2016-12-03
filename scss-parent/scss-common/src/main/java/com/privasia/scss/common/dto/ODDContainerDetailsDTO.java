package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class ODDContainerDetailsDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String containerNo = StringUtils.EMPTY;

  private String location = StringUtils.EMPTY;

  private String remarks = StringUtils.EMPTY;

  private String oddStatus = StringUtils.EMPTY;

  private String rejectionReason = StringUtils.EMPTY;

  private String fullOrEmpty = StringUtils.EMPTY;

  private String containerSize = StringUtils.EMPTY;

  private long hdbsBkgDetailNoId;

  private String hdbsStatus = StringUtils.EMPTY;

  private String hdbsArrivalStatus = StringUtils.EMPTY;

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getOddStatus() {
    return oddStatus;
  }

  public void setOddStatus(String oddStatus) {
    this.oddStatus = oddStatus;
  }

  public String getRejectionReason() {
    return rejectionReason;
  }

  public void setRejectionReason(String rejectionReason) {
    this.rejectionReason = rejectionReason;
  }

  public String getFullOrEmpty() {
    return fullOrEmpty;
  }

  public void setFullOrEmpty(String fullOrEmpty) {
    this.fullOrEmpty = fullOrEmpty;
  }

  public String getContainerSize() {
    return containerSize;
  }

  public void setContainerSize(String containerSize) {
    this.containerSize = containerSize;
  }

  public String getHdbsStatus() {
    return hdbsStatus;
  }

  public void setHdbsStatus(String hdbsStatus) {
    this.hdbsStatus = hdbsStatus;
  }

  public String getHdbsArrivalStatus() {
    return hdbsArrivalStatus;
  }

  public void setHdbsArrivalStatus(String hdbsArrivalStatus) {
    this.hdbsArrivalStatus = hdbsArrivalStatus;
  }

  public long getHdbsBkgDetailNoId() {
    return hdbsBkgDetailNoId;
  }

  public void setHdbsBkgDetailNoId(long hdbsBkgDetailNoId) {
    this.hdbsBkgDetailNoId = hdbsBkgDetailNoId;
  }

  @Override
  public String toString() {
    return "ODDContainerDetailsDTO [containerNo=" + containerNo + ", location=" + location + ", remarks=" + remarks
        + ", oddStatus=" + oddStatus + ", rejectionReason=" + rejectionReason + ", fullOrEmpty=" + fullOrEmpty
        + ", containerSize=" + containerSize + ", hdbsBkgDetailNoId=" + hdbsBkgDetailNoId + ", hdbsStatus=" + hdbsStatus
        + ", hdbsArrivalStatus=" + hdbsArrivalStatus + "]";
  }



}
