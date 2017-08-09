/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ContainerSize;

/**
 * @author Janaka
 *
 */
@Embeddable
public class KioskBoothContainerAttribute  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cancelPickup;

	private String location;

	private String status;

	private String others;

	private String rejectRemarks;

	private String customCheck;

	private String shipper;

	private String line;

	private String containerNumber;

	/*@Type(type = "com.privasia.scss.common.enumusertype.ContainerSizeEnumUserType")
	private ContainerSize containerLength;*/

	private String containerISOCode;

	@Type(type = "com.privasia.scss.common.enumusertype.ContainerFullEmptyTypeEnumUserType")
	private ContainerFullEmptyType containerFullOrEmpty;

	public KioskBoothContainerAttribute() {
		super();
	}

	public String getCancelPickup() {
		return cancelPickup;
	}

	public void setCancelPickup(String cancelPickup) {
		this.cancelPickup = cancelPickup;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getRejectRemarks() {
		return rejectRemarks;
	}

	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}

	public String getCustomCheck() {
		return customCheck;
	}

	public void setCustomCheck(String customCheck) {
		this.customCheck = customCheck;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/*public ContainerSize getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(ContainerSize containerLength) {
		this.containerLength = containerLength;
	}*/

	public String getContainerISOCode() {
		return containerISOCode;
	}

	public void setContainerISOCode(String containerISOCode) {
		this.containerISOCode = containerISOCode;
	}

	public ContainerFullEmptyType getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(ContainerFullEmptyType containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

}
