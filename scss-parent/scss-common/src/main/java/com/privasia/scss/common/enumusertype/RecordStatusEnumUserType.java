/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.RecordStatus;

/**
 * @author Janaka
 *
 */
public class RecordStatusEnumUserType extends GeneralEnumMapUserType<RecordStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public RecordStatusEnumUserType() {
        super(RecordStatus.class);
    }

}
