/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;


/**
 * @author Janaka
 *
 */
public class ODDLocationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String oddCode;
	private String oddDesc;
	
	public String getOddCode() {
		return oddCode;
	}
	public void setOddCode(String oddCode) {
		this.oddCode = oddCode;
	}
	public String getOddDesc() {
		return oddDesc;
	}
	public void setOddDesc(String oddDesc) {
		this.oddDesc = oddDesc;
	}
	
	

}
