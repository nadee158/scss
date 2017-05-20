package com.privasia.scss.common.dto;

import java.io.Serializable;
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
public class ExportContainer extends Container implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long exportID;

  private String manualPlanIndicator;

  private String bookingNo; // opus exportOrderNo

  private String exportOrderType;

  private String exportOrderStatus;

  // private ShipSCNDTO shipSCN; //perviously it was scn

  private String expOut;

  private String expCar;

  private String expSpod;

  private Integer expWeightBridge;

  private Integer expNetWeight;

  private Integer emptyWeight;

  private Boolean referFlag;

  private String referTempType;

  private Double referTemp;

  private String dgUNCode;

  private String imdgLabelID;

  private Integer oogOH;

  private Integer oogOL;

  private Integer oogOF;

  private Integer oogOA;

  @JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime preCheckDate;

  private Integer pmBTM;

  private Integer trBTM;

  private Integer oogOR;

  private String vesselVoyageIN;

  private String vesselVoyageOUT;

  private String expAgent;

  private String shipCode;

  private String vesselSCN;

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

  private String userRemarks;

  private String lpkApproval;

  private String hdlGoodsCode;

  private String dgDescription;

  private String hdlGoodsDescription;

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


  // private String contRefer;// for GateInXMLRequestService -
  // constructExportContainerRequestXML
  // method

  private int shipperVGM;// for GateInXMLRequestService -
                         // constructExportContainerRequestXML
  // method


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

  private String reeferTempUnit;

  @JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime yardDGOpeningDateTime;

  private String subHandlingType;// ":"1"

  //////////////////////////////////////

  private boolean ogaBlock = false;

  private Optional<Long> shipSCNID;


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


  public Long getExportID() {
    return exportID;
  }


  public void setExportID(Long exportID) {
    this.exportID = exportID;
  }


  public String getManualPlanIndicator() {
    return manualPlanIndicator;
  }


  public void setManualPlanIndicator(String manualPlanIndicator) {
    this.manualPlanIndicator = manualPlanIndicator;
  }


  public String getBookingNo() {
    return bookingNo;
  }


  public void setBookingNo(String bookingNo) {
    this.bookingNo = bookingNo;
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


  public Integer getEmptyWeight() {
    return emptyWeight;
  }


  public void setEmptyWeight(Integer emptyWeight) {
    this.emptyWeight = emptyWeight;
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


  public LocalDateTime getPreCheckDate() {
    return preCheckDate;
  }


  public void setPreCheckDate(LocalDateTime preCheckDate) {
    this.preCheckDate = preCheckDate;
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


  public String getExpAgent() {
    return expAgent;
  }


  public void setExpAgent(String expAgent) {
    this.expAgent = expAgent;
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


  public boolean isInternalBlock() {
    return internalBlock;
  }


  public void setInternalBlock(boolean internalBlock) {
    this.internalBlock = internalBlock;
  }


  public String getInternalBlockDesc() {
    return internalBlockDesc;
  }


  public void setInternalBlockDesc(String internalBlockDesc) {
    this.internalBlockDesc = internalBlockDesc;
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


  public boolean isAccessToByPassDg() {
    return accessToByPassDg;
  }


  public void setAccessToByPassDg(boolean accessToByPassDg) {
    this.accessToByPassDg = accessToByPassDg;
  }


  public String getDgMessage() {
    return dgMessage;
  }


  public void setDgMessage(String dgMessage) {
    this.dgMessage = dgMessage;
  }


  public String getDgBypassRemark() {
    return dgBypassRemark;
  }


  public void setDgBypassRemark(String dgBypassRemark) {
    this.dgBypassRemark = dgBypassRemark;
  }


  public boolean isDontValidateDg() {
    return dontValidateDg;
  }


  public void setDontValidateDg(boolean dontValidateDg) {
    this.dontValidateDg = dontValidateDg;
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


  public int getShipperVGM() {
    return shipperVGM;
  }


  public void setShipperVGM(int shipperVGM) {
    this.shipperVGM = shipperVGM;
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


  public String getSubHandlingType() {
    return subHandlingType;
  }


  public void setSubHandlingType(String subHandlingType) {
    this.subHandlingType = subHandlingType;
  }


  public boolean isOgaBlock() {
    return ogaBlock;
  }


  public void setOgaBlock(boolean ogaBlock) {
    this.ogaBlock = ogaBlock;
  }


  public Optional<Long> getShipSCNID() {
    return shipSCNID;
  }


  public void setShipSCNID(Optional<Long> shipSCNID) {
    this.shipSCNID = shipSCNID;
  }


}
