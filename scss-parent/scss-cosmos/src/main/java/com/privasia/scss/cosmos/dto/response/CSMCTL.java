package com.privasia.scss.cosmos.dto.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CSMCTL implements Serializable {

  private static final long serialVersionUID = 1L;
  String ERRI;

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
