/**
 * 
 */
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



/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_ODD_EXPORT_REASON")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE"))})
public class ODDExportReason extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_ODD_EXPORT_REASON")
  @SequenceGenerator(name = "SEQ_SCSS_ODD_EXPORT_REASON", sequenceName = "SEQ_ODD_EXPORT_REASON")
  @Column(name = "ODD_EXPORT_REASON_SEQ")
  private Long oddExportReasonID;

  @Column(name = "ODD_EXPORT_REASON")
  private String exportReason;

  public Long getOddExportReasonID() {
    return oddExportReasonID;
  }

  public void setOddExportReasonID(Long oddExportReasonID) {
    this.oddExportReasonID = oddExportReasonID;
  }

  public String getExportReason() {
    return exportReason;
  }

  public void setExportReason(String exportReason) {
    this.exportReason = exportReason;
  }



}
