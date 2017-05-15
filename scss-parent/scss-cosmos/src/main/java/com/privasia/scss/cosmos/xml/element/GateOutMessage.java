package com.privasia.scss.cosmos.xml.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class GateOutMessage {

  private CSMCTL CSMCTL;
  private GOTTRCINF GOTTRCINF;
  private int Index;

  public CSMCTL getCSMCTL() {
    return CSMCTL;
  }

  @XmlElement
  public void setCSMCTL(CSMCTL cSMCTL) {
    CSMCTL = cSMCTL;
  }

  public GOTTRCINF getGOTTRCINF() {
    return GOTTRCINF;
  }

  @XmlElement
  public void setGOTTRCINF(GOTTRCINF gOTTRCINF) {
    GOTTRCINF = gOTTRCINF;
  }

  public int getIndex() {
    return Index;
  }

  @XmlAttribute
  public void setIndex(int index) {
    Index = index;
  }



}
