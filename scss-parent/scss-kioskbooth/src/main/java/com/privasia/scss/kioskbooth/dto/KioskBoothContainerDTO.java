package com.privasia.scss.kioskbooth.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.core.model.KioskBoothContainerAttribute;

public class KioskBoothContainerDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String cancelPickup = StringUtils.EMPTY;

  private String location = StringUtils.EMPTY;

  private String status = StringUtils.EMPTY;

  private String others = StringUtils.EMPTY;

  private String rejectRemarks = StringUtils.EMPTY;

  private String customCheck = StringUtils.EMPTY;

  private String shipper = StringUtils.EMPTY;

  private String line = StringUtils.EMPTY;

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

  @Override
  public String toString() {
    return "KioskBoothContainerDTO [cancelPickup=" + cancelPickup + ", location=" + location + ", status=" + status
        + ", others=" + others + ", rejectRemarks=" + rejectRemarks + ", customCheck=" + customCheck + ", shipper="
        + shipper + ", line=" + line + "]";
  }



}
