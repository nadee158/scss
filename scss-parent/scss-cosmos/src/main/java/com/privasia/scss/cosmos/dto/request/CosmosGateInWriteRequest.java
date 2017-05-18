package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.xml.element.GINTRCINF;

public class CosmosGateInWriteRequest extends CosmosGateWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private GINTRCINF GINTRCINF;
	private List<CosmosGateInExport> exportList;
	private List<CosmosGateInImport> importList;

	public GINTRCINF getGINTRCINF() {
		return GINTRCINF;
	}

	@XmlElement(name = "Message")
	public void setGINTRCINF(GINTRCINF gINTRCINF) {
		GINTRCINF = gINTRCINF;
	}

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
