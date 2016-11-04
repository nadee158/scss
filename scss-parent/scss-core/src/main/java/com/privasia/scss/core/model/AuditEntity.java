/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * @author Janaka
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "ADD_BY")
  @CreatedBy
  private Long addBy;

  @Column(name = "UPDATE_BY")
  @LastModifiedBy
  private Long updateBy;

  @CreatedDate
  @Column(name = "DATETIME_ADD", nullable = false)
  @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
  private ZonedDateTime dateTimeAdd;

  @LastModifiedDate
  @Column(name = "DATETIME_UPDATE", nullable = false)
  @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
  private ZonedDateTime dateTimeUpdate;

  @Version
  @Column(name = "VERSION", nullable = true)
  private Long version;


  public AuditEntity(Long addBy, Long updateBy, ZonedDateTime dateTimeAdd, ZonedDateTime dateTimeUpdate, Long version) {
    super();
    this.addBy = addBy;
    this.updateBy = updateBy;
    this.dateTimeAdd = dateTimeAdd;
    this.dateTimeUpdate = dateTimeUpdate;
    this.version = version;
  }

  public AuditEntity() {}

  public Long getAddBy() {
	  return addBy;
  }

  public void setAddBy(Long addBy) {
	  this.addBy = addBy;
  }

  public Long getUpdateBy() {
	  return updateBy;
  }

  public void setUpdateBy(Long updateBy) {
	  this.updateBy = updateBy;
  }

  public ZonedDateTime getDateTimeAdd() {
	  return dateTimeAdd;
  }

  public void setDateTimeAdd(ZonedDateTime dateTimeAdd) {
	  this.dateTimeAdd = dateTimeAdd;
  }

  public ZonedDateTime getDateTimeUpdate() {
	  return dateTimeUpdate;
  }

  public void setDateTimeUpdate(ZonedDateTime dateTimeUpdate) {
	  this.dateTimeUpdate = dateTimeUpdate;
  }

  public Long getVersion() {
	  return version;
  }

  public void setVersion(Long version) {
	  this.version = version;
  }

  

  
}
