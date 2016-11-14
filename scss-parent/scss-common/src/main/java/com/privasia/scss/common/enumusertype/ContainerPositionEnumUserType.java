/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.ContainerPosition;

/**
 * @author Janaka
 *
 */
public class ContainerPositionEnumUserType extends GeneralEnumMapUserType<ContainerPosition> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public ContainerPositionEnumUserType() {
        super(ContainerPosition.class);
    }

}
