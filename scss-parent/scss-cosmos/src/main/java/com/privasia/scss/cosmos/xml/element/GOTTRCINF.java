package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlType(propOrder = {"MSGTSC", "LANESC", "VMIDSC", "ATDDSC", "ATDTSC", "VMYKSC"})
public class GOTTRCINF implements Serializable {

  private static final long serialVersionUID = 1L;
  private String MSGTSC;
  private String LANESC;
  private String VMIDSC;
  private String ATDDSC;
  private String ATDTSC;

  private String VMYKSC;

  public String getMSGTSC() {
    return MSGTSC;
  }

  @XmlElement
  public void setMSGTSC(String mSGTSC) {
    MSGTSC = mSGTSC;
  }

  public String getLANESC() {
    return LANESC;
  }

  @XmlElement
  public void setLANESC(String lANESC) {
    LANESC = lANESC;
  }

  public String getVMIDSC() {
    return VMIDSC;
  }

  @XmlElement
  public void setVMIDSC(String vMIDSC) {
    VMIDSC = vMIDSC;
  }

  public String getATDDSC() {
    return ATDDSC;
  }

  @XmlElement
  public void setATDDSC(String aTDDSC) {
    ATDDSC = aTDDSC;
  }

  public String getATDTSC() {
    return ATDTSC;
  }

  @XmlElement
  public void setATDTSC(String aTDTSC) {
    ATDTSC = aTDTSC;
  }

  public String getVMYKSC() {
    return VMYKSC;
  }

  @XmlElement
  public void setVMYKSC(String vMYKSC) {
    VMYKSC = vMYKSC;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }


}
