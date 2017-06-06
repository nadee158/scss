package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlType(propOrder = {"RQST", "ACTN", "RTNC", "ERRI", "RQDS", "RTNM", "USID", "RQUI", "TRMC"})
public class CSMCTL implements Serializable {

  private static final long serialVersionUID = 1L;
  private String RQST;
  private String ACTN;
  private String RTNC;
  private String ERRI;
  private String RQDS;
  private String RTNM;
  private String USID;
  private String RQUI;
  private String TRMC;

  public String getRQST() {
    return RQST;
  }

  @XmlElement
  public void setRQST(String rQST) {
    RQST = rQST;
  }

  public String getACTN() {
    return ACTN;
  }

  @XmlElement
  public void setACTN(String aCTN) {
    ACTN = aCTN;
  }

  public String getRTNC() {
    return RTNC;
  }

  @XmlElement
  public void setRTNC(String rTNC) {
    RTNC = rTNC;
  }

  public String getRQDS() {
    return RQDS;
  }

  @XmlElement
  public void setRQDS(String rQDS) {
    RQDS = rQDS;
  }

  public String getRTNM() {
    return RTNM;
  }

  @XmlElement
  public void setRTNM(String rTNM) {
    RTNM = rTNM;
  }

  public String getUSID() {
    return USID;
  }

  @XmlElement
  public void setUSID(String uSID) {
    USID = uSID;
  }

  public String getRQUI() {
    return RQUI;
  }

  @XmlElement
  public void setRQUI(String rQUI) {
    RQUI = rQUI;
  }

  public String getTRMC() {
    return TRMC;
  }

  @XmlElement
  public void setTRMC(String tRMC) {
    TRMC = tRMC;
  }

  public String getERRI() {
    return ERRI;
  }

  @XmlElement
  public void setERRI(String eRRI) {
    ERRI = eRRI;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
