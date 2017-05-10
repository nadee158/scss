package com.privasia.scss.common.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

/**
 * @author nadee158
 *
 */
public class ExportContainer {

	private Long exportID;

	private CommonContainerDTO container;

	private CommonGateInOutDTO commonGateInOut;

	private BaseCommonGateInOutDTO baseCommonGateInOutAttribute;

	private String manualPlanIndicator;

	private String bookingNo; // opus exportOrderNo

	private String exportOrderType;

	private String exportOrderStatus;

	// private ShipSCNDTO shipSCN; //perviously it was scn

	private String gateInOut;

	private String expOut;

	private String expCar;

	private String expSpod;

	private CommonSealDTO sealAttribute;

	private Integer expWeightBridge;

	private Integer expNetWeight;

	private Integer emptyWeight;

	private Boolean referFlag;

	private String referTempType;

	private Double referTemp;

	private String imdg;

	private String dgUNCode;

	private String imdgLabelID;

	private Integer oogOH;

	private Integer oogOL;

	private Integer oogOF;

	private Integer oogOA;

	private String containerPosition;

	private String gateOutRemarks;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime preCheckDate;

	private String yardPosition;

	private String yardBayCode;

	private Integer pmBTM;

	private Integer trBTM;

	private Integer oogOR;

	private Integer callCard;

	private String vesselVisitID;

	private String vesselVoyageIN;

	private String vesselVoyageOUT;

	private String vesselCode;

	private String vesselName;

	private String expAgent;

	private String vesselStatus;

	private String shipCode;

	private String vesselSCN;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime vesselETADate;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime vesselATADate;

	private String shippingAgent;

	private boolean oogSSR = false;

	private boolean overClosingSSR = false;

	private boolean replanSSR = false;

	private String ssrBlockStatus;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime ssrBlockStatusDate;

	private String gcsBlockStatus;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime gcsBlockStatusDate;

	private String gcsDeclareNo;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime gcsLastCheck;

	private PrintEirDTO printEir;

	private String userRemarks;

	private String lpkApproval;

	private String hdlGoodsCode;

	private String dgDescription;

	private String hdlGoodsDescription;

	private Integer tareWeight;

	private Integer grossWeight;

	private Integer cosmosNetWeight;

	private Boolean backToback = false;

	private Double weightDiffPercentage;

	private Double weightDifference;

	private DamageCodeDTO damageCode_01;

	private DamageCodeDTO damageCode_02;

	private DamageCodeDTO damageCode_03;

	private DamageCodeDTO damageCode_04;

	private DamageCodeDTO damageCode_05;

	private DamageCodeDTO damageCode_06;

	private DamageCodeDTO damageCode_07;

	private DamageCodeDTO damageCode_08;

	private DamageCodeDTO damageCode_09;

	private Boolean dontValidateSeal = true;

	private Boolean wrongDoor;

	private String hpabISOCode;

	private String cosmosISOCode;

	private String pmWeight;

	private String trailerWeight;

	private String trailerPlateNo;

	private String fuelWeight;

	private String tireWeight;

	private String variance;

	private boolean withinTolerance = false;

	private String calculatedVariance;

	private String solasCertNo;

	private CommonSolasDTO solas = new CommonSolasDTO();

	/////////////// EXTRA FILEDS FOUND BASED ON COMPILE ERRORS/////////////////
	private boolean internalBlock = false; // for CosmosExportRepository -
	// extractInternalBlock method

	private String internalBlockDesc; // for CosmosExportRepository -
										// extractInternalBlock method

	private Boolean lpkEdiEnabled = false; // for GateInExpNormalController -
									// checkIfDGContainer method

	private boolean bypassDg; // for GateInExpNormalController -
								// checkIfDGContainer method

	private boolean dgWithinWindowEntry; // for GateInExpNormalController -
											// checkIfDGContainer method
	
	private boolean accessToByPassDg; 
	
	private String dgMessage;
	
	private String dgBypassRemark;
	
