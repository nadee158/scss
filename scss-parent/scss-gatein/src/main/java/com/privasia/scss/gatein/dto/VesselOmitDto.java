package com.privasia.scss.gatein.dto;

public class VesselOmitDto {
  private String lineCode;
  private String agentCode;
  private String vesselVoyIn;
  private String vesselVoyOut;

  public VesselOmitDto() {}

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }

  public String getAgentCode() {
    return agentCode;
  }

  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }

  public String getVesselVoyIn() {
    return vesselVoyIn;
  }

  public void setVesselVoyIn(String vesselVoyIn) {
    this.vesselVoyIn = vesselVoyIn;
  }

  public String getVesselVoyOut() {
    return vesselVoyOut;
  }

  public void setVesselVoyOut(String vesselVoyOut) {
    this.vesselVoyOut = vesselVoyOut;
  }

}
