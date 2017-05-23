package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ODDContainerDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String containerNo = StringUtils.EMPTY;

	private ODDLocationDTO location;

	private String remarks = StringUtils.EMPTY;

	private String oddStatus = StringUtils.EMPTY;

	private String rejectionReason = StringUtils.EMPTY;

	private Long rejectionReasonID;

	private String fullOrEmpty = StringUtils.EMPTY;

	private String containerSize = StringUtils.EMPTY;

	private String hdbsBkgDetailNoId;

	private String hdbsStatus;

	private String hdbsArrivalStatus;

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public ODDLocationDTO getLocation() {
		return location;
	}

	public void setLocation(ODDLocationDTO location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOddStatus() {
		return oddStatus;
	}

	public void setOddStatus(String oddStatus) {
		this.oddStatus = oddStatus;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getFullOrEmpty() {
		return fullOrEmpty;
	}

	public void setFullOrEmpty(String fullOrEmpty) {
		this.fullOrEmpty = fullOrEmpty;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getHdbsStatus() {
		return hdbsStatus;
	}

	public void setHdbsStatus(String hdbsStatus) {
		this.hdbsStatus = hdbsStatus;
	}

	public String getHdbsArrivalStatus() {
		return hdbsArrivalStatus;
	}

	public void setHdbsArrivalStatus(String hdbsArrivalStatus) {
		this.hdbsArrivalStatus = hdbsArrivalStatus;
	}

	public String getHdbsBkgDetailNoId() {
		return hdbsBkgDetailNoId;
	}

	public void setHdbsBkgDetailNoId(String hdbsBkgDetailNoId) {
		this.hdbsBkgDetailNoId = hdbsBkgDetailNoId;
	}

	public Long getRejectionReasonID() {
		return rejectionReasonID;
	}

	public void setRejectionReasonID(Long rejectionReasonID) {
		this.rejectionReasonID = rejectionReasonID;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
