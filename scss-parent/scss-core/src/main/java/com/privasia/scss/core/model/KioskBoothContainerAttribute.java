/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.privasia.scss.core.dto.KioskBoothRightInfo;



/**
 * @author Janaka
 *
 */
@Embeddable
public class KioskBoothContainerAttribute extends CommonContainerAttribute implements Serializable {

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

  public KioskBoothContainerAttribute(KioskBoothRightInfo kioskBoothRightInfo, int containerNo) {
    updateFromKioskBoothRightInfo(kioskBoothRightInfo, containerNo);
  }

  public KioskBoothContainerAttribute() {
    super();
  }

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

  public void updateFromKioskBoothRightInfo(KioskBoothRightInfo kioskBoothRightInfo, int containerNo) {
    if (!(kioskBoothRightInfo == null)) {
      switch (containerNo) {
        case 1:
          this.setCancelPickup(kioskBoothRightInfo.getCt1CanPickup());
          this.setContainerNumber(kioskBoothRightInfo.getCt1());
          this.setLine(kioskBoothRightInfo.getCt1L());
          this.setShipper(kioskBoothRightInfo.getCt1s());
          this.setContainerISOCode(kioskBoothRightInfo.getCt1ISO());
          this.setLocation(kioskBoothRightInfo.getCt1Loc());
          this.setStatus(kioskBoothRightInfo.getCt1Status());
          //this.setContainerFullOrEmpty(kioskBoothRightInfo.getCt1fe());
          this.setOthers(kioskBoothRightInfo.getCt1Others());
          this.setRejectRemarks(kioskBoothRightInfo.getCt1RejectRemarks());
          this.setCustomCheck(kioskBoothRightInfo.getCt1CustomCheck());
          break;
        case 2:
          this.setCancelPickup(kioskBoothRightInfo.getCt2CanPickup());
          this.setContainerNumber(kioskBoothRightInfo.getCt2());
          this.setLine(kioskBoothRightInfo.getCt2L());
          this.setShipper(kioskBoothRightInfo.getCt2s());
          this.setContainerISOCode(kioskBoothRightInfo.getCt2ISO());
          this.setLocation(kioskBoothRightInfo.getCt2Loc());
          this.setStatus(kioskBoothRightInfo.getCt2Status());
          //this.setContainerFullOrEmpty(kioskBoothRightInfo.getCt2fe());
          this.setOthers(kioskBoothRightInfo.getCt2Others());
          this.setRejectRemarks(kioskBoothRightInfo.getCt2RejectRemarks());
          this.setCustomCheck(kioskBoothRightInfo.getCt2CustomCheck());
          break;
        case 3:
          this.setCancelPickup(kioskBoothRightInfo.getCt3CanPickup());
          this.setContainerNumber(kioskBoothRightInfo.getCt3());
          this.setLine(kioskBoothRightInfo.getCt3L());
          this.setShipper(kioskBoothRightInfo.getCt3s());
          this.setContainerISOCode(kioskBoothRightInfo.getCt3ISO());
          this.setLocation(kioskBoothRightInfo.getCt3Loc());
          this.setStatus(kioskBoothRightInfo.getCt3Status());
          //this.setContainerFullOrEmpty(kioskBoothRightInfo.getCt3fe());
          this.setOthers(kioskBoothRightInfo.getCt3Others());
          this.setRejectRemarks(kioskBoothRightInfo.getCt3RejectRemarks());
          this.setCustomCheck(kioskBoothRightInfo.getCt3CustomCheck());
          break;
        case 4:
          this.setCancelPickup(kioskBoothRightInfo.getCt4CanPickup());
          this.setContainerNumber(kioskBoothRightInfo.getCt4());
          this.setLine(kioskBoothRightInfo.getCt4L());
          this.setShipper(kioskBoothRightInfo.getCt4s());
          this.setContainerISOCode(kioskBoothRightInfo.getCt4ISO());
          this.setLocation(kioskBoothRightInfo.getCt4Loc());
          this.setStatus(kioskBoothRightInfo.getCt4Status());
          //this.setContainerFullOrEmpty(kioskBoothRightInfo.getCt4fe());
          this.setOthers(kioskBoothRightInfo.getCt4Others());
          this.setRejectRemarks(kioskBoothRightInfo.getCt4RejectRemarks());
          this.setCustomCheck(kioskBoothRightInfo.getCt4CustomCheck());
          break;
        default:
          break;
      }
    } else {
      this.setCancelPickup(null);
      this.setContainerNumber(null);
      this.setLine(null);
      this.setShipper(null);
      this.setContainerISOCode(null);
      this.setLocation(null);
      this.setStatus(null);
      //this.setContainerFullOrEmpty(null);
      this.setOthers(null);
      this.setRejectRemarks(null);
      this.setCustomCheck(null);
    }


  }



}
