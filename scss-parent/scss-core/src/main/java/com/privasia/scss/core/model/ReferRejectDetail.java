/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.privasia.scss.core.util.constant.ContainerPosition;
import com.privasia.scss.core.util.constant.ReferStatus;

/**
 * @author Janaka
 *
 */

@Entity
@Table(name = "SCSS_REFER_REJECT_DET")
public class ReferRejectDetail extends AuditEntity implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_REFER_REJECT_DET")
  @SequenceGenerator(name = "SEQ_SCSS_REFER_REJECT_DET", sequenceName = "SEQ_SCSS_REFER_REJECT_DET")
  @Column(name = "REFER_REJECT_DET_ID")
  private Long referRejectDetailID;

  @Column(name = "CONT_NO")
  private String containerNo;

  @Column(name = "CONT_ISO_CODE")
  private String containerIsoCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REFER_ID", nullable = false)
  private ReferReject referReject;

  @Embedded
  private CommonSealAttribute seal;

  @Column(name = "REMARKS")
  private String remarks;

  @Column(name = "STATUS_CODE")
  @Enumerated(EnumType.STRING)
  private ReferStatus status;

  @Column(name = "SUP_REMARKS")
  private String supervisorRemarks;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REJECT_BY", nullable = false, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser rejectBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REFER_BY", nullable = false, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser referBy;

  @Column(name = "EXP_PM_BTM")
  private int expPmBTM;

  @Column(name = "EXP_NET_WEIGHT")
  private int expNetWeight;

  @Column(name = "DOUBLE_BOOKING")
  private String doubleBooking;

  @Column(name = "LINE_CODE")
  private String lineCode;

  @Column(name = "GATE_IN_DATETIME")
  // @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime gateInTime;

  @Column(name = "POSITION")
  @Enumerated(EnumType.STRING)
  private ContainerPosition position;

  @Column(name = "MEASURED_WEIGHT_BRIDGE")
  private int measuredWeightBridge;

  @Embedded
  private CommonSolasAttribute solas;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "referRejectDetail")
  private Set<ReferRejectReason> referRejectReason;

  public Long getReferRejectDetailID() {
    return referRejectDetailID;
  }

  public void setReferRejectDetailID(Long referRejectDetailID) {
    this.referRejectDetailID = referRejectDetailID;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getContainerIsoCode() {
    return containerIsoCode;
  }

  public void setContainerIsoCode(String containerIsoCode) {
    this.containerIsoCode = containerIsoCode;
  }

  public ReferReject getReferReject() {
    return referReject;
  }

  public void setReferReject(ReferReject referReject) {
    this.referReject = referReject;
  }

  public CommonSealAttribute getSeal() {
    return seal;
  }

  public void setSeal(CommonSealAttribute seal) {
    this.seal = seal;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public ReferStatus getStatus() {
    return status;
  }

  public void setStatus(ReferStatus status) {
    this.status = status;
  }

  public String getSupervisorRemarks() {
    return supervisorRemarks;
  }

  public void setSupervisorRemarks(String supervisorRemarks) {
    this.supervisorRemarks = supervisorRemarks;
  }

  public SystemUser getRejectBy() {
    return rejectBy;
  }

  public void setRejectBy(SystemUser rejectBy) {
    this.rejectBy = rejectBy;
  }

  public SystemUser getReferBy() {
    return referBy;
  }

  public void setReferBy(SystemUser referBy) {
    this.referBy = referBy;
  }

  public int getExpPmBTM() {
    return expPmBTM;
  }

  public void setExpPmBTM(int expPmBTM) {
    this.expPmBTM = expPmBTM;
  }

  public int getExpNetWeight() {
    return expNetWeight;
  }

  public void setExpNetWeight(int expNetWeight) {
    this.expNetWeight = expNetWeight;
  }

  public String getDoubleBooking() {
    return doubleBooking;
  }

  public void setDoubleBooking(String doubleBooking) {
    this.doubleBooking = doubleBooking;
  }

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }

  public LocalDateTime getGateInTime() {
    return gateInTime;
  }

  public void setGateInTime(LocalDateTime gateInTime) {
    this.gateInTime = gateInTime;
  }

  public ContainerPosition getPosition() {
    return position;
  }

  public void setPosition(ContainerPosition position) {
    this.position = position;
  }

  public int getMeasuredWeightBridge() {
    return measuredWeightBridge;
  }

  public void setMeasuredWeightBridge(int measuredWeightBridge) {
    this.measuredWeightBridge = measuredWeightBridge;
  }

  public CommonSolasAttribute getSolas() {
    return solas;
  }

  public void setSolas(CommonSolasAttribute solas) {
    this.solas = solas;
  }

  public Set<ReferRejectReason> getReferRejectReason() {
    return referRejectReason;
  }

  public void setReferRejectReason(Set<ReferRejectReason> referRejectReason) {
    this.referRejectReason = referRejectReason;
  }

}
