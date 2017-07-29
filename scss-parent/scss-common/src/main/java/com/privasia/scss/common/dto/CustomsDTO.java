package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long exportIDSeq01;
	private Long exportIDSeq02;
	private Long gatePassIDSeq01;
	private Long gatePassIDSeq02;
	private Long oddIdSeq01;
	private Long oddIdSeq02;
	private Long gateOutClientId;
	private String fullOREmptyCon01; //F or E
	private String fullOREmptyCon02;
	private String transactionStatus;

	public Optional<Long> getExportIDSeq01() {
		return Optional.ofNullable(exportIDSeq01);
	}

	public void setExportIDSeq01(Long exportIDSeq01) {
		this.exportIDSeq01 = exportIDSeq01;
	}

	public Optional<Long> getExportIDSeq02() {
		return Optional.ofNullable(exportIDSeq02);
	}

	public void setExportIDSeq02(Long exportIDSeq02) {
		this.exportIDSeq02 = exportIDSeq02;
	}

	public Optional<Long> getGatePassIDSeq01() {
		return Optional.ofNullable(gatePassIDSeq01);
	}

	public void setGatePassIDSeq01(Long gatePassIDSeq01) {
		this.gatePassIDSeq01 = gatePassIDSeq01;
	}

	public Optional<Long> getGatePassIDSeq02() {
		return Optional.ofNullable(gatePassIDSeq02);
	}

	public void setGatePassIDSeq02(Long gatePassIDSeq02) {
		this.gatePassIDSeq02 = gatePassIDSeq02;
	}

	public Optional<Long> getOddIdSeq01() {
		return Optional.ofNullable(oddIdSeq01);
	}

	public void setOddIdSeq01(Long oddIdSeq01) {
		this.oddIdSeq01 = oddIdSeq01;
	}

	public Optional<Long> getOddIdSeq02() {
		return Optional.ofNullable(oddIdSeq02);
	}

	public void setOddIdSeq02(Long oddIdSeq02) {
		this.oddIdSeq02 = oddIdSeq02;
	}

	public Long getGateOutClientId() {
		return gateOutClientId;
	}

	public void setGateOutClientId(Long gateOutClientId) {
		this.gateOutClientId = gateOutClientId;
	}

	public Optional<String> getFullOREmptyCon01() {
		return Optional.ofNullable(fullOREmptyCon01);
	}

	public void setFullOREmptyCon01(String fullOREmptyCon01) {
		this.fullOREmptyCon01 = fullOREmptyCon01;
	}

	public Optional<String> getFullOREmptyCon02() {
		return Optional.ofNullable(fullOREmptyCon02);
	}

	public void setFullOREmptyCon02(String fullOREmptyCon02) {
		this.fullOREmptyCon02 = fullOREmptyCon02;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
