package com.privasia.scss.common.dto;

import java.io.Serializable;

public class DamageCodeDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String damageCode;

  private String damageDesc;

  public String getDamageCode() {
    return damageCode;
  }

  public void setDamageCode(String damageCode) {
    this.damageCode = damageCode;
  }

  public String getDamageDesc() {
    return damageDesc;
  }

  public void setDamageDesc(String damageDesc) {
    this.damageDesc = damageDesc;
  }

  @Override
  public String toString() {
    return "DamageCodeDTO [damageCode=" + damageCode + ", damageDesc=" + damageDesc + "]";
  }



}
