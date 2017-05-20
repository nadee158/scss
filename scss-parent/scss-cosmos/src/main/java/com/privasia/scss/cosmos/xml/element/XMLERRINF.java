package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class XMLERRINF implements Serializable {

  private static final long serialVersionUID = 1L;
  String TAGCSG;

  public String getTAGCSG() {
    return TAGCSG;
  }

  @XmlElement
  public void setTAGCSG(String tAGCSG) {
    TAGCSG = tAGCSG;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
