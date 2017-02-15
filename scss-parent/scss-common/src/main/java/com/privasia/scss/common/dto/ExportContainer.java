package com.privasia.scss.common.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.CommonUtil;

/**
 * @author nadee158
 *
 */
public class ExportContainer {

	private static final Log log = LogFactory.getLog(ExportContainer.class);

	private Long exportID;

	private CommonContainerDTO container;

	private CommonGateInOutDTO commonGateInOut;

	private BaseCommonGateInOutDTO baseCommonGateInOutAttribute;

	private String optFlag;

	private String bookingNo;

	private ShipSCNDTO scn;

	private String gateInOut;

	private String expLine;

	private String expOut;

	private String expCar;

	private String expSpod;

	private CommonSealDTO sealAttribute;

	private Integer expWeightBridge;

	private Integer expNetWeight;

	private Boolean referFlag;

	private String referTempType;

	private Integer referTemp;

	private String imdg;

	private String expUN;

	private String imdgLabelID;

	private Integer oogOH;

	private Integer oogOL;

	private Integer oogOF;

	private Integer oogOA;

	private String containerPosition;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime preCheckDate;

	private String yardPosition;

	private String bayCode;

	private Integer pmBTM;

	private Integer trBTM;

	private Integer oogOR;

	private Integer callCard;

	private String vesselVisitID;

	private String vesselVoyage;

	private String vesselCode;

	private String vesselName;

	private String expAgent;

	private String vesselStatus;

	private String shipCode;

	private String vesselSCN;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime vesselETADate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime vesselATADate;

	private String agentCode;

	private Boolean oogSSR;

	private Boolean overClosingSSR;

	private Boolean replanSSR;

	private String ssrBlockStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime ssrBlockStatusDate;

	private String gcsBlockStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime gcsBlockStatusDate;

	private String gcsDeclareNo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime gcsLastCheck;

	private PrintEirDTO printEir;

	private String userRemarks;

	private String kpaApproval;

	private String hdlGoodsCode;

	private String dgDescription;

	private String hdlGoodsDescription;

	private Integer cosmosTareWeight;

	private Integer cosmosGrossWeight;

	private Integer cosmosNetWeight;

	private CardUsageDTO cardUsage;

	private Boolean backToback;

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

	private Boolean dontValidateSeal;

	private Boolean wrongDoor;

	private String hpabISOCode;

	private String cosmosISOCode;

	private String pmWeight;

	private String trailerWeight;

	private String trailerPlateNo;

	private String fuelWeight;

	private String tireWeight;

	private String variance;

	private boolean withinTolerance;

	private String calculatedVariance;

	private String solasCertNo;

	private CommonSolasDTO solas;

	/////////////// EXTRA FILEDS FOUND BASED ON COMPILE ERRORS/////////////////
	private boolean internalBlock; // for CosmosExportRepository -
									// extractInternalBlock method

	private String internalBlockDesc; // for CosmosExportRepository -
										// extractInternalBlock method

	private String lpkEdiEnabled; // for GateInExpNormalController -
									// checkIfDGContainer method

	private boolean bypassDg; // for GateInExpNormalController -
								// checkIfDGContainer method

	private boolean dgWithinWindowEntry; // for GateInExpNormalController -
											// checkIfDGContainer method

	private String kpaClass; // for GateInExpNormalController -
								// checkIfDGContainer method

	private boolean isRegisteredInEarlyEntry; // for GateInExpNormalController -
												// checkIfDGContainer
												// method

	private String startEarlyEntry; // for GateInExpNormalController -
									// checkIfDGContainer method

	private String endEarlyEntry; // for GateInExpNormalController -
									// checkIfDGContainer method

	private boolean allowBypassDgValRemote; // for GateInExpNormalController -
											// checkIfDGContainer
											// method

	private int totalBooking;// for GateInExpNormalController -
								// validateContainer method

	private boolean bookingNoExist;// for GateInExpNormalController -
									// validateContainer method

	private boolean earlyEntry;// for GateInExpNormalController -
								// validateContainer method

	private int storagePeriod;// for ContainerService - isAllowIn method

	private boolean bypassEEntry;// for ContainerService - isAllowIn method

	private String errXMLMsg;// for GateInXMLRequestService -
								// constructExportContainerRequestXML
								// method

	private String contRefer;// for GateInXMLRequestService -
								// constructExportContainerRequestXML
	// method

	private int shipperVGM;// for GateInXMLRequestService -
							// constructExportContainerRequestXML
	// method

	private String operationReefer;// for GateInXMLRequestService -
									// constructExportContainerRequestXML
									// constructExportContainerOperationReeferInfo

	private String temp;// for GateInXMLRequestService -
						// constructExportContainerRequestXMLconstructExportContainerOperationReeferInfo

