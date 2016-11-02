package com.privasia.scss.gatein.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExportSSR {


  private void checkExportSSR() {
    Calendar eta = Calendar.getInstance();
    Calendar gateIn = Calendar.getInstance();
    eta.setTime(vesselETADateTime);
    gateIn.setTime(gateInDateTime);
    eta.add(Calendar.HOUR, -4);

    if (eta.before(gateIn)) {
      hasReplan = "Y";
    } else {
      eta.add(Calendar.HOUR, -6);
      if (eta.before(gateIn)) {
        hasOverClosing = "Y";
      }
    }
  }

  public boolean getSSRBlockStatus() {
    if ((hasReplan.equals("Y") || hasOverClosing.equals("Y"))) {
      return true;
    }
    return false;
  }

  public String getHasReplan() {
    return hasReplan;
  }

  public String getHasOverClosing() {
    return hasOverClosing;
  }

  public ExportSSR(String gateInDateTime, String vesselETADateTime) {
    SimpleDateFormat dd_MM_yyyy_HH_mm_ss = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat ddMMyyyyHHmmss = new SimpleDateFormat("ddMMyyyyHHmmss");

    try {
      this.gateInDateTime = dd_MM_yyyy_HH_mm_ss.parse(gateInDateTime);
      this.vesselETADateTime = ddMMyyyyHHmmss.parse(vesselETADateTime);
      this.checkExportSSR();
    } catch (Exception e) {
    }
  }

  public ExportSSR(java.util.Date gateInDateTime, java.util.Date vesselETADateTime) {
    this.gateInDateTime = gateInDateTime;
    this.vesselETADateTime = vesselETADateTime;
    checkExportSSR();
  }

  private String hasReplan = "N";
  private String hasOverClosing = "N";

  private java.util.Date gateInDateTime;
  private java.util.Date vesselETADateTime;
}
