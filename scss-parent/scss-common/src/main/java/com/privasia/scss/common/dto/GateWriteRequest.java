/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Janaka
 *
 */
public class GateWriteRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName = StringUtils.EMPTY;
	private String laneNo = StringUtils.EMPTY;// 
	private String haulageCode = StringUtils.EMPTY;// 
	private String truckPlateNo = StringUtils.EMPTY;// 
	private String truckHeadNo;
	private int cosmosPort;
	private List<ExportContainer> exportContainers;
	private List<ImportContainer> importContainers;
	private List<WHoddDTO> whoddContainers;
	
	@NotNull(message = "cardId is required!")
	private Long cardID;
	
	private String gateInStatus;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLaneNo() {
		return laneNo;
	}
	
	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

	public String getHaulageCode() {
		return haulageCode;
	}

	public void setHaulageCode(String haulageCode) {
		this.haulageCode = haulageCode;
	}

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}
	
	public String getTruckHeadNo() {
		return truckHeadNo;
	}

	public void setTruckHeadNo(String truckHeadNo) {
		this.truckHeadNo = truckHeadNo;
	}
	
	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}
	
	public int getCosmosPort() {
		return cosmosPort;
	}

	public void setCosmosPort(int cosmosPort) {
		this.cosmosPort = cosmosPort;
	}
	
	public List<ExportContainer> getExportContainers() {
		return exportContainers;
	}

	public void setExportContainers(List<ExportContainer> exportContainers) {
		this.exportContainers = exportContainers;
	}

	public List<ImportContainer> getImportContainers() {
		return importContainers;
	}

	public void setImportContainers(List<ImportContainer> importContainers) {
		this.importContainers = importContainers;
	}

	public List<WHoddDTO> getWhoddContainers() {
		return whoddContainers;
	}

	public void setWhoddContainers(List<WHoddDTO> whoddContainers) {
		this.whoddContainers = whoddContainers;
	}
	
	public String getGateInStatus() {
		return gateInStatus;
	}

	public void setGateInStatus(String gateInStatus) {
		this.gateInStatus = gateInStatus;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	

}
