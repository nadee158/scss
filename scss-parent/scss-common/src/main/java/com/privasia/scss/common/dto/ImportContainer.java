package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.CommonUtil;

public class ImportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PrintEirDTO printEir;

	private CardUsageDTO cardUsage;

	private Long gatePassID;

	private Long gatePassNo;

	private Long gateOrderNo;

	private int containerLength;

	private CommonContainerDTO container;

	private Long company;

	private CommonGateInOutDTO commonGateInOut;

	private BaseCommonGateInOutDTO baseCommonGateInOutAttribute;

	private String gatePassStatus;

	private Long handlingID;

	private String orderNo;

	private String gateInOut;

	private String shippingLine;

	private String currentPosition;

	private String containerPosition;

	private String gateInLaneNo;

	private String gateOutLaneNo;

	private CommonSealDTO sealAttribute;

	private String gateOutRemarks;

	private String yardPosition;

	private String yardBayCode;

	private Long callCard;

	private String cosmosSeal01Origin;

	private String cosmosSeal01Type;

	private String cosmosSeal01Number;

	private String cosmosSeal02Origin;

	private String cosmosSeal02Type;

	private String cosmosSeal02Number;

	private String gatePassValidDate;

	private ISOInfo isoInfo;

	private String shippingAgent;

	private String orderFOT;

	// systemUser id
	private long userSessionId;

	private long printEIRNo;

	private String acceptOrReject;

	// cardUsageID
	private String cugId;

	//////////////////////////// TO PRINT EIR
	private String operationReefer;

	private String temp;

	private String tempUnit;

	private String imdg;

	private String unc;

	private String oogoh;

	private String oogol;

	private String oogof;

	private String oogoa;

	private String oogor;

	private boolean FOTBKGFlag = true;

	private Integer tareWeight;

	private Integer grossWeight;

	private Integer netWeight;

	private String containerType;

	//////////////////////////// TO createImpRequestXML
	private String errXMLMsg;

	private String contRefer;

	// from opus import container
	private LocalDateTime containerDischargeDateTime;// 20161124162510,
	private String impCarrierType;// null,
	private String impCarrier;// null,
	private String vesselCode;// UANE,
	private String vesselScn;// DB0899,
	private String vesselName;// AL NEFUD,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime vesselETADate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime vesselATADate;

	private String vesselVisitID;

	private String vesselVoyage;

	private String vesselStatus;

	private DamageCodeInfo damageCodeInfo;

	private String gcsDelcarerNo;

	private String gcsBlock;

	private String pkfzBlock;

	private String lpkBlock;

	private LocalDateTime cusGCSReleaseDate;

	private LocalDateTime gatePassIssued;

	private LocalDateTime portSecurity;

	private String moveType;

	private String rtgExecustionStatus;// ":"RGS"

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime rtgExecustionDateTime;// ;":"20161212101010"

	private String impOrderType;

	private String impOrderStatus;

	private String containerSubHandlingType;

	public String getRtgExecustionStatus() {
		return rtgExecustionStatus;
	}

	public void setRtgExecustionStatus(String rtgExecustionStatus) {
		this.rtgExecustionStatus = rtgExecustionStatus;
	}

	public LocalDateTime getRtgExecustionDateTime() {
		return rtgExecustionDateTime;
	}

	public void setRtgExecustionDateTime(LocalDateTime rtgExecustionDateTime) {
		this.rtgExecustionDateTime = rtgExecustionDateTime;
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

	public CommonContainerDTO getContainer() {
		return container;
	}

	public void setContainer(CommonContainerDTO container) {
		this.container = container;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public CommonGateInOutDTO getCommonGateInOut() {
		return commonGateInOut;
	}

	public void setCommonGateInOut(CommonGateInOutDTO commonGateInOut) {
		this.commonGateInOut = commonGateInOut;
	}

	public BaseCommonGateInOutDTO getBaseCommonGateInOutAttribute() {
		return baseCommonGateInOutAttribute;
	}

	public void setBaseCommonGateInOutAttribute(BaseCommonGateInOutDTO baseCommonGateInOutAttribute) {
		this.baseCommonGateInOutAttribute = baseCommonGateInOutAttribute;
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

	public String getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(String gateInOut) {
		this.gateInOut = gateInOut;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getContainerPosition() {
		return containerPosition;
	}

	public void setContainerPosition(String containerPosition) {
		this.containerPosition = containerPosition;
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

	public CommonSealDTO getSealAttribute() {
		return sealAttribute;
	}

	public void setSealAttribute(CommonSealDTO sealAttribute) {
		this.sealAttribute = sealAttribute;
	}

	public String getGateOutRemarks() {
		return gateOutRemarks;
	}

	public void setGateOutRemarks(String gateOutRemarks) {
		this.gateOutRemarks = gateOutRemarks;
	}

	public String getYardPosition() {
		return yardPosition;
	}

	public void setYardPosition(String yardPosition) {
		this.yardPosition = yardPosition;
	}

	public String getYardBayCode() {
		return yardBayCode;
	}

	public void setYardBayCode(String yardBayCode) {
		this.yardBayCode = yardBayCode;
	}

	public Long getCallCard() {
		return callCard;
	}

	public void setCallCard(Long callCard) {
		this.callCard = callCard;
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

	public String getOperationReefer() {
		return operationReefer;
	}

	public void setOperationReefer(String operationReefer) {
		this.operationReefer = operationReefer;
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

	public String getImdg() {
		return imdg;
	}

	public void setImdg(String imdg) {
		this.imdg = imdg;
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

	public Integer getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Integer tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Integer netWeight) {
		this.netWeight = netWeight;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getErrXMLMsg() {
		return errXMLMsg;
	}

	public void setErrXMLMsg(String errXMLMsg) {
		this.errXMLMsg = errXMLMsg;
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

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public LocalDateTime getVesselETADate() {
		return vesselETADate;
	}

	public void setVesselETADate(LocalDateTime vesselETADate) {
		this.vesselETADate = vesselETADate;
	}

	public LocalDateTime getVesselATADate() {
		return vesselATADate;
	}

	public void setVesselATADate(LocalDateTime vesselATADate) {
		this.vesselATADate = vesselATADate;
	}

	public String getVesselVisitID() {
		return vesselVisitID;
	}

	public void setVesselVisitID(String vesselVisitID) {
		this.vesselVisitID = vesselVisitID;
	}

	public String getVesselVoyage() {
		return vesselVoyage;
	}

	public void setVesselVoyage(String vesselVoyage) {
		this.vesselVoyage = vesselVoyage;
	}

	public String getVesselStatus() {
		return vesselStatus;
	}

	public void setVesselStatus(String vesselStatus) {
		this.vesselStatus = vesselStatus;
	}

	public String getVesselScn() {
		return vesselScn;
	}

	public void setVesselScn(String vesselScn) {
		this.vesselScn = vesselScn;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public DamageCodeInfo getDamageCodeInfo() {
		return damageCodeInfo;
	}

	public void setDamageCodeInfo(DamageCodeInfo damageCodeInfo) {
		this.damageCodeInfo = damageCodeInfo;
	}

	public CardUsageDTO getCardUsage() {
		return cardUsage;
	}

	public void setCardUsage(CardUsageDTO cardUsage) {
		this.cardUsage = cardUsage;
	}

	public PrintEirDTO getPrintEir() {
		return printEir;
	}

	public void setPrintEir(PrintEirDTO printEir) {
		this.printEir = printEir;
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

	@Override
	public String toString() {
		return "ImportContainer [gatePassID=" + gatePassID + ", gatePassNo=" + gatePassNo + ", gateOrderNo="
				+ gateOrderNo + ", containerLength=" + containerLength + ", container=" + container + ", company="
				+ company + ", commonGateInOut=" + commonGateInOut + ", baseCommonGateInOutAttribute="
				+ baseCommonGateInOutAttribute + ", gatePassStatus=" + gatePassStatus + ", handlingID=" + handlingID
				+ ", orderNo=" + orderNo + ", gateInOut=" + gateInOut + ", shippingLine=" + shippingLine
				+ ", currentPosition=" + currentPosition + ", containerPosition=" + containerPosition
				+ ", gateInLaneNo=" + gateInLaneNo + ", gateOutLaneNo=" + gateOutLaneNo + ", sealAttribute="
				+ sealAttribute + ", gateOutRemarks=" + gateOutRemarks + ", yardPosition=" + yardPosition
				+ ", yardBayCode=" + yardBayCode + ", callCard=" + callCard + ", cosmosSeal01Origin="
				+ cosmosSeal01Origin + ", cosmosSeal01Type=" + cosmosSeal01Type + ", cosmosSeal01Number="
				+ cosmosSeal01Number + ", cosmosSeal02Origin=" + cosmosSeal02Origin + ", cosmosSeal02Type="
				+ cosmosSeal02Type + ", cosmosSeal02Number=" + cosmosSeal02Number + ", gatePassValidDate="
				+ gatePassValidDate + ", isoInfo=" + isoInfo + ", shippingAgent=" + shippingAgent + ", orderFOT="
				+ orderFOT + ", userSessionId=" + userSessionId + ", printEIRNo=" + printEIRNo + ", acceptOrReject="
				+ acceptOrReject + ", cugId=" + cugId + ", operationReefer=" + operationReefer + ", temp=" + temp
				+ ", tempUnit=" + tempUnit + ", imdg=" + imdg + ", unc=" + unc + ", oogoh=" + oogoh + ", oogol=" + oogol
				+ ", oogof=" + oogof + ", oogoa=" + oogoa + ", oogor=" + oogor + ", FOTBKGFlag=" + FOTBKGFlag
				+ ", tareWeight=" + tareWeight + ", grossWeight=" + grossWeight + ", netWeight=" + netWeight
				+ ", containerType=" + containerType + ", errXMLMsg=" + errXMLMsg + ", contRefer=" + contRefer
				+ ", containerDischargeDateTime=" + containerDischargeDateTime + ", impCarrierType=" + impCarrierType
				+ ", impCarrier=" + impCarrier + ", vesselCode=" + vesselCode + ", vesselVoyage=" + vesselVoyage
				+ ", vesselVisitID=" + vesselVisitID + ", vesselScn=" + vesselScn + ", vesselName=" + vesselName
				+ ", vesselETADate=" + vesselETADate + ", vesselATADate=" + vesselATADate + ", damageCodeInfo="
				+ damageCodeInfo + ", vesselStatus=" + vesselStatus + ", rtgExecustionStatus=" + rtgExecustionStatus
				+ ", gcsDelcarerNo=" + gcsDelcarerNo + ", gcsBlock=" + gcsBlock + ", pkfzBlock=" + pkfzBlock
				+ ", lpkBlock=" + lpkBlock + ", cusGCSReleaseDate=" + cusGCSReleaseDate + ", gatePassIssued="
				+ gatePassIssued + ", portSecurity=" + portSecurity + ", moveType=" + moveType
				+ ", rtgExecustionDateTime=" + rtgExecustionDateTime + ", impOrderType=" + impOrderType
				+ ", impOrderStatus=" + impOrderStatus + ", containerSubHandlingType=" + containerSubHandlingType + "]";
	}

	public static ImportContainer emptyImportContainer() {
		ImportContainer importContainer = new ImportContainer();
		importContainer.setContainer(new CommonContainerDTO());
		importContainer.getContainer().setContainerNumber("FSCU8145801");
		importContainer.getContainer().setContainerFullOrEmpty("E");
		importContainer.setSealAttribute(new CommonSealDTO());
		importContainer.getSealAttribute().setSeal01Number("SL191");
		importContainer.getSealAttribute().setSeal01Origin("C");
		importContainer.getSealAttribute().setSeal02Number("SL192");
		importContainer.getSealAttribute().setSeal02Origin("C");
		importContainer.setRtgExecustionDateTime(LocalDateTime.now());
		importContainer.setRtgExecustionStatus("RGS");
		return importContainer;
	}

	public int getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(int containerLength) {
		this.containerLength = containerLength;
	}

}