	private String tempUnit;// for GateInXMLRequestService -
							// constructExportContainerRequestXML
							// constructExportContainerOperationReeferInfo

	private List<DamageCodeDTO> damages;
	/////////////////////////////////////
	// FOR OPUS SERVICE
	private String expCarrierType;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime yardOpeningDateTime;

	private String containerType;

	private String reeferTempUnit;

	private String containerDGUNCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime yardDGOpeningDateTime;

	private String vgmType;// S
	private String vgmWeighingStation;//
	private String vgmWitnessName;//
	private String vgmWitnessID;//
	private String vgmRefNo;//
	private String vgmMGW;// 1
	private String vgmNetWeight;//
	private String vgmVerificationDatetime;//

	private String shippingLine;

	private String subHandlingType;// ":"1"
	private String rtgExecustionStatus;// ":"RGS"
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime rtgExecustionDateTime;// ;":"20161212101010"

	//////////////////////////////////////

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

	public String getOptFlag() {
		return optFlag;
	}

	public void setOptFlag(String optFlag) {
		this.optFlag = optFlag;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public ShipSCNDTO getScn() {
		return scn;
	}

	public void setScn(ShipSCNDTO scn) {
		this.scn = scn;
	}

	public String getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(String gateInOut) {
		this.gateInOut = gateInOut;
	}

	public String getExpLine() {
		return expLine;
	}

	public void setExpLine(String expLine) {
		this.expLine = expLine;
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

	public Integer getReferTemp() {
		return referTemp;
	}

	public void setReferTemp(Integer referTemp) {
		this.referTemp = referTemp;
	}

	public String getImdg() {
		return imdg;
	}

	public void setImdg(String imdg) {
		this.imdg = imdg;
	}

	public String getExpUN() {
		return expUN;
	}

	public void setExpUN(String expUN) {
		this.expUN = expUN;
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

	public String getBayCode() {
		return bayCode;
	}

	public void setBayCode(String bayCode) {
		this.bayCode = bayCode;
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

	public String getVesselVoyage() {
		return vesselVoyage;
	}

	public void setVesselVoyage(String vesselVoyage) {
		this.vesselVoyage = vesselVoyage;
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

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public Boolean getOogSSR() {
		return oogSSR;
	}

	public void setOogSSR(Boolean oogSSR) {
		this.oogSSR = oogSSR;
	}

	public Boolean getOverClosingSSR() {
		return overClosingSSR;
	}

	public void setOverClosingSSR(Boolean overClosingSSR) {
		this.overClosingSSR = overClosingSSR;
	}

	public Boolean getReplanSSR() {
		return replanSSR;
	}

	public void setReplanSSR(Boolean replanSSR) {
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

	public String getKpaApproval() {
		return kpaApproval;
	}

	public void setKpaApproval(String kpaApproval) {
		this.kpaApproval = kpaApproval;
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

	public Integer getCosmosTareWeight() {
		return cosmosTareWeight;
	}

	public void setCosmosTareWeight(Integer cosmosTareWeight) {
		this.cosmosTareWeight = cosmosTareWeight;
	}

	public Integer getCosmosGrossWeight() {
		return cosmosGrossWeight;
	}

	public void setCosmosGrossWeight(Integer cosmosGrossWeight) {
		this.cosmosGrossWeight = cosmosGrossWeight;
	}

	public Integer getCosmosNetWeight() {
		return cosmosNetWeight;
	}

	public void setCosmosNetWeight(Integer cosmosNetWeight) {
		this.cosmosNetWeight = cosmosNetWeight;
	}

	public CardUsageDTO getCardUsage() {
		return cardUsage;
	}

	public void setCardUsage(CardUsageDTO cardUsage) {
		this.cardUsage = cardUsage;
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

	public static Log getLog() {
		return log;
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

	public List<DamageCodeDTO> getDamages() {
		this.damages = new ArrayList<DamageCodeDTO>();
		if (!(this.damageCode_01 == null)) {
			this.damages.add(this.damageCode_01);
		}
		if (!(this.damageCode_02 == null)) {
			this.damages.add(this.damageCode_02);
		}
		if (!(this.damageCode_03 == null)) {
			this.damages.add(this.damageCode_03);
		}
		if (!(this.damageCode_04 == null)) {
			this.damages.add(this.damageCode_04);
		}
		if (!(this.damageCode_05 == null)) {
			this.damages.add(this.damageCode_05);
		}
		if (!(this.damageCode_06 == null)) {
			this.damages.add(this.damageCode_06);
		}
		if (!(this.damageCode_07 == null)) {
			this.damages.add(this.damageCode_07);
		}
		if (!(this.damageCode_08 == null)) {
			this.damages.add(this.damageCode_08);
		}
		if (!(this.damageCode_09 == null)) {
			this.damages.add(this.damageCode_09);
		}
		return damages;
	}

	public void setDamages(List<DamageCodeDTO> damages) {
		this.damages = damages;
	}

	public String getLpkEdiEnabled() {
		return lpkEdiEnabled;
	}

	public void setLpkEdiEnabled(String lpkEdiEnabled) {
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

	public String getKpaClass() {
		return kpaClass;
	}

	public void setKpaClass(String kpaClass) {
		this.kpaClass = kpaClass;
	}

	public boolean isRegisteredInEarlyEntry() {
		return isRegisteredInEarlyEntry;
	}

	public void setRegisteredInEarlyEntry(boolean isRegisteredInEarlyEntry) {
		this.isRegisteredInEarlyEntry = isRegisteredInEarlyEntry;
	}

	public String getStartEarlyEntry() {
		return startEarlyEntry;
	}

	public void setStartEarlyEntry(String startEarlyEntry) {
		this.startEarlyEntry = startEarlyEntry;
	}

	public String getEndEarlyEntry() {
		return endEarlyEntry;
	}

	public void setEndEarlyEntry(String endEarlyEntry) {
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

	public String getContRefer() {
		return contRefer;
	}

	public void setContRefer(String contRefer) {
		this.contRefer = contRefer;
	}

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

	public String getContainerDGUNCode() {
		return containerDGUNCode;
	}

	public void setContainerDGUNCode(String containerDGUNCode) {
		this.containerDGUNCode = containerDGUNCode;
	}

	public LocalDateTime getYardDGOpeningDateTime() {
		return yardDGOpeningDateTime;
	}

	public void setYardDGOpeningDateTime(LocalDateTime yardDGOpeningDateTime) {
		this.yardDGOpeningDateTime = yardDGOpeningDateTime;
	}

	public String getVgmType() {
		return vgmType;
	}

	public void setVgmType(String vgmType) {
		this.vgmType = vgmType;
	}

	public String getVgmWeighingStation() {
		return vgmWeighingStation;
	}

	public void setVgmWeighingStation(String vgmWeighingStation) {
		this.vgmWeighingStation = vgmWeighingStation;
	}

	public String getVgmWitnessName() {
		return vgmWitnessName;
	}

	public void setVgmWitnessName(String vgmWitnessName) {
		this.vgmWitnessName = vgmWitnessName;
	}

	public String getVgmWitnessID() {
		return vgmWitnessID;
	}

	public void setVgmWitnessID(String vgmWitnessID) {
		this.vgmWitnessID = vgmWitnessID;
	}

	public String getVgmRefNo() {
		return vgmRefNo;
	}

	public void setVgmRefNo(String vgmRefNo) {
		this.vgmRefNo = vgmRefNo;
	}

	public String getVgmMGW() {
		return vgmMGW;
	}

	public void setVgmMGW(String vgmMGW) {
		this.vgmMGW = vgmMGW;
	}

	public String getVgmNetWeight() {
		return vgmNetWeight;
	}

	public void setVgmNetWeight(String vgmNetWeight) {
		this.vgmNetWeight = vgmNetWeight;
	}

	public String getVgmVerificationDatetime() {
		return vgmVerificationDatetime;
	}

	public void setVgmVerificationDatetime(String vgmVerificationDatetime) {
		this.vgmVerificationDatetime = vgmVerificationDatetime;
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

	@Override
	public String toString() {
		return "ExportContainer [exportID=" + exportID + ", container=" + container + ", commonGateInOut="
				+ commonGateInOut + ", baseCommonGateInOutAttribute=" + baseCommonGateInOutAttribute + ", optFlag="
				+ optFlag + ", bookingNo=" + bookingNo + ", scn=" + scn + ", gateInOut=" + gateInOut + ", expLine="
				+ expLine + ", expOut=" + expOut + ", expCar=" + expCar + ", expSpod=" + expSpod + ", sealAttribute="
				+ sealAttribute + ", expWeightBridge=" + expWeightBridge + ", expNetWeight=" + expNetWeight
				+ ", referFlag=" + referFlag + ", referTempType=" + referTempType + ", referTemp=" + referTemp
				+ ", imdg=" + imdg + ", expUN=" + expUN + ", imdgLabelID=" + imdgLabelID + ", oogOH=" + oogOH
				+ ", oogOL=" + oogOL + ", oogOF=" + oogOF + ", oogOA=" + oogOA + ", containerPosition="
				+ containerPosition + ", preCheckDate=" + preCheckDate + ", yardPosition=" + yardPosition + ", bayCode="
				+ bayCode + ", pmBTM=" + pmBTM + ", trBTM=" + trBTM + ", oogOR=" + oogOR + ", callCard=" + callCard
				+ ", vesselVisitID=" + vesselVisitID + ", vesselVoyage=" + vesselVoyage + ", vesselCode=" + vesselCode
				+ ", vesselName=" + vesselName + ", expAgent=" + expAgent + ", vesselStatus=" + vesselStatus
				+ ", shipCode=" + shipCode + ", vesselSCN=" + vesselSCN + ", vesselETADate=" + vesselETADate
				+ ", vesselATADate=" + vesselATADate + ", agentCode=" + agentCode + ", oogSSR=" + oogSSR
				+ ", overClosingSSR=" + overClosingSSR + ", replanSSR=" + replanSSR + ", ssrBlockStatus="
				+ ssrBlockStatus + ", ssrBlockStatusDate=" + ssrBlockStatusDate + ", gcsBlockStatus=" + gcsBlockStatus
				+ ", gcsBlockStatusDate=" + gcsBlockStatusDate + ", gcsDeclareNo=" + gcsDeclareNo + ", gcsLastCheck="
				+ gcsLastCheck + ", printEir=" + printEir + ", userRemarks=" + userRemarks + ", kpaApproval="
				+ kpaApproval + ", hdlGoodsCode=" + hdlGoodsCode + ", dgDescription=" + dgDescription
				+ ", hdlGoodsDescription=" + hdlGoodsDescription + ", cosmosTareWeight=" + cosmosTareWeight
				+ ", cosmosGrossWeight=" + cosmosGrossWeight + ", cosmosNetWeight=" + cosmosNetWeight + ", cardUsage="
				+ cardUsage + ", backToback=" + backToback + ", weightDiffPercentage=" + weightDiffPercentage
				+ ", weightDifference=" + weightDifference + ", damageCode_01=" + damageCode_01 + ", damageCode_02="
				+ damageCode_02 + ", damageCode_03=" + damageCode_03 + ", damageCode_04=" + damageCode_04
				+ ", damageCode_05=" + damageCode_05 + ", damageCode_06=" + damageCode_06 + ", damageCode_07="
				+ damageCode_07 + ", damageCode_08=" + damageCode_08 + ", damageCode_09=" + damageCode_09
				+ ", dontValidateSeal=" + dontValidateSeal + ", wrongDoor=" + wrongDoor + ", hpabISOCode=" + hpabISOCode
				+ ", cosmosISOCode=" + cosmosISOCode + ", pmWeight=" + pmWeight + ", trailerWeight=" + trailerWeight
				+ ", trailerPlateNo=" + trailerPlateNo + ", fuelWeight=" + fuelWeight + ", tireWeight=" + tireWeight
				+ ", variance=" + variance + ", withinTolerance=" + withinTolerance + ", calculatedVariance="
				+ calculatedVariance + ", solasCertNo=" + solasCertNo + ", solas=" + solas + "]";
	}

	public static ExportContainer emptyExportContainer() {
		// TODO Auto-generated method stub
		// "containerNo":"WANG0000112"
		// ,"exportBookingNo":"BKG193"
		// ,"containerFullOrEmpty":"E"
		// ,"subHandlingType":"1"
		// ,"rtgExecustionStatus":"RGS"
		// ,"rtgExecustionDateTime":"20161212101010"
		ExportContainer exportContainer = new ExportContainer();
		exportContainer.setContainer(new CommonContainerDTO());
		exportContainer.getContainer().setContainerNumber("WANG0000112");
		exportContainer.setBookingNo("BKG193");
		exportContainer.getContainer().setContainerFullOrEmpty("E");
		exportContainer.setSubHandlingType("1");
		exportContainer.setRtgExecustionDateTime(LocalDateTime.now());
		exportContainer.setRtgExecustionStatus("RGS");
		exportContainer.setScn(new ShipSCNDTO());
		exportContainer.setSealAttribute(new CommonSealDTO());
		exportContainer.setPrintEir(new PrintEirDTO());
		exportContainer.setSolas(new CommonSolasDTO());
		exportContainer.setDamageCode_01(new DamageCodeDTO());
		exportContainer.setDamageCode_02(new DamageCodeDTO());
		exportContainer.setDamageCode_03(new DamageCodeDTO());
		exportContainer.setDamageCode_04(new DamageCodeDTO());
		exportContainer.setDamageCode_05(new DamageCodeDTO());
		exportContainer.setDamageCode_06(new DamageCodeDTO());
		exportContainer.setDamageCode_07(new DamageCodeDTO());
		exportContainer.setDamageCode_08(new DamageCodeDTO());
		exportContainer.setDamageCode_09(new DamageCodeDTO());
		return exportContainer;
	}

}
