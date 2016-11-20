/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;
import com.privasia.scss.common.enums.ImpExpFlagStatus;


/**
 * @author Janaka
 *
 */
public class ImpExpFlagEnumUserType extends GeneralEnumMapUserType<ImpExpFlagStatus> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 */
	public ImpExpFlagEnumUserType() {
		super(ImpExpFlagStatus.class);
	}

}
