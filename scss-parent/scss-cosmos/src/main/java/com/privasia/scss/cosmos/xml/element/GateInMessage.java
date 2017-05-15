package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GateInMessage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private CSMCTL CSMCTL;
  private GINTRCINF GINTRCINF;
  private int Index;

  public CSMCTL getCSMCTL() {
    return CSMCTL;
  }

  @XmlElement
  public void setCSMCTL(CSMCTL cSMCTL) {
    CSMCTL = cSMCTL;
  }

  public GINTRCINF getGINTRCINF() {
    return GINTRCINF;
  }

  @XmlElement
  public void setGINTRCINF(GINTRCINF gINTRCINF) {
    GINTRCINF = gINTRCINF;
  }

  public int getIndex() {
    return Index;
  }

  @XmlElement
  public void setIndex(int index) {
    Index = index;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


}
