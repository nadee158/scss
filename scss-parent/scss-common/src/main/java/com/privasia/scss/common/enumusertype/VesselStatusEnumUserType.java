/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.VesselStatus;

/**
 * @author Janaka
 *
 */
public class VesselStatusEnumUserType extends GeneralEnumMapUserType<VesselStatus> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 */
	public VesselStatusEnumUserType() {
		super(VesselStatus.class);
	}

}
