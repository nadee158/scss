package com.privasia.scss.core.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.privasia.scss.core.model.HPATBookingDetail;

public class ImportContainer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String impGatePassNumber;

  private String containerNumber;

  private ISOInfo isoInfo;

  public ImportContainer(HPATBookingDetail bookingDetail) {
    super();
    if (!(bookingDetail == null)) {
      BeanUtils.copyProperties(bookingDetail, this);
    }
  }

  public ImportContainer() {
    super();
  }

  public ISOInfo getIsoInfo() {
    return isoInfo;
  }

  public void setIsoInfo(ISOInfo isoInfo) {
    this.isoInfo = isoInfo;
  }

  public String getImpGatePassNumber() {
    return impGatePassNumber;
  }

  public void setImpGatePassNumber(String impGatePassNumber) {
    this.impGatePassNumber = impGatePassNumber;
  }

  public String getContainerNumber() {
    return containerNumber;
  }

  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }



}
