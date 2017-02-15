package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.GateInOutStatus;

@Embeddable
public class PrintEIRContainerInfo extends CommonContainerAttribute implements Serializable {

  /**
   * 
   */

  private static final long serialVersionUID = 1L;

  private String containerBayCode;

  @Type(type = "com.privasia.scss.common.enumusertype.GateInOutStatusEnumUserType")
  private GateInOutStatus containerInOrOut;

  private String containerPositionOnTruck;

  private String containerLine;

  private String containerHeight;

  private String containerNetWeight;

  private String containerType;

  private String lineOneInfo;

  private String lineTwoInfo;

  private String containerSeal;

  private ContainerSize containerLength;

  public String getContainerBayCode() {
    return containerBayCode;
  }

  public void setContainerBayCode(String containerBayCode) {
    this.containerBayCode = containerBayCode;
  }

  public GateInOutStatus getContainerInOrOut() {
    return containerInOrOut;
  }

  public void setContainerInOrOut(GateInOutStatus containerInOrOut) {
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

  public String getContainerSeal() {
    return containerSeal;
  }

  public void setContainerSeal(String containerSeal) {
    this.containerSeal = containerSeal;
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

  public ContainerSize getContainerLength() {
    return containerLength;
  }

  public void setContainerLength(ContainerSize containerLength) {
    this.containerLength = containerLength;
  }

}
