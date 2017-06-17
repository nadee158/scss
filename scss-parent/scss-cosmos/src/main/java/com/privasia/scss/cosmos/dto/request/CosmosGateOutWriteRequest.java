package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.xml.element.SGS2CosmosRequest;

@XmlRootElement(name = "SGS2Cosmos")
public class CosmosGateOutWriteRequest extends SGS2CosmosRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  // import - both importList and requestMessage available
  // export - importlist is null - only requestMessage is available
  // importexport - both importList and requestMessage available - additionally CosmosGateOutExport

  private List<CosmosGateOutImport> importList;

  private CosmosGateOutExport export;

  public List<CosmosGateOutImport> getImportList() {
    return importList;
  }

  @XmlElement(name = "Message")
  public void setImportList(List<CosmosGateOutImport> importList) {
    this.importList = importList;
  }



  public CosmosGateOutExport getExport() {
    return export;
  }

  @XmlElement(name = "Message")
  public void setExport(CosmosGateOutExport export) {
    this.export = export;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
