/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.TransactionStatus;

/**
 * @author Janaka
 *
 */
@Embeddable
public class CustomContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String containerNumber;

	private String customRejection;

	@Type(type = "com.privasia.scss.common.enumusertype.ContainerFullEmptyTypeEnumUserType")
	private ContainerFullEmptyType containerFullOrEmpty;

	@Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus customEirStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "GATE_PASS_NO", nullable = true, referencedColumnName = "GTP_PASSNO")
	private GatePass gatePass;

	private String gcsDelcarerNo;

	private LocalDateTime cusGCSReleaseDate;

	private LocalDateTime portPoliceDate;

	private LocalDateTime gatePassIssuedDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "EXP_EXPORTNO_SEQ", nullable = true, referencedColumnName = "EXP_EXPORTNO_SEQ")
	private Exports export;
	
	private String oddLocation;

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getCustomRejection() {
		return customRejection;
	}

	public void setCustomRejection(String customRejection) {
		this.customRejection = customRejection;
	}

	public ContainerFullEmptyType getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(ContainerFullEmptyType containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

	public TransactionStatus getCustomEirStatus() {
		return customEirStatus;
	}

	public void setCustomEirStatus(TransactionStatus customEirStatus) {
		this.customEirStatus = customEirStatus;
	}

	public GatePass getGatePass() {
		return gatePass;
	}

	public void setGatePass(GatePass gatePass) {
		this.gatePass = gatePass;
	}

	public String getGcsDelcarerNo() {
		return gcsDelcarerNo;
	}

	public void setGcsDelcarerNo(String gcsDelcarerNo) {
		this.gcsDelcarerNo = gcsDelcarerNo;
	}

	public LocalDateTime getCusGCSReleaseDate() {
		return cusGCSReleaseDate;
	}

	public void setCusGCSReleaseDate(LocalDateTime cusGCSReleaseDate) {
		this.cusGCSReleaseDate = cusGCSReleaseDate;
	}

	public LocalDateTime getPortPoliceDate() {
		return portPoliceDate;
	}

	public void setPortPoliceDate(LocalDateTime portPoliceDate) {
		this.portPoliceDate = portPoliceDate;
	}

	public LocalDateTime getGatePassIssuedDate() {
		return gatePassIssuedDate;
	}

	public void setGatePassIssuedDate(LocalDateTime gatePassIssuedDate) {
		this.gatePassIssuedDate = gatePassIssuedDate;
	}

	public Exports getExport() {
		return export;
	}

	public void setExport(Exports export) {
		this.export = export;
	}

	public String getOddLocation() {
		return oddLocation;
	}

	public void setOddLocation(String oddLocation) {
		this.oddLocation = oddLocation;
	}
	
	

}
