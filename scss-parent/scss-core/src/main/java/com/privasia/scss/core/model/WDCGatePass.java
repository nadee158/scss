/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "WDC_GATE_PASS")
public class WDCGatePass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GATE_PASS_NO")
	private Long gatePassNO;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GATE_ORDER_NO", nullable = false, referencedColumnName = "GATE_ORDER_NO")
	private WDCGateOrder gateOrder;

	@Column(name = "GCS_DELCARENO")
	private String gcsDelcarerNo;

	@Column(name = "CONT_LENGTH")
	private Long containerLength;

	@Column(name = "CONT_TYPE")
	private String containerType;

	@Column(name = "GCS_BLOCK")
	private String gcsBlock;

	@Column(name = "PKFZ_BLOCK")
	private String pkfzBlock;

	@Column(name = "LPK_BLOCK")
	private String lpkBlock;

	@Column(name = "CUSTOMS_GCS_RLS_DATE")
	private LocalDateTime cusGCSReleaseDate;

	@Column(name = "DATETIME_ADD")
	private LocalDateTime dateTimeADD;

	@Column(name = "CONT_ISO_CODE")
	private String isoCode;

	@Column(name = "DATE_GATEPASS_VALID")
	private LocalDateTime gatePassValidDate;

	@Column(name = "EDO_EXPIRY_DATE")
	private LocalDateTime edoExpiryDate;

	public Long getGatePassNO() {
		return gatePassNO;
	}

	public void setGatePassNO(Long gatePassNO) {
		this.gatePassNO = gatePassNO;
	}

	public WDCGateOrder getGateOrder() {
		return gateOrder;
	}

	public void setGateOrder(WDCGateOrder gateOrder) {
		this.gateOrder = gateOrder;
	}

	public String getGcsDelcarerNo() {
		return gcsDelcarerNo;
	}

	public void setGcsDelcarerNo(String gcsDelcarerNo) {
		this.gcsDelcarerNo = gcsDelcarerNo;
	}

	public Long getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(Long containerLength) {
		this.containerLength = containerLength;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getGcsBlock() {
		return gcsBlock;
	}

	public void setGcsBlock(String gcsBlock) {
		this.gcsBlock = gcsBlock;
	}

	public String getPkfzBlock() {
		return pkfzBlock;
	}

	public void setPkfzBlock(String pkfzBlock) {
		this.pkfzBlock = pkfzBlock;
	}

	public String getLpkBlock() {
		return lpkBlock;
	}

	public void setLpkBlock(String lpkBlock) {
		this.lpkBlock = lpkBlock;
	}

	public LocalDateTime getCusGCSReleaseDate() {
		return cusGCSReleaseDate;
	}

	public void setCusGCSReleaseDate(LocalDateTime cusGCSReleaseDate) {
		this.cusGCSReleaseDate = cusGCSReleaseDate;
	}

	public LocalDateTime getDateTimeADD() {
		return dateTimeADD;
	}

	public void setDateTimeADD(LocalDateTime dateTimeADD) {
		this.dateTimeADD = dateTimeADD;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public LocalDateTime getGatePassValidDate() {
		return gatePassValidDate;
	}

	public void setGatePassValidDate(LocalDateTime gatePassValidDate) {
		this.gatePassValidDate = gatePassValidDate;
	}

	public LocalDateTime getEdoExpiryDate() {
		return edoExpiryDate;
	}

	public void setEdoExpiryDate(LocalDateTime edoExpiryDate) {
		this.edoExpiryDate = edoExpiryDate;
	}

}
