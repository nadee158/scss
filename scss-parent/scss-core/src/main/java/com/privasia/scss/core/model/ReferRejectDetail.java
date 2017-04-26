/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.ReferStatus;

/**
 * @author Janaka
 *
 */

@Entity
@Table(name = "SCSS_REFER_REJECT_DET")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY") ),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY") ),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD") ),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE") )})
public class ReferRejectDetail extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_REFER_REJECT_DET")
  @SequenceGenerator(name = "SEQ_SCSS_REFER_REJECT_DET", sequenceName = "SEQ_SCSS_REFER_REJECT_DET", allocationSize = 1)
  @Column(name = "REFER_REJECT_DET_ID")
  private Long referRejectDetailID;

  @Column(name = "CONT_NO")
  private String containerNo;

  @Column(name = "CONT_ISO_CODE")
  private String containerIsoCode;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "REFER_ID", nullable = false)
  private ReferReject referReject;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "seal01Origin", column = @Column(name = "EXP_SEAL_1_ORIGIN") ),
      @AttributeOverride(name = "seal01Type", column = @Column(name = "EXP_SEAL_1_TYPE") ),
      @AttributeOverride(name = "seal01Number", column = @Column(name = "EXP_SEAL_1_NUMBER") ),
      @AttributeOverride(name = "seal02Origin", column = @Column(name = "EXP_SEAL_2_ORIGIN") ),
      @AttributeOverride(name = "seal02Type", column = @Column(name = "EXP_SEAL_2_TYPE") ),
      @AttributeOverride(name = "seal02Number", column = @Column(name = "EXP_SEAL_2_NUMBER") )})
  private CommonSealAttribute seal;

  @Column(name = "REMARKS")
  private String remarks;

  @Column(name = "STATUS_CODE")
  @Type(type = "com.privasia.scss.common.enumusertype.ReferStatusEnumUserType")
  private ReferStatus status;

  @Column(name = "SUP_REMARKS")
  private String supervisorRemarks;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REJECT_BY", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser rejectBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REFER_BY", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
  private SystemUser referBy;

  @Column(name = "EXP_PM_BTM", nullable = true)
  private Integer expPmBTM;

  @Column(name = "EXP_NET_WEIGHT", nullable = true)
  private Integer expNetWeight;

  @Column(name = "DOUBLE_BOOKING", nullable = true)
  @Type(type = "yes_no")
  private Boolean doubleBooking;

  @Column(name = "LINE_CODE")
  private String lineCode;

  @Column(name = "GATE_IN_DATETIME")
  private LocalDateTime gateInTime;

  @Column(name = "POSITION", nullable = true)
  @Type(type = "com.privasia.scss.common.enumusertype.ContainerPositionEnumUserType")
  private ContainerPosition position;

  @Column(name = "MEASURED_WEIGHT_BRIDGE")
  private Integer measuredWeightBridge;

  @Embedded
  private CommonSolasAttribute solas;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "referRejectDetail", cascade = CascadeType.ALL)
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
    if (StringUtils.isNotEmpty(containerNo)) {
      containerNo = StringUtils.upperCase(containerNo);
    }
    this.containerNo = containerNo;
  }

  public String getContainerIsoCode() {
    return containerIsoCode;
  }

  public void setContainerIsoCode(String containerIsoCode) {
    if (StringUtils.isNotEmpty(containerIsoCode)) {
      containerIsoCode = StringUtils.upperCase(containerIsoCode);
    }
    this.containerIsoCode = containerIsoCode;
  }

  public ReferReject getReferReject() {
    return referReject;
  }

  public void setReferReject(ReferReject referReject) {
    this.referReject = referReject;
  }

  public Optional<CommonSealAttribute> getSeal() {
    return Optional.ofNullable(seal);
  }

  public void setSeal(Optional<CommonSealAttribute> optSeal) {
    this.seal = optSeal.orElse(null);
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    if (StringUtils.isNotEmpty(remarks)) {
      remarks = StringUtils.upperCase(remarks);
    }
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
    if (StringUtils.isNotEmpty(supervisorRemarks)) {
      supervisorRemarks = StringUtils.upperCase(supervisorRemarks);
    }
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

  public Integer getExpPmBTM() {
    return expPmBTM;
  }

  public void setExpPmBTM(Integer expPmBTM) {
    this.expPmBTM = expPmBTM;
  }

  public Integer getExpNetWeight() {
    return expNetWeight;
  }

  public void setExpNetWeight(Integer expNetWeight) {
    this.expNetWeight = expNetWeight;
  }

  public Optional<Boolean> getDoubleBooking() {
    return Optional.ofNullable(doubleBooking);
  }

  public void setDoubleBooking(Optional<Boolean> optDoubleBooking) {
    this.doubleBooking = optDoubleBooking.orElse(false);
  }

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    if (StringUtils.isNotEmpty(lineCode)) {
      lineCode = StringUtils.upperCase(lineCode);
    }
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

  public Integer getMeasuredWeightBridge() {
    return measuredWeightBridge;
  }

  public void setMeasuredWeightBridge(Integer measuredWeightBridge) {
    this.measuredWeightBridge = measuredWeightBridge;
  }

  public Optional<CommonSolasAttribute> getSolas() {
    return Optional.ofNullable(solas);
  }

  public void setSolas(Optional<CommonSolasAttribute> optSolas) {
    this.solas = optSolas.orElse(null);
  }

  public Optional<Set<ReferRejectReason>> getReferRejectReason() {
    return Optional.ofNullable(referRejectReason);
  }

  public void setReferRejectReason(Optional<Set<ReferRejectReason>> optReferRejectReason) {
    this.referRejectReason = optReferRejectReason.orElse(null);
  }

}
