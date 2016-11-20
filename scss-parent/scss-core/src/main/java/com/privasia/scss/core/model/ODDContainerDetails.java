package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.HDBSArrivalStatus;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.enums.TransactionStatus;

@Embeddable
public class ODDContainerDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String containerNo;

	private String location;

	private String remarks;
	
	@Type(type = "com.privasia.scss.core.util.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus oddStatus;

	private String rejectionReason;

	@Type(type = "com.privasia.scss.core.util.enumusertype.GateInOutStatusEnumUserType")
	private ContainerFullEmptyType fullOrEmpty;

	@Type(type = "com.privasia.scss.core.util.enumusertype.ContainerSizeEnumUserType")
	private ContainerSize containerSize;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "oddIdSeq", cascade = CascadeType.MERGE)
	private HDBSBkgDetail hdbsBkgDetailNo;

	@Type(type = "com.privasia.scss.core.util.enumusertype.HDBSStatusEnumUserType")
	private HDBSStatus hdbsStatus;

	@Type(type = "com.privasia.scss.core.util.enumusertype.HDBSArrivalStatusEnumUserType")
	private HDBSArrivalStatus hdbsArrivalStatus;

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public TransactionStatus getOddStatus() {
		return oddStatus;
	}

	public void setOddStatus(TransactionStatus oddStatus) {
		this.oddStatus = oddStatus;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public ContainerFullEmptyType getFullOrEmpty() {
		return fullOrEmpty;
	}

	public void setFullOrEmpty(ContainerFullEmptyType fullOrEmpty) {
		this.fullOrEmpty = fullOrEmpty;
	}

	public ContainerSize getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(ContainerSize containerSize) {
		this.containerSize = containerSize;
	}

	public HDBSBkgDetail getHdbsBkgDetailNo() {
		return hdbsBkgDetailNo;
	}

	public void setHdbsBkgDetailNo(HDBSBkgDetail hdbsBkgDetailNo) {
		this.hdbsBkgDetailNo = hdbsBkgDetailNo;
	}

	public HDBSStatus getHdbsStatus() {
		return hdbsStatus;
	}

	public void setHdbsStatus(HDBSStatus hdbsStatus) {
		this.hdbsStatus = hdbsStatus;
	}

	public HDBSArrivalStatus getHdbsArrivalStatus() {
		return hdbsArrivalStatus;
	}

	public void setHdbsArrivalStatus(HDBSArrivalStatus hdbsArrivalStatus) {
		this.hdbsArrivalStatus = hdbsArrivalStatus;
	}

}