	private boolean dontValidateDg = false;

	private String lpkClass; // for GateInExpNormalController -
								// checkIfDGContainer method

	private boolean isRegisteredInEarlyEntry; // for GateInExpNormalController -
												// checkIfDGContainer
												// method
	
	private LocalTime startEarlyEntry; // for GateInExpNormalController -
									// checkIfDGContainer method
	
	private LocalTime endEarlyEntry; // for GateInExpNormalController -
									// checkIfDGContainer method
	
	private String startFullEarlyEntryTime;
	
	private String endFullEarlyEntryTime;

	private boolean allowBypassDgValRemote; // for GateInExpNormalController -
											// checkIfDGContainer
											// method

	private int totalBooking;// for GateInExpNormalController -
								// validateContainer method

	private boolean bookingNoExist = false;// for GateInExpNormalController -
									// validateContainer method

	private boolean earlyEntry = false;// for GateInExpNormalController -
								// validateContainer method

	private int storagePeriod = -1;// for ContainerService - isAllowIn method

	private boolean bypassEEntry;// for ContainerService - isAllowIn method

	private String errXMLMsg;// for GateInXMLRequestService -
								// constructExportContainerRequestXML
								// method

	//private String contRefer;// for GateInXMLRequestService -
								// constructExportContainerRequestXML
	// method

	private int shipperVGM;// for GateInXMLRequestService -
							// constructExportContainerRequestXML
	// method

	private String operationReefer;// for GateInXMLRequestService -
									// constructExportContainerRequestXML
									// constructExportContainerOperationReeferInfo

	// private String temp;// for GateInXMLRequestService -
	// constructExportContainerRequestXMLconstructExportContainerOperationReeferInfo

	// private String tempUnit;// for GateInXMLRequestService -
	// constructExportContainerRequestXML
	// constructExportContainerOperationReeferInfo

	/////////////////////////////////////
	// FOR OPUS SERVICE
	private String expCarrierType;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime yardOpeningDateTime;

	private String containerType;

	private String reeferTempUnit;

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime yardDGOpeningDateTime;

	private String shippingLine;

	private String subHandlingType;// ":"1"
	private String rtgExecustionStatus;// ":"RGS"

	@JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime rtgExecustionDateTime;// ;":"20161212101010"

	//////////////////////////////////////

	private boolean ogaBlock = false;
	
	private Optional<Long> shipSCNID;


	public Long getExportID() {
		return exportID;
	}

	public void setExportID(Long exportID) {
		this.exportID = exportID;
	}

	public CommonContainerDTO getContainer() {
		return container;
	}

