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
public class CommonContainerAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String containerNumber;

	@Type(type = "com.privasia.scss.common.enumusertype.ContainerSizeEnumUserType")
	private ContainerSize containerLength;

	private String containerISOCode;

	@Type(type = "com.privasia.scss.common.enumusertype.ContainerFullEmptyTypeEnumUserType")
	private ContainerFullEmptyType containerFullOrEmpty;

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

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

	public ContainerSize getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(ContainerSize containerLength) {
		this.containerLength = containerLength;
	}

}
