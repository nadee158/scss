package com.privasia.scss.kioskbooth.dto;

import java.io.Serializable;

import com.privasia.scss.core.model.KioskBoothContainerAttribute;

public class KioskBoothContainerDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String cancelPickup;

  private String location;

  private String status;

  private String others;

  private String rejectRemarks;

  private String customCheck;

  private String shipper;

  private String line;

  public String getCancelPickup() {
    return cancelPickup;
  }

  public void setCancelPickup(String cancelPickup) {
    this.cancelPickup = cancelPickup;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOthers() {
    return others;
  }

  public void setOthers(String others) {
    this.others = others;
  }

  public String getRejectRemarks() {
    return rejectRemarks;
  }

  public void setRejectRemarks(String rejectRemarks) {
    this.rejectRemarks = rejectRemarks;
  }

  public String getCustomCheck() {
    return customCheck;
  }

  public void setCustomCheck(String customCheck) {
    this.customCheck = customCheck;
  }

  public String getShipper() {
    return shipper;
  }

  public void setShipper(String shipper) {
    this.shipper = shipper;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public KioskBoothContainerAttribute constructContainerAttribute() {
    KioskBoothContainerAttribute kioskBoothContainerAttribute = new KioskBoothContainerAttribute();
    kioskBoothContainerAttribute = updateKioskBoothContainerAttribute(kioskBoothContainerAttribute);
    return kioskBoothContainerAttribute;
  }

  private KioskBoothContainerAttribute updateKioskBoothContainerAttribute(
      KioskBoothContainerAttribute kioskBoothContainerAttribute) {
    kioskBoothContainerAttribute.setCancelPickup(cancelPickup);
    kioskBoothContainerAttribute.setCustomCheck(customCheck);
    kioskBoothContainerAttribute.setLine(line);
    kioskBoothContainerAttribute.setLocation(location);
    kioskBoothContainerAttribute.setOthers(others);
    kioskBoothContainerAttribute.setRejectRemarks(rejectRemarks);
    kioskBoothContainerAttribute.setShipper(shipper);
    kioskBoothContainerAttribute.setStatus(status);
    return kioskBoothContainerAttribute;
  }

  public KioskBoothContainerAttribute updateContainerAttribute(
      KioskBoothContainerAttribute kioskBoothContainerAttribute) {
    return updateContainerAttribute(kioskBoothContainerAttribute);
  }



}
