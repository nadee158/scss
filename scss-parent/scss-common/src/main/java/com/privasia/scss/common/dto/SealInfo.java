package com.privasia.scss.common.dto;

import java.io.Serializable;

public class SealInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String sealOrigin = "";
  private String sealType = "";
  private String sealNo = "";

  public String getSealOrigin() {
    return sealOrigin;
  }

  public void setSealOrigin(String sealOrigin) {
    this.sealOrigin = sealOrigin;
  }

  public String getSealType() {
    return sealType;
  }

  public void setSealType(String sealType) {
    this.sealType = sealType;
  }

  public String getSealNo() {
    return sealNo;
  }

  public void setSealNo(String sealNo) {
    this.sealNo = sealNo;
  }



}
