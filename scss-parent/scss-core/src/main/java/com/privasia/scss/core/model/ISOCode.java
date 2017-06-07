/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.privasia.scss.common.dto.ISOInfo;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_ISO_CODES")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class ISOCode extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CN_ISO_CODE")
	private String isoCode;

	@Column(name = "CN_LENGTH")
	private int length;

	@Column(name = "CN_HEIGHT")
	private Double height;

	@Column(name = "CN_TYPE")
	private String type;

	@Column(name = "CN_TARE_WEIGHT")
	private int tareWeight;

	@Column(name = "CN_WIDTH")
	private int width;

	@Column(name = "CN_ISO_DESC")
	private String description;

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(int tareWeight) {
		this.tareWeight = tareWeight;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
