package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GINTRCINFR implements Serializable {

  private static final long serialVersionUID = 1L;
  String BZKNSC;

  public String getBZKNSC() {
    return BZKNSC;
  }

  @XmlElement
  public void setBZKNSC(String bZKNSC) {
    BZKNSC = bZKNSC;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }



}
