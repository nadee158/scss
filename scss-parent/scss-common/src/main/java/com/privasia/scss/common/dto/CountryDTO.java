package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class CountryDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String code;

  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "CountryDTO [code=" + code + ", name=" + name + "]";
  }

  public CountryDTO initializeWithDefaultValues() {
    this.code = StringUtils.EMPTY;
    this.name = StringUtils.EMPTY;
    return this;
  }



}
