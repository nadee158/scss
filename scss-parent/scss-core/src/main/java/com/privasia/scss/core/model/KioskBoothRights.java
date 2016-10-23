/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.KioskLockStatus;
import com.privasia.scss.core.util.constant.TransactionType;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_KIOSK_BOOTH_RIGHTS")
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
	
	@Column(name = "KIOSK_LOCK_STATUS")
	@Enumerated(EnumType.STRING) 
	private KioskLockStatus kioskLockStatus;
	
	@Column(name = "CARDNUMBER")
	private int cardNumber;
	
	@Column(name = "CARD_SCAN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime cardScanTime;
	
	@Column(name = "KIOSK_SELECT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime kioskSelectedTime;
	
	@Column(name = "DISPLAY_SCREEN_ID")
	private int displayScreenID;
	
	@Column(name = "TRANSID")
	private String transactionIDList;
	
	@Column(name = "DRIVERNAME")
	private String driverName;
	
	@Column(name = "PMHEAD")
	private String pmHeadNo;
	
	@Column(name = "TRUCKCO")
	private String truckCompany;
	
	@Column(name = "DRIVER_IC")
	private String driverIC;
	
	@Column(name = "PLATE_NO")
	private String plateNo;
	
	@Column(name = "TRANSACTION_TYPE")
	@Enumerated(EnumType.STRING) 
	private TransactionType transactionType;
	
	@Column(name = "TRANSACTION_TYPE")
	private String reviseHeadNo;
	
	@Column(name = "TRANSACTION_TYPE")
	private String reviseHeadNoRemarks;
	
	@Column(name = "TRANSACTION_TYPE")
	@Type(type="YES_NO")
	private boolean retakePhoto;
	
	@Column(name = "TRANSACTION_TYPE")
	private LocalDateTime trxCompleteTime;
	
	@Column(name = "LOCK_USER_ID")
	private String lockUserID;
	
	@Column(name = "LOCK_USER_NAME")
	private String lockUserName;
	
	@Column(name = "REFER_REASON_01")
	private String referReason01List;
	
	@Column(name = "REFER_REASON_02")
	private String referReason02List;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "containerNumber",
		            column = @Column( name = "CT1")
		        ),
		        @AttributeOverride(
		            name = "containerISOCode",
		            column = @Column( name = "CT1ISO")
		        ),
		        @AttributeOverride(
		            name = "containerLength",
		            column = @Column( name = "CT1_LENGTH")
		        ),
		        @AttributeOverride(
		            name = "containerFullOrEmpty",
		            column = @Column( name = "CT1FE")
		        ),
		        @AttributeOverride(
			            name = "cancelPickup",
			            column = @Column( name = "CT1CANPICKUP")
			    ),
		        @AttributeOverride(
			            name = "location",
			            column = @Column( name = "CT1LOC")
			    ),
		        @AttributeOverride(
			            name = "status",
			            column = @Column( name = "CT1STATUS")
			    ),
		        @AttributeOverride(
			            name = "others",
			            column = @Column( name = "CT1_OTHERS")
			    ),
		        @AttributeOverride(
			            name = "rejectRemarks",
			            column = @Column( name = "CT1_REJECT_REMARK")
			    ),
		        @AttributeOverride(
			            name = "customCheck",
			            column = @Column( name = "CT1_CUSTOM_CHECK")
			    ),
		        @AttributeOverride(
			            name = "shipper",
			            column = @Column( name = "CT1S")
			    ),
		        @AttributeOverride(
			            name = "line",
			            column = @Column( name = "CT1L")
			    )
	})
	private KioskBoothContainerAttribute container01;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "containerNumber",
		            column = @Column( name = "CT2")
		        ),
		        @AttributeOverride(
		            name = "containerISOCode",
		            column = @Column( name = "CT2ISO")
		        ),
		        @AttributeOverride(
		            name = "containerLength",
		            column = @Column( name = "CT2_LENGTH")
		        ),
		        @AttributeOverride(
		            name = "containerFullOrEmpty",
		            column = @Column( name = "CT2FE")
		        ),
		        @AttributeOverride(
			            name = "cancelPickup",
			            column = @Column( name = "CT2CANPICKUP")
			    ),
		        @AttributeOverride(
			            name = "location",
			            column = @Column( name = "CT2LOC")
			    ),
		        @AttributeOverride(
			            name = "status",
			            column = @Column( name = "CT2STATUS")
			    ),
		        @AttributeOverride(
			            name = "others",
			            column = @Column( name = "CT2_OTHERS")
			    ),
		        @AttributeOverride(
			            name = "rejectRemarks",
			            column = @Column( name = "CT2_REJECT_REMARK")
			    ),
		        @AttributeOverride(
			            name = "customCheck",
			            column = @Column( name = "CT2_CUSTOM_CHECK")
			    ),
		        @AttributeOverride(
			            name = "shipper",
			            column = @Column( name = "CT2S")
			    ),
		        @AttributeOverride(
			            name = "line",
			            column = @Column( name = "CT2L")
			    )
	})
	private KioskBoothContainerAttribute container02;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "containerNumber",
		            column = @Column( name = "CT3")
		        ),
		        @AttributeOverride(
		            name = "containerISOCode",
		            column = @Column( name = "CT3ISO")
		        ),
		        @AttributeOverride(
		            name = "containerLength",
		            column = @Column( name = "CT3_LENGTH")
		        ),
		        @AttributeOverride(
		            name = "containerFullOrEmpty",
		            column = @Column( name = "CT3FE")
		        ),
		        @AttributeOverride(
			            name = "cancelPickup",
			            column = @Column( name = "CT3CANPICKUP")
			    ),
		        @AttributeOverride(
			            name = "location",
			            column = @Column( name = "CT3LOC")
			    ),
		        @AttributeOverride(
			            name = "status",
			            column = @Column( name = "CT3STATUS")
			    ),
		        @AttributeOverride(
			            name = "others",
			            column = @Column( name = "CT3_OTHERS")
			    ),
		        @AttributeOverride(
			            name = "rejectRemarks",
			            column = @Column( name = "CT3_REJECT_REMARK")
			    ),
		        @AttributeOverride(
			            name = "customCheck",
			            column = @Column( name = "CT3_CUSTOM_CHECK")
			    ),
		        @AttributeOverride(
			            name = "shipper",
			            column = @Column( name = "CT3S")
			    ),
		        @AttributeOverride(
			            name = "line",
			            column = @Column( name = "CT3L")
			    )
	})
	private KioskBoothContainerAttribute container03;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "containerNumber",
		            column = @Column( name = "CT4")
		        ),
		        @AttributeOverride(
		            name = "containerISOCode",
		            column = @Column( name = "CT4ISO")
		        ),
		        @AttributeOverride(
		            name = "containerLength",
		            column = @Column( name = "CT4_LENGTH")
		        ),
		        @AttributeOverride(
		            name = "containerFullOrEmpty",
		            column = @Column( name = "CT4FE")
		        ),
		        @AttributeOverride(
			            name = "cancelPickup",
			            column = @Column( name = "CT4CANPICKUP")
			    ),
		        @AttributeOverride(
			            name = "location",
			            column = @Column( name = "CT4LOC")
			    ),
		        @AttributeOverride(
			            name = "status",
			            column = @Column( name = "CT4STATUS")
			    ),
		        @AttributeOverride(
			            name = "others",
			            column = @Column( name = "CT4_OTHERS")
			    ),
		        @AttributeOverride(
			            name = "rejectRemarks",
			            column = @Column( name = "CT4_REJECT_REMARK")
			    ),
		        @AttributeOverride(
			            name = "customCheck",
			            column = @Column( name = "CT4_CUSTOM_CHECK")
			    ),
		        @AttributeOverride(
			            name = "shipper",
			            column = @Column( name = "CT4S")
			    ),
		        @AttributeOverride(
			            name = "line",
			            column = @Column( name = "CT4L")
			    )
	})
	private KioskBoothContainerAttribute container04;
	
	

}
