package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class ImportContainer extends BaseContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long gatePassID;

	private Long gatePassNo;

	private Long gateOrderNo;

	private int containerLength;

	private Long company;

	private String gatePassStatus;

	private Long handlingID;

	private String orderNo;

	private String currentPosition;

	private String gateInLaneNo;

	private String gateOutLaneNo;

	private String cosmosSeal01Origin;

	private String cosmosSeal01Type;

	private String cosmosSeal01Number;

	private String cosmosSeal02Origin;

	private String cosmosSeal02Type;

	private String cosmosSeal02Number;

	private String gatePassValidDate;

	private ISOInfo isoInfo;

	private String orderFOT;

	// systemUser id
	private long userSessionId;

	private long printEIRNo;

	private String acceptOrReject;

	// cardUsageID
	private String cugId;

	private String temp;

	private String tempUnit;

	private String unc;

	private String oogoh;

	private String oogol;

	private String oogof;

	private String oogoa;

	private String oogor;

	private boolean FOTBKGFlag = true;

	private Integer netWeight;

	private String contRefer;

	// from opus import container
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime containerDischargeDateTime;// 20161124162510,
	private String impCarrierType;// null,
	private String impCarrier;// null,
	private String vesselScn;// DB0899,

	private String vesselVoyage;

	private DamageCodeInfo damageCodeInfo;

	private String gcsDelcarerNo;

	private String gcsBlock;

	private String pkfzBlock;

	private String lpkBlock;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime cusGCSReleaseDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gatePassIssued;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime portSecurity;

	private String moveType;

	private String impOrderType;

	private String impOrderStatus;

	private String containerSubHandlingType;

	private boolean retrievedCosmos = false;

	private String tosServiceType;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(int containerLength) {
		this.containerLength = containerLength;
	}

	public Long getGatePassID() {
		return gatePassID;
	}

	public void setGatePassID(Long gatePassID) {
		this.gatePassID = gatePassID;
	}

	public Long getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(Long gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public Long getGateOrderNo() {
		return gateOrderNo;
	}

	public void setGateOrderNo(Long gateOrderNo) {
		this.gateOrderNo = gateOrderNo;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public String getGatePassStatus() {
		return gatePassStatus;
	}

	public void setGatePassStatus(String gatePassStatus) {
		this.gatePassStatus = gatePassStatus;
	}

	public Long getHandlingID() {
		return handlingID;
	}

	public void setHandlingID(Long handlingID) {
		this.handlingID = handlingID;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getGateInLaneNo() {
		return gateInLaneNo;
	}

	public void setGateInLaneNo(String gateInLaneNo) {
		this.gateInLaneNo = gateInLaneNo;
	}

	public String getGateOutLaneNo() {
		return gateOutLaneNo;
	}

	public void setGateOutLaneNo(String gateOutLaneNo) {
		this.gateOutLaneNo = gateOutLaneNo;
	}

	public String getCosmosSeal01Origin() {
		return cosmosSeal01Origin;
	}

	public void setCosmosSeal01Origin(String cosmosSeal01Origin) {
		this.cosmosSeal01Origin = cosmosSeal01Origin;
	}

	public String getCosmosSeal01Type() {
		return cosmosSeal01Type;
	}

	public void setCosmosSeal01Type(String cosmosSeal01Type) {
		this.cosmosSeal01Type = cosmosSeal01Type;
	}

	public String getCosmosSeal01Number() {
		return cosmosSeal01Number;
	}

	public void setCosmosSeal01Number(String cosmosSeal01Number) {
		this.cosmosSeal01Number = cosmosSeal01Number;
	}

	public String getCosmosSeal02Origin() {
		return cosmosSeal02Origin;
	}

	public void setCosmosSeal02Origin(String cosmosSeal02Origin) {
		this.cosmosSeal02Origin = cosmosSeal02Origin;
	}

	public String getCosmosSeal02Type() {
		return cosmosSeal02Type;
	}

	public void setCosmosSeal02Type(String cosmosSeal02Type) {
		this.cosmosSeal02Type = cosmosSeal02Type;
	}

	public String getCosmosSeal02Number() {
		return cosmosSeal02Number;
	}

	public void setCosmosSeal02Number(String cosmosSeal02Number) {
		this.cosmosSeal02Number = cosmosSeal02Number;
	}

	public String getGatePassValidDate() {
		return gatePassValidDate;
	}

	public void setGatePassValidDate(String gatePassValidDate) {
		this.gatePassValidDate = gatePassValidDate;
	}

	public ISOInfo getIsoInfo() {
		return isoInfo;
	}

	public void setIsoInfo(ISOInfo isoInfo) {
		this.isoInfo = isoInfo;
	}

	public String getOrderFOT() {
		return orderFOT;
	}

	public void setOrderFOT(String orderFOT) {
		this.orderFOT = orderFOT;
	}

	public long getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(long userSessionId) {
		this.userSessionId = userSessionId;
	}

	public long getPrintEIRNo() {
		return printEIRNo;
	}

	public void setPrintEIRNo(long printEIRNo) {
		this.printEIRNo = printEIRNo;
	}

	public String getAcceptOrReject() {
		return acceptOrReject;
	}

	public void setAcceptOrReject(String acceptOrReject) {
		this.acceptOrReject = acceptOrReject;
	}

	public String getCugId() {
		return cugId;
	}

	public void setCugId(String cugId) {
		this.cugId = cugId;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTempUnit() {
		return tempUnit;
	}

	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}

	public String getUnc() {
		return unc;
	}

	public void setUnc(String unc) {
		this.unc = unc;
	}

	public String getOogoh() {
		return oogoh;
	}

	public void setOogoh(String oogoh) {
		this.oogoh = oogoh;
	}

	public String getOogol() {
		return oogol;
	}

	public void setOogol(String oogol) {
		this.oogol = oogol;
	}

	public String getOogof() {
		return oogof;
	}

	public void setOogof(String oogof) {
		this.oogof = oogof;
	}

	public String getOogoa() {
		return oogoa;
	}

	public void setOogoa(String oogoa) {
		this.oogoa = oogoa;
	}

	public String getOogor() {
		return oogor;
	}

	public void setOogor(String oogor) {
		this.oogor = oogor;
	}

	public boolean isFOTBKGFlag() {
		return FOTBKGFlag;
	}

	public void setFOTBKGFlag(boolean fOTBKGFlag) {
		FOTBKGFlag = fOTBKGFlag;
	}

	public Integer getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Integer netWeight) {
		this.netWeight = netWeight;
	}

	public String getContRefer() {
		return contRefer;
	}

	public void setContRefer(String contRefer) {
		this.contRefer = contRefer;
	}

	public LocalDateTime getContainerDischargeDateTime() {
		return containerDischargeDateTime;
	}

	public void setContainerDischargeDateTime(LocalDateTime containerDischargeDateTime) {
		this.containerDischargeDateTime = containerDischargeDateTime;
	}

	public String getImpCarrierType() {
		return impCarrierType;
	}

	public void setImpCarrierType(String impCarrierType) {
		this.impCarrierType = impCarrierType;
	}

	public String getImpCarrier() {
		return impCarrier;
	}

	public void setImpCarrier(String impCarrier) {
		this.impCarrier = impCarrier;
	}

	public String getVesselScn() {
		return vesselScn;
	}

	public void setVesselScn(String vesselScn) {
		this.vesselScn = vesselScn;
	}

	public String getVesselVoyage() {
		return vesselVoyage;
	}

	public void setVesselVoyage(String vesselVoyage) {
		this.vesselVoyage = vesselVoyage;
	}

	public DamageCodeInfo getDamageCodeInfo() {
		return damageCodeInfo;
	}

	public void setDamageCodeInfo(DamageCodeInfo damageCodeInfo) {
		this.damageCodeInfo = damageCodeInfo;
	}

	public String getGcsDelcarerNo() {
		return gcsDelcarerNo;
	}

	public void setGcsDelcarerNo(String gcsDelcarerNo) {
		this.gcsDelcarerNo = gcsDelcarerNo;
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

	public LocalDateTime getGatePassIssued() {
		return gatePassIssued;
	}

	public void setGatePassIssued(LocalDateTime gatePassIssued) {
		this.gatePassIssued = gatePassIssued;
	}

	public LocalDateTime getPortSecurity() {
		return portSecurity;
	}

	public void setPortSecurity(LocalDateTime portSecurity) {
		this.portSecurity = portSecurity;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getImpOrderType() {
		return impOrderType;
	}

	public void setImpOrderType(String impOrderType) {
		this.impOrderType = impOrderType;
	}

	public String getImpOrderStatus() {
		return impOrderStatus;
	}

	public void setImpOrderStatus(String impOrderStatus) {
		this.impOrderStatus = impOrderStatus;
	}

	public String getContainerSubHandlingType() {
		return containerSubHandlingType;
	}

	public void setContainerSubHandlingType(String containerSubHandlingType) {
		this.containerSubHandlingType = containerSubHandlingType;
	}

	public boolean isRetrievedCosmos() {
		return retrievedCosmos;
	}

	public void setRetrievedCosmos(boolean retrievedCosmos) {
		this.retrievedCosmos = retrievedCosmos;
	}

	public String getTosServiceType() {
		return tosServiceType;
	}

	public void setTosServiceType(String tosServiceType) {
		this.tosServiceType = tosServiceType;
	}

}
