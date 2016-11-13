/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.CompanyStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_EXPORTS")
public class Exports implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_EXPORTS")
	@SequenceGenerator(name = "SEQ_SCSS_EXPORTS", sequenceName = "EXP_EXPORTNO_SEQ")
	@Column(name = "EXP_EXPORTNO_SEQ")
	private Long exportID;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "EXP_CONTAINERNO")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "EXP_CONT_ISO_CODE")),
			@AttributeOverride(name = "containerLength", column = @Column(name = "CONT_LENGTH")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "EXP_FULL_EMPTY_FLAG")) })
	private CommonContainerAttribute container;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "timeGateIn", column = @Column(name = "EXP_TIMEGATEIN")),
			@AttributeOverride(name = "timeGateInOk", column = @Column(name = "GTP_TIMEGATEINOK")),
			@AttributeOverride(name = "timeGateOut", column = @Column(name = "EXP_TIMEGATEOUT")),
			@AttributeOverride(name = "timeGateOutOk", column = @Column(name = "EXP_TIMEGATEOUTOK")),
			@AttributeOverride(name = "eirNumber", column = @Column(name = "EXP_EIRNO")),
			@AttributeOverride(name = "eirStatus", column = @Column(name = "EXP_EIRSTATUS")),
			@AttributeOverride(name = "impExpFlag", column = @Column(name = "EXP_IMPEXPFLAG")),
			@AttributeOverride(name = "rejectReason", column = @Column(name = "EXP_REJECTREASON")),
			@AttributeOverride(name = "pmHeadNo", column = @Column(name = "GTP_TRUCK_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "GTP_TRUCK_PLATE_NO")),
			@AttributeOverride(name = "kioskConfirmed", column = @Column(name = "KIOSK_CONFIRMED")),
			@AttributeOverride(name = "kioskCancelPickUp", column = @Column(name = "KIOSK_CANCEL_PICKUP")),
			@AttributeOverride(name = "transactionSlipPrinted", column = @Column(name = "TRANSACTION_SLIP_PRINTED")),
			@AttributeOverride(name = "gateOutBoothNo", column = @Column(name = "EXP_GATE_OUT_BOOTH_NO")),
			@AttributeOverride(name = "gateOutBoothClerk", column = @Column(name = "EXP_GATEOUT_BOOTH_CLERKID")),
			@AttributeOverride(name = "timeGateOutBooth", column = @Column(name = "EXP_TIMEGATEOUT_BOOTH")),
			@AttributeOverride(name = "gateInStatus", column = @Column(name = "EXP_GATEIN_STATUS")),
			@AttributeOverride(name = "hpatBooking", column = @Column(name = "BOOKING_ID"))

	})
	@AssociationOverrides({
		@AssociationOverride(name = "card", joinColumns = @JoinColumn(name = "EXP_HCTDID", referencedColumnName = "CRD_CARDID_SEQ")),
		@AssociationOverride(name = "gateInClerk", joinColumns = @JoinColumn(name = "EXP_GATEINCLERKID", referencedColumnName = "SYS_USERID_SEQ")),
		@AssociationOverride(name = "gateOutClerk", joinColumns = @JoinColumn(name = "EXP_GATEOUTCLERKID", referencedColumnName = "SYS_USERID_SEQ")),
		@AssociationOverride(name = "gateInClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEIN", referencedColumnName = "CLI_CLIENTID_SEQ")),
		@AssociationOverride(name = "gateOutClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEOUT", referencedColumnName = "CLI_CLIENTID_SEQ")) })
	private CommonGateInOutAttribute commonGateInOut;

	@Column(name = "EXP_BOOKINGNO")
	private String bookingNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCN_SEQ", nullable = true, referencedColumnName = "SCN_SEQ")
	private ShipSCN scn;

	@Column(name = "EXP_IN_OUT", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.InOutEnumUserType")
	private CompanyStatus inOut;

	@Column(name = "EXP_LINE")
	private String expLine;

	@Column(name = "EXP_OUT")
	private String expOut;

	@Column(name = "EXP_CAR")
	private String expCar;

	@Column(name = "EXP_SPOD")
	private String expSpod;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "seal01Origin", column = @Column(name = "EXP_SEAL_1_ORIGIN")),
			@AttributeOverride(name = "seal01Type", column = @Column(name = "EXP_SEAL_1_TYPE")),
			@AttributeOverride(name = "seal01Number", column = @Column(name = "EXP_SEAL_1_NUMBER")),
			@AttributeOverride(name = "seal02Origin", column = @Column(name = "EXP_SEAL_2_ORIGIN")),
			@AttributeOverride(name = "seal02Type", column = @Column(name = "EXP_SEAL_2_TYPE")),
			@AttributeOverride(name = "seal02Number", column = @Column(name = "EXP_SEAL_2_NUMBER")) })
	private CommonSealAttribute sealAttribute;

	@Column(name = "EXP_WEIGHT_BRIDGE")
	private int expWeightBridge;

	@Column(name = "EXP_NET_WEIGHT")
	private int expNetWeight;
	
	@Column(name = "TRUCK_WEIGHT")
	private String pmWeight;

	@Column(name = "TRAILER_WEIGHT")
	private String trailerWeight;

	@Column(name = "TRAILER_PLATE_NO")
	private String trailerPlateNo;
	
	@Column(name = "FUEL_WEIGHT")
	private String fuelWeight;
	
	@Column(name = "TIRE_WEIGHT")
	private String tireWeight;
	
	@Column(name = "VARIANCE")
	private String variance;
	
	@Column(name = "EXP_SUB_TYPE")
	private String subType;
	
	@Column(name = "WITHIN_TOLERANCE")
	@Type(type = "true_false")
	private boolean withinTolerance;
	
	@Column(name = "CAL_VARIANCE")
	private String calculatedVariance;
	
	@Column(name = "SOLAS_CERT")
	private String solasCertNo;
	
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "mgw", column = @Column(name = "MGW")),
		@AttributeOverride(name = "faLedgerCode", column = @Column(name = "FA_LEDGER_CODE")),
		@AttributeOverride(name = "solasRefNumber", column = @Column(name = "SOLAS_REF_NO")),
		@AttributeOverride(name = "solasDetailID", column = @Column(name = "SOLAS_DETAIL_NO")),
		@AttributeOverride(name = "solasInstruction", column = @Column(name = "VGM_TYPE")),
		@AttributeOverride(name = "shipperVGM", column = @Column(name = "SHIPPER_VGM")) })
	private CommonSolasAttribute solas;

}
