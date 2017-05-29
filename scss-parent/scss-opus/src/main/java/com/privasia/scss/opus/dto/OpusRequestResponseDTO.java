/**
 * 
 */
package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.Gson;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ReadWriteStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.util.DateUtil;

/**
 * @author Janaka
 *
 */
public class OpusRequestResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long opusReqResID;

	private String transactionType;

	private String gateInOut;

	private String impContainer01;

	private String impContainer02;

	private String oddImpContainer01;

	private String oddImpContainer02;

	private String expContainer01;

	private String expContainer02;

	private Long cardID;

	private LocalDateTime gateinTime;

	private String readWrite;

	private String request;

	private String response;

	private LocalDateTime sendTime;

	private LocalDateTime receivedTime;

	public OpusRequestResponseDTO() {
	}

	public OpusRequestResponseDTO(OpusGateInReadRequest gateInReadRequest, Gson gson, long cardId) {
		this.setRequest(gson.toJson(gateInReadRequest));
		this.setGateinTime(DateUtil.getLocalDateFromString(gateInReadRequest.getGateINDateTime()));
		this.setCardID(cardId);
		this.setImpContainer01(gateInReadRequest.getContainerNo1ImportCY());
		this.setImpContainer02(gateInReadRequest.getContainerNo2ImportCY());
		this.setExpContainer01(gateInReadRequest.getContainerNo1ExportCY());
		this.setExpContainer02(gateInReadRequest.getContainerNo2ExportCY());
		this.setTransactionType(this.determineTransactionType());
		this.setGateInOut(GateInOutStatus.IN.getValue());
		this.setReadWrite(ReadWriteStatus.READ.getValue());
	}

	public OpusRequestResponseDTO(OpusGateInWriteRequest opusGateInWriteRequest, Gson gson, long cardId) {
		this.setRequest(gson.toJson(opusGateInWriteRequest));
		this.setGateinTime(DateUtil.getLocalDateFromString(opusGateInWriteRequest.getGateINDateTime()));
		this.setCardID(cardId);

		if (!(opusGateInWriteRequest.getImportContainerListCY() == null
				|| opusGateInWriteRequest.getImportContainerListCY().isEmpty())) {
			GIWriteRequestImportContainer importContainer01 = opusGateInWriteRequest.getImportContainerListCY().get(0);
			this.setImpContainer01(importContainer01.getContainerNo());
			if (opusGateInWriteRequest.getImportContainerListCY().size() > 1) {
				GIWriteRequestImportContainer importContainer02 = opusGateInWriteRequest.getImportContainerListCY()
						.get(1);
				this.setImpContainer02(importContainer02.getContainerNo());
			}
		}

		if (!(opusGateInWriteRequest.getExportContainerListCY() == null
				|| opusGateInWriteRequest.getExportContainerListCY().isEmpty())) {
			GIWriteRequestExportContainer exportContainer01 = opusGateInWriteRequest.getExportContainerListCY().get(0);
			this.setExpContainer01(exportContainer01.getContainerNo());
			if (opusGateInWriteRequest.getExportContainerListCY().size() > 1) {
				GIWriteRequestExportContainer exportContainer02 = opusGateInWriteRequest.getExportContainerListCY()
						.get(1);
				this.setExpContainer02(exportContainer02.getContainerNo());
			}
		}
		this.setTransactionType(this.determineTransactionType());
		this.setGateInOut(GateInOutStatus.IN.getValue());
		this.setReadWrite(ReadWriteStatus.WRITE.getValue());
	}

	public OpusRequestResponseDTO(OpusGateOutReadRequest gateOutReadRequest, Gson gson, Long cardID) {
		this.setRequest(gson.toJson(gateOutReadRequest));
		this.setGateinTime(DateUtil.getLocalDateFromString(gateOutReadRequest.getGateOUTDateTime()));
		this.setCardID(cardID);
		this.setImpContainer01(gateOutReadRequest.getContainerNo1ImportCY());
		this.setImpContainer02(gateOutReadRequest.getContainerNo2ImportCY());
		this.setExpContainer01(gateOutReadRequest.getContainerNo1ExportCY());
		this.setExpContainer02(gateOutReadRequest.getContainerNo2ExportCY());
		this.setOddImpContainer01(gateOutReadRequest.getContainerNo1ImportWHODD());
		this.setOddImpContainer02(gateOutReadRequest.getContainerNo2ImportWHODD());
		this.setGateInOut(GateInOutStatus.OUT.getValue());
		this.setReadWrite(ReadWriteStatus.READ.getValue());
		this.setTransactionType(this.determineTransactionType());
	}

	public OpusRequestResponseDTO(OpusGateOutWriteRequest opusGateOutWriteRequest, Gson gson, Long cardID) {
		this.setRequest(gson.toJson(opusGateOutWriteRequest));
		this.setGateinTime(DateUtil.getLocalDateFromString(opusGateOutWriteRequest.getGateOUTDateTime()));
		this.setCardID(cardID);

		if (!(opusGateOutWriteRequest.getImportContainerListCY() == null
				|| opusGateOutWriteRequest.getImportContainerListCY().isEmpty())) {
			GOWriteRequestImportContainer importContainer01 = opusGateOutWriteRequest.getImportContainerListCY().get(0);
			this.setImpContainer01(importContainer01.getContainerNo());
			if (opusGateOutWriteRequest.getImportContainerListCY().size() > 1) {
				GOWriteRequestImportContainer importContainer02 = opusGateOutWriteRequest.getImportContainerListCY()
						.get(1);
				this.setImpContainer02(importContainer02.getContainerNo());
			}
		}

		if (!(opusGateOutWriteRequest.getExportContainerListCY() == null
				|| opusGateOutWriteRequest.getExportContainerListCY().isEmpty())) {
			GOWriteRequestExportContainer exportContainer01 = opusGateOutWriteRequest.getExportContainerListCY().get(0);
			this.setExpContainer01(exportContainer01.getContainerNo());
			if (opusGateOutWriteRequest.getExportContainerListCY().size() > 1) {
				GOWriteRequestExportContainer exportContainer02 = opusGateOutWriteRequest.getExportContainerListCY()
						.get(1);
				this.setExpContainer02(exportContainer02.getContainerNo());
			}
		}
		this.setTransactionType(this.determineTransactionType());
		this.setGateInOut(GateInOutStatus.OUT.getValue());
		this.setReadWrite(ReadWriteStatus.WRITE.getValue());
	}

	public String determineTransactionType() {
		boolean isExportPresent = false;
		boolean isImportPresent = false;
		boolean isODDImportPresent = false;
		if (StringUtils.isNotEmpty(this.getExpContainer01()) || StringUtils.isNotEmpty(this.getExpContainer02())) {
			isExportPresent = true;
		}
		if (StringUtils.isNotEmpty(this.getImpContainer01()) || StringUtils.isNotEmpty(this.getImpContainer02())) {
			isImportPresent = true;
		}
		
		if (StringUtils.isNotEmpty(this.getOddImpContainer01()) || StringUtils.isNotEmpty(this.getOddImpContainer02())) {
			isODDImportPresent = true;
		} 
		
		if (isExportPresent && isImportPresent) {
			return TransactionType.IMPORT_EXPORT.getValue();
		}

		if (isExportPresent) {
			return TransactionType.EXPORT.getValue();
		}

		if (isImportPresent) {
			return TransactionType.IMPORT.getValue();
		}
		
		if (isODDImportPresent) {
			return TransactionType.ODD_IMPORT.getValue();
		}
		return null;
	}

	public Long getOpusReqResID() {
		return opusReqResID;
	}

	public void setOpusReqResID(Long opusReqResID) {
		this.opusReqResID = opusReqResID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(String gateInOut) {
		this.gateInOut = gateInOut;
	}

	public String getImpContainer01() {
		return impContainer01;
	}

	public void setImpContainer01(String impContainer01) {
		this.impContainer01 = impContainer01;
	}

	public String getImpContainer02() {
		return impContainer02;
	}

	public void setImpContainer02(String impContainer02) {
		this.impContainer02 = impContainer02;
	}

	public String getExpContainer01() {
		return expContainer01;
	}

	public void setExpContainer01(String expContainer01) {
		this.expContainer01 = expContainer01;
	}

	public String getExpContainer02() {
		return expContainer02;
	}

	public void setExpContainer02(String expContainer02) {
		this.expContainer02 = expContainer02;
	}

	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}

	public LocalDateTime getGateinTime() {
		return gateinTime;
	}

	public void setGateinTime(LocalDateTime gateinTime) {
		this.gateinTime = gateinTime;
	}

	public String getReadWrite() {
		return readWrite;
	}

	public void setReadWrite(String readWrite) {
		this.readWrite = readWrite;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public LocalDateTime getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(LocalDateTime receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getOddImpContainer01() {
		return oddImpContainer01;
	}

	public void setOddImpContainer01(String oddImpContainer01) {
		this.oddImpContainer01 = oddImpContainer01;
	}

	public String getOddImpContainer02() {
		return oddImpContainer02;
	}

	public void setOddImpContainer02(String oddImpContainer02) {
		this.oddImpContainer02 = oddImpContainer02;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
