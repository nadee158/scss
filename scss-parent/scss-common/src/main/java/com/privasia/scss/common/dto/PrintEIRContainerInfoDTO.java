package com.privasia.scss.common.dto;

import java.io.Serializable;

public class PrintEIRContainerInfoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String containerBayCode;

  private String containerInOrOut;

  private String containerPositionOnTruck;

  private String containerLine;

  private String containerHeight;

  private String containerNetWeight;

  private String containerType;

  private String lineOneInfo;

  private String lineTwoInfo;

  private String containerSeal;

  public String getContainerBayCode() {
    return containerBayCode;
  }

  public void setContainerBayCode(String containerBayCode) {
    this.containerBayCode = containerBayCode;
  }

  public String getContainerInOrOut() {
    return containerInOrOut;
  }

  public void setContainerInOrOut(String containerInOrOut) {
    this.containerInOrOut = containerInOrOut;
  }

  public String getContainerPositionOnTruck() {
    return containerPositionOnTruck;
  }

  public void setContainerPositionOnTruck(String containerPositionOnTruck) {
    this.containerPositionOnTruck = containerPositionOnTruck;
  }

  public String getContainerLine() {
    return containerLine;
  }

  public void setContainerLine(String containerLine) {
    this.containerLine = containerLine;
  }

  public String getContainerHeight() {
    return containerHeight;
  }

  public void setContainerHeight(String containerHeight) {
    this.containerHeight = containerHeight;
  }

  public String getContainerNetWeight() {
    return containerNetWeight;
  }

  public void setContainerNetWeight(String containerNetWeight) {
    this.containerNetWeight = containerNetWeight;
  }

  public String getContainerType() {
    return containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }

  public String getLineOneInfo() {
    return lineOneInfo;
  }

  public void setLineOneInfo(String lineOneInfo) {
    this.lineOneInfo = lineOneInfo;
  }

  public String getLineTwoInfo() {
    return lineTwoInfo;
  }

  public void setLineTwoInfo(String lineTwoInfo) {
    this.lineTwoInfo = lineTwoInfo;
  }

  public String getContainerSeal() {
    return containerSeal;
  }

  public void setContainerSeal(String containerSeal) {
    this.containerSeal = containerSeal;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "PrintEIRContainerInfoDTO [containerBayCode=" + containerBayCode + ", containerInOrOut=" + containerInOrOut
        + ", containerPositionOnTruck=" + containerPositionOnTruck + ", containerLine=" + containerLine
        + ", containerHeight=" + containerHeight + ", containerNetWeight=" + containerNetWeight + ", containerType="
        + containerType + ", lineOneInfo=" + lineOneInfo + ", lineTwoInfo=" + lineTwoInfo + ", containerSeal="
        + containerSeal + "]";
  }



}
