/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.BillingStatus;
import com.privasia.scss.common.enums.ContainerPosition;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "WDC_TERM_WEIGHING_DETAIL")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class WDCTermWeighingDetail extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WDC_TERM_WEIGHING_DETAIL")
	@SequenceGenerator(name = "SEQ_WDC_TERM_WEIGHING_DETAIL", sequenceName = "SEQ_WDC_TERM_WG_DETAIL", allocationSize = 1)
	@Column(name = "TW_DETAIL_NO")
	private Long termWeighingDetailID;

	@Column(name = "EXP_BOOKING_NO")
	private String bookingNo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "HPAT_BOOKING_ID", nullable = true, referencedColumnName = "BOOKING_ID")
	private HPABBooking hpabBooking;

	@Column(name = "CONT_NO")
	private String containerNumber;

	@Column(name = "TW_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.BillingStatusEnumUserType")
	private BillingStatus status;

	@Column(name = "TW_LOCATION")
	private String location;

	@Column(name = "SHIPPER_WG")
	private String shipperVGM;

	@Column(name = "TERMINAL_WG")
	private String terminalVGM;

	@Column(name = "VARIANCE_WG")
	private String calculatedVariance;

	@Column(name = "TW_INVOICE_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.BillingStatusEnumUserType")
	private BillingStatus invoiceStatus;

	@Column(name = "DATETIME_GATE_IN")
	private LocalDateTime timeGateIn;

	@Column(name = "DATETIME_BOOKING")
	private LocalDateTime hpabBookingDate;

	@Column(name = "REMARKS")
	private String userRemarks;

	@Column(name = " BACK_TO_BACK", nullable = true)
	@Type(type = "yes_no")
	private Boolean backToback = false;

	@Column(name = "FA_LEDGER_CODE")
	private String faLedgerCode;

	@Column(name = "LINE_CODE")
	private String shippingLine;

	@Column(name = "SOLAS_CERT_NO")
	private String solasCertNo;

	@Column(name = "SOLAS_REF_NO")
	private String solasRefNumber;

	@Column(name = "CONT_ISO_CODE")
	private String containerISOCode;

	@Column(name = "EXP_TRUCK_POS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.ContainerPositionEnumUserType")
	private ContainerPosition containerPosition;

	@Column(name = "VESSEL_SCN")
	private String vesselSCN;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "EXP_PRINT_EIR", nullable = true, referencedColumnName = "PRINT_NO")
	private PrintEir printEir;

	public Long getTermWeighingDetailID() {
		return termWeighingDetailID;
	}

	public void setTermWeighingDetailID(Long termWeighingDetailID) {
		this.termWeighingDetailID = termWeighingDetailID;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public HPABBooking getHpabBooking() {
		return hpabBooking;
	}

	public void setHpabBooking(HPABBooking hpabBooking) {
		this.hpabBooking = hpabBooking;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public BillingStatus getStatus() {
		return status;
	}

	public void setStatus(BillingStatus status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getShipperVGM() {
		return shipperVGM;
	}

	public void setShipperVGM(String shipperVGM) {
		this.shipperVGM = shipperVGM;
	}

	public String getTerminalVGM() {
		return terminalVGM;
	}

	public void setTerminalVGM(String terminalVGM) {
		this.terminalVGM = terminalVGM;
	}

	public String getCalculatedVariance() {
		return calculatedVariance;
	}

	public void setCalculatedVariance(String calculatedVariance) {
		this.calculatedVariance = calculatedVariance;
	}

	public BillingStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(BillingStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public LocalDateTime getTimeGateIn() {
		return timeGateIn;
	}

	public void setTimeGateIn(LocalDateTime timeGateIn) {
		this.timeGateIn = timeGateIn;
	}

	public LocalDateTime getHpabBookingDate() {
		return hpabBookingDate;
	}

	public void setHpabBookingDate(LocalDateTime hpabBookingDate) {
		this.hpabBookingDate = hpabBookingDate;
	}

	public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}

	public Boolean getBackToback() {
		return backToback;
	}

	public void setBackToback(Boolean backToback) {
		this.backToback = backToback;
	}

	public String getFaLedgerCode() {
		return faLedgerCode;
	}

	public void setFaLedgerCode(String faLedgerCode) {
		this.faLedgerCode = faLedgerCode;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getSolasCertNo() {
		return solasCertNo;
	}

	public void setSolasCertNo(String solasCertNo) {
		this.solasCertNo = solasCertNo;
	}

	public String getSolasRefNumber() {
		return solasRefNumber;
	}

	public void setSolasRefNumber(String solasRefNumber) {
		this.solasRefNumber = solasRefNumber;
	}

	public String getContainerISOCode() {
		return containerISOCode;
	}

	public void setContainerISOCode(String containerISOCode) {
		this.containerISOCode = containerISOCode;
	}

	public ContainerPosition getContainerPosition() {
		return containerPosition;
	}

	public void setContainerPosition(ContainerPosition containerPosition) {
		this.containerPosition = containerPosition;
	}

	public String getVesselSCN() {
		return vesselSCN;
	}

	public void setVesselSCN(String vesselSCN) {
		this.vesselSCN = vesselSCN;
	}

	public PrintEir getPrintEir() {
		return printEir;
	}

	public void setPrintEir(PrintEir printEir) {
		this.printEir = printEir;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
