package com.privasia.scss.core.dto;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @author nadee158 moved all the methods to service class - Container Service Only the attributes
 *         remains in the dto class
 *
 */
public class Container {

  private static final Log log = LogFactory.getLog(Container.class);

  private String bookingNo = "";
  private String containerNo = "";
  private String inOrOut = "";
  private String line = "";
  private String fullOrEmpty = "";
  private String goods = "";
  private String OUT = "";
  private String car = "";
  private String SPOD = "";
  private String ISO = "";
  private String size = "";
  private String height = "";
  private String type = "";
  private String seal1Origin = "";
  private String seal1Type = "";
  private String seal1 = "";
  private String seal2 = "";
  private String seal2Origin = "";
  private String seal2Type = "";
  private String totalWeightBridge = "";
  private String PMBTM = "0";
  private String TRBTM = "0";
  private String netWeight = "";
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

  private String shipCode = "";
  private String agentCode = "";
  private String vesselCode = "";
  private String vesselName = "";
  private String vesselVoyage = "";
  private String vesselVisitId = "";
  private String vesselStatus = "";
  private String vesselScn = "";
  private java.util.Date vesselDateEta;
  private java.util.Date vesselDateAta;
  private String vesselVoyageOut = "";
  private String lineCode = "";

  private String fOrE = "";
  private String scn = "";
  private String seq = "";
  private java.util.Date etad;
  private String internalBlockDesc;

  private int totalBooking = 0;
  private int storagePeriod = -1;

  private boolean bookingNoExist = false;
  private boolean OGABlock = false;
  private boolean internalBlock = false;


  private boolean earlyEntry = false;
  private String startEarlyEntry = "";
  private String endEarlyEntry = "";
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
  private String emptyWeight;

  public String getSeq() {
    return seq;
  }

  public String getContainerNo() {
    return containerNo;
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

  public String getVesselCode() {
    return vesselCode;
  }

  public String getVesselVoyage() {
    return vesselVoyage;
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

  public void setVesselVoyage(String vesselVoyage) {
    this.vesselVoyage = vesselVoyage;
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

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
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

  public String getISO() {
    return ISO;
  }

  public void setISO(String iSO) {
    ISO = iSO;
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

  public String getSeal1Origin() {
    return seal1Origin;
  }

  public void setSeal1Origin(String seal1Origin) {
    this.seal1Origin = seal1Origin;
  }

  public String getSeal1Type() {
    return seal1Type;
  }

  public void setSeal1Type(String seal1Type) {
    this.seal1Type = seal1Type;
  }

  public String getSeal1() {
    return seal1;
  }

  public void setSeal1(String seal1) {
    this.seal1 = seal1;
  }

  public String getSeal2() {
    return seal2;
  }

  public void setSeal2(String seal2) {
    this.seal2 = seal2;
  }

  public String getSeal2Origin() {
    return seal2Origin;
  }

  public void setSeal2Origin(String seal2Origin) {
    this.seal2Origin = seal2Origin;
  }

  public String getSeal2Type() {
    return seal2Type;
  }

  public void setSeal2Type(String seal2Type) {
    this.seal2Type = seal2Type;
  }

  public String getTotalWeightBridge() {
    return totalWeightBridge;
  }

  public void setTotalWeightBridge(String totalWeightBridge) {
    this.totalWeightBridge = totalWeightBridge;
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

  public String getNetWeight() {
    return netWeight;
  }

  public void setNetWeight(String netWeight) {
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

  public String getEmptyWeight() {
    return emptyWeight;
  }

  public void setEmptyWeight(String emptyWeight) {
    this.emptyWeight = emptyWeight;
  }


}
