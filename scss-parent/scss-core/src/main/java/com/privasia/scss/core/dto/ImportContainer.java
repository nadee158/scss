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

  private String gateInOut;

  private String line;

  private ISOInfo isoInfo;

  private SealInfo sealInfo;


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

  public String getGateInOut() {
    return gateInOut;
  }

  public void setGateInOut(String gateInOut) {
    this.gateInOut = gateInOut;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public SealInfo getSealInfo() {
    return sealInfo;
  }

  public void setSealInfo(SealInfo sealInfo) {
    this.sealInfo = sealInfo;
  }



}
