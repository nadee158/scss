package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SCSS_SHIP_SCN")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "SCN_CREATEDBY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "SCN_UPDATEDBY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "SCN_DATECREATE")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "SCN_DATEUPDATE"))})
public class ShipSCN extends AuditEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_SHIP_SCN")
  @SequenceGenerator(name = "SEQ_SCSS_SHIP_SCN", sequenceName = "SCN_SEQ")
  @Column(name = "SCN_SEQ")
  private Long shipSCNID;

  @Column(name = "SCN_SCNNO")
  private String scnNo;

  @Column(name = "SCN_CONTAINERNO")
  private String containerNo;

  @Column(name = "SCN_CHARGEABLE")
  @Type(type = "yes_no")
  private Boolean scnChargebale;

  @Column(name = "SCN_SHIPPER")
  private String scnShipper;

  @Column(name = "SCN_BYPASS_EENTRY")
  @Type(type = "yes_no")
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



}
