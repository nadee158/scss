/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;



/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_ETP_SOLAS_LOG")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class ETPSolasLog extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_ETP_SOLAS_LOG")
  @SequenceGenerator(name = "SEQ_SCSS_ETP_SOLAS_LOG", sequenceName = "ETP_SOLAS_SEQ")
  @Column(name = "ETP_SOLAS_SEQ")
  private Long etpSolasID;

  @Column(name = "WEIGHING_METHOD")
  private int weighingMethod;

  @Column(name = "WEIGHING_STATION")
  private String weighingStation;

  @Column(name = "TIME_GATE_IN_OK")
  // @Temporal(TemporalType.TIMESTAMP)
  @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
  private ZonedDateTime timeGateInOk;

  @Column(name = "CERTIFICATE_NO")
  private String certificateNumber;

  @Column(name = "CER_FILE_DATA")
  @Type(type = "yes_no")
  private boolean filedata;

  @Column(name = "WITHNESS_NAME")
  private String withnessName;

  @Column(name = "WITHNESS_NRIC")
  private String withnessNRIC;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "etpSolasLog")
  private Set<ETPSolasLogDetail> etpSolasLogDetails;


  public Long getEtpSolasID() {
    return etpSolasID;
  }

  public void setEtpSolasID(Long etpSolasID) {
    this.etpSolasID = etpSolasID;
  }

  public int getWeighingMethod() {
    return weighingMethod;
  }

  public void setWeighingMethod(int weighingMethod) {
    this.weighingMethod = weighingMethod;
  }

  public String getWeighingStation() {
    return weighingStation;
  }

  public void setWeighingStation(String weighingStation) {
    this.weighingStation = weighingStation;
  }

  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public boolean isFiledata() {
    return filedata;
  }

  public void setFiledata(boolean filedata) {
    this.filedata = filedata;
  }

  public String getWithnessName() {
    return withnessName;
  }

  public void setWithnessName(String withnessName) {
    this.withnessName = withnessName;
  }

  public String getWithnessNRIC() {
    return withnessNRIC;
  }

  public void setWithnessNRIC(String withnessNRIC) {
    this.withnessNRIC = withnessNRIC;
  }

  public ZonedDateTime getTimeGateInOk() {
    return timeGateInOk;
  }

  public void setTimeGateInOk(ZonedDateTime timeGateInOk) {
    this.timeGateInOk = timeGateInOk;
  }

  public Set<ETPSolasLogDetail> getEtpSolasLogDetails() {
    return etpSolasLogDetails;
  }

  public void setEtpSolasLogDetails(Set<ETPSolasLogDetail> etpSolasLogDetails) {
    this.etpSolasLogDetails = etpSolasLogDetails;
  }


}
