/**
 * 
 */
package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Janaka
 *
 */
public class OpusBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userID = StringUtils.EMPTY;// USER01"
	private String laneNo = StringUtils.EMPTY;// LNO01"
	private String haulageCode = StringUtils.EMPTY;// HAUCD"
	private String truckHeadNo = StringUtils.EMPTY;// TRUCK"
	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLaneNo() {
		return laneNo;
	}

	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

	public String getHaulageCode() {
		return haulageCode;
	}

	public void setHaulageCode(String haulageCode) {
		this.haulageCode = haulageCode;
	}

	public String getTruckHeadNo() {
		return truckHeadNo;
	}

	public void setTruckHeadNo(String truckHeadNo) {
		this.truckHeadNo = truckHeadNo;
	}

}
