/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;


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

	private int containerLength;

	private String containerISOCode;

	private String containerFullOrEmpty;

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public int getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(int containerLength) {
		this.containerLength = containerLength;
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
	
	
	
	

}
