package com.privasia.scss.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.privasia.scss.core.model.ISOCode;

public class ISOInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int length;

  private BigDecimal height;

  private String type;

  private int tareWeight;

  private int totalWeightBridge;

  private int width;


  public ISOInfo(ISOCode code) {
    super();
    if (!(code == null)) {
      this.setLength(code.getLength());
      this.setHeight(code.getHeight());
      this.setType(code.getType());
      this.setTareWeight(code.getTareWeight());
      this.setTotalWeightBridge(code.getTareWeight());
    }
  }

  public ISOInfo() {
    super();
  }


  public int getLength() {
    return length;
  }


  public void setLength(int length) {
    this.length = length;
  }


  public BigDecimal getHeight() {
    return height;
  }


  public void setHeight(BigDecimal height) {
    this.height = height;
  }


  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public int getTareWeight() {
    return tareWeight;
  }


  public void setTareWeight(int tareWeight) {
    this.tareWeight = tareWeight;
  }


  public int getTotalWeightBridge() {
    return totalWeightBridge;
  }


  public void setTotalWeightBridge(int totalWeightBridge) {
    this.totalWeightBridge = totalWeightBridge;
  }


  public int getWidth() {
    return width;
  }


  public void setWidth(int width) {
    this.width = width;
  }



}
