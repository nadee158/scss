/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class HDBSBookingResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean avaliable = false;
	
	private HDBSBkgGridDTO hdbsGrid;

	public boolean isAvaliable() {
		return avaliable;
	}

	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}

	public HDBSBkgGridDTO getHdbsGrid() {
		return hdbsGrid;
	}

	public void setHdbsGrid(HDBSBkgGridDTO hdbsGrid) {
		this.hdbsGrid = hdbsGrid;
	}
	
	

}
