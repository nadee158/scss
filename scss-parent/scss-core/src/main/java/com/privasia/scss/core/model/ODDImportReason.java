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
@Table(name = "SCSS_ODD_IMPORT_REASON")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE"))})
public class ODDImportReason extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_ODD_IMPORT_REASON")
  @SequenceGenerator(name = "SEQ_SCSS_ODD_IMPORT_REASON", sequenceName = "SEQ_ODD_IMPORT_REASON")
  @Column(name = "ODD_IMPORT_REASON_SEQ")
  private Long oddImportReasonID;

  @Column(name = "ODD_IMPORT_REASON")
  private String importReason;

  public Long getOddImportReasonID() {
    return oddImportReasonID;
  }

  public void setOddImportReasonID(Long oddImportReasonID) {
    this.oddImportReasonID = oddImportReasonID;
  }

  public String getImportReason() {
    return importReason;
  }

  public void setImportReason(String importReason) {
    this.importReason = importReason;
  }



}
