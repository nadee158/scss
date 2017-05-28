/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.common.enums.ContainerFullEmptyType;

/**
 * @author Janaka
 *
 */
public class CommonContainerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String containerNumber;

	private double containerHeight;

	private String containerISOCode;

	private String containerFullOrEmpty;

	private String containerSize;

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

	public String getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(String containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}
	
	public double getContainerHeight() {
		return containerHeight;
	}

	public void setContainerHeight(double containerHeight) {
		this.containerHeight = containerHeight;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
