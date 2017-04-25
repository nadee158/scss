package com.privasia.scss.cosmos.dto.request;

import javax.xml.bind.annotation.XmlElement;

public class GOTTRCINF {

  String MSGTSC;
  String LANESC;
  String VMIDSC;
  String ATDDSC;
  String ATDTSC;

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

}
