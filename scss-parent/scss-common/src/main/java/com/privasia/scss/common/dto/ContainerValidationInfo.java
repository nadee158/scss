/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class ContainerValidationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean containerNo1Status;
	private String containerNo1;
	private boolean containerNo2Status;
	private String containerNo2;
	private String tosIndicator;
	
	public String getContainerNo1() {
		return containerNo1;
	}
	public void setContainerNo1(String containerNo1) {
		this.containerNo1 = containerNo1;
	}
	public String getContainerNo2() {
		return containerNo2;
	}
	public void setContainerNo2(String containerNo2) {
		this.containerNo2 = containerNo2;
	}
	public boolean isContainerNo1Status() {
		return containerNo1Status;
	}
	public void setContainerNo1Status(boolean containerNo1Status) {
		this.containerNo1Status = containerNo1Status;
	}
	public boolean isContainerNo2Status() {
		return containerNo2Status;
	}
	public void setContainerNo2Status(boolean containerNo2Status) {
		this.containerNo2Status = containerNo2Status;
	}
	public String getTosIndicator() {
		return tosIndicator;
	}
	public void setTosIndicator(String tosIndicator) {
		this.tosIndicator = tosIndicator;
	}
	

}