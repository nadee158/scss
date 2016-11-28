package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.List;

public class HDBSBkgGridDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private boolean acceptBooking;

  private String smartCardNo;

  private String showManual;

  private List<HDBSBkgDetailGridDTO> hdbsBkgDetailGridDTOList;

  public boolean isAcceptBooking() {
    return acceptBooking;
  }

  public void setAcceptBooking(boolean acceptBooking) {
    this.acceptBooking = acceptBooking;
  }

  public String getSmartCardNo() {
    return smartCardNo;
  }

  public void setSmartCardNo(String smartCardNo) {
    this.smartCardNo = smartCardNo;
  }

  public String getShowManual() {
    return showManual;
  }

  public void setShowManual(String showManual) {
    this.showManual = showManual;
  }

  public List<HDBSBkgDetailGridDTO> getHdbsBkgDetailGridDTOList() {
    return hdbsBkgDetailGridDTOList;
  }

  public void setHdbsBkgDetailGridDTOList(List<HDBSBkgDetailGridDTO> hdbsBkgDetailGridDTOList) {
    this.hdbsBkgDetailGridDTOList = hdbsBkgDetailGridDTOList;
  }



}
