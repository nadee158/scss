/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.ClientType;

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
