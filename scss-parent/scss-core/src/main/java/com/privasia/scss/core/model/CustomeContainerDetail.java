/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.TransactionStatus;

/**
 * @author Janaka
 *
 */
@Embeddable
public class CustomeContainerDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String containerNumber;

	@Type(type = "com.privasia.scss.common.enumusertype.ContainerFullEmptyTypeEnumUserType")
	private ContainerFullEmptyType containerFullOrEmpty;
	
	@Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus eirStatus;
	
	


}
