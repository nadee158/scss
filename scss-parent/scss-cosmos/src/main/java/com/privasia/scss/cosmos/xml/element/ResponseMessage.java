package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResponseMessage implements Serializable {

  private static final long serialVersionUID = 1L;
  private int Index;
  private CSMCTL CSMCTL;
  private GINTRCINFR GINTRCINFR;
  private GOTTRCINF GOTTRCINF;
  private GINCNTPUPR GINCNTPUPR;
  private GINCNTDRPR GINCNTDRPR;

  public int getIndex() {
    return Index;
  }

  @XmlAttribute
  public void setIndex(int index) {
    Index = index;
  }

  public CSMCTL getCSMCTL() {
    return CSMCTL;
  }

  public void setCSMCTL(CSMCTL cSMCTL) {
    CSMCTL = cSMCTL;
  }



  public GINCNTDRPR getGINCNTDRPR() {
    return GINCNTDRPR;
  }

  public void setGINCNTDRPR(GINCNTDRPR gINCNTDRPR) {
    GINCNTDRPR = gINCNTDRPR;
  }

  public GINCNTPUPR getGINCNTPUPR() {
    return GINCNTPUPR;
  }

  public void setGINCNTPUPR(GINCNTPUPR gINCNTPUPR) {
    GINCNTPUPR = gINCNTPUPR;
  }

  public GINTRCINFR getGINTRCINFR() {
    return GINTRCINFR;
  }

  public void setGINTRCINFR(GINTRCINFR gINTRCINFR) {
    GINTRCINFR = gINTRCINFR;
  }

  public GOTTRCINF getGOTTRCINF() {
    return GOTTRCINF;
  }

  public void setGOTTRCINF(GOTTRCINF gOTTRCINF) {
    GOTTRCINF = gOTTRCINF;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
