package com.privasia.scss.common.dto;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author nadee158 moved all the methods to service class - ExportContainer Service Only the
 *         attributes remains in the dto class
 *
 */
public class ExportContainer {

  private static final Log log = LogFactory.getLog(ExportContainer.class);

  // -------------------START OF RESOLVED PROPERTIES -------------------------
  private String containerNumber;

  private String expSealNo01;

  private String expSealNo02;

  private String containerISO;

  private SolasInfo solasInfo;

  private String expBookingNo;

  // -------------------END OF RESOLVED PROPERTIES -------------------------
  private DGInfo dgInfo;

  //////////////////////////// TO createImpRequestXML
  private String errXMLMsg;

  private double shipperVGM;

  private SealInfo sealInfo01;

  private SealInfo sealInfo02;

  private boolean withInTolerance;

  private String expAgent;

  private String contRefer;

  private String yardPosition;

  private String bayCode;



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

  public ExportContainer() {
    super();
  }


  private String bookingNo = "";
  private String inOrOut = "";
  private String line = "";
  private String fullOrEmpty = "";
  private String goods = "";
  private String OUT = "";
  private String car = "";
  private String SPOD = "";
  private String size = "";
  private String height = "";
  private String type = "";
  private double totalWeightBridge;
  private String PMBTM = "0";
  private String TRBTM = "0";
  private double netWeight;
  private String operationReefer = "";
  private String tempSign = "";
  private String tempUnit = "";
  private String temp = "";
  private String IMDG = "";
  private String UN = "";
  private String IMDGlabels = "";
  private String OOGflag = "";
  private String OOGOH = "";
  private String OOGOL = "";
  private String OOGOF = "";
  private String OOGOA = "";
  private String OOGOR = "";

  private List<String> damage;
  private List<String> damageA;

  // private String damage1 = "";
  // private String damage2 = "";
  // private String damage3 = "";
  // private String damage4 = "";
  // private String damage5 = "";
  // private String damage6 = "";
  // private String damage7 = "";
  // private String damage8 = "";
  // private String damage9 = "";
  //
  // private String damage1A = "";
  // private String damage2A = "";
  // private String damage3A = "";
  // private String damage4A = "";
  // private String damage5A = "";
  // private String damage6A = "";
  // private String damage7A = "";
  // private String damage8A = "";
  // private String damage9A = "";

  private String positionOnTruck = "";
  private String orderFOT = "";
  private String curPos = "";

  private String shipCode = StringUtils.EMPTY;
  private String agentCode = StringUtils.EMPTY;
  private String vesselCode = StringUtils.EMPTY;
  private String vesselName = StringUtils.EMPTY;
  private String vesselVoyageIn = StringUtils.EMPTY;
  private String vesselVisitId = StringUtils.EMPTY;
  private String vesselStatus = StringUtils.EMPTY;
  private String vesselScn = StringUtils.EMPTY;
  private java.util.Date vesselDateEta;
  private java.util.Date vesselDateAta;
  private String vesselVoyageOut = StringUtils.EMPTY;
  private String lineCode = StringUtils.EMPTY;;

  private String fOrE = StringUtils.EMPTY;
  private String scn = StringUtils.EMPTY;
  private String seq = StringUtils.EMPTY;
  private java.util.Date etad;
  private String internalBlockDesc = StringUtils.EMPTY;

  private int totalBooking = 0;
  private int storagePeriod = -1;

  private boolean bookingNoExist = false;
  private boolean OGABlock = false;
  private boolean internalBlock = false;

  private boolean earlyEntry = false;
  private String startEarlyEntry = StringUtils.EMPTY;
  private String endEarlyEntry = StringUtils.EMPTY;
  private boolean bypassEEntry = false;
  private boolean bypassDg = false;

  public static final String FULL_FLAG = "F";
  public static final String EMPTY_FLAG = "E";
  public static final String YES = "Y";
  public static final String NO = "N";

  private boolean registeredInEarlyEntry = false;

  private String lpkEdiEnabled;

