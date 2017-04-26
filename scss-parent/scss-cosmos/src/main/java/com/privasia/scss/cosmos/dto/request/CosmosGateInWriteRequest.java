package com.privasia.scss.cosmos.dto.request;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SGS2Cosmos")
public class CosmosGateInWriteRequest {

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

}
