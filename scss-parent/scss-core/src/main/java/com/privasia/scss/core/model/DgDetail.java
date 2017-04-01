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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SCSS_DG_DETAIL")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATE_BY") ),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY") ),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "CREATE_DATE") ),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "UPDATE_DATE") )})
public class DgDetail extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_DG")
  @SequenceGenerator(name = "SEQ_SCSS_DG", sequenceName = "DG_SEQ")
  @Column(name = "SEQ_DG")
  private Long dgDetailId;

  @Column(name = "CONTAINER_NO")
  private String containerNo;

  @Column(name = "SCN")
  private String scn;

  @Column(name = "REMARKS")
  private String remarks;

  @Column(name = "SHIPPER")
  private String shipper;

  @Column(name = "CHARGEABLE")
  @Type(type = "yes_no")
  private Boolean chargeable;

  public Long getDgDetailId() {
    return dgDetailId;
  }

  public void setDgDetailId(Long dgDetailId) {
    this.dgDetailId = dgDetailId;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getScn() {
    return scn;
  }

  public void setScn(String scn) {
    this.scn = scn;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getShipper() {
    return shipper;
  }

  public void setShipper(String shipper) {
    this.shipper = shipper;
  }

  public Boolean getChargeable() {
    return chargeable;
  }

  public void setChargeable(Boolean chargeable) {
    this.chargeable = chargeable;
  }

  @Override
  public String toString() {
    return "DgDetail [dgDetailId=" + dgDetailId + ", containerNo=" + containerNo + ", scn=" + scn + ", remarks="
        + remarks + ", shipper=" + shipper + ", chargeable=" + chargeable + "]";
  }



}
