/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Janaka
 *
 */
public class SolasETPDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;
	private String certificateNo;
	private LocalDateTime gateInOK;
	private String issueBy;
	private String weighStation;
	private int weighingMethod = 1;
	private int terminalVGM;
	private int shipperVGM;
	private int grossWeight = 0;
	private int tolerance;
	private BigDecimal calculatedVariance;
	private int mgw;
	private String exportSEQ;
	private byte[] certificate;
	private String solasDetail;
	private String issuerNRIC;
	private String issuerId;
	private boolean c1WithInTolerance = true;
	private boolean c2WithInTolerance = true;
	private String etpC1ResponseCode;
	private String etpC1ResponseMessage;
	private String requestSendTime;
	private String responseReceivedTime;
	private String etpC2ResponseCode;
	private String etpC2ResponseMessage;
	private String solasRefNumber;
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public LocalDateTime getGateInOK() {
		return gateInOK;
	}
	public void setGateInOK(LocalDateTime gateInOK) {
		this.gateInOK = gateInOK;
	}
	public String getIssueBy() {
		return issueBy;
	}
	public void setIssueBy(String issueBy) {
		this.issueBy = issueBy;
	}
	public String getWeighStation() {
		return weighStation;
	}
	public void setWeighStation(String weighStation) {
		this.weighStation = weighStation;
	}
	public int getWeighingMethod() {
		return weighingMethod;
	}
	public void setWeighingMethod(int weighingMethod) {
		this.weighingMethod = weighingMethod;
	}
	public int getTerminalVGM() {
		return terminalVGM;
	}
	public void setTerminalVGM(int terminalVGM) {
		this.terminalVGM = terminalVGM;
	}
	public int getShipperVGM() {
		return shipperVGM;
	}
	public void setShipperVGM(int shipperVGM) {
		this.shipperVGM = shipperVGM;
	}
	public int getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(int grossWeight) {
		this.grossWeight = grossWeight;
	}
	public int getTolerance() {
		return tolerance;
	}
	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}
	public BigDecimal getCalculatedVariance() {
		return calculatedVariance;
	}
	public void setCalculatedVariance(BigDecimal calculatedVariance) {
		this.calculatedVariance = calculatedVariance;
	}
	public int getMgw() {
		return mgw;
	}
	public void setMgw(int mgw) {
		this.mgw = mgw;
	}
	public String getExportSEQ() {
		return exportSEQ;
	}
	public void setExportSEQ(String exportSEQ) {
		this.exportSEQ = exportSEQ;
	}
	public byte[] getCertificate() {
		return certificate;
	}
	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}
	public String getSolasDetail() {
		return solasDetail;
	}
	public void setSolasDetail(String solasDetail) {
		this.solasDetail = solasDetail;
	}
	public String getIssuerNRIC() {
		return issuerNRIC;
	}
	public void setIssuerNRIC(String issuerNRIC) {
		this.issuerNRIC = issuerNRIC;
	}
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public boolean isC1WithInTolerance() {
		return c1WithInTolerance;
	}
	public void setC1WithInTolerance(boolean c1WithInTolerance) {
		this.c1WithInTolerance = c1WithInTolerance;
	}
	public boolean isC2WithInTolerance() {
		return c2WithInTolerance;
	}
	public void setC2WithInTolerance(boolean c2WithInTolerance) {
		this.c2WithInTolerance = c2WithInTolerance;
	}
	public String getEtpC1ResponseCode() {
		return etpC1ResponseCode;
	}
	public void setEtpC1ResponseCode(String etpC1ResponseCode) {
		this.etpC1ResponseCode = etpC1ResponseCode;
	}
	public String getEtpC1ResponseMessage() {
		return etpC1ResponseMessage;
	}
	public void setEtpC1ResponseMessage(String etpC1ResponseMessage) {
		this.etpC1ResponseMessage = etpC1ResponseMessage;
	}
	public String getRequestSendTime() {
		return requestSendTime;
	}
	public void setRequestSendTime(String requestSendTime) {
		this.requestSendTime = requestSendTime;
	}
	public String getResponseReceivedTime() {
		return responseReceivedTime;
	}
	public void setResponseReceivedTime(String responseReceivedTime) {
		this.responseReceivedTime = responseReceivedTime;
	}
	public String getEtpC2ResponseCode() {
		return etpC2ResponseCode;
	}
	public void setEtpC2ResponseCode(String etpC2ResponseCode) {
		this.etpC2ResponseCode = etpC2ResponseCode;
	}
	public String getEtpC2ResponseMessage() {
		return etpC2ResponseMessage;
	}
	public void setEtpC2ResponseMessage(String etpC2ResponseMessage) {
		this.etpC2ResponseMessage = etpC2ResponseMessage;
	}
	public String getSolasRefNumber() {
		return solasRefNumber;
	}
	public void setSolasRefNumber(String solasRefNumber) {
		this.solasRefNumber = solasRefNumber;
	}
	
	
	

}
