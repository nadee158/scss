/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ShippingLineReportType;

/**
 * @author Janaka
 *
 */

@Entity
@Table(name = "SCSS_SHIP_EMAIL")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATEDBY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATEBY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATECREATE")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATEUPDATE"))})
public class ShipEmail extends AuditEntity implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "LINE_CODE")
  private String lineCode;

  @Column(name = "EMAIL_TO")
  private String emailTo;

  @Column(name = "EMAIL_CC")
  private String emailCC;

  @Column(name = "EMAIL_BCC")
  private String emailBCC;

  @Column(name = "PERCENTAGE")
  private BigDecimal percentage;

  @Column(name = "WEIGHT_NOTIFICATION")
  @Type(type = "yes_no")
  private boolean notifyWeight;

  @Column(name = "WRONG_DOOR_NOTIFICATION")
  @Type(type = "yes_no")
  private boolean notifyWrongDoor;

  @Column(name = "SEAL_LINE_NOTIFICATION")
  @Type(type = "yes_no")
  private boolean notifySealLine;

  @Column(name = "TYPE_CODE")
  @Enumerated(EnumType.STRING)
  private ShippingLineReportType typeCode;

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }

  public String getEmailTo() {
    return emailTo;
  }

  public void setEmailTo(String emailTo) {
    this.emailTo = emailTo;
  }

  public String getEmailCC() {
    return emailCC;
  }

  public void setEmailCC(String emailCC) {
    this.emailCC = emailCC;
  }

  public String getEmailBCC() {
    return emailBCC;
  }

  public void setEmailBCC(String emailBCC) {
    this.emailBCC = emailBCC;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public boolean isNotifyWeight() {
    return notifyWeight;
  }

  public void setNotifyWeight(boolean notifyWeight) {
    this.notifyWeight = notifyWeight;
  }

  public boolean isNotifyWrongDoor() {
    return notifyWrongDoor;
  }

  public void setNotifyWrongDoor(boolean notifyWrongDoor) {
    this.notifyWrongDoor = notifyWrongDoor;
  }

  public boolean isNotifySealLine() {
    return notifySealLine;
  }

  public void setNotifySealLine(boolean notifySealLine) {
    this.notifySealLine = notifySealLine;
  }

  public ShippingLineReportType getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(ShippingLineReportType typeCode) {
    this.typeCode = typeCode;
  }



}
