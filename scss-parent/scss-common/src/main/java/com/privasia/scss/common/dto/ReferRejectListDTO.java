package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.enums.TOSServiceType;
import com.privasia.scss.common.util.DateUtil;

public class ReferRejectListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// ReferReject.referRejectID;
	private Long referId;

	// ReferReject.baseCommonGateInOut.gateInClient.unitNo
	private String boothNo;

	// ReferReject.baseCommonGateInOut.pmHeadNo
	private String pmHeadNo;

	// ReferReject.baseCommonGateInOut.card.company.companyName
	private String haulierCompany;

	// ReferReject.baseCommonGateInOut.card.smartCardUser.commonContactAttribute.name
	private String driverName;

	// ReferReject.referDateTime
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime referDateTime;

	// ReferReject.referRejectDetails.containerNo
	private String contNo01;

	private String contNo02;

	private long cardID;

	private int expWeightBridge;

	// ReferReject.referRejectDetails.doubleBooking
	private boolean doubleBooking = false;
	
	private Long gatePass01;
	
	private Long gatePass02;

	private String tosIndicator;
		
	public Long getReferId() {
		return referId;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public LocalDateTime getReferDateTime() {
		return referDateTime;
	}

	public void setReferDateTime(LocalDateTime referDateTime) {
		this.referDateTime = referDateTime;
	}

	public String getBoothNo() {
		return boothNo;
	}

	public void setBoothNo(String boothNo) {
		this.boothNo = boothNo;
	}

	public String getPmHeadNo() {
		return pmHeadNo;
	}

	public void setPmHeadNo(String pmHeadNo) {
		this.pmHeadNo = pmHeadNo;
	}

	public String getHaulierCompany() {
		return haulierCompany;
	}

	public void setHaulierCompany(String haulierCompany) {
		this.haulierCompany = haulierCompany;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public boolean isDoubleBooking() {
		return doubleBooking;
	}

	public void setDoubleBooking(boolean doubleBooking) {
		this.doubleBooking = doubleBooking;
	}

	public String getContNo01() {
		return contNo01;
	}

	public void setContNo01(String contNo01) {
		this.contNo01 = contNo01;
	}

	public String getContNo02() {
		return contNo02;
	}

	public void setContNo02(String contNo02) {
		this.contNo02 = contNo02;
	}

	public long getCardID() {
		return cardID;
	}

	public void setCardID(long cardID) {
		this.cardID = cardID;
	}

	public int getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(int expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}
	
	public Long getGatePass01() {
		return gatePass01;
	}

	public void setGatePass01(Long gatePass01) {
		this.gatePass01 = gatePass01;
	}

	public Long getGatePass02() {
		return gatePass02;
	}

	public void setGatePass02(Long gatePass02) {
		this.gatePass02 = gatePass02;
	}

	public String getTosIndicator() {
		return tosIndicator;
	}

	public void setTosIndicator(String tosIndicator) {
		this.tosIndicator = tosIndicator;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