	public void setContainer(CommonContainerDTO container) {
		this.container = container;
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

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	/*
	 * public ShipSCNDTO getShipSCN() { return shipSCN; }
	 * 
	 * public void setShipSCN(ShipSCNDTO shipSCN) { this.shipSCN = shipSCN; }
	 */

	public String getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(String gateInOut) {
		this.gateInOut = gateInOut;
	}

	public String getExpOut() {
		return expOut;
	}

	public void setExpOut(String expOut) {
		this.expOut = expOut;
	}

	public String getExpCar() {
		return expCar;
	}

	public void setExpCar(String expCar) {
		this.expCar = expCar;
	}

	public String getExpSpod() {
		return expSpod;
	}

	public void setExpSpod(String expSpod) {
		this.expSpod = expSpod;
	}

	public CommonSealDTO getSealAttribute() {
		return sealAttribute;
	}

	public void setSealAttribute(CommonSealDTO sealAttribute) {
		this.sealAttribute = sealAttribute;
	}

	public Integer getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(Integer expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	public Integer getExpNetWeight() {
		return expNetWeight;
	}

	public void setExpNetWeight(Integer expNetWeight) {
		this.expNetWeight = expNetWeight;
	}

	public Boolean getReferFlag() {
		return referFlag;
	}

	public void setReferFlag(Boolean referFlag) {
		this.referFlag = referFlag;
	}

	public String getReferTempType() {
		return referTempType;
	}

	public void setReferTempType(String referTempType) {
		this.referTempType = referTempType;
	}

	public Double getReferTemp() {
		return referTemp;
	}

	public void setReferTemp(Double referTemp) {
		this.referTemp = referTemp;
	}

	public String getImdg() {
		return imdg;
	}

	public void setImdg(String imdg) {
		this.imdg = imdg;
	}

	public String getDgUNCode() {
		return dgUNCode;
	}

	public void setDgUNCode(String dgUNCode) {
		this.dgUNCode = dgUNCode;
	}

	public String getImdgLabelID() {
		return imdgLabelID;
	}

	public void setImdgLabelID(String imdgLabelID) {
		this.imdgLabelID = imdgLabelID;
	}

	public Integer getOogOH() {
		return oogOH;
	}

	public void setOogOH(Integer oogOH) {
		this.oogOH = oogOH;
	}

	public Integer getOogOL() {
		return oogOL;
	}

	public void setOogOL(Integer oogOL) {
		this.oogOL = oogOL;
	}

	public Integer getOogOF() {
		return oogOF;
	}

	public void setOogOF(Integer oogOF) {
		this.oogOF = oogOF;
	}

	public Integer getOogOA() {
		return oogOA;
	}

	public void setOogOA(Integer oogOA) {
		this.oogOA = oogOA;
	}

	public String getContainerPosition() {
		return containerPosition;
	}

	public void setContainerPosition(String containerPosition) {
		this.containerPosition = containerPosition;
	}

	public LocalDateTime getPreCheckDate() {
		return preCheckDate;
	}

	public void setPreCheckDate(LocalDateTime preCheckDate) {
		this.preCheckDate = preCheckDate;
	}

	public String getYardPosition() {
		return yardPosition;
	}

	public void setYardPosition(String yardPosition) {
		this.yardPosition = yardPosition;
	}

	public Integer getPmBTM() {
		return pmBTM;
	}

	public void setPmBTM(Integer pmBTM) {
		this.pmBTM = pmBTM;
	}

	public Integer getTrBTM() {
		return trBTM;
	}

	public void setTrBTM(Integer trBTM) {
		this.trBTM = trBTM;
	}

	public Integer getOogOR() {
		return oogOR;
	}

	public void setOogOR(Integer oogOR) {
		this.oogOR = oogOR;
	}

	public Integer getCallCard() {
		return callCard;
	}

	public void setCallCard(Integer callCard) {
		this.callCard = callCard;
	}

	public String getVesselVisitID() {
		return vesselVisitID;
	}

	public void setVesselVisitID(String vesselVisitID) {
		this.vesselVisitID = vesselVisitID;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getExpAgent() {
		return expAgent;
	}

	public void setExpAgent(String expAgent) {
		this.expAgent = expAgent;
	}

	public String getVesselStatus() {
		return vesselStatus;
	}

	public void setVesselStatus(String vesselStatus) {
		this.vesselStatus = vesselStatus;
	}

	public String getShipCode() {
		return shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public String getVesselSCN() {
		return vesselSCN;
	}

	public void setVesselSCN(String vesselSCN) {
		this.vesselSCN = vesselSCN;
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

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public boolean isOogSSR() {
		return oogSSR;
	}

	public void setOogSSR(boolean oogSSR) {
		this.oogSSR = oogSSR;
	}

	public boolean isOverClosingSSR() {
		return overClosingSSR;
	}

	public void setOverClosingSSR(boolean overClosingSSR) { 
		this.overClosingSSR = overClosingSSR;
	}

	public boolean isReplanSSR() {
		return replanSSR;
	}

	public void setReplanSSR(boolean replanSSR) {
		this.replanSSR = replanSSR;
	}

	public String getSsrBlockStatus() {
		return ssrBlockStatus;
	}

	public void setSsrBlockStatus(String ssrBlockStatus) {
		this.ssrBlockStatus = ssrBlockStatus;
	}

	public LocalDateTime getSsrBlockStatusDate() {
		return ssrBlockStatusDate;
	}

	public void setSsrBlockStatusDate(LocalDateTime ssrBlockStatusDate) {
		this.ssrBlockStatusDate = ssrBlockStatusDate;
	}

	public String getGcsBlockStatus() {
		return gcsBlockStatus;
	}

	public void setGcsBlockStatus(String gcsBlockStatus) {
		this.gcsBlockStatus = gcsBlockStatus;
	}

	public LocalDateTime getGcsBlockStatusDate() {
		return gcsBlockStatusDate;
	}

	public void setGcsBlockStatusDate(LocalDateTime gcsBlockStatusDate) {
		this.gcsBlockStatusDate = gcsBlockStatusDate;
	}

	public String getGcsDeclareNo() {
		return gcsDeclareNo;
	}

	public void setGcsDeclareNo(String gcsDeclareNo) {
		this.gcsDeclareNo = gcsDeclareNo;
	}

	public LocalDateTime getGcsLastCheck() {
		return gcsLastCheck;
	}

	public void setGcsLastCheck(LocalDateTime gcsLastCheck) {
		this.gcsLastCheck = gcsLastCheck;
	}

	public PrintEirDTO getPrintEir() {
		return printEir;
	}

	public void setPrintEir(PrintEirDTO printEir) {
		this.printEir = printEir;
	}

	public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}

	public String getLpkApproval() {
		return lpkApproval;
	}

	public void setLpkApproval(String lpkApproval) {
		this.lpkApproval = lpkApproval;
	}

	public String getHdlGoodsCode() {
		return hdlGoodsCode;
	}

	public void setHdlGoodsCode(String hdlGoodsCode) {
		this.hdlGoodsCode = hdlGoodsCode;
	}

	public String getDgDescription() {
		return dgDescription;
	}

	public void setDgDescription(String dgDescription) {
		this.dgDescription = dgDescription;
	}

	public String getHdlGoodsDescription() {
		return hdlGoodsDescription;
	}

	public void setHdlGoodsDescription(String hdlGoodsDescription) {
		this.hdlGoodsDescription = hdlGoodsDescription;
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

	public Integer getCosmosNetWeight() {
		return cosmosNetWeight;
	}

	public void setCosmosNetWeight(Integer cosmosNetWeight) {
		this.cosmosNetWeight = cosmosNetWeight;
	}

	public Boolean getBackToback() {
		return backToback;
	}

	public void setBackToback(Boolean backToback) {
		this.backToback = backToback;
	}

	public Double getWeightDiffPercentage() {
		return weightDiffPercentage;
	}

	public void setWeightDiffPercentage(Double weightDiffPercentage) {
		this.weightDiffPercentage = weightDiffPercentage;
	}

	public Double getWeightDifference() {
		return weightDifference;
	}

	public void setWeightDifference(Double weightDifference) {
		this.weightDifference = weightDifference;
	}

	public DamageCodeDTO getDamageCode_01() {
		return damageCode_01;
	}

	public void setDamageCode_01(DamageCodeDTO damageCode_01) {
		this.damageCode_01 = damageCode_01;
	}

	public DamageCodeDTO getDamageCode_02() {
		return damageCode_02;
	}

	public void setDamageCode_02(DamageCodeDTO damageCode_02) {
		this.damageCode_02 = damageCode_02;
	}

	public DamageCodeDTO getDamageCode_03() {
		return damageCode_03;
	}

	public void setDamageCode_03(DamageCodeDTO damageCode_03) {
		this.damageCode_03 = damageCode_03;
	}

	public DamageCodeDTO getDamageCode_04() {
		return damageCode_04;
	}

	public void setDamageCode_04(DamageCodeDTO damageCode_04) {
		this.damageCode_04 = damageCode_04;
	}

	public DamageCodeDTO getDamageCode_05() {
		return damageCode_05;
	}

	public void setDamageCode_05(DamageCodeDTO damageCode_05) {
		this.damageCode_05 = damageCode_05;
	}

	public DamageCodeDTO getDamageCode_06() {
		return damageCode_06;
	}

	public void setDamageCode_06(DamageCodeDTO damageCode_06) {
		this.damageCode_06 = damageCode_06;
	}

	public DamageCodeDTO getDamageCode_07() {
		return damageCode_07;
	}

	public void setDamageCode_07(DamageCodeDTO damageCode_07) {
		this.damageCode_07 = damageCode_07;
	}

	public DamageCodeDTO getDamageCode_08() {
		return damageCode_08;
	}

	public void setDamageCode_08(DamageCodeDTO damageCode_08) {
		this.damageCode_08 = damageCode_08;
	}

	public DamageCodeDTO getDamageCode_09() {
		return damageCode_09;
	}

	public void setDamageCode_09(DamageCodeDTO damageCode_09) {
		this.damageCode_09 = damageCode_09;
	}

	public Boolean getDontValidateSeal() {
		return dontValidateSeal;
	}

	public void setDontValidateSeal(Boolean dontValidateSeal) {
		this.dontValidateSeal = dontValidateSeal;
	}

	public Boolean getWrongDoor() {
		return wrongDoor;
	}

	public void setWrongDoor(Boolean wrongDoor) {
		this.wrongDoor = wrongDoor;
	}

	public String getHpabISOCode() {
		return hpabISOCode;
	}

	public void setHpabISOCode(String hpabISOCode) {
		this.hpabISOCode = hpabISOCode;
	}

	public String getCosmosISOCode() {
		return cosmosISOCode;
	}

	public void setCosmosISOCode(String cosmosISOCode) {
		this.cosmosISOCode = cosmosISOCode;
	}

	public String getPmWeight() {
		return pmWeight;
	}

	public void setPmWeight(String pmWeight) {
		this.pmWeight = pmWeight;
	}

	public String getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(String trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	public String getTrailerPlateNo() {
		return trailerPlateNo;
	}

	public void setTrailerPlateNo(String trailerPlateNo) {
		this.trailerPlateNo = trailerPlateNo;
	}

	public String getFuelWeight() {
		return fuelWeight;
	}

	public void setFuelWeight(String fuelWeight) {
		this.fuelWeight = fuelWeight;
	}

	public String getTireWeight() {
		return tireWeight;
	}

	public void setTireWeight(String tireWeight) {
		this.tireWeight = tireWeight;
	}

	public String getVariance() {
		return variance;
	}

	public void setVariance(String variance) {
		this.variance = variance;
	}

	public boolean isWithinTolerance() {
		return withinTolerance;
	}

	public void setWithinTolerance(boolean withinTolerance) {
		this.withinTolerance = withinTolerance;
	}

	public String getCalculatedVariance() {
		return calculatedVariance;
	}

	public void setCalculatedVariance(String calculatedVariance) {
		this.calculatedVariance = calculatedVariance;
	}

	public String getSolasCertNo() {
		return solasCertNo;
	}

	public void setSolasCertNo(String solasCertNo) {
		this.solasCertNo = solasCertNo;
	}

	public CommonSolasDTO getSolas() {
		return solas;
	}

	public void setSolas(CommonSolasDTO solas) {
		this.solas = solas;
	}

	public String getInternalBlockDesc() {
		return internalBlockDesc;
	}

	public void setInternalBlockDesc(String internalBlockDesc) {
		this.internalBlockDesc = internalBlockDesc;
	}

	public boolean isInternalBlock() {
		return internalBlock;
	}

	public void setInternalBlock(boolean internalBlock) {
		this.internalBlock = internalBlock;
	}

	public boolean isOgaBlock() {
		return ogaBlock;
	}

	public void setOgaBlock(boolean ogaBlock) {
		this.ogaBlock = ogaBlock;
	}

	public Boolean getLpkEdiEnabled() {
		return lpkEdiEnabled;
	}

	public void setLpkEdiEnabled(Boolean lpkEdiEnabled) {
		this.lpkEdiEnabled = lpkEdiEnabled;
	}

	public boolean isBypassDg() {
		return bypassDg;
	}

	public void setBypassDg(boolean bypassDg) {
		this.bypassDg = bypassDg;
	}

	public boolean isDgWithinWindowEntry() {
		return dgWithinWindowEntry;
	}

	public void setDgWithinWindowEntry(boolean dgWithinWindowEntry) {
		this.dgWithinWindowEntry = dgWithinWindowEntry;
	}

	public String getLpkClass() {
		return lpkClass;
	}

	public void setLpkClass(String lpkClass) {
		this.lpkClass = lpkClass;
	}

	public boolean isRegisteredInEarlyEntry() {
		return isRegisteredInEarlyEntry;
	}

	public void setRegisteredInEarlyEntry(boolean isRegisteredInEarlyEntry) {
		this.isRegisteredInEarlyEntry = isRegisteredInEarlyEntry;
	}

	public LocalTime getStartEarlyEntry() {
		return startEarlyEntry;
	}

	public void setStartEarlyEntry(LocalTime startEarlyEntry) {
		this.startEarlyEntry = startEarlyEntry;
	}

	public LocalTime getEndEarlyEntry() {
		return endEarlyEntry;
	}

	public void setEndEarlyEntry(LocalTime endEarlyEntry) {
		this.endEarlyEntry = endEarlyEntry;
	}

	public boolean isAllowBypassDgValRemote() {
		return allowBypassDgValRemote;
	}

	public void setAllowBypassDgValRemote(boolean allowBypassDgValRemote) {
		this.allowBypassDgValRemote = allowBypassDgValRemote;
	}

	public int getTotalBooking() {
		return totalBooking;
	}

	public void setTotalBooking(int totalBooking) {
		this.totalBooking = totalBooking;
	}

	public boolean isBookingNoExist() {
		return bookingNoExist;
	}

	public void setBookingNoExist(boolean bookingNoExist) {
		this.bookingNoExist = bookingNoExist;
	}

	public boolean isEarlyEntry() {
		return earlyEntry;
	}

	public void setEarlyEntry(boolean earlyEntry) {
		this.earlyEntry = earlyEntry;
	}

	public int getStoragePeriod() {
		return storagePeriod;
	}

	public void setStoragePeriod(int storagePeriod) {
		this.storagePeriod = storagePeriod;
	}

	public boolean isBypassEEntry() {
		return bypassEEntry;
	}

	public void setBypassEEntry(boolean bypassEEntry) {
		this.bypassEEntry = bypassEEntry;
	}

	public String getErrXMLMsg() {
		return errXMLMsg;
	}

	public void setErrXMLMsg(String errXMLMsg) {
		this.errXMLMsg = errXMLMsg;
	}

	/*public String getContRefer() {
		return contRefer;
	}

	public void setContRefer(String contRefer) {
		this.contRefer = contRefer;
	}*/

	public int getShipperVGM() {
		return shipperVGM;
	}

	public void setShipperVGM(int shipperVGM) {
		this.shipperVGM = shipperVGM;
	}

	public String getOperationReefer() {
		return operationReefer;
	}

	public void setOperationReefer(String operationReefer) {
		this.operationReefer = operationReefer;
	}

	/*
	 * public String getTemp() { return temp; }
	 * 
	 * public void setTemp(String temp) { this.temp = temp; }
	 * 
	 * public String getTempUnit() { return tempUnit; }
	 * 
	 * public void setTempUnit(String tempUnit) { this.tempUnit = tempUnit; }
	 */

	public String getExpCarrierType() {
		return expCarrierType;
	}

	public void setExpCarrierType(String expCarrierType) {
		this.expCarrierType = expCarrierType;
	}

	public LocalDateTime getYardOpeningDateTime() {
		return yardOpeningDateTime;
	}

	public void setYardOpeningDateTime(LocalDateTime yardOpeningDateTime) {
		this.yardOpeningDateTime = yardOpeningDateTime;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getReeferTempUnit() {
		return reeferTempUnit;
	}

	public void setReeferTempUnit(String reeferTempUnit) {
		this.reeferTempUnit = reeferTempUnit;
	}

	public LocalDateTime getYardDGOpeningDateTime() {
		return yardDGOpeningDateTime;
	}

	public void setYardDGOpeningDateTime(LocalDateTime yardDGOpeningDateTime) {
		this.yardDGOpeningDateTime = yardDGOpeningDateTime;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getSubHandlingType() {
		return subHandlingType;
	}

	public void setSubHandlingType(String subHandlingType) {
		this.subHandlingType = subHandlingType;
	}

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

	public String getExportOrderType() {
		return exportOrderType;
	}

	public void setExportOrderType(String exportOrderType) {
		this.exportOrderType = exportOrderType;
	}

	public String getExportOrderStatus() {
		return exportOrderStatus;
	}

	public void setExportOrderStatus(String exportOrderStatus) {
		this.exportOrderStatus = exportOrderStatus;
	}

	public String getVesselVoyageIN() {
		return vesselVoyageIN;
	}

	public void setVesselVoyageIN(String vesselVoyageIN) {
		this.vesselVoyageIN = vesselVoyageIN;
	}

	public String getVesselVoyageOUT() {
		return vesselVoyageOUT;
	}

	public void setVesselVoyageOUT(String vesselVoyageOUT) {
		this.vesselVoyageOUT = vesselVoyageOUT;
	}

	public String getManualPlanIndicator() {
		return manualPlanIndicator;
	}

	public void setManualPlanIndicator(String manualPlanIndicator) {
		this.manualPlanIndicator = manualPlanIndicator;
	}

	public String getYardBayCode() {
		return yardBayCode;
	}

	public void setYardBayCode(String yardBayCode) {
		this.yardBayCode = yardBayCode;
	}

	public Integer getEmptyWeight() {
		return emptyWeight;
	}

	public void setEmptyWeight(Integer emptyWeight) {
		this.emptyWeight = emptyWeight;
	}

	public String getGateOutRemarks() {
		return gateOutRemarks;
	}

	public void setGateOutRemarks(String gateOutRemarks) {
		this.gateOutRemarks = gateOutRemarks;
	}

	public Optional<Long> getShipSCNID() {
		return shipSCNID;
	}

	public void setShipSCNID(Optional<Long> shipSCNID) {
		this.shipSCNID = shipSCNID;
	}
	
	public String getStartFullEarlyEntryTime() {
		return startFullEarlyEntryTime;
	}

	public void setStartFullEarlyEntryTime(String startFullEarlyEntryTime) {
		this.startFullEarlyEntryTime = startFullEarlyEntryTime;
	}

	public String getEndFullEarlyEntryTime() {
		return endFullEarlyEntryTime;
	}

	public void setEndFullEarlyEntryTime(String endFullEarlyEntryTime) {
		this.endFullEarlyEntryTime = endFullEarlyEntryTime;
	}
	
	public String getDgMessage() {
		return dgMessage;
	}

	public void setDgMessage(String dgMessage) {
		this.dgMessage = dgMessage;
	}
	
	public boolean isAccessToByPassDg() {
		return accessToByPassDg;
	}

	public void setAccessToByPassDg(boolean accessToByPassDg) {
		this.accessToByPassDg = accessToByPassDg;
	}
	
	public boolean isDontValidateDg() {
		return dontValidateDg;
	}

	public void setDontValidateDg(boolean dontValidateDg) {
		this.dontValidateDg = dontValidateDg;
	}
	
	public String getDgBypassRemark() {
		return dgBypassRemark;
	}

	public void setDgBypassRemark(String dgBypassRemark) {
		this.dgBypassRemark = dgBypassRemark;
	}

	@Override
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
