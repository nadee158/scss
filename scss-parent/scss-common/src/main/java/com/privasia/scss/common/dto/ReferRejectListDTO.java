package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ReferRejectListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// ReferReject.referRejectID;
	private String referId;;

	// ReferReject.baseCommonGateInOut.gateInClient.unitNo
	private String boothNo;

	// ReferReject.baseCommonGateInOut.pmHeadNo
	private String pmHeadNo;

	// ReferReject.baseCommonGateInOut.card.company.companyName
	private String haulierCompany;

	// ReferReject.baseCommonGateInOut.card.smartCardUser.commonContactAttribute.name
	private String driverName;

	// ReferReject.referDateTime
	private String referDateTime;

	// ReferReject.referRejectDetails.containerNo
	private String contNo01;

	private String contNo02;

	// ReferReject.referRejectDetails.doubleBooking
	private boolean doubleBooking = false;

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
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

	public String getReferDateTime() {
		return referDateTime;
	}

	public void setReferDateTime(String referDateTime) {
		this.referDateTime = referDateTime;
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

	@Override
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
