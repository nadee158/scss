package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.enums.SCSSHDBSStatus;
import com.privasia.scss.common.util.DateUtil;

public class HDBSBkgDetailGridDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hdbsBKGDetailID;

	//private String hDBSBkgMasterPmHeadNo;

	//private String hDBSBkgMasterPlateNo;

	private String hDBSBkgMasterDepotCode;

	private String hDBSBkgMasterCardNo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime hDBSBkgMasterDateTimeAdd;

	private String hDBSBkgMasterHdbsBookingSeq;

	private String appName;

	private String hdbsBkgDetailNo;

	private String hdbsBookingSeq;

	private String hdbsBkgType;

	private String containerNo;

	private String containerType;

	private String containerSize;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime apptDateTimeFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime apptDateTimeTo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime apptDateTimeToActual;

	private Integer apptWindow;

	private Integer gracePeriod;

	private String commodity;

	private String shipperCode;

	private String shipperName;

	private String containerWeight;

	private String reeferMinTemp;

	private String reeferMaxTemp;

	private String reeferCarriageTemp;

	private String dgUnNumber;

	private String equipment;

	private String containerGrade;

	private String containerLocation;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateInDateTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateOutDateTime;

	private HDBSStatus statusCode;

	private Integer excelDataSeq;

	private String remarks;

	private String tareWeight;

	private String isoCode;

	private String isoDescription;

	private String vesselCode;

	private String manufactureDate;

	private SCSSHDBSStatus scssStatusCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime newDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime accpetedDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime approvedDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime completedDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime cancelledDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime rejectedDate;

	// private WHODD oddIdSeq;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime oddTimeGateInOk;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime oddTimeGateOutOk;

	private String hdbsSynch;

	private boolean acceptBooking;

	private String pmHeadNo;
	
	private String plateNo;

	private String durration = StringUtils.EMPTY;
	private String status = StringUtils.EMPTY;
	private String onTimeFlag = StringUtils.EMPTY;

	public String getHdbsBKGDetailID() {
		return hdbsBKGDetailID;
	}

	public void setHdbsBKGDetailID(String hdbsBKGDetailID) {
		this.hdbsBKGDetailID = hdbsBKGDetailID;
	}

	/*public String gethDBSBkgMasterPmHeadNo() {
		return hDBSBkgMasterPmHeadNo;
	}

	public void sethDBSBkgMasterPmHeadNo(String hDBSBkgMasterPmHeadNo) {
		this.hDBSBkgMasterPmHeadNo = hDBSBkgMasterPmHeadNo;
	}

	public String gethDBSBkgMasterPlateNo() {
		return hDBSBkgMasterPlateNo;
	}

	public void sethDBSBkgMasterPlateNo(String hDBSBkgMasterPlateNo) {
		this.hDBSBkgMasterPlateNo = hDBSBkgMasterPlateNo;
	}*/

	public String gethDBSBkgMasterDepotCode() {
		return hDBSBkgMasterDepotCode;
	}

	public void sethDBSBkgMasterDepotCode(String hDBSBkgMasterDepotCode) {
		this.hDBSBkgMasterDepotCode = hDBSBkgMasterDepotCode;
	}

	public String gethDBSBkgMasterCardNo() {
		return hDBSBkgMasterCardNo;
	}

	public void sethDBSBkgMasterCardNo(String hDBSBkgMasterCardNo) {
		this.hDBSBkgMasterCardNo = hDBSBkgMasterCardNo;
	}

	public LocalDateTime gethDBSBkgMasterDateTimeAdd() {
		return hDBSBkgMasterDateTimeAdd;
	}

	public void sethDBSBkgMasterDateTimeAdd(LocalDateTime hDBSBkgMasterDateTimeAdd) {
		this.hDBSBkgMasterDateTimeAdd = hDBSBkgMasterDateTimeAdd;
	}

	public String gethDBSBkgMasterHdbsBookingSeq() {
		return hDBSBkgMasterHdbsBookingSeq;
	}

	public void sethDBSBkgMasterHdbsBookingSeq(String hDBSBkgMasterHdbsBookingSeq) {
		this.hDBSBkgMasterHdbsBookingSeq = hDBSBkgMasterHdbsBookingSeq;
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

	public String getHdbsBkgType() {
		return hdbsBkgType;
	}

	public void setHdbsBkgType(String hdbsBkgType) {
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

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isAcceptBooking() {
		return acceptBooking;
	}

	public void setAcceptBooking(boolean acceptBooking) {
		this.acceptBooking = acceptBooking;
	}

	public String getDurration() {
		return durration;
	}

	public void setDurration(String durration) {
		this.durration = durration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOnTimeFlag() {
		return onTimeFlag;
	}

	public void setOnTimeFlag(String onTimeFlag) {
		this.onTimeFlag = onTimeFlag;
	}

	public String getPmHeadNo() {
		return pmHeadNo;
	}

	public void setPmHeadNo(String pmHeadNo) {
		this.pmHeadNo = pmHeadNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}



	public static Comparator<HDBSBkgDetailGridDTO> ApptDateTimeFromComparator = new Comparator<HDBSBkgDetailGridDTO>() {

		public int compare(HDBSBkgDetailGridDTO dto1, HDBSBkgDetailGridDTO dto2) {
			if (dto1 != null && dto2 != null) {
				if (dto1.getApptDateTimeFrom() != null && dto2.getApptDateTimeFrom() != null) {
					return dto1.getApptDateTimeFrom().compareTo(dto2.getApptDateTimeFrom());
				} else {
					if (dto1.getApptDateTimeFrom() != null) {
						return 1;
					} else if (dto2.getApptDateTimeFrom() != null) {
						return -1;
					}
				}
			} else {
				if (dto1 != null) {
					return 1;
				} else if (dto2 != null) {
					return -1;
				}
			}
			return 0;
		}

	};

	@Override
	public boolean equals(Object compare) {
		HDBSBkgDetailGridDTO other = (HDBSBkgDetailGridDTO) compare;
		if (StringUtils.equalsIgnoreCase(this.containerNo, other.containerNo)
				&& StringUtils.equalsIgnoreCase(this.hdbsBKGDetailID, other.hdbsBKGDetailID)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + hdbsBKGDetailID.hashCode();
        result = prime * result + ((this.containerNo == null) ? 0 : this.containerNo.hashCode());
        return result;
    }

}
