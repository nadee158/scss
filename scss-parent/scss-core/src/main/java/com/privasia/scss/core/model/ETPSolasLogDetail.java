/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_ETP_SOLAS_LOG_DETAIL")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY") ),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY") ),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED") ),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE") )})
public class ETPSolasLogDetail extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_ETP_SOLAS_LOG_DETAIL")
  @SequenceGenerator(name = "SEQ_SCSS_ETP_SOLAS_LOG_DETAIL", sequenceName = "ETP_SOLAS_DEL_SEQ", allocationSize = 1)
  @Column(name = "ETP_SOLAS_DEL_SEQ")
  private Long etpSolasDetailID;

  @Column(name = "SOLAS_DETAIL")
  private String solasDetail;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "EXP_SEQ", nullable = false, referencedColumnName = "EXP_EXPORTNO_SEQ")
  private Exports export;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "ETP_SOLAS_LOG", nullable = false, referencedColumnName = "ETP_SOLAS_SEQ")
  private ETPSolasLog etpSolasLog;

  @Column(name = "TERMINAL_VGM")
  private int terminalVGM;

  @Column(name = "SHIPPER_VGM")
  private int shipperVGM;

  @Column(name = "MGW")
  private int mgw;

  @Column(name = "WITHIN_TOLERANCE")
  @Type(type = "yes_no")
  private boolean withInTolerance;

  @Column(name = "CAL_VARIANCE")
  private BigDecimal calculatedVariance;

  @Column(name = "GROSS_WEIGHT")
  private int grossWeight;

  @Column(name = "REQUEST_SEND_TIME")
  private LocalDateTime requestSendTime;

  @Column(name = "RES_RECEIVED_TIME")
  private LocalDateTime responseReceivedTime;

  @Column(name = "RESPONSE_CODE")
  private String responseCode;

  @Column(name = "RESPONSE_MESSAGE")
  private String responseMessage;

  public Long getEtpSolasDetailID() {
    return etpSolasDetailID;
  }

  public void setEtpSolasDetailID(Long etpSolasDetailID) {
    this.etpSolasDetailID = etpSolasDetailID;
  }

  public String getSolasDetail() {
    return solasDetail;
  }

  public void setSolasDetail(String solasDetail) {
    this.solasDetail = solasDetail;
  }

  public Exports getExport() {
    return export;
  }

  public void setExport(Exports export) {
    this.export = export;
  }

  public ETPSolasLog getEtpSolasLog() {
    return etpSolasLog;
  }

  public void setEtpSolasLog(ETPSolasLog etpSolasLog) {
    this.etpSolasLog = etpSolasLog;
  }

  public int getTerminalVGM() {
    return terminalVGM;
  }

  public void setTerminalVGM(int terminalVGM) {
    this.terminalVGM = terminalVGM;
  }

  public int getShipperVGM() {
    return shipperVGM;
  }

  public void setShipperVGM(int shipperVGM) {
    this.shipperVGM = shipperVGM;
  }

  public int getMgw() {
    return mgw;
  }

  public void setMgw(int mgw) {
    this.mgw = mgw;
  }

  public boolean isWithInTolerance() {
    return withInTolerance;
  }

  public void setWithInTolerance(boolean withInTolerance) {
    this.withInTolerance = withInTolerance;
  }

  public BigDecimal getCalculatedVariance() {
    return calculatedVariance;
  }

  public void setCalculatedVariance(BigDecimal calculatedVariance) {
    this.calculatedVariance = calculatedVariance;
  }

  public int getGrossWeight() {
    return grossWeight;
  }

  public void setGrossWeight(int grossWeight) {
    this.grossWeight = grossWeight;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  public LocalDateTime getRequestSendTime() {
    return requestSendTime;
  }

  public void setRequestSendTime(LocalDateTime requestSendTime) {
    this.requestSendTime = requestSendTime;
  }

  public LocalDateTime getResponseReceivedTime() {
    return responseReceivedTime;
  }

  public void setResponseReceivedTime(LocalDateTime responseReceivedTime) {
    this.responseReceivedTime = responseReceivedTime;
  }

}
