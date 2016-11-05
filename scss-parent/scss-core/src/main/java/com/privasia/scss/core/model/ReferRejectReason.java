/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

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


/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_REFER_REJECT_REASON")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE"))})
public class ReferRejectReason extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_REFER_REJECT_REASON")
  @SequenceGenerator(name = "SEQ_SCSS_REFER_REJECT_REASON", sequenceName = "SEQ_SCSS_REFER_REJECT_REASON")
  @Column(name = "REFER_REJECT_REASON_ID")
  private Long referRejectReasonID;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "REFER_REJECT_DET_ID", nullable = false)
  private ReferRejectDetail referRejectDetail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REF_REASON_ID", nullable = false)
  private ReferReason referReason;

  public Long getReferRejectReasonID() {
    return referRejectReasonID;
  }

  public void setReferRejectReasonID(Long referRejectReasonID) {
    this.referRejectReasonID = referRejectReasonID;
  }

  public ReferRejectDetail getReferRejectDetail() {
    return referRejectDetail;
  }

  public void setReferRejectDetail(ReferRejectDetail referRejectDetail) {
    this.referRejectDetail = referRejectDetail;
  }

  public ReferReason getReferReason() {
    return referReason;
  }

  public void setReferReason(ReferReason referReason) {
    this.referReason = referReason;
  }
}
