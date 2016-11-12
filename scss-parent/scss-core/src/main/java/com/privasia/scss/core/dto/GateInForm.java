package com.privasia.scss.core.dto;

import java.io.Serializable;

public class GateInForm implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int pressedButton;

  // container1
  private ExportContainer container1;

  // container2
  private ExportContainer container2;

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
  private GatePassContainer gpC1;

  // Gate Pass Container2
  private GatePassContainer gpC2;

  private String hpatSeqId;

  /*
   * Weight Bridge Validation
   */
  private String impExpScreen;
  private String weighBridgeRemarks;



  /* WPTSCSSSUP-76 */
  private boolean allowBypassHpat;

  private boolean allowBypassDgVal;

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

  public int getPressedButton() {
    return pressedButton;
  }

  public void setPressedButton(int pressedButton) {
    this.pressedButton = pressedButton;
  }

  public ExportContainer getContainer1() {
    return container1;
  }

  public void setContainer1(ExportContainer container1) {
    this.container1 = container1;
  }

  public ExportContainer getContainer2() {
    return container2;
  }

  public void setContainer2(ExportContainer container2) {
    this.container2 = container2;
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

  public GatePassContainer getGpC1() {
    return gpC1;
  }

  public void setGpC1(GatePassContainer gpC1) {
    this.gpC1 = gpC1;
  }

  public GatePassContainer getGpC2() {
    return gpC2;
  }

  public void setGpC2(GatePassContainer gpC2) {
    this.gpC2 = gpC2;
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

  public String getWeighBridgeRemarks() {
    return weighBridgeRemarks;
  }

  public void setWeighBridgeRemarks(String weighBridgeRemarks) {
    this.weighBridgeRemarks = weighBridgeRemarks;
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

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
