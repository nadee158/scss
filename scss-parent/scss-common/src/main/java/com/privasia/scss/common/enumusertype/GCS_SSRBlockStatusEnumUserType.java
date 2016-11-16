/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.GCS_SSRBlockStatusType;

/**
 * @author Janaka
 *
 */
public class GCS_SSRBlockStatusEnumUserType extends GeneralEnumMapUserType<GCS_SSRBlockStatusType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public GCS_SSRBlockStatusEnumUserType() {
        super(GCS_SSRBlockStatusType.class);
    }

}
