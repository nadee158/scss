package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.xml.element.GateOutMessage;

@XmlRootElement(name = "SGS2CosmosRequest")
public class CosmosGateOutWriteRequest extends CosmosGateWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private GateOutMessage gateOutMessage;

  private CosmosGateOutExport export;

  private List<CosmosGateOutImport> importList;

  public CosmosGateOutExport getExport() {
    return export;
  }


  public GateOutMessage getGateOutMessage() {
    return gateOutMessage;
  }

  @XmlElement(name = "Message")
  public void setGateOutMessage(GateOutMessage gateOutMessage) {
    this.gateOutMessage = gateOutMessage;
  }

  @XmlElement(name = "Message")
  public void setExport(CosmosGateOutExport export) {
    this.export = export;
  }

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
