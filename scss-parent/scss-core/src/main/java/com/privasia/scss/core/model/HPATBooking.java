/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.HpatReferStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "ETP_BOOKING_HPAT")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE"))})
public class HPATBooking extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "BOOKING_ID")
  private String bookingID;

  @Column(name = "APPT_DATE_START")
  private LocalDateTime appointmentStartDate;

  @Column(name = "BUFFER")
  private String buffer;

  @Column(name = "CRD_SCARDNO")
  private String cardNo;

  @Column(name = "CREATION_DATE")
  private LocalDateTime hpatCreationDate;

  @Column(name = "DRIVER_NAME")
  private String driverName;

  @Column(name = "DRIVER_IC_PP")
  private String driverICNumber;

  @Column(name = "LAST_MODIFIED_DATE")
  private LocalDateTime hpatLastModifiedDate;

  @Column(name = "PM_NO")
  private String pmNumber;

  @Column(name = "STATUS_CODE")
  @Type(type="com.privasia.scss.core.util.enumusertype.HPATReferStatusEnumUserType")
  private HpatReferStatus status;

  @Column(name = "TRAILER_NO")
  private String trailerNo;

  @Column(name = "TRAILER_TYPE")
  private String trailerType;

  @Column(name = "HAULIER_CODE")
  private String haulierCode;

  @Column(name = "APPT_DATE_END")
  private LocalDateTime appointmentEndDate;

  @Column(name = "PM_HEAD_WEIGHT")
  private String pmWeight;

  @Column(name = "AXLE_WEIGHT")
  private String axleWeight;

  @Column(name = "TRAILER_PLATE")
  private String trailerPlate;

  @Column(name = "AXLE_VERIFIED")
  @Type(type = "yes_no")
  private boolean axleVerified;

  @Column(name = "PM_VERIFIED")
  @Type(type = "yes_no")
  private boolean pmVerified;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "hpatBooking")
  private Set<HPATBookingDetail> hpatBookingDetails;

  public String getBookingID() {
    return bookingID;
  }


  public void setBookingID(String bookingID) {
    this.bookingID = bookingID;
  }


  public LocalDateTime getAppointmentStartDate() {
    return appointmentStartDate;
  }


  public void setAppointmentStartDate(LocalDateTime appointmentStartDate) {
    this.appointmentStartDate = appointmentStartDate;
  }


  public String getBuffer() {
    return buffer;
  }


  public void setBuffer(String buffer) {
    this.buffer = buffer;
  }


  public String getCardNo() {
    return cardNo;
  }


  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }


  public LocalDateTime getHpatCreationDate() {
    return hpatCreationDate;
  }


  public void setHpatCreationDate(LocalDateTime hpatCreationDate) {
    this.hpatCreationDate = hpatCreationDate;
  }


  public String getDriverName() {
    return driverName;
  }


  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }


  public String getDriverICNumber() {
    return driverICNumber;
  }


  public void setDriverICNumber(String driverICNumber) {
    this.driverICNumber = driverICNumber;
  }


  public LocalDateTime getHpatLastModifiedDate() {
    return hpatLastModifiedDate;
  }


  public void setHpatLastModifiedDate(LocalDateTime hpatLastModifiedDate) {
    this.hpatLastModifiedDate = hpatLastModifiedDate;
  }


  public String getPmNumber() {
    return pmNumber;
  }


  public void setPmNumber(String pmNumber) {
    this.pmNumber = pmNumber;
  }


  public HpatReferStatus getStatus() {
    return status;
  }


  public void setStatus(HpatReferStatus status) {
    this.status = status;
  }


  public String getTrailerNo() {
    return trailerNo;
  }


  public void setTrailerNo(String trailerNo) {
    this.trailerNo = trailerNo;
  }


  public String getTrailerType() {
    return trailerType;
  }


  public void setTrailerType(String trailerType) {
    this.trailerType = trailerType;
  }


  public String getHaulierCode() {
    return haulierCode;
  }


  public void setHaulierCode(String haulierCode) {
    this.haulierCode = haulierCode;
  }


  public LocalDateTime getAppointmentEndDate() {
    return appointmentEndDate;
  }


  public void setAppointmentEndDate(LocalDateTime appointmentEndDate) {
    this.appointmentEndDate = appointmentEndDate;
  }


  public String getPmWeight() {
    return pmWeight;
  }


  public void setPmWeight(String pmWeight) {
    this.pmWeight = pmWeight;
  }


  public String getAxleWeight() {
    return axleWeight;
  }


  public void setAxleWeight(String axleWeight) {
    this.axleWeight = axleWeight;
  }


  public String getTrailerPlate() {
    return trailerPlate;
  }


  public void setTrailerPlate(String trailerPlate) {
    this.trailerPlate = trailerPlate;
  }


  public boolean isAxleVerified() {
    return axleVerified;
  }


  public void setAxleVerified(boolean axleVerified) {
    this.axleVerified = axleVerified;
  }


  public boolean isPmVerified() {
    return pmVerified;
  }


  public void setPmVerified(boolean pmVerified) {
    this.pmVerified = pmVerified;
  }


  public Set<HPATBookingDetail> getHpatBookingDetails() {
    return hpatBookingDetails;
  }


  public void setHpatBookingDetails(Set<HPATBookingDetail> hpatBookingDetails) {
    this.hpatBookingDetails = hpatBookingDetails;
  }


}
