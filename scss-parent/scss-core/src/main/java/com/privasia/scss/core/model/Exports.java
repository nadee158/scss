/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_EXPORTS")
public class Exports implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_EXPORTS")
    @SequenceGenerator(name = "SEQ_SCSS_EXPORTS", sequenceName = "EXP_EXPORTNO_SEQ")
	@Column(name = "EXP_EXPORTNO_SEQ")
	private Long exportSEQ;
	
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "EXP_CONTAINERNO")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "GTP_CONT_ISO_CODE")),
			@AttributeOverride(name = "containerLength", column = @Column(name = "CONT_LENGTH")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "GTP_FULLEMPTYFLAG")) })
	private CommonContainerAttribute container;
	
	
	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "timeGateIn", column = @Column(name = "EXP_TIMEGATEIN")),
			@AttributeOverride(name = "timeGateInOk", column = @Column(name = "GTP_TIMEGATEINOK")),
			@AttributeOverride(name = "timeGateOut", column = @Column(name = "EXP_TIMEGATEOUT")),
			@AttributeOverride(name = "timeGateOutOk", column = @Column(name = "EXP_TIMEGATEOUTOK")),
			@AttributeOverride(name = "eirNumber", column = @Column(name = "EXP_EIRNO")),
			@AttributeOverride(name = "eirStatus", column = @Column(name = "EXP_EIRSTATUS")),
			@AttributeOverride(name = "impExpFlag", column = @Column(name = "GTP_IMPEXPFLAG")),
			@AttributeOverride(name = "rejectReason", column = @Column(name = "GTP_REJECTREASON")),
			@AttributeOverride(name = "pmHeadNo", column = @Column(name = "GTP_TRUCK_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "GTP_TRUCK_PLATE_NO")),
			@AttributeOverride(name = "kioskConfirmed", column = @Column(name = "KIOSK_CONFIRMED")),
			@AttributeOverride(name = "kioskCancelPickUp", column = @Column(name = "KIOSK_CANCEL_PICKUP")),
			@AttributeOverride(name = "transactionSlipPrinted", column = @Column(name = "TRANSACTION_SLIP_PRINTED")),
			@AttributeOverride(name = "gateOutBoothNo", column = @Column(name = "GTP_GATE_OUT_BOOTH_NO")),
			@AttributeOverride(name = "gateOutBoothClerk", column = @Column(name = "GTP_GATEOUT_BOOTH_CLERKID")),
			@AttributeOverride(name = "timeGateOutBooth", column = @Column(name = "GTP_TIMEGATEOUT_BOOTH")),
			@AttributeOverride(name = "gateInStatus", column = @Column(name = "GTP_GATEIN_STATUS")),
			@AttributeOverride(name = "hpatBooking", column = @Column(name = "BOOKING_ID"))

	})
	/*!@AssociationOverrides({
		   @AssociationOverride(name = "list",
		      joinColumns = @JoinColumn(referencedColumnName = "COLUMN_NEW_NAME"))
		})*/
	private CommonGateInOutAttribute commonGateInOut;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_GATEINCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateInClerk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_GATEOUTCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateOutClerk;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "EXP_HCTDID", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;
	
	@Column(name = "EXP_BOOKINGNO")
	private String bookingNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEIN", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateInClient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEOUT", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateOutClient;
	
	
}
