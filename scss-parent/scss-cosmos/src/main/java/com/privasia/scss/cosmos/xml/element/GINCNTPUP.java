package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GINCNTPUP implements Serializable {

  private static final long serialVersionUID = 1L;
  String MSGTSE;
  String UNITSE;
  String UNBTSE;
  String CNPVSE;
  String UPLKSE;
  String UPPKSE;
  String UPOMSE;
  String CYOISE;
  String CYCISE;
  String ACHISE;
  String PCHISE;
  String CRORSE;

  public String getMSGTSE() {
    return MSGTSE;
  }

  @XmlElement
  public void setMSGTSE(String mSGTSE) {
    MSGTSE = mSGTSE;
  }

  public String getUNITSE() {
    return UNITSE;
  }

  @XmlElement
  public void setUNITSE(String uNITSE) {
    UNITSE = uNITSE;
  }

  public String getUNBTSE() {
    return UNBTSE;
  }

  @XmlElement
  public void setUNBTSE(String uNBTSE) {
    UNBTSE = uNBTSE;
  }

  public String getCNPVSE() {
    return CNPVSE;
  }

  @XmlElement
  public void setCNPVSE(String cNPVSE) {
    CNPVSE = cNPVSE;
  }

  public String getUPLKSE() {
    return UPLKSE;
  }

  @XmlElement
  public void setUPLKSE(String uPLKSE) {
    UPLKSE = uPLKSE;
  }

  public String getUPPKSE() {
    return UPPKSE;
  }

  @XmlElement
  public void setUPPKSE(String uPPKSE) {
    UPPKSE = uPPKSE;
  }

  public String getUPOMSE() {
    return UPOMSE;
  }

  @XmlElement
  public void setUPOMSE(String uPOMSE) {
    UPOMSE = uPOMSE;
  }

  public String getCYOISE() {
    return CYOISE;
  }

  @XmlElement
  public void setCYOISE(String cYOISE) {
    CYOISE = cYOISE;
  }

  public String getCYCISE() {
    return CYCISE;
  }

  @XmlElement
  public void setCYCISE(String cYCISE) {
    CYCISE = cYCISE;
  }

  public String getACHISE() {
    return ACHISE;
  }

  @XmlElement
  public void setACHISE(String aCHISE) {
    ACHISE = aCHISE;
  }

  public String getPCHISE() {
    return PCHISE;
  }

  @XmlElement
  public void setPCHISE(String pCHISE) {
    PCHISE = pCHISE;
  }

  public String getCRORSE() {
    return CRORSE;
  }

  @XmlElement
  public void setCRORSE(String cRORSE) {
    CRORSE = cRORSE;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
