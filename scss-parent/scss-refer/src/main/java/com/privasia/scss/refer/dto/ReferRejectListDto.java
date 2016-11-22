package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.SmartCardUser;

public class ReferRejectListDto implements Serializable {

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
	private String doubleBooking;

	public ReferRejectListDto(ReferReject referReject) {
		super();

		this.referId = Long.toString(referReject.getReferRejectID());
		BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();
		this.pmHeadNo = baseCommonGateInOut.getPmHeadNo();

		Client client = baseCommonGateInOut.getGateInClient();
		if (client != null) {
			this.boothNo = client.getUnitNo();
		}

		Card card = baseCommonGateInOut.getCard();
		if (card != null) {
			Company company = card.getCompany();
			if (company != null) {
				this.haulierCompany = company.getCompanyName();
			}

			SmartCardUser smartCardUser = card.getSmartCardUser();
			if (card != null) {
				this.driverName = smartCardUser.getCommonContactAttribute().getPersonName();
			}

			if (referReject.getReferDateTime() != null) {
				this.referDateTime = referReject.getReferDateTime()
						.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a"));
			}

		}

		referReject.getReferRejectDetails().forEach(referRejectDetail -> {
			if (StringUtils.isBlank(this.contNo01)) {
				this.contNo01 = referRejectDetail.getContainerNo();
			} else {
				this.contNo02 = referRejectDetail.getContainerNo();
			}

			if (StringUtils.isBlank(this.doubleBooking)) {
				this.doubleBooking = referRejectDetail.getDoubleBooking();
			} else {
				this.doubleBooking = referRejectDetail.getDoubleBooking();
			}
		});

	}

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

	public String getDoubleBooking() {
		return doubleBooking;
	}

	public void setDoubleBooking(String doubleBooking) {
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

}
