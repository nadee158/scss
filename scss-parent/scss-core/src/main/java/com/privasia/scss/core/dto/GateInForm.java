package com.privasia.scss.core.dto;

import java.io.Serializable;

public class GateInForm implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int pressedButton;

  // container1
  private String bookingNoC1 = "";
  private String containerNoC1 = "";
  private String inOrOutC1 = "";
  private String lineC1 = "";
  private String fullOrEmptyC1 = "";
  private String goodsC1 = "";
  private String OUTC1 = "";
  private String carC1 = "";
  private String SPODC1 = "";
  private String ISOC1 = "";
  private String sizeC1 = "";
  private String heightC1 = "";
  private String typeC1 = "";
  private String seal1OriginC1 = "";
  private String seal1TypeC1 = "";
  private String seal1C1 = "";
  private String seal2C1 = "";
  private String seal2OriginC1 = "";
  private String seal2TypeC1 = "";
  private String totalWeightBridgeC1 = "";
  private String PMBTMC1 = "0";
  private String TRBTMC1 = "0";
  private String netWeightC1 = "";
  private String operationReeferC1 = "";
  private String tempSignC1 = "";
  private String tempUnitC1 = "";
  private String tempC1 = "";
  private String IMDGC1 = "";
  private String UNC1 = "";
  private String IMDGlabelsC1 = "";
  private String OOGC1flag = "";
  private String OOGOHC1 = "";
  private String OOGOLC1 = "";
  private String OOGOFC1 = "";
  private String OOGOAC1 = "";
  private String OOGORC1 = "";
  private String damage1C1 = "";
  private String damage2C1 = "";
  private String damage3C1 = "";
  private String damage4C1 = "";
  private String damage5C1 = "";
  private String damage6C1 = "";
  private String damage7C1 = "";
  private String damage8C1 = "";
  private String damage9C1 = "";
  private String damage1AC1 = "";
  private String damage2AC1 = "";
  private String damage3AC1 = "";
  private String damage4AC1 = "";
  private String damage5AC1 = "";
  private String damage6AC1 = "";
  private String damage7AC1 = "";
  private String damage8AC1 = "";
  private String damage9AC1 = "";
  private String positionOnTruckC1 = "";
  private String orderFOTC1 = "";
  private String curPosC1 = "";

  // container2
  private String bookingNoC2 = "";
  private String containerNoC2 = "";
  private String inOrOutC2 = "";
  private String lineC2 = "";
  private String fullOrEmptyC2 = "";
  private String goodsC2 = "";
  private String OUTC2 = "";
  private String carC2 = "";
  private String SPODC2 = "";
  private String ISOC2 = "";
  private String sizeC2 = "";
  private String heightC2 = "";
  private String typeC2 = "";
  private String seal1C2 = "";
  private String seal1OriginC2 = "";
  private String seal1TypeC2 = "";
  private String seal2C2 = "";
  private String seal2OriginC2 = "";
  private String seal2TypeC2 = "";
  private String totalWeightBridgeC2 = "";
  private String PMBTMC2 = "0";
  private String TRBTMC2 = "0";
  private String netWeightC2 = "";
  private String operationReeferC2 = "";
  private String tempSignC2 = "";
  private String tempC2 = "";
  private String tempUnitC2 = "";
  private String IMDGC2 = "";
  private String UNC2 = "";
  private String IMDGlabelsC2 = "";
  private String OOGC2flag = "";
  private String OOGOHC2 = "";
  private String OOGOLC2 = "";
  private String OOGOFC2 = "";
  private String OOGOAC2 = "";
  private String OOGORC2 = "";
  private String damage1C2 = "";
  private String damage2C2 = "";
  private String damage3C2 = "";
  private String damage4C2 = "";
  private String damage5C2 = "";
  private String damage6C2 = "";
  private String damage7C2 = "";
  private String damage8C2 = "";
  private String damage9C2 = "";
  private String damage1BC2 = "";
  private String damage2BC2 = "";
  private String damage3BC2 = "";
  private String damage4BC2 = "";
  private String damage5BC2 = "";
  private String damage6BC2 = "";
  private String damage7BC2 = "";
  private String damage8BC2 = "";
  private String damage9BC2 = "";
  private String positionOnTruckC2 = "";
  private String orderFOTC2 = "";
  private String curPosC2 = "";

  // truck info
  private String laneNo = "";
  private String truckHeadNo = "";
  private String truckPlateNo = "";
  private String remarks = "";
  private String EIRNo = "";
  private String newEIRNo = "";

  private String requestXML = "";
  private String replyXML = "";

  private String SCUIdSeq = "";
  private String SCUName = "";
  private String SCNo = "";
  private String comIdSeq = "";
  private String compName = "";
  private String compAcctNo = "";
  private String compStatusDesc = "";
  private String compStatus = "";
  private String compCode = "";
  private String compType = "";
  private String seq1 = "";
  private String seq2 = "";

  private String cardStatus = "";
  private String cardStatusDesc = "";
  private String nationality = "";
  private String newICNo = "";
  private String oldICNo = "";
  private String passportNo = "";
  private String gateImpOrExp = "";
  private String userSessionId = "";

  private String agentCode = "";

  private String MCName = "";
  private String MCNewICNo = "";
  private String MCOldICNo = "";
  private String MCNationality = "";
  private String MCPassportNo = "";
  private String MCNo = "";
  private String MCStatus = "";
  private String MCStatusDesc = "";
  private String MCRole = "";

  private String gatePassNo1 = "";
  private String gatePassNo2 = "";
  private String associateGatePassOrContainer = "";

  private String yardPositionC1 = "";
  private String yardPositionC2 = "";
  private String bayCodeC1 = "";
  private String bayCodeC2 = "";
  private String callCard = "";

  private String impExpFlag = "";
  private String gateInNo = "";
  private String timeIn = "";
  private String compNamePrint = "";
  private String cardIdSeq = "";
  private String clientId = "";

  private String errXMLMsg1 = "";
  private String errXMLMsg2 = "";
  private String errXMLMsg3 = "";

  private boolean OGABlock = false;
  private boolean internalBlock = false;
  private boolean popUpPrint = false;
  private boolean vbPrint = false;
  private boolean displayPrintButton = false;
  private boolean FOTBKGFlag = true;

  private boolean MCBypass;
  private int portNo = 0;

  private String checkPreEx;
  private String checkPreIm;

  private String expHasOOGSSRC1;
  private String expHasOverClosingSSRC1;
  private String expHasReplanSSRC1;
  private String expSSRBlockStatusC1;
  private String expHasOOGSSRC2;
  private String expHasOverClosingSSRC2;
  private String expHasReplanSSRC2;
  private String expSSRBlockStatusC2;

  private String expWeightBridge;
  private String expAgentC1;
  private String expAgentC2;
  private String vesselCodeC1;
  private String vesselCodeC2;
  private String vesselNameC1;
  private String vesselNameC2;
  private String vesselVoyageC1;
  private String vesselVoyageC2;
  private String vesselVisitIdC1;
  private String vesselVisitIdC2;
  private String vesselStatusC1;
  private String vesselStatusC2;
  private String vesselScnC1;
  private String vesselScnC2;
  private String vesselDateEtaC1;
  private String vesselDateEtaC2;
  private String vesselDateAtaC1;
  private String vesselDateAtaC2;
  private String expLine;
  private String expGCSBlockStatusC1;
  private String expGCSBlockStatusC2;

  /**
   * Vessel ETA Display
   */
  private String vesselDateEtaC1Display;
  private String vesselDateEtaC2Display;

  private String checkBoxValue = "";

  // Gate Pass Container1
  private String gp1ContainerNoC1;
  private String gp1AgentCodeC1;
  private String gp1InOrOutC1;
  private String gp1FullOrEmptyC1;
  private String gp1ISOC1;
  private String gp1LineC1;
  private String gp1OrderFOTC1;
  private String gp1CurPosC1;
  private String gp1SizeC1;
  private String gp1HeightC1;
  private String gp1TypeC1;
  private String gp1TotalWeightBridgeC1;
  private String gp1NetWeightC1;
  private String gp1Seal1OriginC1;
  private String gp1Seal1TypeC1;
  private String gp1Seal1C1;
  private String gp1Seal2OriginC1;
  private String gp1Seal2TypeC1;
  private String gp1Seal2C1;
  private String gp1PositionOnTruckC1;
  private String gp1YardPositonC1;
  private String gp1BayCodeC1;
  private String gpCallCard;

  // Gate Pass Container2
  private String gp2ContainerNoC2;
  private String gp2AgentCodeC2;
  private String gp2OrderFOTC2;
  private String gp2InOrOutC2;
  private String gp2FullOrEmptyC2;
  private String gp2LineC2;
  private String gp2ISOC2;
  private String gp2SizeC2;
  private String gp2HeightC2;
  private String gp2TypeC2;
  private String gp2CurPosC2;
  private String gp2TotalWeightBridgeC2;
  private String gp2NetWeightC2;
  private String gp2Seal1OriginC2;
  private String gp2Seal1TypeC2;
  private String gp2Seal1C2;
  private String gp2Seal2OriginC2;
  private String gp2Seal2TypeC2;
  private String gp2Seal2C2;
  private String gp2YardPositonC2;
  private String gp2BayCodeC2;
  private String gp2PositionOnTruckC2;

  private String hpatSeqId;

  /*
   * Weight Bridge Validation
   */
  private String impExpScreen;
  private String tarWeightC1;
  private String maxWeightC1;
  private String tarWeightC2;
  private String maxWeightC2;
  private String weighBridgeRemarks;
  private String emptyWeightC1;
  private String emptyWeightC2;

  /**
   * LPK EDI
   */
  private String kpaClassC1;
  private String kpaApprovalC1;
  private String goodsHdlCodeC1;
  private String goodsHdlDescC1;
  private String dgDescC1;
  private String dontValidateDgC1;
  private boolean dgWithinWindowEntryC1;
  private String dgBypassRemarkC1;

  private String lpkEdiEnabled;

  private String kpaClassC2;
  private String kpaApprovalC2;
  private String goodsHdlCodeC2;
  private String goodsHdlDescC2;
  private String dgDescC2;
  private String dontValidateDgC2;
  private boolean dgWithinWindowEntryC2;
  private String dgBypassRemarkC2;

  /**
   * WPTSCSSSUP-76
   */
  private boolean allowBypassHpat;

  private boolean allowBypassDgVal;
  private boolean allowBypassDgValRemoteC1;
  private boolean allowBypassDgValRemoteC2;

  /**
   * Weight From COSMOS
   */
  private String netWeightCosmosC1;
  private String tareWeightCosmosC1;
  private String grossWeightCosmosC1;

  private String netWeightCosmosC2;
  private String tareWeightCosmosC2;
  private String grossWeightCosmosC2;
  private String menuId;
  private String cugIdSeq;
  private String backToBack;

  private String gateInTime;
  private String gateInTimeOK;
  private double weightDifferentC1;
  private double weightDifferentPercentageC1;
  private double weightDifferentC2;
  private double weightDifferentPercentageC2;
  private String wrongDoorC1;
  private String wrongDoorC2;
  private String dontValidateSealC1;
  private String dontValidateSealC2;
  private String hpabIsoCodeC1;
  private String hpabIsoCodeC2;
  private String cosmosIsoCodeC1;
  private String cosmosIsoCodeC2;

  // System User
  private String staffNo;
  private String userFullName;

  // Refer
  private String referPHeadNo;

  private String cont1Refer;
  private String cont1ReferContNo;
  private String cont1ReferPosition;
  private String cont1ReferRemarks;
  private String cont1ReferReason;
  private String cont1ReferIsoCode;
  private String cont1ReferSeal1;
  private String cont1ReferSeal2;

  private String cont2Refer;
  private String cont2ReferContNo;
  private String cont2ReferPosition;
  private String cont2ReferRemarks;
  private String cont2ReferReason;
  private String cont2ReferIsoCode;
  private String cont2ReferSeal1;
  private String cont2ReferSeal2;
  private String cont1SelectedReferReason = "";
  private String cont2SelectedReferReason = "";

  private String referId;
  private String doubleBookingSelection;

  /**
   * WPTSCSSSUP-165 Export Double Booking
   */
  private String doubleBookingC1;
  private String doubleBookingC2;
  private String acceptOrReject = "A";
  private String rejectRemarks = "";
  private String con01handlingSubType = "";
  private String con02handlingSubType = "";
  private int fuelWeight;
  private int tireWeight;
  private int truckWeight;
  private int trailerWeight;
  private int mgwWeightC1;
  private int mgwWeightC2;
  private int variance;
  private String calculatedVarianceC1;
  private String calculatedVarianceC2;
  private int ternimalVGMC1;
  private int shipperVGMC1;
  private int shipperVGMC2;
  private String trailerHeadNo;
  private String hpatBookigDate;
  private boolean solasRejection = false;
  private boolean shipperVGM = false;
  private boolean solasCheck = false;
  private boolean c1WithInTolerance = false;
  private boolean c2WithInTolerance = false;
  private String solasDetailC1;
  private String solasDetailC2;
  private String expSeq01;
  private String expSeq02;
  private String solasInstruction;// = SCSSConstant.VGM_INSTRUCTION_TERMINAL;
  private String expBookingNoC1;
  private String expBookingNoC2;
  private String faLedgerCodeC1;
  private String faLedgerCodeC2;

  private int minPMWeight;
  private int maxPMWeight;
  private int minTrailerWeight;
  private int maxTrailerWeight;
  private int c1MaxMGW;
  private int c1MinMGW;
  private int c2MaxMGW;
  private int c2MinMGW;

  private String pmVerified;
  private String trailerVerified;

  private String solasRefNumberC1;
  private String solasRefNumberC2;

  private String printEIRNo;
  private boolean refersolas = false;

  public String getBookingNoC1() {
    return bookingNoC1;
  }

  public void setBookingNoC1(String bookingNoC1) {
    this.bookingNoC1 = bookingNoC1;
  }

  public String getContainerNoC1() {
    return containerNoC1;
  }

  public void setContainerNoC1(String containerNoC1) {
    this.containerNoC1 = containerNoC1;
  }

  public String getInOrOutC1() {
    return inOrOutC1;
  }

  public void setInOrOutC1(String inOrOutC1) {
    this.inOrOutC1 = inOrOutC1;
  }

  public String getLineC1() {
    return lineC1;
  }

  public void setLineC1(String lineC1) {
    this.lineC1 = lineC1;
  }

  public String getFullOrEmptyC1() {
    return fullOrEmptyC1;
  }

  public void setFullOrEmptyC1(String fullOrEmptyC1) {
    this.fullOrEmptyC1 = fullOrEmptyC1;
  }

  public String getGoodsC1() {
    return goodsC1;
  }

  public void setGoodsC1(String goodsC1) {
    this.goodsC1 = goodsC1;
  }

  public String getOUTC1() {
    return OUTC1;
  }

  public void setOUTC1(String oUTC1) {
    OUTC1 = oUTC1;
  }

  public String getCarC1() {
    return carC1;
  }

  public void setCarC1(String carC1) {
    this.carC1 = carC1;
  }

  public String getSPODC1() {
    return SPODC1;
  }

  public void setSPODC1(String sPODC1) {
    SPODC1 = sPODC1;
  }

  public String getISOC1() {
    return ISOC1;
  }

  public void setISOC1(String iSOC1) {
    ISOC1 = iSOC1;
  }

  public String getSizeC1() {
    return sizeC1;
  }

  public void setSizeC1(String sizeC1) {
    this.sizeC1 = sizeC1;
  }

  public String getHeightC1() {
    return heightC1;
  }

  public void setHeightC1(String heightC1) {
    this.heightC1 = heightC1;
  }

  public String getTypeC1() {
    return typeC1;
  }

  public void setTypeC1(String typeC1) {
    this.typeC1 = typeC1;
  }

  public String getSeal1OriginC1() {
    return seal1OriginC1;
  }

  public void setSeal1OriginC1(String seal1OriginC1) {
    this.seal1OriginC1 = seal1OriginC1;
  }

  public String getSeal1TypeC1() {
    return seal1TypeC1;
  }

  public void setSeal1TypeC1(String seal1TypeC1) {
    this.seal1TypeC1 = seal1TypeC1;
  }

  public String getSeal1C1() {
    return seal1C1;
  }

  public void setSeal1C1(String seal1c1) {
    seal1C1 = seal1c1;
  }

  public String getSeal2C1() {
    return seal2C1;
  }

  public void setSeal2C1(String seal2c1) {
    seal2C1 = seal2c1;
  }

  public String getSeal2OriginC1() {
    return seal2OriginC1;
  }

  public void setSeal2OriginC1(String seal2OriginC1) {
    this.seal2OriginC1 = seal2OriginC1;
  }

  public String getSeal2TypeC1() {
    return seal2TypeC1;
  }

  public void setSeal2TypeC1(String seal2TypeC1) {
    this.seal2TypeC1 = seal2TypeC1;
  }

  public String getTotalWeightBridgeC1() {
    return totalWeightBridgeC1;
  }

  public void setTotalWeightBridgeC1(String totalWeightBridgeC1) {
    this.totalWeightBridgeC1 = totalWeightBridgeC1;
  }

  public String getPMBTMC1() {
    return PMBTMC1;
  }

  public void setPMBTMC1(String pMBTMC1) {
    PMBTMC1 = pMBTMC1;
  }

  public String getTRBTMC1() {
    return TRBTMC1;
  }

  public void setTRBTMC1(String tRBTMC1) {
    TRBTMC1 = tRBTMC1;
  }

  public String getNetWeightC1() {
    return netWeightC1;
  }

  public void setNetWeightC1(String netWeightC1) {
    this.netWeightC1 = netWeightC1;
  }

  public String getOperationReeferC1() {
    return operationReeferC1;
  }

  public void setOperationReeferC1(String operationReeferC1) {
    this.operationReeferC1 = operationReeferC1;
  }

  public String getTempSignC1() {
    return tempSignC1;
  }

  public void setTempSignC1(String tempSignC1) {
    this.tempSignC1 = tempSignC1;
  }

  public String getTempUnitC1() {
    return tempUnitC1;
  }

  public void setTempUnitC1(String tempUnitC1) {
    this.tempUnitC1 = tempUnitC1;
  }

  public String getTempC1() {
    return tempC1;
  }

  public void setTempC1(String tempC1) {
    this.tempC1 = tempC1;
  }

  public String getIMDGC1() {
    return IMDGC1;
  }

  public void setIMDGC1(String iMDGC1) {
    IMDGC1 = iMDGC1;
  }

  public String getUNC1() {
    return UNC1;
  }

  public void setUNC1(String uNC1) {
    UNC1 = uNC1;
  }

  public String getIMDGlabelsC1() {
    return IMDGlabelsC1;
  }

  public void setIMDGlabelsC1(String iMDGlabelsC1) {
    IMDGlabelsC1 = iMDGlabelsC1;
  }

  public String getOOGC1flag() {
    return OOGC1flag;
  }

  public void setOOGC1flag(String oOGC1flag) {
    OOGC1flag = oOGC1flag;
  }

  public String getOOGOHC1() {
    return OOGOHC1;
  }

  public void setOOGOHC1(String oOGOHC1) {
    OOGOHC1 = oOGOHC1;
  }

  public String getOOGOLC1() {
    return OOGOLC1;
  }

  public void setOOGOLC1(String oOGOLC1) {
    OOGOLC1 = oOGOLC1;
  }

  public String getOOGOFC1() {
    return OOGOFC1;
  }

  public void setOOGOFC1(String oOGOFC1) {
    OOGOFC1 = oOGOFC1;
  }

  public String getOOGOAC1() {
    return OOGOAC1;
  }

  public void setOOGOAC1(String oOGOAC1) {
    OOGOAC1 = oOGOAC1;
  }

  public String getOOGORC1() {
    return OOGORC1;
  }

  public void setOOGORC1(String oOGORC1) {
    OOGORC1 = oOGORC1;
  }

  public String getDamage1C1() {
    return damage1C1;
  }

  public void setDamage1C1(String damage1c1) {
    damage1C1 = damage1c1;
  }

  public String getDamage2C1() {
    return damage2C1;
  }

  public void setDamage2C1(String damage2c1) {
    damage2C1 = damage2c1;
  }

  public String getDamage3C1() {
    return damage3C1;
  }

  public void setDamage3C1(String damage3c1) {
    damage3C1 = damage3c1;
  }

  public String getDamage4C1() {
    return damage4C1;
  }

  public void setDamage4C1(String damage4c1) {
    damage4C1 = damage4c1;
  }

  public String getDamage5C1() {
    return damage5C1;
  }

  public void setDamage5C1(String damage5c1) {
    damage5C1 = damage5c1;
  }

  public String getDamage6C1() {
    return damage6C1;
  }

  public void setDamage6C1(String damage6c1) {
    damage6C1 = damage6c1;
  }

  public String getDamage7C1() {
    return damage7C1;
  }

  public void setDamage7C1(String damage7c1) {
    damage7C1 = damage7c1;
  }

  public String getDamage8C1() {
    return damage8C1;
  }

  public void setDamage8C1(String damage8c1) {
    damage8C1 = damage8c1;
  }

  public String getDamage9C1() {
    return damage9C1;
  }

  public void setDamage9C1(String damage9c1) {
    damage9C1 = damage9c1;
  }

  public String getDamage1AC1() {
    return damage1AC1;
  }

  public void setDamage1AC1(String damage1ac1) {
    damage1AC1 = damage1ac1;
  }

  public String getDamage2AC1() {
    return damage2AC1;
  }

  public void setDamage2AC1(String damage2ac1) {
    damage2AC1 = damage2ac1;
  }

  public String getDamage3AC1() {
    return damage3AC1;
  }

  public void setDamage3AC1(String damage3ac1) {
    damage3AC1 = damage3ac1;
  }

  public String getDamage4AC1() {
    return damage4AC1;
  }

  public void setDamage4AC1(String damage4ac1) {
    damage4AC1 = damage4ac1;
  }

  public String getDamage5AC1() {
    return damage5AC1;
  }

  public void setDamage5AC1(String damage5ac1) {
    damage5AC1 = damage5ac1;
  }

  public String getDamage6AC1() {
    return damage6AC1;
  }

  public void setDamage6AC1(String damage6ac1) {
    damage6AC1 = damage6ac1;
  }

  public String getDamage7AC1() {
    return damage7AC1;
  }

  public void setDamage7AC1(String damage7ac1) {
    damage7AC1 = damage7ac1;
  }

  public String getDamage8AC1() {
    return damage8AC1;
  }

  public void setDamage8AC1(String damage8ac1) {
    damage8AC1 = damage8ac1;
  }

  public String getDamage9AC1() {
    return damage9AC1;
  }

  public void setDamage9AC1(String damage9ac1) {
    damage9AC1 = damage9ac1;
  }

  public String getPositionOnTruckC1() {
    return positionOnTruckC1;
  }

  public void setPositionOnTruckC1(String positionOnTruckC1) {
    this.positionOnTruckC1 = positionOnTruckC1;
  }

  public String getOrderFOTC1() {
    return orderFOTC1;
  }

  public void setOrderFOTC1(String orderFOTC1) {
    this.orderFOTC1 = orderFOTC1;
  }

  public String getCurPosC1() {
    return curPosC1;
  }

  public void setCurPosC1(String curPosC1) {
    this.curPosC1 = curPosC1;
  }

  public String getBookingNoC2() {
    return bookingNoC2;
  }

  public void setBookingNoC2(String bookingNoC2) {
    this.bookingNoC2 = bookingNoC2;
  }

  public String getContainerNoC2() {
    return containerNoC2;
  }

  public void setContainerNoC2(String containerNoC2) {
    this.containerNoC2 = containerNoC2;
  }

  public String getInOrOutC2() {
    return inOrOutC2;
  }

  public void setInOrOutC2(String inOrOutC2) {
    this.inOrOutC2 = inOrOutC2;
  }

  public String getLineC2() {
    return lineC2;
  }

  public void setLineC2(String lineC2) {
    this.lineC2 = lineC2;
  }

  public String getFullOrEmptyC2() {
    return fullOrEmptyC2;
  }

  public void setFullOrEmptyC2(String fullOrEmptyC2) {
    this.fullOrEmptyC2 = fullOrEmptyC2;
  }

  public String getGoodsC2() {
    return goodsC2;
  }

  public void setGoodsC2(String goodsC2) {
    this.goodsC2 = goodsC2;
  }

  public String getOUTC2() {
    return OUTC2;
  }

  public void setOUTC2(String oUTC2) {
    OUTC2 = oUTC2;
  }

  public String getCarC2() {
    return carC2;
  }

  public void setCarC2(String carC2) {
    this.carC2 = carC2;
  }

  public String getSPODC2() {
    return SPODC2;
  }

  public void setSPODC2(String sPODC2) {
    SPODC2 = sPODC2;
  }

  public String getISOC2() {
    return ISOC2;
  }

  public void setISOC2(String iSOC2) {
    ISOC2 = iSOC2;
  }

  public String getSizeC2() {
    return sizeC2;
  }

  public void setSizeC2(String sizeC2) {
    this.sizeC2 = sizeC2;
  }

  public String getHeightC2() {
    return heightC2;
  }

  public void setHeightC2(String heightC2) {
    this.heightC2 = heightC2;
  }

  public String getTypeC2() {
    return typeC2;
  }

  public void setTypeC2(String typeC2) {
    this.typeC2 = typeC2;
  }

  public String getSeal1C2() {
    return seal1C2;
  }

  public void setSeal1C2(String seal1c2) {
    seal1C2 = seal1c2;
  }

  public String getSeal1OriginC2() {
    return seal1OriginC2;
  }

  public void setSeal1OriginC2(String seal1OriginC2) {
    this.seal1OriginC2 = seal1OriginC2;
  }

  public String getSeal1TypeC2() {
    return seal1TypeC2;
  }

  public void setSeal1TypeC2(String seal1TypeC2) {
    this.seal1TypeC2 = seal1TypeC2;
  }

  public String getSeal2C2() {
    return seal2C2;
  }

  public void setSeal2C2(String seal2c2) {
    seal2C2 = seal2c2;
  }

  public String getSeal2OriginC2() {
    return seal2OriginC2;
  }

  public void setSeal2OriginC2(String seal2OriginC2) {
    this.seal2OriginC2 = seal2OriginC2;
  }

  public String getSeal2TypeC2() {
    return seal2TypeC2;
  }

  public void setSeal2TypeC2(String seal2TypeC2) {
    this.seal2TypeC2 = seal2TypeC2;
  }

  public String getTotalWeightBridgeC2() {
    return totalWeightBridgeC2;
  }

  public void setTotalWeightBridgeC2(String totalWeightBridgeC2) {
    this.totalWeightBridgeC2 = totalWeightBridgeC2;
  }

  public String getPMBTMC2() {
    return PMBTMC2;
  }

  public void setPMBTMC2(String pMBTMC2) {
    PMBTMC2 = pMBTMC2;
  }

  public String getTRBTMC2() {
    return TRBTMC2;
  }

  public void setTRBTMC2(String tRBTMC2) {
    TRBTMC2 = tRBTMC2;
  }

  public String getNetWeightC2() {
    return netWeightC2;
  }

  public void setNetWeightC2(String netWeightC2) {
    this.netWeightC2 = netWeightC2;
  }

  public String getOperationReeferC2() {
    return operationReeferC2;
  }

  public void setOperationReeferC2(String operationReeferC2) {
    this.operationReeferC2 = operationReeferC2;
  }

  public String getTempSignC2() {
    return tempSignC2;
  }

  public void setTempSignC2(String tempSignC2) {
    this.tempSignC2 = tempSignC2;
  }

  public String getTempC2() {
    return tempC2;
  }

  public void setTempC2(String tempC2) {
    this.tempC2 = tempC2;
  }

  public String getTempUnitC2() {
    return tempUnitC2;
  }

  public void setTempUnitC2(String tempUnitC2) {
    this.tempUnitC2 = tempUnitC2;
  }

  public String getIMDGC2() {
    return IMDGC2;
  }

  public void setIMDGC2(String iMDGC2) {
    IMDGC2 = iMDGC2;
  }

  public String getUNC2() {
    return UNC2;
  }

  public void setUNC2(String uNC2) {
    UNC2 = uNC2;
  }

  public String getIMDGlabelsC2() {
    return IMDGlabelsC2;
  }

  public void setIMDGlabelsC2(String iMDGlabelsC2) {
    IMDGlabelsC2 = iMDGlabelsC2;
  }

  public String getOOGC2flag() {
    return OOGC2flag;
  }

  public void setOOGC2flag(String oOGC2flag) {
    OOGC2flag = oOGC2flag;
  }

  public String getOOGOHC2() {
    return OOGOHC2;
  }

  public void setOOGOHC2(String oOGOHC2) {
    OOGOHC2 = oOGOHC2;
  }

  public String getOOGOLC2() {
    return OOGOLC2;
  }

  public void setOOGOLC2(String oOGOLC2) {
    OOGOLC2 = oOGOLC2;
  }

  public String getOOGOFC2() {
    return OOGOFC2;
  }

  public void setOOGOFC2(String oOGOFC2) {
    OOGOFC2 = oOGOFC2;
  }

  public String getOOGOAC2() {
    return OOGOAC2;
  }

  public void setOOGOAC2(String oOGOAC2) {
    OOGOAC2 = oOGOAC2;
  }

  public String getOOGORC2() {
    return OOGORC2;
  }

  public void setOOGORC2(String oOGORC2) {
    OOGORC2 = oOGORC2;
  }

  public String getDamage1C2() {
    return damage1C2;
  }

  public void setDamage1C2(String damage1c2) {
    damage1C2 = damage1c2;
  }

  public String getDamage2C2() {
    return damage2C2;
  }

  public void setDamage2C2(String damage2c2) {
    damage2C2 = damage2c2;
  }

  public String getDamage3C2() {
    return damage3C2;
  }

  public void setDamage3C2(String damage3c2) {
    damage3C2 = damage3c2;
  }

  public String getDamage4C2() {
    return damage4C2;
  }

  public void setDamage4C2(String damage4c2) {
    damage4C2 = damage4c2;
  }

  public String getDamage5C2() {
    return damage5C2;
  }

  public void setDamage5C2(String damage5c2) {
    damage5C2 = damage5c2;
  }

  public String getDamage6C2() {
    return damage6C2;
  }

  public void setDamage6C2(String damage6c2) {
    damage6C2 = damage6c2;
  }

  public String getDamage7C2() {
    return damage7C2;
  }

  public void setDamage7C2(String damage7c2) {
    damage7C2 = damage7c2;
  }

  public String getDamage8C2() {
    return damage8C2;
  }

  public void setDamage8C2(String damage8c2) {
    damage8C2 = damage8c2;
  }

  public String getDamage9C2() {
    return damage9C2;
  }

  public void setDamage9C2(String damage9c2) {
    damage9C2 = damage9c2;
  }

  public String getDamage1BC2() {
    return damage1BC2;
  }

  public void setDamage1BC2(String damage1bc2) {
    damage1BC2 = damage1bc2;
  }

  public String getDamage2BC2() {
    return damage2BC2;
  }

  public void setDamage2BC2(String damage2bc2) {
    damage2BC2 = damage2bc2;
  }

  public String getDamage3BC2() {
    return damage3BC2;
  }

  public void setDamage3BC2(String damage3bc2) {
    damage3BC2 = damage3bc2;
  }

  public String getDamage4BC2() {
    return damage4BC2;
  }

  public void setDamage4BC2(String damage4bc2) {
    damage4BC2 = damage4bc2;
  }

  public String getDamage5BC2() {
    return damage5BC2;
  }

  public void setDamage5BC2(String damage5bc2) {
    damage5BC2 = damage5bc2;
  }

  public String getDamage6BC2() {
    return damage6BC2;
  }

  public void setDamage6BC2(String damage6bc2) {
    damage6BC2 = damage6bc2;
  }

  public String getDamage7BC2() {
    return damage7BC2;
  }

  public void setDamage7BC2(String damage7bc2) {
    damage7BC2 = damage7bc2;
  }

  public String getDamage8BC2() {
    return damage8BC2;
  }

  public void setDamage8BC2(String damage8bc2) {
    damage8BC2 = damage8bc2;
  }

  public String getDamage9BC2() {
    return damage9BC2;
  }

  public void setDamage9BC2(String damage9bc2) {
    damage9BC2 = damage9bc2;
  }

  public String getPositionOnTruckC2() {
    return positionOnTruckC2;
  }

  public void setPositionOnTruckC2(String positionOnTruckC2) {
    this.positionOnTruckC2 = positionOnTruckC2;
  }

  public String getOrderFOTC2() {
    return orderFOTC2;
  }

  public void setOrderFOTC2(String orderFOTC2) {
    this.orderFOTC2 = orderFOTC2;
  }

  public String getCurPosC2() {
    return curPosC2;
  }

  public void setCurPosC2(String curPosC2) {
    this.curPosC2 = curPosC2;
  }

  public String getLaneNo() {
    return laneNo;
  }

  public void setLaneNo(String laneNo) {
    this.laneNo = laneNo;
  }

  public String getTruckHeadNo() {
    return truckHeadNo;
  }

  public void setTruckHeadNo(String truckHeadNo) {
    this.truckHeadNo = truckHeadNo;
  }

  public String getTruckPlateNo() {
    return truckPlateNo;
  }

  public void setTruckPlateNo(String truckPlateNo) {
    this.truckPlateNo = truckPlateNo;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getEIRNo() {
    return EIRNo;
  }

  public void setEIRNo(String eIRNo) {
    EIRNo = eIRNo;
  }

  public String getNewEIRNo() {
    return newEIRNo;
  }

  public void setNewEIRNo(String newEIRNo) {
    this.newEIRNo = newEIRNo;
  }

  public String getRequestXML() {
    return requestXML;
  }

  public void setRequestXML(String requestXML) {
    this.requestXML = requestXML;
  }

  public String getReplyXML() {
    return replyXML;
  }

  public void setReplyXML(String replyXML) {
    this.replyXML = replyXML;
  }

  public String getSCUIdSeq() {
    return SCUIdSeq;
  }

  public void setSCUIdSeq(String sCUIdSeq) {
    SCUIdSeq = sCUIdSeq;
  }

  public String getSCUName() {
    return SCUName;
  }

  public void setSCUName(String sCUName) {
    SCUName = sCUName;
  }

  public String getSCNo() {
    return SCNo;
  }

  public void setSCNo(String sCNo) {
    SCNo = sCNo;
  }

  public String getComIdSeq() {
    return comIdSeq;
  }

  public void setComIdSeq(String comIdSeq) {
    this.comIdSeq = comIdSeq;
  }

  public String getCompName() {
    return compName;
  }

  public void setCompName(String compName) {
    this.compName = compName;
  }

  public String getCompAcctNo() {
    return compAcctNo;
  }

  public void setCompAcctNo(String compAcctNo) {
    this.compAcctNo = compAcctNo;
  }

  public String getCompStatusDesc() {
    return compStatusDesc;
  }

  public void setCompStatusDesc(String compStatusDesc) {
    this.compStatusDesc = compStatusDesc;
  }

  public String getCompStatus() {
    return compStatus;
  }

  public void setCompStatus(String compStatus) {
    this.compStatus = compStatus;
  }

  public String getCompCode() {
    return compCode;
  }

  public void setCompCode(String compCode) {
    this.compCode = compCode;
  }

  public String getCompType() {
    return compType;
  }

  public void setCompType(String compType) {
    this.compType = compType;
  }

  public String getSeq1() {
    return seq1;
  }

  public void setSeq1(String seq1) {
    this.seq1 = seq1;
  }

  public String getSeq2() {
    return seq2;
  }

  public void setSeq2(String seq2) {
    this.seq2 = seq2;
  }

  public String getCardStatus() {
    return cardStatus;
  }

  public void setCardStatus(String cardStatus) {
    this.cardStatus = cardStatus;
  }

  public String getCardStatusDesc() {
    return cardStatusDesc;
  }

  public void setCardStatusDesc(String cardStatusDesc) {
    this.cardStatusDesc = cardStatusDesc;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getNewICNo() {
    return newICNo;
  }

  public void setNewICNo(String newICNo) {
    this.newICNo = newICNo;
  }

  public String getOldICNo() {
    return oldICNo;
  }

  public void setOldICNo(String oldICNo) {
    this.oldICNo = oldICNo;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

  public String getGateImpOrExp() {
    return gateImpOrExp;
  }

  public void setGateImpOrExp(String gateImpOrExp) {
    this.gateImpOrExp = gateImpOrExp;
  }

  public String getUserSessionId() {
    return userSessionId;
  }

  public void setUserSessionId(String userSessionId) {
    this.userSessionId = userSessionId;
  }

  public String getAgentCode() {
    return agentCode;
  }

  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }

  public String getMCName() {
    return MCName;
  }

  public void setMCName(String mCName) {
    MCName = mCName;
  }

  public String getMCNewICNo() {
    return MCNewICNo;
  }

  public void setMCNewICNo(String mCNewICNo) {
    MCNewICNo = mCNewICNo;
  }

  public String getMCOldICNo() {
    return MCOldICNo;
  }

  public void setMCOldICNo(String mCOldICNo) {
    MCOldICNo = mCOldICNo;
  }

  public String getMCNationality() {
    return MCNationality;
  }

  public void setMCNationality(String mCNationality) {
    MCNationality = mCNationality;
  }

  public String getMCPassportNo() {
    return MCPassportNo;
  }

  public void setMCPassportNo(String mCPassportNo) {
    MCPassportNo = mCPassportNo;
  }

  public String getMCNo() {
    return MCNo;
  }

  public void setMCNo(String mCNo) {
    MCNo = mCNo;
  }

  public String getMCStatus() {
    return MCStatus;
  }

  public void setMCStatus(String mCStatus) {
    MCStatus = mCStatus;
  }

  public String getMCStatusDesc() {
    return MCStatusDesc;
  }

  public void setMCStatusDesc(String mCStatusDesc) {
    MCStatusDesc = mCStatusDesc;
  }

  public String getMCRole() {
    return MCRole;
  }

  public void setMCRole(String mCRole) {
    MCRole = mCRole;
  }

  public String getGatePassNo1() {
    return gatePassNo1;
  }

  public void setGatePassNo1(String gatePassNo1) {
    this.gatePassNo1 = gatePassNo1;
  }

  public String getGatePassNo2() {
    return gatePassNo2;
  }

  public void setGatePassNo2(String gatePassNo2) {
    this.gatePassNo2 = gatePassNo2;
  }

  public String getAssociateGatePassOrContainer() {
    return associateGatePassOrContainer;
  }

  public void setAssociateGatePassOrContainer(String associateGatePassOrContainer) {
    this.associateGatePassOrContainer = associateGatePassOrContainer;
  }

  public String getYardPositionC1() {
    return yardPositionC1;
  }

  public void setYardPositionC1(String yardPositionC1) {
    this.yardPositionC1 = yardPositionC1;
  }

  public String getYardPositionC2() {
    return yardPositionC2;
  }

  public void setYardPositionC2(String yardPositionC2) {
    this.yardPositionC2 = yardPositionC2;
  }

  public String getBayCodeC1() {
    return bayCodeC1;
  }

  public void setBayCodeC1(String bayCodeC1) {
    this.bayCodeC1 = bayCodeC1;
  }

  public String getBayCodeC2() {
    return bayCodeC2;
  }

  public void setBayCodeC2(String bayCodeC2) {
    this.bayCodeC2 = bayCodeC2;
  }

  public String getCallCard() {
    return callCard;
  }

  public void setCallCard(String callCard) {
    this.callCard = callCard;
  }

  public String getImpExpFlag() {
    return impExpFlag;
  }

  public void setImpExpFlag(String impExpFlag) {
    this.impExpFlag = impExpFlag;
  }

  public String getGateInNo() {
    return gateInNo;
  }

  public void setGateInNo(String gateInNo) {
    this.gateInNo = gateInNo;
  }

  public String getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(String timeIn) {
    this.timeIn = timeIn;
  }

  public String getCompNamePrint() {
    return compNamePrint;
  }

  public void setCompNamePrint(String compNamePrint) {
    this.compNamePrint = compNamePrint;
  }

  public String getCardIdSeq() {
    return cardIdSeq;
  }

  public void setCardIdSeq(String cardIdSeq) {
    this.cardIdSeq = cardIdSeq;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getErrXMLMsg1() {
    return errXMLMsg1;
  }

  public void setErrXMLMsg1(String errXMLMsg1) {
    this.errXMLMsg1 = errXMLMsg1;
  }

  public String getErrXMLMsg2() {
    return errXMLMsg2;
  }

  public void setErrXMLMsg2(String errXMLMsg2) {
    this.errXMLMsg2 = errXMLMsg2;
  }

  public String getErrXMLMsg3() {
    return errXMLMsg3;
  }

  public void setErrXMLMsg3(String errXMLMsg3) {
    this.errXMLMsg3 = errXMLMsg3;
  }

  public boolean isOGABlock() {
    return OGABlock;
  }

  public void setOGABlock(boolean oGABlock) {
    OGABlock = oGABlock;
  }

  public boolean isInternalBlock() {
    return internalBlock;
  }

  public void setInternalBlock(boolean internalBlock) {
    this.internalBlock = internalBlock;
  }

  public boolean isPopUpPrint() {
    return popUpPrint;
  }

  public void setPopUpPrint(boolean popUpPrint) {
    this.popUpPrint = popUpPrint;
  }

  public boolean isVbPrint() {
    return vbPrint;
  }

  public void setVbPrint(boolean vbPrint) {
    this.vbPrint = vbPrint;
  }

  public boolean isDisplayPrintButton() {
    return displayPrintButton;
  }

  public void setDisplayPrintButton(boolean displayPrintButton) {
    this.displayPrintButton = displayPrintButton;
  }

  public boolean isFOTBKGFlag() {
    return FOTBKGFlag;
  }

  public void setFOTBKGFlag(boolean fOTBKGFlag) {
    FOTBKGFlag = fOTBKGFlag;
  }

  public boolean isMCBypass() {
    return MCBypass;
  }

  public void setMCBypass(boolean mCBypass) {
    MCBypass = mCBypass;
  }

  public int getPortNo() {
    return portNo;
  }

  public void setPortNo(int portNo) {
    this.portNo = portNo;
  }

  public String getCheckPreEx() {
    return checkPreEx;
  }

  public void setCheckPreEx(String checkPreEx) {
    this.checkPreEx = checkPreEx;
  }

  public String getCheckPreIm() {
    return checkPreIm;
  }

  public void setCheckPreIm(String checkPreIm) {
    this.checkPreIm = checkPreIm;
  }

  public String getExpHasOOGSSRC1() {
    return expHasOOGSSRC1;
  }

  public void setExpHasOOGSSRC1(String expHasOOGSSRC1) {
    this.expHasOOGSSRC1 = expHasOOGSSRC1;
  }

  public String getExpHasOverClosingSSRC1() {
    return expHasOverClosingSSRC1;
  }

  public void setExpHasOverClosingSSRC1(String expHasOverClosingSSRC1) {
    this.expHasOverClosingSSRC1 = expHasOverClosingSSRC1;
  }

  public String getExpHasReplanSSRC1() {
    return expHasReplanSSRC1;
  }

  public void setExpHasReplanSSRC1(String expHasReplanSSRC1) {
    this.expHasReplanSSRC1 = expHasReplanSSRC1;
  }

  public String getExpSSRBlockStatusC1() {
    return expSSRBlockStatusC1;
  }

  public void setExpSSRBlockStatusC1(String expSSRBlockStatusC1) {
    this.expSSRBlockStatusC1 = expSSRBlockStatusC1;
  }

  public String getExpHasOOGSSRC2() {
    return expHasOOGSSRC2;
  }

  public void setExpHasOOGSSRC2(String expHasOOGSSRC2) {
    this.expHasOOGSSRC2 = expHasOOGSSRC2;
  }

  public String getExpHasOverClosingSSRC2() {
    return expHasOverClosingSSRC2;
  }

  public void setExpHasOverClosingSSRC2(String expHasOverClosingSSRC2) {
    this.expHasOverClosingSSRC2 = expHasOverClosingSSRC2;
  }

  public String getExpHasReplanSSRC2() {
    return expHasReplanSSRC2;
  }

  public void setExpHasReplanSSRC2(String expHasReplanSSRC2) {
    this.expHasReplanSSRC2 = expHasReplanSSRC2;
  }

  public String getExpSSRBlockStatusC2() {
    return expSSRBlockStatusC2;
  }

  public void setExpSSRBlockStatusC2(String expSSRBlockStatusC2) {
    this.expSSRBlockStatusC2 = expSSRBlockStatusC2;
  }

  public String getExpWeightBridge() {
    return expWeightBridge;
  }

  public void setExpWeightBridge(String expWeightBridge) {
    this.expWeightBridge = expWeightBridge;
  }

  public String getExpAgentC1() {
    return expAgentC1;
  }

  public void setExpAgentC1(String expAgentC1) {
    this.expAgentC1 = expAgentC1;
  }

  public String getExpAgentC2() {
    return expAgentC2;
  }

  public void setExpAgentC2(String expAgentC2) {
    this.expAgentC2 = expAgentC2;
  }

  public String getVesselCodeC1() {
    return vesselCodeC1;
  }

  public void setVesselCodeC1(String vesselCodeC1) {
    this.vesselCodeC1 = vesselCodeC1;
  }

  public String getVesselCodeC2() {
    return vesselCodeC2;
  }

  public void setVesselCodeC2(String vesselCodeC2) {
    this.vesselCodeC2 = vesselCodeC2;
  }

  public String getVesselNameC1() {
    return vesselNameC1;
  }

  public void setVesselNameC1(String vesselNameC1) {
    this.vesselNameC1 = vesselNameC1;
  }

  public String getVesselNameC2() {
    return vesselNameC2;
  }

  public void setVesselNameC2(String vesselNameC2) {
    this.vesselNameC2 = vesselNameC2;
  }

  public String getVesselVoyageC1() {
    return vesselVoyageC1;
  }

  public void setVesselVoyageC1(String vesselVoyageC1) {
    this.vesselVoyageC1 = vesselVoyageC1;
  }

  public String getVesselVoyageC2() {
    return vesselVoyageC2;
  }

  public void setVesselVoyageC2(String vesselVoyageC2) {
    this.vesselVoyageC2 = vesselVoyageC2;
  }

  public String getVesselVisitIdC1() {
    return vesselVisitIdC1;
  }

  public void setVesselVisitIdC1(String vesselVisitIdC1) {
    this.vesselVisitIdC1 = vesselVisitIdC1;
  }

  public String getVesselVisitIdC2() {
    return vesselVisitIdC2;
  }

  public void setVesselVisitIdC2(String vesselVisitIdC2) {
    this.vesselVisitIdC2 = vesselVisitIdC2;
  }

  public String getVesselStatusC1() {
    return vesselStatusC1;
  }

  public void setVesselStatusC1(String vesselStatusC1) {
    this.vesselStatusC1 = vesselStatusC1;
  }

  public String getVesselStatusC2() {
    return vesselStatusC2;
  }

  public void setVesselStatusC2(String vesselStatusC2) {
    this.vesselStatusC2 = vesselStatusC2;
  }

  public String getVesselScnC1() {
    return vesselScnC1;
  }

  public void setVesselScnC1(String vesselScnC1) {
    this.vesselScnC1 = vesselScnC1;
  }

  public String getVesselScnC2() {
    return vesselScnC2;
  }

  public void setVesselScnC2(String vesselScnC2) {
    this.vesselScnC2 = vesselScnC2;
  }

  public String getVesselDateEtaC1() {
    return vesselDateEtaC1;
  }

  public void setVesselDateEtaC1(String vesselDateEtaC1) {
    this.vesselDateEtaC1 = vesselDateEtaC1;
  }

  public String getVesselDateEtaC2() {
    return vesselDateEtaC2;
  }

  public void setVesselDateEtaC2(String vesselDateEtaC2) {
    this.vesselDateEtaC2 = vesselDateEtaC2;
  }

  public String getVesselDateAtaC1() {
    return vesselDateAtaC1;
  }

  public void setVesselDateAtaC1(String vesselDateAtaC1) {
    this.vesselDateAtaC1 = vesselDateAtaC1;
  }

  public String getVesselDateAtaC2() {
    return vesselDateAtaC2;
  }

  public void setVesselDateAtaC2(String vesselDateAtaC2) {
    this.vesselDateAtaC2 = vesselDateAtaC2;
  }

  public String getExpLine() {
    return expLine;
  }

  public void setExpLine(String expLine) {
    this.expLine = expLine;
  }

  public String getExpGCSBlockStatusC1() {
    return expGCSBlockStatusC1;
  }

  public void setExpGCSBlockStatusC1(String expGCSBlockStatusC1) {
    this.expGCSBlockStatusC1 = expGCSBlockStatusC1;
  }

  public String getExpGCSBlockStatusC2() {
    return expGCSBlockStatusC2;
  }

  public void setExpGCSBlockStatusC2(String expGCSBlockStatusC2) {
    this.expGCSBlockStatusC2 = expGCSBlockStatusC2;
  }

  public String getVesselDateEtaC1Display() {
    return vesselDateEtaC1Display;
  }

  public void setVesselDateEtaC1Display(String vesselDateEtaC1Display) {
    this.vesselDateEtaC1Display = vesselDateEtaC1Display;
  }

  public String getVesselDateEtaC2Display() {
    return vesselDateEtaC2Display;
  }

  public void setVesselDateEtaC2Display(String vesselDateEtaC2Display) {
    this.vesselDateEtaC2Display = vesselDateEtaC2Display;
  }

  public String getCheckBoxValue() {
    return checkBoxValue;
  }

  public void setCheckBoxValue(String checkBoxValue) {
    this.checkBoxValue = checkBoxValue;
  }

  public String getGp1ContainerNoC1() {
    return gp1ContainerNoC1;
  }

  public void setGp1ContainerNoC1(String gp1ContainerNoC1) {
    this.gp1ContainerNoC1 = gp1ContainerNoC1;
  }

  public String getGp1AgentCodeC1() {
    return gp1AgentCodeC1;
  }

  public void setGp1AgentCodeC1(String gp1AgentCodeC1) {
    this.gp1AgentCodeC1 = gp1AgentCodeC1;
  }

  public String getGp1InOrOutC1() {
    return gp1InOrOutC1;
  }

  public void setGp1InOrOutC1(String gp1InOrOutC1) {
    this.gp1InOrOutC1 = gp1InOrOutC1;
  }

  public String getGp1FullOrEmptyC1() {
    return gp1FullOrEmptyC1;
  }

  public void setGp1FullOrEmptyC1(String gp1FullOrEmptyC1) {
    this.gp1FullOrEmptyC1 = gp1FullOrEmptyC1;
  }

  public String getGp1ISOC1() {
    return gp1ISOC1;
  }

  public void setGp1ISOC1(String gp1isoc1) {
    gp1ISOC1 = gp1isoc1;
  }

  public String getGp1LineC1() {
    return gp1LineC1;
  }

  public void setGp1LineC1(String gp1LineC1) {
    this.gp1LineC1 = gp1LineC1;
  }

  public String getGp1OrderFOTC1() {
    return gp1OrderFOTC1;
  }

  public void setGp1OrderFOTC1(String gp1OrderFOTC1) {
    this.gp1OrderFOTC1 = gp1OrderFOTC1;
  }

  public String getGp1CurPosC1() {
    return gp1CurPosC1;
  }

  public void setGp1CurPosC1(String gp1CurPosC1) {
    this.gp1CurPosC1 = gp1CurPosC1;
  }

  public String getGp1SizeC1() {
    return gp1SizeC1;
  }

  public void setGp1SizeC1(String gp1SizeC1) {
    this.gp1SizeC1 = gp1SizeC1;
  }

  public String getGp1HeightC1() {
    return gp1HeightC1;
  }

  public void setGp1HeightC1(String gp1HeightC1) {
    this.gp1HeightC1 = gp1HeightC1;
  }

  public String getGp1TypeC1() {
    return gp1TypeC1;
  }

  public void setGp1TypeC1(String gp1TypeC1) {
    this.gp1TypeC1 = gp1TypeC1;
  }

  public String getGp1TotalWeightBridgeC1() {
    return gp1TotalWeightBridgeC1;
  }

  public void setGp1TotalWeightBridgeC1(String gp1TotalWeightBridgeC1) {
    this.gp1TotalWeightBridgeC1 = gp1TotalWeightBridgeC1;
  }

  public String getGp1NetWeightC1() {
    return gp1NetWeightC1;
  }

  public void setGp1NetWeightC1(String gp1NetWeightC1) {
    this.gp1NetWeightC1 = gp1NetWeightC1;
  }

  public String getGp1Seal1OriginC1() {
    return gp1Seal1OriginC1;
  }

  public void setGp1Seal1OriginC1(String gp1Seal1OriginC1) {
    this.gp1Seal1OriginC1 = gp1Seal1OriginC1;
  }

  public String getGp1Seal1TypeC1() {
    return gp1Seal1TypeC1;
  }

  public void setGp1Seal1TypeC1(String gp1Seal1TypeC1) {
    this.gp1Seal1TypeC1 = gp1Seal1TypeC1;
  }

  public String getGp1Seal1C1() {
    return gp1Seal1C1;
  }

  public void setGp1Seal1C1(String gp1Seal1C1) {
    this.gp1Seal1C1 = gp1Seal1C1;
  }

  public String getGp1Seal2OriginC1() {
    return gp1Seal2OriginC1;
  }

  public void setGp1Seal2OriginC1(String gp1Seal2OriginC1) {
    this.gp1Seal2OriginC1 = gp1Seal2OriginC1;
  }

  public String getGp1Seal2TypeC1() {
    return gp1Seal2TypeC1;
  }

  public void setGp1Seal2TypeC1(String gp1Seal2TypeC1) {
    this.gp1Seal2TypeC1 = gp1Seal2TypeC1;
  }

  public String getGp1Seal2C1() {
    return gp1Seal2C1;
  }

  public void setGp1Seal2C1(String gp1Seal2C1) {
    this.gp1Seal2C1 = gp1Seal2C1;
  }

  public String getGp1PositionOnTruckC1() {
    return gp1PositionOnTruckC1;
  }

  public void setGp1PositionOnTruckC1(String gp1PositionOnTruckC1) {
    this.gp1PositionOnTruckC1 = gp1PositionOnTruckC1;
  }

  public String getGp1YardPositonC1() {
    return gp1YardPositonC1;
  }

  public void setGp1YardPositonC1(String gp1YardPositonC1) {
    this.gp1YardPositonC1 = gp1YardPositonC1;
  }

  public String getGp1BayCodeC1() {
    return gp1BayCodeC1;
  }

  public void setGp1BayCodeC1(String gp1BayCodeC1) {
    this.gp1BayCodeC1 = gp1BayCodeC1;
  }

  public String getGpCallCard() {
    return gpCallCard;
  }

  public void setGpCallCard(String gpCallCard) {
    this.gpCallCard = gpCallCard;
  }

  public String getGp2ContainerNoC2() {
    return gp2ContainerNoC2;
  }

  public void setGp2ContainerNoC2(String gp2ContainerNoC2) {
    this.gp2ContainerNoC2 = gp2ContainerNoC2;
  }

  public String getGp2AgentCodeC2() {
    return gp2AgentCodeC2;
  }

  public void setGp2AgentCodeC2(String gp2AgentCodeC2) {
    this.gp2AgentCodeC2 = gp2AgentCodeC2;
  }

  public String getGp2OrderFOTC2() {
    return gp2OrderFOTC2;
  }

  public void setGp2OrderFOTC2(String gp2OrderFOTC2) {
    this.gp2OrderFOTC2 = gp2OrderFOTC2;
  }

  public String getGp2InOrOutC2() {
    return gp2InOrOutC2;
  }

  public void setGp2InOrOutC2(String gp2InOrOutC2) {
    this.gp2InOrOutC2 = gp2InOrOutC2;
  }

  public String getGp2FullOrEmptyC2() {
    return gp2FullOrEmptyC2;
  }

  public void setGp2FullOrEmptyC2(String gp2FullOrEmptyC2) {
    this.gp2FullOrEmptyC2 = gp2FullOrEmptyC2;
  }

  public String getGp2LineC2() {
    return gp2LineC2;
  }

  public void setGp2LineC2(String gp2LineC2) {
    this.gp2LineC2 = gp2LineC2;
  }

  public String getGp2ISOC2() {
    return gp2ISOC2;
  }

  public void setGp2ISOC2(String gp2isoc2) {
    gp2ISOC2 = gp2isoc2;
  }

  public String getGp2SizeC2() {
    return gp2SizeC2;
  }

  public void setGp2SizeC2(String gp2SizeC2) {
    this.gp2SizeC2 = gp2SizeC2;
  }

  public String getGp2HeightC2() {
    return gp2HeightC2;
  }

  public void setGp2HeightC2(String gp2HeightC2) {
    this.gp2HeightC2 = gp2HeightC2;
  }

  public String getGp2TypeC2() {
    return gp2TypeC2;
  }

  public void setGp2TypeC2(String gp2TypeC2) {
    this.gp2TypeC2 = gp2TypeC2;
  }

  public String getGp2CurPosC2() {
    return gp2CurPosC2;
  }

  public void setGp2CurPosC2(String gp2CurPosC2) {
    this.gp2CurPosC2 = gp2CurPosC2;
  }

  public String getGp2TotalWeightBridgeC2() {
    return gp2TotalWeightBridgeC2;
  }

  public void setGp2TotalWeightBridgeC2(String gp2TotalWeightBridgeC2) {
    this.gp2TotalWeightBridgeC2 = gp2TotalWeightBridgeC2;
  }

  public String getGp2NetWeightC2() {
    return gp2NetWeightC2;
  }

  public void setGp2NetWeightC2(String gp2NetWeightC2) {
    this.gp2NetWeightC2 = gp2NetWeightC2;
  }

  public String getGp2Seal1OriginC2() {
    return gp2Seal1OriginC2;
  }

  public void setGp2Seal1OriginC2(String gp2Seal1OriginC2) {
    this.gp2Seal1OriginC2 = gp2Seal1OriginC2;
  }

  public String getGp2Seal1TypeC2() {
    return gp2Seal1TypeC2;
  }

  public void setGp2Seal1TypeC2(String gp2Seal1TypeC2) {
    this.gp2Seal1TypeC2 = gp2Seal1TypeC2;
  }

  public String getGp2Seal1C2() {
    return gp2Seal1C2;
  }

  public void setGp2Seal1C2(String gp2Seal1C2) {
    this.gp2Seal1C2 = gp2Seal1C2;
  }

  public String getGp2Seal2OriginC2() {
    return gp2Seal2OriginC2;
  }

  public void setGp2Seal2OriginC2(String gp2Seal2OriginC2) {
    this.gp2Seal2OriginC2 = gp2Seal2OriginC2;
  }

  public String getGp2Seal2TypeC2() {
    return gp2Seal2TypeC2;
  }

  public void setGp2Seal2TypeC2(String gp2Seal2TypeC2) {
    this.gp2Seal2TypeC2 = gp2Seal2TypeC2;
  }

  public String getGp2Seal2C2() {
    return gp2Seal2C2;
  }

  public void setGp2Seal2C2(String gp2Seal2C2) {
    this.gp2Seal2C2 = gp2Seal2C2;
  }

  public String getGp2YardPositonC2() {
    return gp2YardPositonC2;
  }

  public void setGp2YardPositonC2(String gp2YardPositonC2) {
    this.gp2YardPositonC2 = gp2YardPositonC2;
  }

  public String getGp2BayCodeC2() {
    return gp2BayCodeC2;
  }

  public void setGp2BayCodeC2(String gp2BayCodeC2) {
    this.gp2BayCodeC2 = gp2BayCodeC2;
  }

  public String getGp2PositionOnTruckC2() {
    return gp2PositionOnTruckC2;
  }

  public void setGp2PositionOnTruckC2(String gp2PositionOnTruckC2) {
    this.gp2PositionOnTruckC2 = gp2PositionOnTruckC2;
  }

  public String getHpatSeqId() {
    return hpatSeqId;
  }

  public void setHpatSeqId(String hpatSeqId) {
    this.hpatSeqId = hpatSeqId;
  }

  public String getImpExpScreen() {
    return impExpScreen;
  }

  public void setImpExpScreen(String impExpScreen) {
    this.impExpScreen = impExpScreen;
  }

  public String getTarWeightC1() {
    return tarWeightC1;
  }

  public void setTarWeightC1(String tarWeightC1) {
    this.tarWeightC1 = tarWeightC1;
  }

  public String getMaxWeightC1() {
    return maxWeightC1;
  }

  public void setMaxWeightC1(String maxWeightC1) {
    this.maxWeightC1 = maxWeightC1;
  }

  public String getTarWeightC2() {
    return tarWeightC2;
  }

  public void setTarWeightC2(String tarWeightC2) {
    this.tarWeightC2 = tarWeightC2;
  }

  public String getMaxWeightC2() {
    return maxWeightC2;
  }

  public void setMaxWeightC2(String maxWeightC2) {
    this.maxWeightC2 = maxWeightC2;
  }

  public String getWeighBridgeRemarks() {
    return weighBridgeRemarks;
  }

  public void setWeighBridgeRemarks(String weighBridgeRemarks) {
    this.weighBridgeRemarks = weighBridgeRemarks;
  }

  public String getEmptyWeightC1() {
    return emptyWeightC1;
  }

  public void setEmptyWeightC1(String emptyWeightC1) {
    this.emptyWeightC1 = emptyWeightC1;
  }

  public String getEmptyWeightC2() {
    return emptyWeightC2;
  }

  public void setEmptyWeightC2(String emptyWeightC2) {
    this.emptyWeightC2 = emptyWeightC2;
  }

  public String getKpaClassC1() {
    return kpaClassC1;
  }

  public void setKpaClassC1(String kpaClassC1) {
    this.kpaClassC1 = kpaClassC1;
  }

  public String getKpaApprovalC1() {
    return kpaApprovalC1;
  }

  public void setKpaApprovalC1(String kpaApprovalC1) {
    this.kpaApprovalC1 = kpaApprovalC1;
  }

  public String getGoodsHdlCodeC1() {
    return goodsHdlCodeC1;
  }

  public void setGoodsHdlCodeC1(String goodsHdlCodeC1) {
    this.goodsHdlCodeC1 = goodsHdlCodeC1;
  }

  public String getGoodsHdlDescC1() {
    return goodsHdlDescC1;
  }

  public void setGoodsHdlDescC1(String goodsHdlDescC1) {
    this.goodsHdlDescC1 = goodsHdlDescC1;
  }

  public String getDgDescC1() {
    return dgDescC1;
  }

  public void setDgDescC1(String dgDescC1) {
    this.dgDescC1 = dgDescC1;
  }

  public String getDontValidateDgC1() {
    return dontValidateDgC1;
  }

  public void setDontValidateDgC1(String dontValidateDgC1) {
    this.dontValidateDgC1 = dontValidateDgC1;
  }

  public boolean isDgWithinWindowEntryC1() {
    return dgWithinWindowEntryC1;
  }

  public void setDgWithinWindowEntryC1(boolean dgWithinWindowEntryC1) {
    this.dgWithinWindowEntryC1 = dgWithinWindowEntryC1;
  }

  public String getDgBypassRemarkC1() {
    return dgBypassRemarkC1;
  }

  public void setDgBypassRemarkC1(String dgBypassRemarkC1) {
    this.dgBypassRemarkC1 = dgBypassRemarkC1;
  }

  public String getLpkEdiEnabled() {
    return lpkEdiEnabled;
  }

  public void setLpkEdiEnabled(String lpkEdiEnabled) {
    this.lpkEdiEnabled = lpkEdiEnabled;
  }

  public String getKpaClassC2() {
    return kpaClassC2;
  }

  public void setKpaClassC2(String kpaClassC2) {
    this.kpaClassC2 = kpaClassC2;
  }

  public String getKpaApprovalC2() {
    return kpaApprovalC2;
  }

  public void setKpaApprovalC2(String kpaApprovalC2) {
    this.kpaApprovalC2 = kpaApprovalC2;
  }

  public String getGoodsHdlCodeC2() {
    return goodsHdlCodeC2;
  }

  public void setGoodsHdlCodeC2(String goodsHdlCodeC2) {
    this.goodsHdlCodeC2 = goodsHdlCodeC2;
  }

  public String getGoodsHdlDescC2() {
    return goodsHdlDescC2;
  }

  public void setGoodsHdlDescC2(String goodsHdlDescC2) {
    this.goodsHdlDescC2 = goodsHdlDescC2;
  }

  public String getDgDescC2() {
    return dgDescC2;
  }

  public void setDgDescC2(String dgDescC2) {
    this.dgDescC2 = dgDescC2;
  }

  public String getDontValidateDgC2() {
    return dontValidateDgC2;
  }

  public void setDontValidateDgC2(String dontValidateDgC2) {
    this.dontValidateDgC2 = dontValidateDgC2;
  }

  public boolean isDgWithinWindowEntryC2() {
    return dgWithinWindowEntryC2;
  }

  public void setDgWithinWindowEntryC2(boolean dgWithinWindowEntryC2) {
    this.dgWithinWindowEntryC2 = dgWithinWindowEntryC2;
  }

  public String getDgBypassRemarkC2() {
    return dgBypassRemarkC2;
  }

  public void setDgBypassRemarkC2(String dgBypassRemarkC2) {
    this.dgBypassRemarkC2 = dgBypassRemarkC2;
  }

  public boolean isAllowBypassHpat() {
    return allowBypassHpat;
  }

  public void setAllowBypassHpat(boolean allowBypassHpat) {
    this.allowBypassHpat = allowBypassHpat;
  }

  public boolean isAllowBypassDgVal() {
    return allowBypassDgVal;
  }

  public void setAllowBypassDgVal(boolean allowBypassDgVal) {
    this.allowBypassDgVal = allowBypassDgVal;
  }

  public boolean isAllowBypassDgValRemoteC1() {
    return allowBypassDgValRemoteC1;
  }

  public void setAllowBypassDgValRemoteC1(boolean allowBypassDgValRemoteC1) {
    this.allowBypassDgValRemoteC1 = allowBypassDgValRemoteC1;
  }

  public boolean isAllowBypassDgValRemoteC2() {
    return allowBypassDgValRemoteC2;
  }

  public void setAllowBypassDgValRemoteC2(boolean allowBypassDgValRemoteC2) {
    this.allowBypassDgValRemoteC2 = allowBypassDgValRemoteC2;
  }

  public String getNetWeightCosmosC1() {
    return netWeightCosmosC1;
  }

  public void setNetWeightCosmosC1(String netWeightCosmosC1) {
    this.netWeightCosmosC1 = netWeightCosmosC1;
  }

  public String getTareWeightCosmosC1() {
    return tareWeightCosmosC1;
  }

  public void setTareWeightCosmosC1(String tareWeightCosmosC1) {
    this.tareWeightCosmosC1 = tareWeightCosmosC1;
  }

  public String getGrossWeightCosmosC1() {
    return grossWeightCosmosC1;
  }

  public void setGrossWeightCosmosC1(String grossWeightCosmosC1) {
    this.grossWeightCosmosC1 = grossWeightCosmosC1;
  }

  public String getNetWeightCosmosC2() {
    return netWeightCosmosC2;
  }

  public void setNetWeightCosmosC2(String netWeightCosmosC2) {
    this.netWeightCosmosC2 = netWeightCosmosC2;
  }

  public String getTareWeightCosmosC2() {
    return tareWeightCosmosC2;
  }

  public void setTareWeightCosmosC2(String tareWeightCosmosC2) {
    this.tareWeightCosmosC2 = tareWeightCosmosC2;
  }

  public String getGrossWeightCosmosC2() {
    return grossWeightCosmosC2;
  }

  public void setGrossWeightCosmosC2(String grossWeightCosmosC2) {
    this.grossWeightCosmosC2 = grossWeightCosmosC2;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public String getCugIdSeq() {
    return cugIdSeq;
  }

  public void setCugIdSeq(String cugIdSeq) {
    this.cugIdSeq = cugIdSeq;
  }

  public String getBackToBack() {
    return backToBack;
  }

  public void setBackToBack(String backToBack) {
    this.backToBack = backToBack;
  }

  public String getGateInTime() {
    return gateInTime;
  }

  public void setGateInTime(String gateInTime) {
    this.gateInTime = gateInTime;
  }

  public String getGateInTimeOK() {
    return gateInTimeOK;
  }

  public void setGateInTimeOK(String gateInTimeOK) {
    this.gateInTimeOK = gateInTimeOK;
  }

  public double getWeightDifferentC1() {
    return weightDifferentC1;
  }

  public void setWeightDifferentC1(double weightDifferentC1) {
    this.weightDifferentC1 = weightDifferentC1;
  }

  public double getWeightDifferentPercentageC1() {
    return weightDifferentPercentageC1;
  }

  public void setWeightDifferentPercentageC1(double weightDifferentPercentageC1) {
    this.weightDifferentPercentageC1 = weightDifferentPercentageC1;
  }

  public double getWeightDifferentC2() {
    return weightDifferentC2;
  }

  public void setWeightDifferentC2(double weightDifferentC2) {
    this.weightDifferentC2 = weightDifferentC2;
  }

  public double getWeightDifferentPercentageC2() {
    return weightDifferentPercentageC2;
  }

  public void setWeightDifferentPercentageC2(double weightDifferentPercentageC2) {
    this.weightDifferentPercentageC2 = weightDifferentPercentageC2;
  }

  public String getWrongDoorC1() {
    return wrongDoorC1;
  }

  public void setWrongDoorC1(String wrongDoorC1) {
    this.wrongDoorC1 = wrongDoorC1;
  }

  public String getWrongDoorC2() {
    return wrongDoorC2;
  }

  public void setWrongDoorC2(String wrongDoorC2) {
    this.wrongDoorC2 = wrongDoorC2;
  }

  public String getDontValidateSealC1() {
    return dontValidateSealC1;
  }

  public void setDontValidateSealC1(String dontValidateSealC1) {
    this.dontValidateSealC1 = dontValidateSealC1;
  }

  public String getDontValidateSealC2() {
    return dontValidateSealC2;
  }

  public void setDontValidateSealC2(String dontValidateSealC2) {
    this.dontValidateSealC2 = dontValidateSealC2;
  }

  public String getHpabIsoCodeC1() {
    return hpabIsoCodeC1;
  }

  public void setHpabIsoCodeC1(String hpabIsoCodeC1) {
    this.hpabIsoCodeC1 = hpabIsoCodeC1;
  }

  public String getHpabIsoCodeC2() {
    return hpabIsoCodeC2;
  }

  public void setHpabIsoCodeC2(String hpabIsoCodeC2) {
    this.hpabIsoCodeC2 = hpabIsoCodeC2;
  }

  public String getCosmosIsoCodeC1() {
    return cosmosIsoCodeC1;
  }

  public void setCosmosIsoCodeC1(String cosmosIsoCodeC1) {
    this.cosmosIsoCodeC1 = cosmosIsoCodeC1;
  }

  public String getCosmosIsoCodeC2() {
    return cosmosIsoCodeC2;
  }

  public void setCosmosIsoCodeC2(String cosmosIsoCodeC2) {
    this.cosmosIsoCodeC2 = cosmosIsoCodeC2;
  }

  public String getStaffNo() {
    return staffNo;
  }

  public void setStaffNo(String staffNo) {
    this.staffNo = staffNo;
  }

  public String getUserFullName() {
    return userFullName;
  }

  public void setUserFullName(String userFullName) {
    this.userFullName = userFullName;
  }

  public String getReferPHeadNo() {
    return referPHeadNo;
  }

  public void setReferPHeadNo(String referPHeadNo) {
    this.referPHeadNo = referPHeadNo;
  }

  public String getCont1Refer() {
    return cont1Refer;
  }

  public void setCont1Refer(String cont1Refer) {
    this.cont1Refer = cont1Refer;
  }

  public String getCont1ReferContNo() {
    return cont1ReferContNo;
  }

  public void setCont1ReferContNo(String cont1ReferContNo) {
    this.cont1ReferContNo = cont1ReferContNo;
  }

  public String getCont1ReferPosition() {
    return cont1ReferPosition;
  }

  public void setCont1ReferPosition(String cont1ReferPosition) {
    this.cont1ReferPosition = cont1ReferPosition;
  }

  public String getCont1ReferRemarks() {
    return cont1ReferRemarks;
  }

  public void setCont1ReferRemarks(String cont1ReferRemarks) {
    this.cont1ReferRemarks = cont1ReferRemarks;
  }

  public String getCont1ReferReason() {
    return cont1ReferReason;
  }

  public void setCont1ReferReason(String cont1ReferReason) {
    this.cont1ReferReason = cont1ReferReason;
  }

  public String getCont1ReferIsoCode() {
    return cont1ReferIsoCode;
  }

  public void setCont1ReferIsoCode(String cont1ReferIsoCode) {
    this.cont1ReferIsoCode = cont1ReferIsoCode;
  }

  public String getCont1ReferSeal1() {
    return cont1ReferSeal1;
  }

  public void setCont1ReferSeal1(String cont1ReferSeal1) {
    this.cont1ReferSeal1 = cont1ReferSeal1;
  }

  public String getCont1ReferSeal2() {
    return cont1ReferSeal2;
  }

  public void setCont1ReferSeal2(String cont1ReferSeal2) {
    this.cont1ReferSeal2 = cont1ReferSeal2;
  }

  public String getCont2Refer() {
    return cont2Refer;
  }

  public void setCont2Refer(String cont2Refer) {
    this.cont2Refer = cont2Refer;
  }

  public String getCont2ReferContNo() {
    return cont2ReferContNo;
  }

  public void setCont2ReferContNo(String cont2ReferContNo) {
    this.cont2ReferContNo = cont2ReferContNo;
  }

  public String getCont2ReferPosition() {
    return cont2ReferPosition;
  }

  public void setCont2ReferPosition(String cont2ReferPosition) {
    this.cont2ReferPosition = cont2ReferPosition;
  }

  public String getCont2ReferRemarks() {
    return cont2ReferRemarks;
  }

  public void setCont2ReferRemarks(String cont2ReferRemarks) {
    this.cont2ReferRemarks = cont2ReferRemarks;
  }

  public String getCont2ReferReason() {
    return cont2ReferReason;
  }

  public void setCont2ReferReason(String cont2ReferReason) {
    this.cont2ReferReason = cont2ReferReason;
  }

  public String getCont2ReferIsoCode() {
    return cont2ReferIsoCode;
  }

  public void setCont2ReferIsoCode(String cont2ReferIsoCode) {
    this.cont2ReferIsoCode = cont2ReferIsoCode;
  }

  public String getCont2ReferSeal1() {
    return cont2ReferSeal1;
  }

  public void setCont2ReferSeal1(String cont2ReferSeal1) {
    this.cont2ReferSeal1 = cont2ReferSeal1;
  }

  public String getCont2ReferSeal2() {
    return cont2ReferSeal2;
  }

  public void setCont2ReferSeal2(String cont2ReferSeal2) {
    this.cont2ReferSeal2 = cont2ReferSeal2;
  }

  public String getCont1SelectedReferReason() {
    return cont1SelectedReferReason;
  }

  public void setCont1SelectedReferReason(String cont1SelectedReferReason) {
    this.cont1SelectedReferReason = cont1SelectedReferReason;
  }

  public String getCont2SelectedReferReason() {
    return cont2SelectedReferReason;
  }

  public void setCont2SelectedReferReason(String cont2SelectedReferReason) {
    this.cont2SelectedReferReason = cont2SelectedReferReason;
  }

  public String getReferId() {
    return referId;
  }

  public void setReferId(String referId) {
    this.referId = referId;
  }

  public String getDoubleBookingSelection() {
    return doubleBookingSelection;
  }

  public void setDoubleBookingSelection(String doubleBookingSelection) {
    this.doubleBookingSelection = doubleBookingSelection;
  }

  public String getDoubleBookingC1() {
    return doubleBookingC1;
  }

  public void setDoubleBookingC1(String doubleBookingC1) {
    this.doubleBookingC1 = doubleBookingC1;
  }

  public String getDoubleBookingC2() {
    return doubleBookingC2;
  }

  public void setDoubleBookingC2(String doubleBookingC2) {
    this.doubleBookingC2 = doubleBookingC2;
  }

  public String getAcceptOrReject() {
    return acceptOrReject;
  }

  public void setAcceptOrReject(String acceptOrReject) {
    this.acceptOrReject = acceptOrReject;
  }

  public String getRejectRemarks() {
    return rejectRemarks;
  }

  public void setRejectRemarks(String rejectRemarks) {
    this.rejectRemarks = rejectRemarks;
  }

  public String getCon01handlingSubType() {
    return con01handlingSubType;
  }

  public void setCon01handlingSubType(String con01handlingSubType) {
    this.con01handlingSubType = con01handlingSubType;
  }

  public String getCon02handlingSubType() {
    return con02handlingSubType;
  }

  public void setCon02handlingSubType(String con02handlingSubType) {
    this.con02handlingSubType = con02handlingSubType;
  }

  public int getFuelWeight() {
    return fuelWeight;
  }

  public void setFuelWeight(int fuelWeight) {
    this.fuelWeight = fuelWeight;
  }

  public int getTireWeight() {
    return tireWeight;
  }

  public void setTireWeight(int tireWeight) {
    this.tireWeight = tireWeight;
  }

  public int getTruckWeight() {
    return truckWeight;
  }

  public void setTruckWeight(int truckWeight) {
    this.truckWeight = truckWeight;
  }

  public int getTrailerWeight() {
    return trailerWeight;
  }

  public void setTrailerWeight(int trailerWeight) {
    this.trailerWeight = trailerWeight;
  }

  public int getMgwWeightC1() {
    return mgwWeightC1;
  }

  public void setMgwWeightC1(int mgwWeightC1) {
    this.mgwWeightC1 = mgwWeightC1;
  }

  public int getMgwWeightC2() {
    return mgwWeightC2;
  }

  public void setMgwWeightC2(int mgwWeightC2) {
    this.mgwWeightC2 = mgwWeightC2;
  }

  public int getVariance() {
    return variance;
  }

  public void setVariance(int variance) {
    this.variance = variance;
  }

  public String getCalculatedVarianceC1() {
    return calculatedVarianceC1;
  }

  public void setCalculatedVarianceC1(String calculatedVarianceC1) {
    this.calculatedVarianceC1 = calculatedVarianceC1;
  }

  public String getCalculatedVarianceC2() {
    return calculatedVarianceC2;
  }

  public void setCalculatedVarianceC2(String calculatedVarianceC2) {
    this.calculatedVarianceC2 = calculatedVarianceC2;
  }

  public int getTernimalVGMC1() {
    return ternimalVGMC1;
  }

  public void setTernimalVGMC1(int ternimalVGMC1) {
    this.ternimalVGMC1 = ternimalVGMC1;
  }

  public int getShipperVGMC1() {
    return shipperVGMC1;
  }

  public void setShipperVGMC1(int shipperVGMC1) {
    this.shipperVGMC1 = shipperVGMC1;
  }

  public int getShipperVGMC2() {
    return shipperVGMC2;
  }

  public void setShipperVGMC2(int shipperVGMC2) {
    this.shipperVGMC2 = shipperVGMC2;
  }

  public String getTrailerHeadNo() {
    return trailerHeadNo;
  }

  public void setTrailerHeadNo(String trailerHeadNo) {
    this.trailerHeadNo = trailerHeadNo;
  }

  public String getHpatBookigDate() {
    return hpatBookigDate;
  }

  public void setHpatBookigDate(String hpatBookigDate) {
    this.hpatBookigDate = hpatBookigDate;
  }

  public boolean isSolasRejection() {
    return solasRejection;
  }

  public void setSolasRejection(boolean solasRejection) {
    this.solasRejection = solasRejection;
  }

  public boolean isShipperVGM() {
    return shipperVGM;
  }

  public void setShipperVGM(boolean shipperVGM) {
    this.shipperVGM = shipperVGM;
  }

  public boolean isSolasCheck() {
    return solasCheck;
  }

  public void setSolasCheck(boolean solasCheck) {
    this.solasCheck = solasCheck;
  }

  public boolean isC1WithInTolerance() {
    return c1WithInTolerance;
  }

  public void setC1WithInTolerance(boolean c1WithInTolerance) {
    this.c1WithInTolerance = c1WithInTolerance;
  }

  public boolean isC2WithInTolerance() {
    return c2WithInTolerance;
  }

  public void setC2WithInTolerance(boolean c2WithInTolerance) {
    this.c2WithInTolerance = c2WithInTolerance;
  }

  public String getSolasDetailC1() {
    return solasDetailC1;
  }

  public void setSolasDetailC1(String solasDetailC1) {
    this.solasDetailC1 = solasDetailC1;
  }

  public String getSolasDetailC2() {
    return solasDetailC2;
  }

  public void setSolasDetailC2(String solasDetailC2) {
    this.solasDetailC2 = solasDetailC2;
  }

  public String getExpSeq01() {
    return expSeq01;
  }

  public void setExpSeq01(String expSeq01) {
    this.expSeq01 = expSeq01;
  }

  public String getExpSeq02() {
    return expSeq02;
  }

  public void setExpSeq02(String expSeq02) {
    this.expSeq02 = expSeq02;
  }

  public String getSolasInstruction() {
    return solasInstruction;
  }

  public void setSolasInstruction(String solasInstruction) {
    this.solasInstruction = solasInstruction;
  }

  public String getExpBookingNoC1() {
    return expBookingNoC1;
  }

  public void setExpBookingNoC1(String expBookingNoC1) {
    this.expBookingNoC1 = expBookingNoC1;
  }

  public String getExpBookingNoC2() {
    return expBookingNoC2;
  }

  public void setExpBookingNoC2(String expBookingNoC2) {
    this.expBookingNoC2 = expBookingNoC2;
  }

  public String getFaLedgerCodeC1() {
    return faLedgerCodeC1;
  }

  public void setFaLedgerCodeC1(String faLedgerCodeC1) {
    this.faLedgerCodeC1 = faLedgerCodeC1;
  }

  public String getFaLedgerCodeC2() {
    return faLedgerCodeC2;
  }

  public void setFaLedgerCodeC2(String faLedgerCodeC2) {
    this.faLedgerCodeC2 = faLedgerCodeC2;
  }

  public int getMinPMWeight() {
    return minPMWeight;
  }

  public void setMinPMWeight(int minPMWeight) {
    this.minPMWeight = minPMWeight;
  }

  public int getMaxPMWeight() {
    return maxPMWeight;
  }

  public void setMaxPMWeight(int maxPMWeight) {
    this.maxPMWeight = maxPMWeight;
  }

  public int getMinTrailerWeight() {
    return minTrailerWeight;
  }

  public void setMinTrailerWeight(int minTrailerWeight) {
    this.minTrailerWeight = minTrailerWeight;
  }

  public int getMaxTrailerWeight() {
    return maxTrailerWeight;
  }

  public void setMaxTrailerWeight(int maxTrailerWeight) {
    this.maxTrailerWeight = maxTrailerWeight;
  }

  public int getC1MaxMGW() {
    return c1MaxMGW;
  }

  public void setC1MaxMGW(int c1MaxMGW) {
    this.c1MaxMGW = c1MaxMGW;
  }

  public int getC1MinMGW() {
    return c1MinMGW;
  }

  public void setC1MinMGW(int c1MinMGW) {
    this.c1MinMGW = c1MinMGW;
  }

  public int getC2MaxMGW() {
    return c2MaxMGW;
  }

  public void setC2MaxMGW(int c2MaxMGW) {
    this.c2MaxMGW = c2MaxMGW;
  }

  public int getC2MinMGW() {
    return c2MinMGW;
  }

  public void setC2MinMGW(int c2MinMGW) {
    this.c2MinMGW = c2MinMGW;
  }

  public String getPmVerified() {
    return pmVerified;
  }

  public void setPmVerified(String pmVerified) {
    this.pmVerified = pmVerified;
  }

  public String getTrailerVerified() {
    return trailerVerified;
  }

  public void setTrailerVerified(String trailerVerified) {
    this.trailerVerified = trailerVerified;
  }

  public String getSolasRefNumberC1() {
    return solasRefNumberC1;
  }

  public void setSolasRefNumberC1(String solasRefNumberC1) {
    this.solasRefNumberC1 = solasRefNumberC1;
  }

  public String getSolasRefNumberC2() {
    return solasRefNumberC2;
  }

  public void setSolasRefNumberC2(String solasRefNumberC2) {
    this.solasRefNumberC2 = solasRefNumberC2;
  }

  public String getPrintEIRNo() {
    return printEIRNo;
  }

  public void setPrintEIRNo(String printEIRNo) {
    this.printEIRNo = printEIRNo;
  }

  public boolean isRefersolas() {
    return refersolas;
  }

  public void setRefersolas(boolean refersolas) {
    this.refersolas = refersolas;
  }

  public int getPressedButton() {
    return pressedButton;
  }

  public void setPressedButton(int pressedButton) {
    this.pressedButton = pressedButton;
  }



}
