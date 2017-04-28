package com.privasia.scss.cosmos.dto.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GINCNTDRPR implements Serializable {

  private static final long serialVersionUID = 1L;
  String PSIDSE;
  String PKIDSE;

  public String getPSIDSE() {
    return PSIDSE;
  }

  @XmlElement
  public void setPSIDSE(String pSIDSE) {
    PSIDSE = pSIDSE;
  }

  public String getPKIDSE() {
    return PKIDSE;
  }

  @XmlElement
  public void setPKIDSE(String pKIDSE) {
    PKIDSE = pKIDSE;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
