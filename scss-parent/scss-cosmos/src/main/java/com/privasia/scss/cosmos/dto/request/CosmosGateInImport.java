package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.xml.element.CSMCTL;
import com.privasia.scss.cosmos.xml.element.GINCNTPUP;
import com.privasia.scss.cosmos.xml.element.GOTTRCINF;

public class CosmosGateInImport implements Serializable {

  private static final long serialVersionUID = 1L;
  CSMCTL CSMCTL;
  GOTTRCINF GOTTRCINF;
  GINCNTPUP GINCNTPUP;
  int Index;

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

  public GINCNTPUP getGINCNTPUP() {
    return GINCNTPUP;
  }

  @XmlElement
  public void setGINCNTPUP(GINCNTPUP gINCNTPUP) {
    GINCNTPUP = gINCNTPUP;
  }

  public int getIndex() {
    return Index;
  }

  @XmlAttribute
  public void setIndex(int index) {
    Index = index;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
