/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.SolasWeightType;
import com.privasia.scss.common.enums.SolasWeightTypeSize;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_SOLAS_WEIGHT_CONFIG")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "CREATED_DATE")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "UPDATED_DATE"))})
public class SolasWeightConfig extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_SOLAS_WEIGHT_CONFIG")
  @SequenceGenerator(name = "SEQ_SCSS_SOLAS_WEIGHT_CONFIG", sequenceName = "SOLAS_WGT_CON_SEQ")
  @Column(name = "SOLAS_WGT_CON_SEQ")
  private Long solasWegConfigID;

  @Column(name = "WEIGHT_TYPE")
  @Type(type = "com.privasia.scss.common.enumusertype.SolasWeightType")
  private SolasWeightType weightType;

  @Column(name = "MIN_VALUE")
  private int minValue;

  @Column(name = "MAX_VALUE")
  private int maxValue;

  @Column(name = "DEFAULT_VALUE")
  private int defaultValue;

  @Column(name = "WEIGHT_TYPE_SIZE")
  @Type(type = "com.privasia.scss.common.enumusertype.SolasWeightTypeSizeEnumUserType")
  private SolasWeightTypeSize weightTypeSize;

  public Long getSolasWegConfigID() {
    return solasWegConfigID;
  }

  public void setSolasWegConfigID(Long solasWegConfigID) {
    this.solasWegConfigID = solasWegConfigID;
  }

  public SolasWeightType getWeightType() {
    return weightType;
  }

  public void setWeightType(SolasWeightType weightType) {
    this.weightType = weightType;
  }

  public int getMinValue() {
    return minValue;
  }

  public void setMinValue(int minValue) {
    this.minValue = minValue;
  }

  public int getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(int maxValue) {
    this.maxValue = maxValue;
  }

  public int getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(int defaultValue) {
    this.defaultValue = defaultValue;
  }

  public SolasWeightTypeSize getWeightTypeSize() {
    return weightTypeSize;
  }

  public void setWeightTypeSize(SolasWeightTypeSize weightTypeSize) {
    this.weightTypeSize = weightTypeSize;
  }



}
