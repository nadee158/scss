/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

import com.privasia.scss.common.enums.RecordStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_REFER_REASON")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATEDBY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATEDBY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_ADD")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class ReferReason extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_REFER_REASON")
  @SequenceGenerator(name = "SEQ_SCSS_REFER_REASON", sequenceName = "SEQ_REFER_REASON", allocationSize = 1)
  @Column(name = "REF_REASON_ID")
  private Long referReasonID;

  @Column(name = "REF_REASON_DESC")
  private String reasonDescription;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID", nullable = true, referencedColumnName = "REF_REASON_ID")
  private ReferReason referReason;

  @Column(name = "SORT_SEQ")
  private int sortSEQ;

  @Column(name = "REFER_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.RecordStatusEnumUserType")
  private RecordStatus referStatus;

  @Column(name = "IS_PARENT")
  @Type(type = "yes_no")
  private boolean parent;

  public Long getReferReasonID() {
    return referReasonID;
  }

  public void setReferReasonID(Long referReasonID) {
    this.referReasonID = referReasonID;
  }

  public String getReasonDescription() {
    return reasonDescription;
  }

  public void setReasonDescription(String reasonDescription) {
    this.reasonDescription = reasonDescription;
  }


  public ReferReason getReferReason() {
    return referReason;
  }

  public void setReferReason(ReferReason referReason) {
    this.referReason = referReason;
  }

  public int getSortSEQ() {
    return sortSEQ;
  }

  public void setSortSEQ(int sortSEQ) {
    this.sortSEQ = sortSEQ;
  }

  public RecordStatus getReferStatus() {
    return referStatus;
  }

  public void setReferStatus(RecordStatus referStatus) {
    this.referStatus = referStatus;
  }

  public boolean isParent() {
    return parent;
  }

  public void setParent(boolean parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    return "{'referReasonID':" + referReasonID + ", 'reasonDescription':" + reasonDescription + "}";
  }



}
