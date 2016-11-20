/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.ContainerSize;

/**
 * @author Janaka
 *
 */
public class ContainerSizeEnumUserType extends GeneralEnumMapUserType<ContainerSize> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public ContainerSizeEnumUserType() {
        super(ContainerSize.class);
    }

}
