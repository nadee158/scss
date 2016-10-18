/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_KIOSK_BOOTH_RIGHTS", uniqueConstraints={
		@UniqueConstraint(columnNames = "ROL_NAME")
})
@AttributeOverrides({
    @AttributeOverride(name="addBy",
                       column=@Column(name="CREATED_BY")),
    @AttributeOverride(name="updateBy",
                       column=@Column(name="UPDATED_BY")),
    @AttributeOverride(name="dateTimeAdd",
    				   column=@Column(name="DATE_TIME_CREATED")),
    @AttributeOverride(name="dateTimeUpdate",
                       column=@Column(name="DATE_TIME_UPDATE"))
})
public class KioskBoothRights extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	private KioskBoothRightsPK KioskBoothRightsID;

	private String kioskLockStatus;
	
	private int cardNumber;
	
	private ZonedDateTime cardScanTime;
	
	private ZonedDateTime kioskSelectedTime;
	
	private int displayScreenID;
	
	private String transactionIDList;
	
	private String driverName;
	
	private String pmHeadNo;
	
	private String truckCompany;
	
	private String driverIC;
	
	private String plateNo;
	
	private String transactionType;
	
	private String reviseHeadNo;
	
	private String reviseHeadNoRemarks;
	
	private boolean retakePhoto;
	
	private ZonedDateTime trxCompleteTime;
	
	private String lockUserID;
	
	private String lockUserName;
	
	private String referReason01List;
	
	private String referReason02List;
	
	private CommonContainerAttribute container01;
	
	private CommonContainerAttribute container02;
	
	private CommonContainerAttribute container03;
	
	private CommonContainerAttribute container04;
	
	

}