  /**
   * LPK EDI
   */
  private String kpaClass;
  private String kpaApproval;
  private String goodsHdlCode;
  private String goodsHdlDesc;
  private String dgDesc;
  private String dontValidateDg;
  private boolean dgWithinWindowEntry;
  private String dgBypassRemark;

  private boolean allowBypassDgValRemote;

  private String expHasOOGSSR;
  private String expHasOverClosingSSR;
  private String expHasReplanSSR;
  private String expSSRBlockStatus;

  private String tarWeight;
  private String maxWeight;
  private double emptyWeight;


  public String getSeq() {
    return seq;
  }

  public String getExpAgent() {
    return expAgent;
  }


  public double getShipperVGM() {
    return shipperVGM;
  }

  public void setShipperVGM(double shipperVGM) {
    this.shipperVGM = shipperVGM;
  }

  public void setExpAgent(String expAgent) {
    this.expAgent = expAgent;
  }

  public String getContRefer() {
    return contRefer;
  }


  public void setContRefer(String contRefer) {
    this.contRefer = contRefer;
  }

  public boolean isWithInTolerance() {
    return withInTolerance;
  }


  public void setWithInTolerance(boolean withInTolerance) {
    this.withInTolerance = withInTolerance;
  }

  public String getBookingNo() {
    return bookingNo;
  }

  public boolean isBookingNoExist() {
    return bookingNoExist;
  }

  public String getFOrE() {
    return fOrE;
  }

  public java.util.Date getEtad() {
    return etad;
  }

  public boolean isOGABlock() {
    return OGABlock;
  }

  public boolean isInternalBlock() {
    return internalBlock;
  }

  public String getAgentCode() {
    return agentCode;
  }

  public String getErrXMLMsg() {
    return errXMLMsg;
  }


  public void setErrXMLMsg(String errXMLMsg) {
    this.errXMLMsg = errXMLMsg;
  }


  public String getVesselCode() {
    return vesselCode;
  }

  public String getVesselVoyageIn() {
    return vesselVoyageIn;
  }

  public String getVesselVisitId() {
    return vesselVisitId;
  }

  public String getShipCode() {
    return shipCode;
  }

  public String getVesselStatus() {
    return vesselStatus;
  }

  public String getVesselScn() {
    return vesselScn;
  }

  public java.util.Date getVesselDateEta() {
    return vesselDateEta;
  }

  public String getVesselVoyageOut() {
    return vesselVoyageOut;
  }

  public String getLineCode() {
    return lineCode;
  }

  public String getVesselDateEta_ddMMyyyyHHmmss() {
    SimpleDateFormat sdf_ddMMyyyyHHmmss = new SimpleDateFormat("ddMMyyyyHHmmss");
    if (vesselDateEta == null) {
      return "";
    }
    return sdf_ddMMyyyyHHmmss.format(vesselDateEta);
  }

  public java.util.Date getVesselDateAta() {
    return vesselDateAta;
  }

  public String getInternalBlockDesc() {
    return internalBlockDesc;
  }

  public String getVesselDateAta_ddMMyyyyHHmmss() {
    SimpleDateFormat sdf_ddMMyyyyHHmmss = new SimpleDateFormat("ddMMyyyyHHmmss");
    if (vesselDateAta == null) {
      return "";
    }
    return sdf_ddMMyyyyHHmmss.format(vesselDateAta);
  }

  public static String getFullEmptyFlagDesc(String flag) {
    String desc = "";
    if (flag.equals(EMPTY_FLAG)) {
      desc = "Empty";
    } else {
      desc = "Full";
    }
    return desc;
  }

  public static String getReeferDesc(String reefer) {
    String desc = "";
    if (reefer.equals(YES)) {
      desc = "Yes";
    } else {
      desc = "No";
    }
    return desc;
  }

  public String getVesselDateEtaDisplay() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
    if (vesselDateEta == null) {
      return "";
    }

