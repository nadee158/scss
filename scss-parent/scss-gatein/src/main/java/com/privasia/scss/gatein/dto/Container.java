package com.privasia.scss.gatein.dto;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @author nadee158 
 * moved all the methods to service class - Container Service
 * Only the attributes remains in the dto class
 *
 */
public class Container {

  private static final Log log = LogFactory.getLog(Container.class);

  private String bookingNo = "";
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

  private String containerNo = "";
  private boolean fullOrEmpty = false;
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

  public boolean isFullOrEmpty() {
    return fullOrEmpty;
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

  public void setFullOrEmpty(boolean fullOrEmpty) {
    this.fullOrEmpty = fullOrEmpty;
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


}
