/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.ClientType;

/**
 * @author Janaka
 *
 */
public class ClientTypeEnumUserType extends GeneralEnumMapUserType<ClientType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public ClientTypeEnumUserType() {
        super(ClientType.class);
    }

}
