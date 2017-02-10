package com.privasia.scss.common.dto;

import java.io.Serializable;

public class ShipSCNDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long shipSCNID;

  private String scnNo;

  private String containerNo;

  private Boolean scnChargebale;

  private String scnShipper;

  private Boolean scnByPass;

  public Long getShipSCNID() {
    return shipSCNID;
  }

  public void setShipSCNID(Long shipSCNID) {
    this.shipSCNID = shipSCNID;
  }

  public String getScnNo() {
    return scnNo;
  }

  public void setScnNo(String scnNo) {
    this.scnNo = scnNo;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public Boolean getScnChargebale() {
    return scnChargebale;
  }

  public void setScnChargebale(Boolean scnChargebale) {
    this.scnChargebale = scnChargebale;
  }

  public String getScnShipper() {
    return scnShipper;
  }

  public void setScnShipper(String scnShipper) {
    this.scnShipper = scnShipper;
  }

  public Boolean getScnByPass() {
    return scnByPass;
  }

  public void setScnByPass(Boolean scnByPass) {
    this.scnByPass = scnByPass;
  }

  @Override
  public String toString() {
    return "ShipSCNDTO [shipSCNID=" + shipSCNID + ", scnNo=" + scnNo + ", containerNo=" + containerNo
        + ", scnChargebale=" + scnChargebale + ", scnShipper=" + scnShipper + ", scnByPass=" + scnByPass + "]";
  }



}
