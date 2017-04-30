package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement(name = "SGS2Cosmos")
public class CosmosGateInWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  List<CosmosGateInExport> exportList;

  List<CosmosGateInImport> importList;

  public List<CosmosGateInExport> getExportList() {
    return exportList;
  }

  @XmlElement(name = "Message")
  public void setExportList(List<CosmosGateInExport> exportList) {
    this.exportList = exportList;
  }

  public List<CosmosGateInImport> getImportList() {
    return importList;
  }

  @XmlElement(name = "Message")
  public void setImportList(List<CosmosGateInImport> importList) {
    this.importList = importList;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
