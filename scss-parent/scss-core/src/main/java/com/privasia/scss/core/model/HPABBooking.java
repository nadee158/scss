/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.HpatDto;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.util.DateUtil;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "ETP_BOOKING_HPAT")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class HPABBooking extends AuditEntity implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BOOKING_ID")
	private String bookingID;

	@Column(name = "APPT_DATE_START")
	private LocalDateTime appointmentStartDate;

	@Column(name = "BUFFER")
	private String buffer;

	@Column(name = "CRD_SCARDNO")
	private String cardNo;

	@Column(name = "CREATION_DATE")
	private LocalDateTime hpabCreationDate;

	@Column(name = "DRIVER_NAME")
	private String driverName;

	@Column(name = "DRIVER_IC_PP")
	private String driverICNumber;

	@Column(name = "LAST_MODIFIED_DATE")
	private LocalDateTime hpabLastModifiedDate;

	@Column(name = "PM_NO")
	private String pmNumber;

	@Column(name = "STATUS_CODE", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.HPATReferStatusEnumUserType")
	private HpabReferStatus status;

	@Column(name = "TRAILER_NO")
	private String trailerNo;

	@Column(name = "TRAILER_TYPE")
	private String trailerType;

	@Column(name = "HAULIER_CODE")
	private String haulierCode;

	@Column(name = "APPT_DATE_END")
	private LocalDateTime appointmentEndDate;

	@Column(name = "PM_HEAD_WEIGHT")
	private String pmWeight;

	@Column(name = "AXLE_WEIGHT")
	private String axleWeight;

	@Column(name = "TRAILER_PLATE")
	private String trailerPlate;

	@Column(name = "AXLE_VERIFIED", nullable = true)
	@Type(type = "true_false")
	private Boolean axleVerified;

	@Column(name = "PM_VERIFIED", nullable = true)
	@Type(type = "true_false")
	private Boolean pmVerified;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hpabBooking")
	private Set<HPABBookingDetail> hpabBookingDetails;

	public HpatDto constructHpatDto(boolean importOnly) { 
		
		
		if(importOnly){
			
			boolean exsist = this.getHpabBookingDetails().stream().filter(detail -> 
					StringUtils.equalsIgnoreCase(BookingType.EXPORT.getValue(), detail.getBookingType().getValue())).findAny().isPresent();
			
			if(exsist){
				// export booking are available
				return null;
			}
		}
		
		
		HpatDto dto = new HpatDto();
		dto.setBookingId(this.getBookingID());
		dto.setDriverId(this.getDriverICNumber());
		dto.setBuffer(this.getBuffer());
		dto.setComId(this.getHaulierCode());
		dto.setCrdScardno(this.getCardNo());
		dto.setPmNo(this.getPmNumber());
		dto.setFromScss("Y");
		dto.setStatus(this.getStatus().getValue());
		dto.setTrlrNo(this.getTrailerNo());
		dto.setTrlrType(this.getTrailerType());
		dto.setApptStartDate(this.getAppointmentStartDate());
		dto.setApptEndDate(this.getAppointmentEndDate());

		if (this.getAppointmentStartDate() != null) {
			dto.setApptStart(DateUtil.getFormatteDateTime(this.getAppointmentStartDate()));
		}
		if (this.getAppointmentEndDate() != null) {
			dto.setApptEnd(DateUtil.getFormatteDateTime(this.getAppointmentEndDate()));
		}

		this.getHpabBookingDetails().forEach(detail -> setGatePassAndContainDetail(detail, dto));
		return dto;
	}
	
	public HpatDto constructHpatDto() { 
		HpatDto dto = new HpatDto();
		dto.setBookingId(this.getBookingID());
		dto.setDriverId(this.getDriverICNumber());
		dto.setBuffer(this.getBuffer());
		dto.setComId(this.getHaulierCode());
		dto.setCrdScardno(this.getCardNo());
		dto.setPmNo(this.getPmNumber());
		dto.setFromScss("Y");
		dto.setStatus(this.getStatus().getValue());
		dto.setTrlrNo(this.getTrailerNo());
		dto.setTrlrType(this.getTrailerType());
		dto.setApptStartDate(this.getAppointmentStartDate());
		dto.setApptEndDate(this.getAppointmentEndDate());

		if (this.getAppointmentStartDate() != null) {
			dto.setApptStart(DateUtil.getFormatteDateTime(this.getAppointmentStartDate()));
		}
		if (this.getAppointmentEndDate() != null) {
			dto.setApptEnd(DateUtil.getFormatteDateTime(this.getAppointmentEndDate()));
		}

		this.getHpabBookingDetails().forEach(detail -> setGatePassAndContainDetail(detail, dto));
		return dto;
	}

	public void setGatePassAndContainDetail(HPABBookingDetail hpabBookingDetail, HpatDto dto) {
	
		if (StringUtils.equals(BookingType.EXPORT.getValue(), hpabBookingDetail.getBookingType().getValue())) {

			if (StringUtils.isEmpty(dto.getExpContainer01())) {
				dto.setExpContainer01(hpabBookingDetail.getContainerNumber());
				dto.setContainer01Size(hpabBookingDetail.getContainerSize().orElse(null));
			} else {
				dto.setExpContainer02(hpabBookingDetail.getContainerNumber());
				dto.setContainer02Size(hpabBookingDetail.getContainerSize().orElse(null));
			}
		}

		if (StringUtils.equals(BookingType.IMPORT.getValue(), hpabBookingDetail.getBookingType().getValue())) {

			if (StringUtils.isEmpty(dto.getImpGatePass01())) {
				dto.setImpGatePass01(hpabBookingDetail.getImpGatePassNumber());
				dto.setImpContainer01(hpabBookingDetail.getContainerNumber());
				dto.setContainer01Size(hpabBookingDetail.getContainerSize().orElse(null));

			} else {
				dto.setImpGatePass02(hpabBookingDetail.getImpGatePassNumber());
				dto.setImpContainer02(hpabBookingDetail.getContainerNumber());
				dto.setContainer02Size(hpabBookingDetail.getContainerSize().orElse(null));

			}
		}

		if (StringUtils.equals(BookingType.IMPORT_ITT.getValue(), hpabBookingDetail.getBookingType().getValue())) {
			if (StringUtils.isEmpty(dto.getIttGatePass01())) {
				dto.setIttGatePass01(hpabBookingDetail.getImpGatePassNumber());
				dto.setImpContainer01(hpabBookingDetail.getContainerNumber());
				dto.setContainer01Size(hpabBookingDetail.getContainerSize().orElse(null));

			} else {
				dto.setIttGatePass02(hpabBookingDetail.getImpGatePassNumber());
				dto.setImpContainer02(hpabBookingDetail.getContainerNumber());
				dto.setContainer02Size(hpabBookingDetail.getContainerSize().orElse(null));
			}
		}

	}

	public String getBookingID() {
		return bookingID;
	}

	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public LocalDateTime getAppointmentStartDate() {
		return appointmentStartDate;
	}

	public void setAppointmentStartDate(LocalDateTime appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public LocalDateTime getHpabCreationDate() {
		return hpabCreationDate;
	}

	public void setHpabCreationDate(LocalDateTime hpabCreationDate) {
		this.hpabCreationDate = hpabCreationDate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverICNumber() {
		return driverICNumber;
	}

	public void setDriverICNumber(String driverICNumber) {
		this.driverICNumber = driverICNumber;
	}

	public LocalDateTime getHpabLastModifiedDate() {
		return hpabLastModifiedDate;
	}

	public void setHpabLastModifiedDate(LocalDateTime hpabLastModifiedDate) {
		this.hpabLastModifiedDate = hpabLastModifiedDate;
	}

	public String getPmNumber() {
		return pmNumber;
	}

	public void setPmNumber(String pmNumber) {
		this.pmNumber = pmNumber;
	}

	public HpabReferStatus getStatus() {
		return status;
	}

	public void setStatus(HpabReferStatus status) {
		this.status = status;
	}

	public String getTrailerNo() {
		return trailerNo;
	}

	public void setTrailerNo(String trailerNo) {
		this.trailerNo = trailerNo;
	}

	public String getTrailerType() {
		return trailerType;
	}

	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	public String getHaulierCode() {
		return haulierCode;
	}

	public void setHaulierCode(String haulierCode) {
		this.haulierCode = haulierCode;
	}

	public LocalDateTime getAppointmentEndDate() {
		return appointmentEndDate;
	}

	public void setAppointmentEndDate(LocalDateTime appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}

	public String getPmWeight() {
		return pmWeight;
	}

	public void setPmWeight(String pmWeight) {
		this.pmWeight = pmWeight;
	}

	public String getAxleWeight() {
		return axleWeight;
	}

	public void setAxleWeight(String axleWeight) {
		this.axleWeight = axleWeight;
	}

	public String getTrailerPlate() {
		return trailerPlate;
	}

	public void setTrailerPlate(String trailerPlate) {
		this.trailerPlate = trailerPlate;
	}

	public Boolean getAxleVerified() {
		return axleVerified;
	}

	public void setAxleVerified(Boolean axleVerified) {
		this.axleVerified = axleVerified;
	}

	public Boolean getPmVerified() {
		return pmVerified;
	}

	public void setPmVerified(Boolean pmVerified) {
		this.pmVerified = pmVerified;
	}

	public Set<HPABBookingDetail> getHpabBookingDetails() {
		return hpabBookingDetails;
	}

	public void setHpabBookingDetails(Set<HPABBookingDetail> hpabBookingDetails) {
		this.hpabBookingDetails = hpabBookingDetails;
	}

	public TransactionDTO constructTransactionDTO() {
		TransactionDTO dto = new TransactionDTO();
		BeanUtils.copyProperties(this, dto);
		if (!(this.getStatus() == null)) {
			dto.setStatus(this.getStatus().getValue());
		}
		return dto;
	}

	public GateOutReponse constructGateOutReponse() {
		GateOutReponse gateOutReponse = new GateOutReponse();
		gateOutReponse.setCallCardNo(this.cardNo);
		gateOutReponse.setHaulageCode(this.haulierCode);
		gateOutReponse.setLaneNo(this.getPmNumber());
		gateOutReponse.setTruckHeadNo(this.getTrailerNo());
		gateOutReponse.setTruckPlateNo(this.getTrailerPlate());
		return gateOutReponse;
	}

}
