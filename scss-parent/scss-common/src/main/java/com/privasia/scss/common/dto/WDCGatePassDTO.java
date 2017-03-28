/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Janaka
 *
 */
public class WDCGatePassDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long gatePassNO;
	
	private String gcsDelcarerNo;
	
	private Long containerLength;
	
	private String gcsBlock;
	
	private String pkfzBlock;
	
	private String lpkBlock;
	
	private LocalDateTime cusGCSReleaseDate;
	
	private LocalDateTime gatePassIssued;
	
	private LocalDateTime portSecurity;
	
	private WDCGateOrderDTO gateOrderDTO;

	public Long getGatePassNO() {
		return gatePassNO;
	}

	public void setGatePassNO(Long gatePassNO) {
		this.gatePassNO = gatePassNO;
	}

	public String getGcsDelcarerNo() {
		return gcsDelcarerNo;
	}

	public void setGcsDelcarerNo(String gcsDelcarerNo) {
		this.gcsDelcarerNo = gcsDelcarerNo;
	}

	public Long getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(Long containerLength) {
		this.containerLength = containerLength;
	}

	public String getGcsBlock() {
		return gcsBlock;
	}

	public void setGcsBlock(String gcsBlock) {
		this.gcsBlock = gcsBlock;
	}

	public String getPkfzBlock() {
		return pkfzBlock;
	}

	public void setPkfzBlock(String pkfzBlock) {
		this.pkfzBlock = pkfzBlock;
	}

	public String getLpkBlock() {
		return lpkBlock;
	}

	public void setLpkBlock(String lpkBlock) {
		this.lpkBlock = lpkBlock;
	}

	public LocalDateTime getCusGCSReleaseDate() {
		return cusGCSReleaseDate;
	}

	public void setCusGCSReleaseDate(LocalDateTime cusGCSReleaseDate) {
		this.cusGCSReleaseDate = cusGCSReleaseDate;
	}

	public WDCGateOrderDTO getGateOrderDTO() {
		return gateOrderDTO;
	}

	public void setGateOrderDTO(WDCGateOrderDTO gateOrderDTO) {
		this.gateOrderDTO = gateOrderDTO;
	}

	public LocalDateTime getGatePassIssued() {
		return gatePassIssued;
	}

	public void setGatePassIssued(LocalDateTime gatePassIssued) {
		this.gatePassIssued = gatePassIssued;
	}

	public LocalDateTime getPortSecurity() {
		return portSecurity;
	}

	public void setPortSecurity(LocalDateTime portSecurity) {
		this.portSecurity = portSecurity;
	}

	
}
