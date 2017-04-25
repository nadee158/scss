package com.privasia.scss.cosmos.dto.request;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SGS2Cosmos")
public class CosmosGateOutWriteRequest {

	private List<CosmosGateOutExport> exportList;

	private List<CosmosGateOutImport> importList;

	public List<CosmosGateOutExport> getExportList() {
		return exportList;
	}

	@XmlElement(name = "Message")
	public void setExportList(List<CosmosGateOutExport> exportList) {
		this.exportList = exportList;
	}

	public List<CosmosGateOutImport> getImportList() {
		return importList;
	}

	@XmlElement(name = "Message")
	public void setImportList(List<CosmosGateOutImport> importList) {
		this.importList = importList;
	}

}
