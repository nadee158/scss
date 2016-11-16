/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import com.privasia.scss.common.enums.CompanyStatus;
import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.GCS_SSRBlockStatusType;
import com.privasia.scss.common.enums.VesselStatus;

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
			@AttributeOverride(name = "pmHeadNo", column = @Column(name = "EXP_TRUCK_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "EXP_TRUCK_PLATE_NO")),
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
	private Integer expWeightBridge;

	@Column(name = "EXP_NET_WEIGHT")
	private Integer expNetWeight;
	
	//EXP_OVERSIZE_FLAG
	
	@Column(name = "EXP_TRUCK_POS", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.ContainerPositionEnumUserType")
	private ContainerPosition containerPosition;
	 
	@Column(name = "EXP_P_CHECK_DATE_TIME")
	private LocalDateTime preCheckDate;
	
	@Column(name = "EXP_YARD_POSITION")
	private String yardPosition;
	
	@Column(name = "EXP_BAY_CODE")
	private String bayCode;
	
	@Column(name = "EXP_PM_BTM")
	private Integer pmBTM;
	
	@Column(name = "EXP_TR_BTM")
	private Integer trBTM;
	
	@Column(name = "EXP_OOG_OR")
	private Integer oogOR;
	
	@Column(name = "EXP_CALL_CARD")
	private Integer callCard;
	
	@Column(name = "VESSEL_VISIT_ID")
	private String vesselVisitID;
	
	@Column(name = "VESSEL_VOYAGE")
	private String vesselVoyage;
	
	@Column(name = "VESSEL_CODE")
	private String vesselCode;
	
	@Column(name = "VESSEL_NAME")
	private String vesselName;
	
	@Column(name = "EXP_AGENT")
	private String expAgent;
	
	@Column(name = "VESSEL_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.VesselStatusEnumUserType")
	private VesselStatus vesselStatus;
	
	@Column(name = "SHIP_ID")
	private String shipID;
	
	@Column(name = "VESSEL_SCN")
	private String vesselSCN;
	
	@Column(name = "DATE_VESSEL_ETA")
	private LocalDateTime vesselETADate;
	
	@Column(name = "DATE_VESSEL_ATA")
	private LocalDateTime vesselATADate;
	
	@Column(name = " EXP_HAS_OOG_SSR", nullable = true)
	@Type(type = "yes_no")
	private Boolean oogSSR;
	
	@Column(name = " EXP_HAS_OVERCLOSING_SSR", nullable = true)
	@Type(type = "yes_no")
	private Boolean overClosingSSR;
	
	@Column(name = " EXP_HAS_REPLAN_SSR", nullable = true)
	@Type(type = "yes_no")
	private Boolean replanSSR;
	
	@Column(name = "EXP_SSR_BLOCK_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.GCS_SSRBlockStatusEnumUserType")
	private GCS_SSRBlockStatusType ssrBlockStatus;
	
	@Column(name = "EXP_SSR_BLOCK_STATUS_DATETIME")
	private LocalDateTime ssrBlockStatusDate;
	
	@Column(name = "EXP_GCS_BLOCK_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.GCS_SSRBlockStatusEnumUserType")
	private GCS_SSRBlockStatusType gcsBlockStatus;
	
	@Column(name = "EXP_GCS_BLOCK_STATUS_DATETIME")
	private LocalDateTime gcsBlockStatusDate;
	
	@Column(name = "EXP_GCS_DECLARENO")
	private String gcsDeclareNo;
	
	@Column(name = "GCS_LAST_CHK_DATETIME")
	private LocalDateTime gcsLastCheck;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_PRINT_EIR", nullable = true, referencedColumnName = "PRINT_NO")
	private PrintEir printEir;
	
	@Column(name = "USER_REMARKS")
	private String userRemarks;
	
	@Column(name = "KPA_APPROVAL")
	private String kpaApproval;
	
	@Column(name = "GOODS_HDL_CODE")
	private String hdlGoodsCode;
	
	@Column(name = "DG_DESC")
	private String dgDescription;
	
	@Column(name = "GOODS_HDL_DESC")
	private String hdlGoodsDescription;
	
	@Column(name = "COSMOS_TARE_WEIGHT")
	private Integer cosmosTareWeight;
	
	@Column(name = "COSMOS_GROSS_WEIGHT")
	private Integer cosmosGrossWeight;
	
	@Column(name = "COSMOS_NET_WEIGHT")
	private Integer cosmosNetWeight;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUG_ID_SEQ", nullable = true, referencedColumnName = "CUG_ID_SEQ")
	private CardUsage cardUsage;
	
	@Column(name = " BACK_TO_BACK", nullable = true)
	@Type(type = "yes_no")
	private Boolean backToback;
	
	@Column(name = "WEIGHT_DIFF_PERCENTAGE", nullable = true)
	private Double weightDiffPercentage;
	
	@Column(name = "WEIGHT_DIFF", nullable = true)
	private Double weightDifference;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_1", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_01;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_2", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_02;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_3", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_03;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_4", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_04;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_5", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_05;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_6", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_06;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_7", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_07;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_8", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_08;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_DAMAGE_9", nullable = true, referencedColumnName = "DAMAGE_CODE")
	private DamageCode damageCode_09;
	
	@Column(name = "DONT_VALIDATE_SEAL", nullable = true)
	@Type(type = "yes_no")
	private Boolean dontValidateSeal;
	
	@Column(name = "WRONG_DOOR", nullable = true)
	@Type(type = "yes_no")
	private Boolean wrongDoor;
	
	@Column(name = "HPAB_ISO_CODE")
	private String hpabISOCode;
	
	@Column(name = "COSMOS_ISO_CODE")
	private String cosmosISOCode;
	
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
	
	@Column(name = "WITHIN_TOLERANCE", nullable = true)
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
