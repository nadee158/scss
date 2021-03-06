package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlType(propOrder = {"UNITSE", "SO01SE", "ST01SE", "SN01SE", "SO02SE", "ST02SE","SN02SE"})
public class GOTCNTINF implements Serializable {

  private static final long serialVersionUID = 1L;

  private String UNITSE;

  private String SO01SE;
  private String ST01SE;
  private String SN01SE;

  private String SO02SE;
  private String ST02SE;
  private String SN02SE;

  public String getUNITSE() {
    return UNITSE;
  }

  @XmlElement
  public void setUNITSE(String uNITSE) {
    UNITSE = uNITSE;
  }

  public String getSO01SE() {
    return SO01SE;
  }

  @XmlElement
  public void setSO01SE(String sO01SE) {
    SO01SE = sO01SE;
  }

  public String getST01SE() {
    return ST01SE;
  }

  @XmlElement
  public void setST01SE(String sT01SE) {
    ST01SE = sT01SE;
  }

  public String getSN01SE() {
    return SN01SE;
  }

  @XmlElement
  public void setSN01SE(String sN01SE) {
    SN01SE = sN01SE;
  }

  public String getSO02SE() {
    return SO02SE;
  }

  @XmlElement
  public void setSO02SE(String sO02SE) {
    SO02SE = sO02SE;
  }

  public String getST02SE() {
    return ST02SE;
  }

  @XmlElement
  public void setST02SE(String sT02SE) {
    ST02SE = sT02SE;
  }

  public String getSN02SE() {
    return SN02SE;
  }

  @XmlElement
  public void setSN02SE(String sN02SE) {
    SN02SE = sN02SE;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