    return sdf.format(vesselDateEta) + " HRS";
  }

  public String getVesselName() {
    return vesselName;
  }

  public boolean isEarlyEntry() {
    return earlyEntry;
  }

  public boolean isBypassEEntry() {
    return bypassEEntry;
  }

  public String getStartEarlyEntry() {
    return startEarlyEntry;
  }

  public String getEndEarlyEntry() {
    return endEarlyEntry;
  }

  public boolean isBypassDg() {
    return bypassDg;
  }

  public int getTotalBooking() {
    return totalBooking;
  }

  public void setTotalBooking(int totalBooking) {
    this.totalBooking = totalBooking;
  }

  public String getfOrE() {
    return fOrE;
  }

  public void setfOrE(String fOrE) {
    this.fOrE = fOrE;
  }

  public String getScn() {
    return scn;
  }

  public void setScn(String scn) {
    this.scn = scn;
  }

  public int getStoragePeriod() {
    return storagePeriod;
  }

  public void setStoragePeriod(int storagePeriod) {
    this.storagePeriod = storagePeriod;
  }

  public void setBookingNo(String bookingNo) {
    this.bookingNo = bookingNo;
  }

  public void setShipCode(String shipCode) {
    this.shipCode = shipCode;
  }

  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }

  public void setVesselCode(String vesselCode) {
    this.vesselCode = vesselCode;
  }

  public void setVesselName(String vesselName) {
    this.vesselName = vesselName;
  }

  public void setVesselVoyageIn(String vesselVoyageIn) {
    this.vesselVoyageIn = vesselVoyageIn;
  }

  public void setVesselVisitId(String vesselVisitId) {
    this.vesselVisitId = vesselVisitId;
  }

  public void setVesselStatus(String vesselStatus) {
    this.vesselStatus = vesselStatus;
  }

  public void setVesselScn(String vesselScn) {
    this.vesselScn = vesselScn;
  }

  public void setVesselDateEta(java.util.Date vesselDateEta) {
    this.vesselDateEta = vesselDateEta;
  }

  public void setVesselDateAta(java.util.Date vesselDateAta) {
    this.vesselDateAta = vesselDateAta;
  }

  public void setVesselVoyageOut(String vesselVoyageOut) {
    this.vesselVoyageOut = vesselVoyageOut;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }

  public void setSeq(String seq) {
    this.seq = seq;
  }

  public void setEtad(java.util.Date etad) {
    this.etad = etad;
  }

  public void setInternalBlockDesc(String internalBlockDesc) {
    this.internalBlockDesc = internalBlockDesc;
  }


  public void setBookingNoExist(boolean bookingNoExist) {
    this.bookingNoExist = bookingNoExist;
  }

  public void setOGABlock(boolean oGABlock) {
    OGABlock = oGABlock;
  }

  public void setInternalBlock(boolean internalBlock) {
    this.internalBlock = internalBlock;
  }

  public void setEarlyEntry(boolean earlyEntry) {
    this.earlyEntry = earlyEntry;
  }

  public void setStartEarlyEntry(String startEarlyEntry) {
    this.startEarlyEntry = startEarlyEntry;
  }

  public void setEndEarlyEntry(String endEarlyEntry) {
    this.endEarlyEntry = endEarlyEntry;
  }

  public void setBypassEEntry(boolean bypassEEntry) {
    this.bypassEEntry = bypassEEntry;
  }

  public void setBypassDg(boolean bypassDg) {
    this.bypassDg = bypassDg;
  }

  public boolean isRegisteredInEarlyEntry() {
    return registeredInEarlyEntry;
  }

  public void setRegisteredInEarlyEntry(boolean registeredInEarlyEntry) {
    this.registeredInEarlyEntry = registeredInEarlyEntry;
  }

  public String getInOrOut() {
    return inOrOut;
  }

  public void setInOrOut(String inOrOut) {
    this.inOrOut = inOrOut;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String getFullOrEmpty() {
    return fullOrEmpty;
  }

  public void setFullOrEmpty(String fullOrEmpty) {
    this.fullOrEmpty = fullOrEmpty;
  }

  public String getGoods() {
    return goods;
  }

  public void setGoods(String goods) {
    this.goods = goods;
  }

  public String getOUT() {
    return OUT;
  }

  public void setOUT(String oUT) {
    OUT = oUT;
  }

  public String getCar() {
    return car;
  }

  public void setCar(String car) {
    this.car = car;
  }

  public String getSPOD() {
    return SPOD;
  }

  public void setSPOD(String sPOD) {
    SPOD = sPOD;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getPMBTM() {
    return PMBTM;
  }

  public void setPMBTM(String pMBTM) {
    PMBTM = pMBTM;
  }

  public String getTRBTM() {
    return TRBTM;
  }

  public void setTRBTM(String tRBTM) {
    TRBTM = tRBTM;
  }

  public double getNetWeight() {
    return netWeight;
  }

  public void setNetWeight(double netWeight) {
    this.netWeight = netWeight;
  }

  public String getOperationReefer() {
    return operationReefer;
  }

  public void setOperationReefer(String operationReefer) {
    this.operationReefer = operationReefer;
  }

  public String getTempSign() {
    return tempSign;
  }

  public void setTempSign(String tempSign) {
    this.tempSign = tempSign;
  }

  public String getTempUnit() {
    return tempUnit;
  }

  public void setTempUnit(String tempUnit) {
    this.tempUnit = tempUnit;
  }

  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }

  public String getIMDG() {
    return IMDG;
  }

  public void setIMDG(String iMDG) {
    IMDG = iMDG;
  }

  public String getUN() {
    return UN;
  }

  public void setUN(String uN) {
    UN = uN;
  }

  public String getIMDGlabels() {
    return IMDGlabels;
  }

  public void setIMDGlabels(String iMDGlabels) {
    IMDGlabels = iMDGlabels;
  }

  public String getOOGflag() {
    return OOGflag;
  }

  public void setOOGflag(String oOGflag) {
    OOGflag = oOGflag;
  }

  public String getOOGOH() {
    return OOGOH;
  }

  public void setOOGOH(String oOGOH) {
    OOGOH = oOGOH;
  }

  public String getOOGOL() {
    return OOGOL;
  }

  public void setOOGOL(String oOGOL) {
    OOGOL = oOGOL;
  }

  public String getOOGOF() {
    return OOGOF;
  }

  public void setOOGOF(String oOGOF) {
    OOGOF = oOGOF;
  }

  public String getOOGOA() {
    return OOGOA;
  }

  public void setOOGOA(String oOGOA) {
    OOGOA = oOGOA;
  }

  public String getOOGOR() {
    return OOGOR;
  }

  public void setOOGOR(String oOGOR) {
    OOGOR = oOGOR;
  }

  public List<String> getDamage() {
    return damage;
  }

  public void setDamage(List<String> damage) {
    this.damage = damage;
  }

  public List<String> getDamageA() {
    return damageA;
  }

  public void setDamageA(List<String> damageA) {
    this.damageA = damageA;
  }

  public String getPositionOnTruck() {
    return positionOnTruck;
  }

  public void setPositionOnTruck(String positionOnTruck) {
    this.positionOnTruck = positionOnTruck;
  }

  public String getOrderFOT() {
    return orderFOT;
  }

  public void setOrderFOT(String orderFOT) {
    this.orderFOT = orderFOT;
  }

  public String getCurPos() {
    return curPos;
  }

  public void setCurPos(String curPos) {
    this.curPos = curPos;
  }

  public String getKpaClass() {
    return kpaClass;
  }

  public void setKpaClass(String kpaClass) {
    this.kpaClass = kpaClass;
  }

  public String getKpaApproval() {
    return kpaApproval;
  }

  public void setKpaApproval(String kpaApproval) {
    this.kpaApproval = kpaApproval;
  }

  public String getGoodsHdlCode() {
    return goodsHdlCode;
  }

  public void setGoodsHdlCode(String goodsHdlCode) {
    this.goodsHdlCode = goodsHdlCode;
  }

  public String getGoodsHdlDesc() {
    return goodsHdlDesc;
  }

  public void setGoodsHdlDesc(String goodsHdlDesc) {
    this.goodsHdlDesc = goodsHdlDesc;
  }

  public String getDgDesc() {
    return dgDesc;
  }

  public void setDgDesc(String dgDesc) {
    this.dgDesc = dgDesc;
  }

  public String getDontValidateDg() {
    return dontValidateDg;
  }

  public void setDontValidateDg(String dontValidateDg) {
    this.dontValidateDg = dontValidateDg;
  }

  public boolean isDgWithinWindowEntry() {
    return dgWithinWindowEntry;
  }

  public void setDgWithinWindowEntry(boolean dgWithinWindowEntry) {
    this.dgWithinWindowEntry = dgWithinWindowEntry;
  }

  public String getDgBypassRemark() {
    return dgBypassRemark;
  }

  public void setDgBypassRemark(String dgBypassRemark) {
    this.dgBypassRemark = dgBypassRemark;
  }

  public String getLpkEdiEnabled() {
    return lpkEdiEnabled;
  }

  public void setLpkEdiEnabled(String lpkEdiEnabled) {
    this.lpkEdiEnabled = lpkEdiEnabled;
  }

  public boolean isAllowBypassDgValRemote() {
    return allowBypassDgValRemote;
  }

  public void setAllowBypassDgValRemote(boolean allowBypassDgValRemote) {
    this.allowBypassDgValRemote = allowBypassDgValRemote;
  }

  public String getExpHasOOGSSR() {
    return expHasOOGSSR;
  }

  public void setExpHasOOGSSR(String expHasOOGSSR) {
    this.expHasOOGSSR = expHasOOGSSR;
  }

  public String getExpHasOverClosingSSR() {
    return expHasOverClosingSSR;
  }

  public void setExpHasOverClosingSSR(String expHasOverClosingSSR) {
    this.expHasOverClosingSSR = expHasOverClosingSSR;
  }

  public String getExpHasReplanSSR() {
    return expHasReplanSSR;
  }

  public void setExpHasReplanSSR(String expHasReplanSSR) {
    this.expHasReplanSSR = expHasReplanSSR;
  }

  public String getExpSSRBlockStatus() {
    return expSSRBlockStatus;
  }

  public void setExpSSRBlockStatus(String expSSRBlockStatus) {
    this.expSSRBlockStatus = expSSRBlockStatus;
  }

  public String getTarWeight() {
    return tarWeight;
  }

  public void setTarWeight(String tarWeight) {
    this.tarWeight = tarWeight;
  }

  public String getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(String maxWeight) {
    this.maxWeight = maxWeight;
  }

  public double getEmptyWeight() {
    return emptyWeight;
  }

  public void setEmptyWeight(double emptyWeight) {
    this.emptyWeight = emptyWeight;
  }

  public double getTotalWeightBridge() {
    return totalWeightBridge;
  }

  public void setTotalWeightBridge(double totalWeightBridge) {
    this.totalWeightBridge = totalWeightBridge;
  }

  public SolasInfo getSolasInfo() {
    return solasInfo;
  }

  public void setSolasInfo(SolasInfo solasInfo) {
    this.solasInfo = solasInfo;
  }

  public DGInfo getDgInfo() {
    return dgInfo;
  }

  public void setDgInfo(DGInfo dgInfo) {
    this.dgInfo = dgInfo;
  }

  public String getContainerNumber() {
    return containerNumber;
  }

  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }

  public String getExpSealNo01() {
    return expSealNo01;
  }

  public void setExpSealNo01(String expSealNo01) {
    this.expSealNo01 = expSealNo01;
  }

  public String getExpSealNo02() {
    return expSealNo02;
  }

  public void setExpSealNo02(String expSealNo02) {
    this.expSealNo02 = expSealNo02;
  }

  public String getContainerISO() {
    return containerISO;
  }

  public void setContainerISO(String containerISO) {
    this.containerISO = containerISO;
  }

  public String getExpBookingNo() {
    return expBookingNo;
  }

  public void setExpBookingNo(String expBookingNo) {
    this.expBookingNo = expBookingNo;
  }

  public SealInfo getSealInfo01() {
    return sealInfo01;
  }

  public void setSealInfo01(SealInfo sealInfo01) {
    this.sealInfo01 = sealInfo01;
  }

  public SealInfo getSealInfo02() {
    return sealInfo02;
  }

  public void setSealInfo02(SealInfo sealInfo02) {
    this.sealInfo02 = sealInfo02;
  }



}
