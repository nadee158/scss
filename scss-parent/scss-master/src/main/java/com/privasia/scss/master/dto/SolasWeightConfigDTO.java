package com.privasia.scss.master.dto;

import java.io.Serializable;

public class SolasWeightConfigDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long solasWegConfigID;

  private String weightType;

  private int minValue;

  private int maxValue;

  private int defaultValue;

  private int weightTypeSize;

  public Long getSolasWegConfigID() {
    return solasWegConfigID;
  }

  public void setSolasWegConfigID(Long solasWegConfigID) {
    this.solasWegConfigID = solasWegConfigID;
  }

  public String getWeightType() {
    return weightType;
  }

  public void setWeightType(String weightType) {
    this.weightType = weightType;
  }

  public int getMinValue() {
    return minValue;
  }

  public void setMinValue(int minValue) {
    this.minValue = minValue;
  }

  public int getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(int maxValue) {
    this.maxValue = maxValue;
  }

  public int getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(int defaultValue) {
    this.defaultValue = defaultValue;
  }

  public int getWeightTypeSize() {
    return weightTypeSize;
  }

  public void setWeightTypeSize(int weightTypeSize) {
    this.weightTypeSize = weightTypeSize;
  }



}
