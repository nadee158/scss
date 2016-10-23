/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.privasia.scss.core.util.constant.SolasWeightType;
import com.privasia.scss.core.util.constant.SolasWeightTypeSize;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_SOLAS_WEIGHT_CONFIG_LOG")
public class SolasWeightConfigLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_SOLAS_WEIGHT_CONFIG_LOG")
    @SequenceGenerator(name = "SEQ_SCSS_SOLAS_WEIGHT_CONFIG_LOG", sequenceName = "SOLAS_WGTCON_LOG_SEQ")
	@Column(name = "SOLAS_WGTCON_LOG_SEQ")
	private Long solasWegConfigLogID;
	
	@Column(name = "WEIGHT_TYPE")
	@Enumerated(EnumType.STRING) 
	private SolasWeightType weightType;
	
	@Column(name = "MIN_VALUE")
	private int minValue;
	
	@Column(name = "MAX_VALUE")
	private int maxValue;
	
	@Column(name = "DEFAULT_VALUE")
	private int defaultValue;
	
	@Column(name = "WEIGHT_TYPE_SIZE")
	@Enumerated(EnumType.STRING) 
	private SolasWeightTypeSize weightTypeSize;
	
	@Column(name = "CREATED_BY")
	private long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdDate;

	public Long getSolasWegConfigLogID() {
		return solasWegConfigLogID;
	}

	public void setSolasWegConfigLogID(Long solasWegConfigLogID) {
		this.solasWegConfigLogID = solasWegConfigLogID;
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

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	
	
}
