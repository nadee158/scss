package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class CosmosGateOutWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<CosmosGateOutImport> importList;

  public List<CosmosGateOutImport> getImportList() {
    return importList;
  }

  @XmlElement(name = "Message")
  public void setImportList(List<CosmosGateOutImport> importList) {
    this.importList = importList;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
