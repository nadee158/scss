package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.HDBSBookingType;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.enums.SCSSHDBSStatus;

@Entity
@Table(name = "HDBS_BKG_DETAIL")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class HDBSBkgDetail extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BKG_DETAIL_ID")
	private String hdbsBKGDetailID;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "BKG_MASTER_ID", nullable = true)
	private HDBSBkgMaster hDBSBkgMaster;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "APP_NAME")
	private String appName;

	@Column(name = "HDBS_BKG_DETAIL_NO")
	private String hdbsBkgDetailNo;

	@Column(name = "HDBS_BKG_SEQ")
	private String hdbsBookingSeq;

	@Column(name = "HDBS_BKG_TYPE")
	@Type(type = "com.privasia.scss.common.enumusertype.HDBSBookingEnumUserType")
	private HDBSBookingType hdbsBkgType;

	@Column(name = "CONTAINER_NO")
	private String containerNo;

	@Column(name = "CONTAINER_TYPE")
	private String containerType;

	@Column(name = "CONTAINER_SIZE")
	@Type(type = "com.privasia.scss.common.enumusertype.ContainerSizeEnumUserType")
	private ContainerSize containerSize;

	@Column(name = "APPT_DATETIME_FROM")
	@DateTimeFormat(pattern = "M/d/yyyy h:mm:ss a")
	private LocalDateTime apptDateTimeFrom;

	@Column(name = "APPT_DATETIME_TO")
	private LocalDateTime apptDateTimeTo;

	@Column(name = "APPT_DATETIME_TO_ACTUAL")
	private LocalDateTime apptDateTimeToActual;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "APPT_WINDOW")
	private Integer apptWindow;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GRACE_PERIOD")
	private Integer gracePeriod;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "COMMODITY")
	private String commodity;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "SHIPPER_CODE")
	private String shipperCode;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "SHIPPER_NAME")
	private String shipperName;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CONTAINER_WEIGHT")
	private String containerWeight;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REEFER_MIN_TEMP")
	private String reeferMinTemp;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REEFER_MAX_TEMP")
	private String reeferMaxTemp;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REEFER_CARRIAGE_TEMP")
	private String reeferCarriageTemp;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "DG_UN_NUMBER")
	private String dgUnNumber;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "EQUIPMENT_REQ")
	private String equipment;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CONTAINER_GRADE")
	private String containerGrade;

	@Column(name = "CONTAINER_LOCATION")
	private String containerLocation;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GATE_IN_DATETIME")
	private LocalDateTime gateInDateTime;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "GATE_OUT_DATETIME")
	private LocalDateTime gateOutDateTime;

	@Column(name = "STATUS_CODE")
	@Type(type = "com.privasia.scss.common.enumusertype.HDBSStatusEnumUserType")
	private HDBSStatus statusCode;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "EXCEL_DATA_SEQ")
	private Integer excelDataSeq;

	@Column(name = "REMARKS")
	private String remarks;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TARE_WEIGHT")
	private String tareWeight;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ISO_CODE")
	private String isoCode;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ISO_DESCRIPTION")
	private String isoDescription;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "VESSEL_CODE")
	private String vesselCode;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "MANUFACTURE_DATE")
	private String manufactureDate;

	@Column(name = "SCSS_STATUS_CODE")
	@Type(type = "com.privasia.scss.common.enumusertype.SCSSHDBSStatusEnumUserType")
	private SCSSHDBSStatus scssStatusCode;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "NEW_DATE")
	private LocalDateTime newDate;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ACCEPTED_DATE")
	private LocalDateTime accpetedDate;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "APPROVED_DATE")
	private LocalDateTime approvedDate;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "COMPLETED_DATE")
	private LocalDateTime completedDate;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CANCELLED_DATE")
	private LocalDateTime cancelledDate;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REJECTED_DATE")
	private LocalDateTime rejectedDate;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ODD_ID_SEQ", nullable = true)
	private WHODD whodd;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ODD_TIMEGATEINOK")
	private LocalDateTime oddTimeGateInOk;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ODD_TIMEGATEOUTOK")
	private LocalDateTime oddTimeGateOutOk;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "HDBS_SYNC")
	private String hdbsSynch;

	public String getHdbsBKGDetailID() {
		return hdbsBKGDetailID;
	}

	public void setHdbsBKGDetailID(String hdbsBKGDetailID) {
		this.hdbsBKGDetailID = hdbsBKGDetailID;
	}

	public HDBSBkgMaster gethDBSBkgMaster() {
		return hDBSBkgMaster;
	}

	public void sethDBSBkgMaster(HDBSBkgMaster hDBSBkgMaster) {
		this.hDBSBkgMaster = hDBSBkgMaster;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHdbsBkgDetailNo() {
		return hdbsBkgDetailNo;
	}

	public void setHdbsBkgDetailNo(String hdbsBkgDetailNo) {
		this.hdbsBkgDetailNo = hdbsBkgDetailNo;
	}

	public String getHdbsBookingSeq() {
		return hdbsBookingSeq;
	}

	public void setHdbsBookingSeq(String hdbsBookingSeq) {
		this.hdbsBookingSeq = hdbsBookingSeq;
	}

	public HDBSBookingType getHdbsBkgType() {
		return hdbsBkgType;
	}

	public void setHdbsBkgType(HDBSBookingType hdbsBkgType) {
		this.hdbsBkgType = hdbsBkgType;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public ContainerSize getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(ContainerSize containerSize) {
		this.containerSize = containerSize;
	}

	public LocalDateTime getApptDateTimeFrom() {
		return apptDateTimeFrom;
	}

	public void setApptDateTimeFrom(LocalDateTime apptDateTimeFrom) {
		this.apptDateTimeFrom = apptDateTimeFrom;
	}

	public LocalDateTime getApptDateTimeTo() {
		return apptDateTimeTo;
	}

	public void setApptDateTimeTo(LocalDateTime apptDateTimeTo) {
		this.apptDateTimeTo = apptDateTimeTo;
	}

	public LocalDateTime getApptDateTimeToActual() {
		return apptDateTimeToActual;
	}

	public void setApptDateTimeToActual(LocalDateTime apptDateTimeToActual) {
		this.apptDateTimeToActual = apptDateTimeToActual;
	}

	public Integer getApptWindow() {
		return apptWindow;
	}

	public void setApptWindow(Integer apptWindow) {
		this.apptWindow = apptWindow;
	}

	public Integer getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(Integer gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(String containerWeight) {
		this.containerWeight = containerWeight;
	}

	public String getReeferMinTemp() {
		return reeferMinTemp;
	}

	public void setReeferMinTemp(String reeferMinTemp) {
		this.reeferMinTemp = reeferMinTemp;
	}

	public String getReeferMaxTemp() {
		return reeferMaxTemp;
	}

	public void setReeferMaxTemp(String reeferMaxTemp) {
		this.reeferMaxTemp = reeferMaxTemp;
	}

	public String getReeferCarriageTemp() {
		return reeferCarriageTemp;
	}

	public void setReeferCarriageTemp(String reeferCarriageTemp) {
		this.reeferCarriageTemp = reeferCarriageTemp;
	}

	public String getDgUnNumber() {
		return dgUnNumber;
	}

	public void setDgUnNumber(String dgUnNumber) {
		this.dgUnNumber = dgUnNumber;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getContainerGrade() {
		return containerGrade;
	}

	public void setContainerGrade(String containerGrade) {
		this.containerGrade = containerGrade;
	}

	public String getContainerLocation() {
		return containerLocation;
	}

	public void setContainerLocation(String containerLocation) {
		this.containerLocation = containerLocation;
	}

	public LocalDateTime getGateInDateTime() {
		return gateInDateTime;
	}

	public void setGateInDateTime(LocalDateTime gateInDateTime) {
		this.gateInDateTime = gateInDateTime;
	}

	public LocalDateTime getGateOutDateTime() {
		return gateOutDateTime;
	}

	public void setGateOutDateTime(LocalDateTime gateOutDateTime) {
		this.gateOutDateTime = gateOutDateTime;
	}

	public HDBSStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HDBSStatus statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getExcelDataSeq() {
		return excelDataSeq;
	}

	public void setExcelDataSeq(Integer excelDataSeq) {
		this.excelDataSeq = excelDataSeq;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getIsoDescription() {
		return isoDescription;
	}

	public void setIsoDescription(String isoDescription) {
		this.isoDescription = isoDescription;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public String getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public SCSSHDBSStatus getScssStatusCode() {
		return scssStatusCode;
	}

	public void setScssStatusCode(SCSSHDBSStatus scssStatusCode) {
		this.scssStatusCode = scssStatusCode;
	}

	public LocalDateTime getNewDate() {
		return newDate;
	}

	public void setNewDate(LocalDateTime newDate) {
		this.newDate = newDate;
	}

	public LocalDateTime getAccpetedDate() {
		return accpetedDate;
	}

	public void setAccpetedDate(LocalDateTime accpetedDate) {
		this.accpetedDate = accpetedDate;
	}

	public LocalDateTime getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(LocalDateTime approvedDate) {
		this.approvedDate = approvedDate;
	}

	public LocalDateTime getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(LocalDateTime completedDate) {
		this.completedDate = completedDate;
	}

	public LocalDateTime getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(LocalDateTime cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public LocalDateTime getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(LocalDateTime rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public WHODD getWhodd() {
		return whodd;
	}

	public void setWhodd(WHODD whodd) {
		this.whodd = whodd;
	}

	public LocalDateTime getOddTimeGateInOk() {
		return oddTimeGateInOk;
	}

	public void setOddTimeGateInOk(LocalDateTime oddTimeGateInOk) {
		this.oddTimeGateInOk = oddTimeGateInOk;
	}

	public LocalDateTime getOddTimeGateOutOk() {
		return oddTimeGateOutOk;
	}

	public void setOddTimeGateOutOk(LocalDateTime oddTimeGateOutOk) {
		this.oddTimeGateOutOk = oddTimeGateOutOk;
	}

	public String getHdbsSynch() {
		return hdbsSynch;
	}

	public void setHdbsSynch(String hdbsSynch) {
		this.hdbsSynch = hdbsSynch;
	}

}
