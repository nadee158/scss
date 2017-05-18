package com.privasia.scss.cosmos.dto.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement(name = "SGS2CosmosRequest")
public class SGS2Cosmos implements Serializable {

  private static final long serialVersionUID = 1L;
  List<Message> message;

  public List<Message> getMessage() {
    return message;
  }

  @XmlElement(name = "Message")
  public void setMessage(List<Message> message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
