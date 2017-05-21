package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.xml.element.CSMCTL;
import com.privasia.scss.cosmos.xml.element.GOTCNTINF;

public class CosmosGateOutImport implements Serializable {

  private static final long serialVersionUID = 1L;
  private CSMCTL CSMCTL;
  private GOTCNTINF GOTCNTINF;
  int Index;

  public CSMCTL getCSMCTL() {
    return CSMCTL;
  }

  @XmlElement
  public void setCSMCTL(CSMCTL cSMCTL) {
    CSMCTL = cSMCTL;
  }

  public GOTCNTINF getGOTCNTINF() {
    return GOTCNTINF;
  }

  @XmlElement
  public void setGOTCNTINF(GOTCNTINF gOTCNTINF) {
    GOTCNTINF = gOTCNTINF;
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
