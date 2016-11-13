package com.privasia.scss.core.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class ISOInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String code = StringUtils.EMPTY;
  private String desc = StringUtils.EMPTY;
  private String length = StringUtils.EMPTY;
  private String height = StringUtils.EMPTY;
  private String type = StringUtils.EMPTY;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
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



}
