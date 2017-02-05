package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ShipStatus;

@Entity
@Table(name = "SCSS_SHIP_CODE")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "SHP_DATECREATE")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "SHP_DATEUPDATE"))})
public class ShipCode extends AuditEntity implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "SHP_SHIPPINGCODE")
  private String shippingCode;

  @Column(name = "SHP_STORAGEPERIOD")
  private int storagePeriod;

  @Column(name = "SHP_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.ShipStatusEnumUserType")
  private ShipStatus shipStatus;

  public String getShippingCode() {
    return shippingCode;
  }

  public void setShippingCode(String shippingCode) {
    this.shippingCode = shippingCode;
  }

  public int getStoragePeriod() {
    return storagePeriod;
  }

  public void setStoragePeriod(int storagePeriod) {
    this.storagePeriod = storagePeriod;
  }

  public ShipStatus getShipStatus() {
    return shipStatus;
  }

  public void setShipStatus(ShipStatus shipStatus) {
    this.shipStatus = shipStatus;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
