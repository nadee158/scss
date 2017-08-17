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
import javax.persistence.Basic;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.ExportOPTFlagType;
import com.privasia.scss.common.enums.SSRBlockType;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.ReferTempType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.VesselStatus;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.repository.DamageCodeRepository;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_EXPORTS")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "EXP_ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "EXP_UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "EXP_DATECREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "EXP_DATEUPDATE")) })
public class Exports extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_EXPORTS")
	@SequenceGenerator(name = "SEQ_SCSS_EXPORTS", sequenceName = "EXP_EXPORTNO_SEQ", allocationSize = 1)
	@Column(name = "EXP_EXPORTNO_SEQ")
	private Long exportID;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "EXP_CONTAINERNO")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "EXP_CONT_ISO_CODE")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "EXP_FULL_EMPTY_FLAG", nullable = true)) })
	private CommonContainerAttribute container;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "eirNumber", column = @Column(name = "EXP_EIRNO")),
			@AttributeOverride(name = "impExpFlag", column = @Column(name = "EXP_IMPEXPFLAG", nullable = true)),
			@AttributeOverride(name = "rejectReason", column = @Column(name = "EXP_REJECTREASON")),
			@AttributeOverride(name = "kioskConfirmed", column = @Column(name = "KIOSK_CONFIRMED")),
			@AttributeOverride(name = "kioskCancelPickUp", column = @Column(name = "KIOSK_CANCEL_PICKUP")),
			@AttributeOverride(name = "gateInStatus", column = @Column(name = "EXP_GATEIN_STATUS")),
			@AttributeOverride(name = "zipFileNo", column = @Column(name = "ZIP_FILE_NO")),
			@AttributeOverride(name = "trxSlipNo", column = @Column(name = "TRX_SLIP_NO")) })
	private CommonGateInOutAttribute commonGateInOut;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "pmHeadNo", column = @Column(name = "EXP_TRUCK_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "EXP_TRUCK_PLATE_NO")),
			@AttributeOverride(name = "hpabBooking", column = @Column(name = "BOOKING_ID")),
			@AttributeOverride(name = "eirStatus", column = @Column(name = "EXP_EIRSTATUS", nullable = true)),
			@AttributeOverride(name = "transactionSlipPrinted", column = @Column(name = "TRANSACTION_SLIP_PRINTED")),
			@AttributeOverride(name = "gateOutBoothNo", column = @Column(name = "EXP_GATE_OUT_BOOTH_NO")),
			@AttributeOverride(name = "timeGateIn", column = @Column(name = "EXP_TIMEGATEIN")),
			@AttributeOverride(name = "timeGateInOk", column = @Column(name = "EXP_TIMEGATEINOK")),
			@AttributeOverride(name = "timeGateOut", column = @Column(name = "EXP_TIMEGATEOUT")),
			@AttributeOverride(name = "timeGateOutOk", column = @Column(name = "EXP_TIMEGATEOUTOK")),
			@AttributeOverride(name = "timeGateOutBooth", column = @Column(name = "EXP_TIMEGATEOUT_BOOTH")) })
	@AssociationOverrides({
			@AssociationOverride(name = "gateOutBoothClerk", joinColumns = @JoinColumn(name = "EXP_GATEOUT_BOOTH_CLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "card", joinColumns = @JoinColumn(name = "EXP_HCTDID", referencedColumnName = "CRD_CARDID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateInClerk", joinColumns = @JoinColumn(name = "EXP_GATEINCLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateOutClerk", joinColumns = @JoinColumn(name = "EXP_GATEOUTCLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateInClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEIN", referencedColumnName = "CLI_CLIENTID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateOutClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEOUT", referencedColumnName = "CLI_CLIENTID_SEQ", nullable = true)) })
	private BaseCommonGateInOutAttribute baseCommonGateInOutAttribute;

	@Column(name = "EXP_MANUALOPTFLAG", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.ExportOPTFlagEnumUserType")
	private ExportOPTFlagType manualPlanIndicator;

	@Column(name = "EXP_BOOKINGNO")
	private String bookingNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCN_SEQ", nullable = true, referencedColumnName = "SCN_SEQ")
	private ShipSCN shipSCN;

	@Column(name = "EXP_IN_OUT", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.GateInOutStatusEnumUserType")
	private GateInOutStatus gateInOut;

	@Column(name = "EXP_LINE")
	private String shippingLine;

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

	@Column(name = "EXP_REEFER_FLAG", nullable = true)
	@Type(type = "yes_no")
	private Boolean referFlag;

	@Column(name = "EXP_REEFER_TEMP_TYPE")
	@Type(type = "com.privasia.scss.common.enumusertype.ReferTempEnumUserType")
	private ReferTempType referTempType;

	@Column(name = "EXP_REEFER_TEMP")
	private Double referTemp;

	@Column(name = "EXP_IMDG")
	private String imdg;

	@Column(name = "EXP_UN")
	private String dgUNCode;

	@Column(name = "EXP_IMDG_LABEL_ID")
	private String imdgLabelID;

	@Column(name = "EXP_OOG_OH")
	private Integer oogOH;

	@Column(name = "EXP_OOG_OL")
	private Integer oogOL;

	@Column(name = "EXP_OOG_OF")
	private Integer oogOF;

	@Column(name = "EXP_OOG_OA")
	private Integer oogOA;

	@Column(name = "EXP_TRUCK_POS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.ContainerPositionEnumUserType")
	private ContainerPosition containerPosition;

	@Column(name = "EXP_P_CHECK_DATE_TIME")
	private LocalDateTime preCheckDate;

	@Column(name = "EXP_YARD_POSITION")
	private String yardPosition;

	@Column(name = "EXP_BAY_CODE")
	private String yardBayCode;

	@Column(name = "EXP_PM_BTM")
	@Basic(fetch = FetchType.LAZY)
	private Integer pmBTM;

	@Column(name = "EXP_TR_BTM")
	@Basic(fetch = FetchType.LAZY)
	private Integer trBTM;

	@Column(name = "EXP_OOG_OR")
	private Integer oogOR;

	@Column(name = "EXP_CALL_CARD")
	private Long callCard;

	@Column(name = "VESSEL_VISIT_ID")
	private String vesselVisitID;

	@Column(name = "VESSEL_VOYAGE")
	private String vesselVoyageIN;

	@Column(name = "VESSEL_CODE")
	private String vesselCode;

	@Column(name = "VESSEL_NAME")
	private String vesselName;

	@Column(name = "EXP_AGENT")
	private String expAgent;

	@Column(name = "VESSEL_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.VesselStatusEnumUserType")
	private VesselStatus vesselStatus;

	@Column(name = "SHIP_ID")
	private String shipCode;

	@Column(name = "VESSEL_SCN")
	private String vesselSCN;

	@Column(name = "DATE_VESSEL_ETA")
	private LocalDateTime vesselETADate;

	@Column(name = "DATE_VESSEL_ATA")
	private LocalDateTime vesselATADate;

	@Column(name = "EXP_AGENT_CODE")
	private String shippingAgent;

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
	@Type(type = "com.privasia.scss.common.enumusertype.SSRBlockTypeEnumUserType")
	private SSRBlockType ssrBlockStatus;

	@Column(name = "EXP_SSR_BLOCK_STATUS_DATETIME")
	private LocalDateTime ssrBlockStatusDate;

	@Column(name = "EXP_GCS_BLOCK_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.SSRBlockTypeEnumUserType")
	private SSRBlockType gcsBlockStatus;

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
	private Integer tareWeight;

	@Column(name = "COSMOS_GROSS_WEIGHT")
	private Integer grossWeight;

	@Column(name = "COSMOS_NET_WEIGHT")
	private Integer cosmosNetWeight;

	@Column(name = " BACK_TO_BACK", nullable = true)
	@Type(type = "yes_no")
	private Boolean backToback = false;

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
	private String subHandlingType;

	@Column(name = "WITHIN_TOLERANCE", nullable = true)
	@Type(type = "true_false")
	private boolean withinTolerance = false;

	@Column(name = "CAL_VARIANCE")
	private String calculatedVariance;

	@Column(name = "SOLAS_CERT")
	private String solasCertNo;
	
	@Column(name = "IS_DIRECT_ENTRY", nullable = true)
	@Type(type = "true_false")
	private boolean directEntry = false;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "mgw", column = @Column(name = "MGW")),
			@AttributeOverride(name = "faLedgerCode", column = @Column(name = "FA_LEDGER_CODE")),
			@AttributeOverride(name = "solasRefNumber", column = @Column(name = "SOLAS_REF_NO")),
			@AttributeOverride(name = "solasDetailID", column = @Column(name = "SOLAS_DETAIL_NO")),
			@AttributeOverride(name = "solasInstruction", column = @Column(name = "VGM_TYPE")),
			@AttributeOverride(name = "shipperVGM", column = @Column(name = "SHIPPER_VGM")) })
	private CommonSolasAttribute solas;

	public Long getExportID() {
		return exportID;
	}

	public void setExportID(Long exportID) {
		this.exportID = exportID;
	}

	public CommonContainerAttribute getContainer() {
		return container;
	}

	public void setContainer(CommonContainerAttribute container) {
		this.container = container;
	}

	public CommonGateInOutAttribute getCommonGateInOut() {
		return commonGateInOut;
	}

	public void setCommonGateInOut(CommonGateInOutAttribute commonGateInOut) {
		this.commonGateInOut = commonGateInOut;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public ShipSCN getShipSCN() {
		return shipSCN;
	}

	public void setShipSCN(ShipSCN shipSCN) {
		this.shipSCN = shipSCN;
	}

	public GateInOutStatus getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(GateInOutStatus gateInOut) {
		this.gateInOut = gateInOut;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getExpOut() {
		return expOut;
	}

	public void setExpOut(String expOut) {
		this.expOut = expOut;
	}

	public String getExpCar() {
		return expCar;
	}

	public void setExpCar(String expCar) {
		this.expCar = expCar;
	}

	public String getExpSpod() {
		return expSpod;
	}

	public void setExpSpod(String expSpod) {
		this.expSpod = expSpod;
	}

	public CommonSealAttribute getSealAttribute() {
		return sealAttribute;
	}

	public void setSealAttribute(CommonSealAttribute sealAttribute) {
		this.sealAttribute = sealAttribute;
	}

	public Integer getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(Integer expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	public Integer getExpNetWeight() {
		return expNetWeight;
	}

	public void setExpNetWeight(Integer expNetWeight) {
		this.expNetWeight = expNetWeight;
	}

	public ContainerPosition getContainerPosition() {
		return containerPosition;
	}

	public void setContainerPosition(ContainerPosition containerPosition) {
		this.containerPosition = containerPosition;
	}

	public LocalDateTime getPreCheckDate() {
		return preCheckDate;
	}

	public void setPreCheckDate(LocalDateTime preCheckDate) {
		this.preCheckDate = preCheckDate;
	}

	public String getYardPosition() {
		return yardPosition;
	}

	public void setYardPosition(String yardPosition) {
		this.yardPosition = yardPosition;
	}

	public String getYardBayCode() {
		return yardBayCode;
	}

	public void setYardBayCode(String yardBayCode) {
		this.yardBayCode = yardBayCode;
	}

	public Integer getPmBTM() {
		return pmBTM;
	}

	public void setPmBTM(Integer pmBTM) {
		this.pmBTM = pmBTM;
	}

	public Integer getTrBTM() {
		return trBTM;
	}

	public void setTrBTM(Integer trBTM) {
		this.trBTM = trBTM;
	}

	public Integer getOogOR() {
		return oogOR;
	}

	public void setOogOR(Integer oogOR) {
		this.oogOR = oogOR;
	}

	public Long getCallCard() {
		return callCard;
	}

	public void setCallCard(Long callCard) {
		this.callCard = callCard;
	}

	public String getVesselVisitID() {
		return vesselVisitID;
	}

	public void setVesselVisitID(String vesselVisitID) {
		this.vesselVisitID = vesselVisitID;
	}

	public String getVesselVoyageIN() {
		return vesselVoyageIN;
	}

	public void setVesselVoyageIN(String vesselVoyageIN) {
		this.vesselVoyageIN = vesselVoyageIN;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getExpAgent() {
		return expAgent;
	}

	public void setExpAgent(String expAgent) {
		this.expAgent = expAgent;
	}

	public VesselStatus getVesselStatus() {
		return vesselStatus;
	}

	public void setVesselStatus(VesselStatus vesselStatus) {
		this.vesselStatus = vesselStatus;
	}

	public String getShipCode() {
		return shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public String getVesselSCN() {
		return vesselSCN;
	}

	public void setVesselSCN(String vesselSCN) {
		this.vesselSCN = vesselSCN;
	}

	public LocalDateTime getVesselETADate() {
		return vesselETADate;
	}

	public void setVesselETADate(LocalDateTime vesselETADate) {
		this.vesselETADate = vesselETADate;
	}

	public LocalDateTime getVesselATADate() {
		return vesselATADate;
	}

	public void setVesselATADate(LocalDateTime vesselATADate) {
		this.vesselATADate = vesselATADate;
	}

	public Boolean getOogSSR() {
		return oogSSR;
	}

	public void setOogSSR(Boolean oogSSR) {
		this.oogSSR = oogSSR;
	}

	public Boolean getOverClosingSSR() {
		return overClosingSSR;
	}

	public void setOverClosingSSR(Boolean overClosingSSR) {
		this.overClosingSSR = overClosingSSR;
	}

	public Boolean getReplanSSR() {
		return replanSSR;
	}

	public void setReplanSSR(Boolean replanSSR) {
		this.replanSSR = replanSSR;
	}

	public SSRBlockType getSsrBlockStatus() {
		return ssrBlockStatus;
	}

	public void setSsrBlockStatus(SSRBlockType ssrBlockStatus) {
		this.ssrBlockStatus = ssrBlockStatus;
	}

	public LocalDateTime getSsrBlockStatusDate() {
		return ssrBlockStatusDate;
	}

	public void setSsrBlockStatusDate(LocalDateTime ssrBlockStatusDate) {
		this.ssrBlockStatusDate = ssrBlockStatusDate;
	}

	public SSRBlockType getGcsBlockStatus() {
		return gcsBlockStatus;
	}

	public void setGcsBlockStatus(SSRBlockType gcsBlockStatus) {
		this.gcsBlockStatus = gcsBlockStatus;
	}

	public LocalDateTime getGcsBlockStatusDate() {
		return gcsBlockStatusDate;
	}

	public void setGcsBlockStatusDate(LocalDateTime gcsBlockStatusDate) {
		this.gcsBlockStatusDate = gcsBlockStatusDate;
	}

	public String getGcsDeclareNo() {
		return gcsDeclareNo;
	}

	public void setGcsDeclareNo(String gcsDeclareNo) {
		this.gcsDeclareNo = gcsDeclareNo;
	}

	public LocalDateTime getGcsLastCheck() {
		return gcsLastCheck;
	}

	public void setGcsLastCheck(LocalDateTime gcsLastCheck) {
		this.gcsLastCheck = gcsLastCheck;
	}

	public PrintEir getPrintEir() {
		return printEir;
	}

	public void setPrintEir(PrintEir printEir) {
		this.printEir = printEir;
	}

	public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}

	public String getKpaApproval() {
		return kpaApproval;
	}

	public void setKpaApproval(String kpaApproval) {
		this.kpaApproval = kpaApproval;
	}

	public String getHdlGoodsCode() {
		return hdlGoodsCode;
	}

	public void setHdlGoodsCode(String hdlGoodsCode) {
		this.hdlGoodsCode = hdlGoodsCode;
	}

	public String getDgDescription() {
		return dgDescription;
	}

	public void setDgDescription(String dgDescription) {
		this.dgDescription = dgDescription;
	}

	public String getHdlGoodsDescription() {
		return hdlGoodsDescription;
	}

	public void setHdlGoodsDescription(String hdlGoodsDescription) {
		this.hdlGoodsDescription = hdlGoodsDescription;
	}

	public Boolean getBackToback() {
		return backToback;
	}

	public void setBackToback(Boolean backToback) {
		this.backToback = backToback;
	}

	public Double getWeightDiffPercentage() {
		return weightDiffPercentage;
	}

	public void setWeightDiffPercentage(Double weightDiffPercentage) {
		this.weightDiffPercentage = weightDiffPercentage;
	}

	public Double getWeightDifference() {
		return weightDifference;
	}

	public void setWeightDifference(Double weightDifference) {
		this.weightDifference = weightDifference;
	}

	public DamageCode getDamageCode_01() {
		return damageCode_01;
	}

	public void setDamageCode_01(DamageCode damageCode_01) {
		this.damageCode_01 = damageCode_01;
	}

	public DamageCode getDamageCode_02() {
		return damageCode_02;
	}

	public void setDamageCode_02(DamageCode damageCode_02) {
		this.damageCode_02 = damageCode_02;
	}

	public DamageCode getDamageCode_03() {
		return damageCode_03;
	}

	public void setDamageCode_03(DamageCode damageCode_03) {
		this.damageCode_03 = damageCode_03;
	}

	public DamageCode getDamageCode_04() {
		return damageCode_04;
	}

	public void setDamageCode_04(DamageCode damageCode_04) {
		this.damageCode_04 = damageCode_04;
	}

	public DamageCode getDamageCode_05() {
		return damageCode_05;
	}

	public void setDamageCode_05(DamageCode damageCode_05) {
		this.damageCode_05 = damageCode_05;
	}

	public DamageCode getDamageCode_06() {
		return damageCode_06;
	}

	public void setDamageCode_06(DamageCode damageCode_06) {
		this.damageCode_06 = damageCode_06;
	}

	public DamageCode getDamageCode_07() {
		return damageCode_07;
	}

	public void setDamageCode_07(DamageCode damageCode_07) {
		this.damageCode_07 = damageCode_07;
	}

	public DamageCode getDamageCode_08() {
		return damageCode_08;
	}

	public void setDamageCode_08(DamageCode damageCode_08) {
		this.damageCode_08 = damageCode_08;
	}

	public DamageCode getDamageCode_09() {
		return damageCode_09;
	}

	public void setDamageCode_09(DamageCode damageCode_09) {
		this.damageCode_09 = damageCode_09;
	}

	public Boolean getDontValidateSeal() {
		return dontValidateSeal;
	}

	public void setDontValidateSeal(Boolean dontValidateSeal) {
		this.dontValidateSeal = dontValidateSeal;
	}

	public Boolean getWrongDoor() {
		return wrongDoor;
	}

	public void setWrongDoor(Boolean wrongDoor) {
		this.wrongDoor = wrongDoor;
	}

	public String getHpabISOCode() {
		return hpabISOCode;
	}

	public void setHpabISOCode(String hpabISOCode) {
		this.hpabISOCode = hpabISOCode;
	}

	public String getCosmosISOCode() {
		return cosmosISOCode;
	}

	public void setCosmosISOCode(String cosmosISOCode) {
		this.cosmosISOCode = cosmosISOCode;
	}

	public String getPmWeight() {
		return pmWeight;
	}

	public void setPmWeight(String pmWeight) {
		this.pmWeight = pmWeight;
	}

	public String getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(String trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	public String getTrailerPlateNo() {
		return trailerPlateNo;
	}

	public void setTrailerPlateNo(String trailerPlateNo) {
		this.trailerPlateNo = trailerPlateNo;
	}

	public String getFuelWeight() {
		return fuelWeight;
	}

	public void setFuelWeight(String fuelWeight) {
		this.fuelWeight = fuelWeight;
	}

	public String getTireWeight() {
		return tireWeight;
	}

	public void setTireWeight(String tireWeight) {
		this.tireWeight = tireWeight;
	}

	public String getVariance() {
		return variance;
	}

	public void setVariance(String variance) {
		this.variance = variance;
	}

	public String getSubHandlingType() {
		return subHandlingType;
	}

	public void setSubHandlingType(String subHandlingType) {
		this.subHandlingType = subHandlingType;
	}

	public boolean isWithinTolerance() {
		return withinTolerance;
	}

	public void setWithinTolerance(boolean withinTolerance) {
		this.withinTolerance = withinTolerance;
	}

	public String getCalculatedVariance() {
		return calculatedVariance;
	}

	public void setCalculatedVariance(String calculatedVariance) {
		this.calculatedVariance = calculatedVariance;
	}

	public String getSolasCertNo() {
		return solasCertNo;
	}

	public void setSolasCertNo(String solasCertNo) {
		this.solasCertNo = solasCertNo;
	}

	public CommonSolasAttribute getSolas() {
		return solas;
	}

	public void setSolas(CommonSolasAttribute solas) {
		this.solas = solas;
	}

	public BaseCommonGateInOutAttribute getBaseCommonGateInOutAttribute() {
		return baseCommonGateInOutAttribute;
	}

	public void setBaseCommonGateInOutAttribute(BaseCommonGateInOutAttribute baseCommonGateInOutAttribute) {
		this.baseCommonGateInOutAttribute = baseCommonGateInOutAttribute;
	}

	public ExportOPTFlagType getManualPlanIndicator() {
		return manualPlanIndicator;
	}

	public void setManualPlanIndicator(ExportOPTFlagType manualPlanIndicator) {
		this.manualPlanIndicator = manualPlanIndicator;
	}

	public Boolean getReferFlag() {
		return referFlag;
	}

	public void setReferFlag(Boolean referFlag) {
		this.referFlag = referFlag;
	}

	public ReferTempType getReferTempType() {
		return referTempType;
	}

	public void setReferTempType(ReferTempType referTempType) {
		this.referTempType = referTempType;
	}

	public Double getReferTemp() {
		return referTemp;
	}

	public void setReferTemp(Double referTemp) {
		this.referTemp = referTemp;
	}

	public String getImdg() {
		return imdg;
	}

	public void setImdg(String imdg) {
		this.imdg = imdg;
	}

	public String getDgUNCode() {
		return dgUNCode;
	}

	public void setDgUNCode(String dgUNCode) {
		this.dgUNCode = dgUNCode;
	}

	public String getImdgLabelID() {
		return imdgLabelID;
	}

	public void setImdgLabelID(String imdgLabelID) {
		this.imdgLabelID = imdgLabelID;
	}

	public Integer getOogOH() {
		return oogOH;
	}

	public void setOogOH(Integer oogOH) {
		this.oogOH = oogOH;
	}

	public Integer getOogOL() {
		return oogOL;
	}

	public void setOogOL(Integer oogOL) {
		this.oogOL = oogOL;
	}

	public Integer getOogOF() {
		return oogOF;
	}

	public void setOogOF(Integer oogOF) {
		this.oogOF = oogOF;
	}

	public Integer getOogOA() {
		return oogOA;
	}

	public void setOogOA(Integer oogOA) {
		this.oogOA = oogOA;
	}

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public Integer getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Integer tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getCosmosNetWeight() {
		return cosmosNetWeight;
	}

	public void setCosmosNetWeight(Integer cosmosNetWeight) {
		this.cosmosNetWeight = cosmosNetWeight;
	}
	
	public boolean isDirectEntry() {
		return directEntry;
	}

	public void setDirectEntry(boolean directEntry) {
		this.directEntry = directEntry;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void prepareForInsertFromOpus(SystemUser gateInClerk, Card card, Client gateInClient, ShipSCN scn,
			HPABBooking hpabBooking, DamageCodeRepository damageCodeRepository) {
		if (this.getBaseCommonGateInOutAttribute() == null) {
			this.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutAttribute());
		}
		if (this.getBaseCommonGateInOutAttribute().getGateInClerk() == null) {
			this.getBaseCommonGateInOutAttribute().setGateInClerk(gateInClerk);
		}
		this.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
		this.getBaseCommonGateInOutAttribute().setCard(card);
		this.setManualPlanIndicator(ExportOPTFlagType.OPTFLAG_NORMAL);
		this.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS);
		this.setBookingNo(CommonUtil.changeCase(this.bookingNo, CommonUtil.UPPER_CASE));
		if (this.container == null) {
			this.setContainer(new CommonContainerAttribute());
		}
		this.getContainer().setContainerNumber(
				CommonUtil.changeCase(this.getContainer().getContainerNumber(), CommonUtil.UPPER_CASE));
		this.getBaseCommonGateInOutAttribute().setGateInClient(gateInClient);
		if (this.commonGateInOut == null) {
			this.setCommonGateInOut(new CommonGateInOutAttribute());
		}
		this.getCommonGateInOut().setImpExpFlag(ImpExpFlagStatus.EXPORT);
		this.setShipSCN(shipSCN);
		this.setShippingLine(CommonUtil.changeCase(this.shippingLine, CommonUtil.UPPER_CASE));
		this.setExpOut(CommonUtil.changeCase(this.expOut, CommonUtil.UPPER_CASE));
		this.setExpCar(CommonUtil.changeCase(this.expCar, CommonUtil.UPPER_CASE));
		this.setExpSpod(CommonUtil.changeCase(this.expSpod, CommonUtil.UPPER_CASE));
		this.getContainer().setContainerISOCode(
				CommonUtil.changeCase(this.getContainer().getContainerISOCode(), CommonUtil.UPPER_CASE));
		if (this.sealAttribute == null) {
			this.setSealAttribute(new CommonSealAttribute());
		}
		this.getSealAttribute().setSeal01Origin(
				CommonUtil.changeCase(this.getSealAttribute().getSeal01Origin(), CommonUtil.UPPER_CASE));
		this.getSealAttribute()
				.setSeal01Type(CommonUtil.changeCase(this.getSealAttribute().getSeal01Type(), CommonUtil.UPPER_CASE));
		this.getSealAttribute().setSeal01Number(
				CommonUtil.changeCase(this.getSealAttribute().getSeal01Number(), CommonUtil.UPPER_CASE));
		this.getSealAttribute().setSeal02Origin(
				CommonUtil.changeCase(this.getSealAttribute().getSeal02Origin(), CommonUtil.UPPER_CASE));
		this.getSealAttribute()
				.setSeal02Type(CommonUtil.changeCase(this.getSealAttribute().getSeal02Type(), CommonUtil.UPPER_CASE));
		this.getSealAttribute().setSeal02Number(
				CommonUtil.changeCase(this.getSealAttribute().getSeal02Number(), CommonUtil.UPPER_CASE));
		this.setImdg(CommonUtil.changeCase(this.imdg, CommonUtil.UPPER_CASE));
		this.setDgUNCode(CommonUtil.changeCase(this.dgUNCode, CommonUtil.UPPER_CASE));
		this.setImdgLabelID(CommonUtil.changeCase(this.imdgLabelID, CommonUtil.UPPER_CASE));
		if (!(this.damageCode_01 == null)) {
			this.getDamageCode_01().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_01().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_01().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_01().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_01().getDamageCode())) {
				this.damageCode_01 = damageCodeRepository.findOne(this.getDamageCode_01().getDamageCode()).orElse(null);
			} else {
				this.damageCode_01 = null;
			}

		}
		if (!(this.damageCode_02 == null)) {
			this.getDamageCode_02().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_02().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_02().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_02().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_02().getDamageCode())) {
				this.damageCode_02 = damageCodeRepository.findOne(this.getDamageCode_02().getDamageCode()).orElse(null);
			} else {
				this.damageCode_02 = null;
			}

		}
		if (!(this.damageCode_03 == null)) {
			this.getDamageCode_03().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_03().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_03().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_03().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_03().getDamageCode())) {
				this.damageCode_03 = damageCodeRepository.findOne(this.getDamageCode_03().getDamageCode()).orElse(null);
			} else {
				this.damageCode_03 = null;
			}

		}
		if (!(this.damageCode_04 == null)) {
			this.getDamageCode_04().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_04().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_04().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_04().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_04().getDamageCode())) {
				this.damageCode_04 = damageCodeRepository.findOne(this.getDamageCode_04().getDamageCode()).orElse(null);
			} else {
				this.damageCode_04 = null;
			}

		}
		if (!(this.damageCode_05 == null)) {
			this.getDamageCode_05().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_05().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_05().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_05().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_05().getDamageCode())) {
				this.damageCode_05 = damageCodeRepository.findOne(this.getDamageCode_05().getDamageCode()).orElse(null);
			} else {
				this.damageCode_05 = null;
			}

		}
		if (!(this.damageCode_06 == null)) {
			this.getDamageCode_06().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_06().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_06().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_06().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_06().getDamageCode())) {
				this.damageCode_06 = damageCodeRepository.findOne(this.getDamageCode_06().getDamageCode()).orElse(null);
			} else {
				this.damageCode_06 = null;
			}

		}
		if (!(this.damageCode_07 == null)) {
			this.getDamageCode_07().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_07().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_07().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_07().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_07().getDamageCode())) {
				this.damageCode_07 = damageCodeRepository.findOne(this.getDamageCode_07().getDamageCode()).orElse(null);
			} else {
				this.damageCode_07 = null;
			}

		}
		if (!(this.damageCode_08 == null)) {
			this.getDamageCode_08().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_08().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_08().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_08().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_08().getDamageCode())) {
				this.damageCode_08 = damageCodeRepository.findOne(this.getDamageCode_08().getDamageCode()).orElse(null);
			} else {
				this.damageCode_08 = null;
			}

		}
		if (!(this.damageCode_09 == null)) {
			this.getDamageCode_09().setDamageCode(
					CommonUtil.changeCase(this.getDamageCode_09().getDamageCode(), CommonUtil.UPPER_CASE));
			this.getDamageCode_09().setDamageDesc(
					CommonUtil.changeCase(this.getDamageCode_09().getDamageDesc(), CommonUtil.UPPER_CASE));

			if (StringUtils.isNotEmpty(this.getDamageCode_09().getDamageCode())) {
				this.damageCode_09 = damageCodeRepository.findOne(this.getDamageCode_09().getDamageCode()).orElse(null);
			} else {
				this.damageCode_09 = null;
			}

		}
		this.getBaseCommonGateInOutAttribute().setPmHeadNo(
				CommonUtil.changeCase(this.getBaseCommonGateInOutAttribute().getPmHeadNo(), CommonUtil.UPPER_CASE));
		this.getBaseCommonGateInOutAttribute().setPmPlateNo(
				CommonUtil.changeCase(this.getBaseCommonGateInOutAttribute().getPmPlateNo(), CommonUtil.UPPER_CASE));
		this.setYardPosition(CommonUtil.changeCase(this.yardPosition, CommonUtil.UPPER_CASE));
		this.setYardBayCode(CommonUtil.changeCase(this.yardBayCode, CommonUtil.UPPER_CASE));
		this.setExpAgent(CommonUtil.changeCase(this.expAgent, CommonUtil.UPPER_CASE));
		this.getCommonGateInOut().setRejectReason(
				CommonUtil.changeCase(this.getCommonGateInOut().getRejectReason(), CommonUtil.UPPER_CASE));
		this.setSubHandlingType(CommonUtil.changeCase(this.subHandlingType, CommonUtil.UPPER_CASE));
		this.setVesselCode(CommonUtil.changeCase(this.vesselCode, CommonUtil.UPPER_CASE));
		this.setVesselName(CommonUtil.changeCase(this.vesselName, CommonUtil.UPPER_CASE));
		this.setVesselSCN(CommonUtil.changeCase(this.vesselSCN, CommonUtil.UPPER_CASE));
		this.setVesselVisitID(CommonUtil.changeCase(this.vesselVisitID, CommonUtil.UPPER_CASE));
		this.setVesselVoyageIN(CommonUtil.changeCase(this.vesselVoyageIN, CommonUtil.UPPER_CASE));
		this.setGcsBlockStatusDate(LocalDateTime.now());
		if (!(this.ssrBlockStatus == null)) {
			if (this.ssrBlockStatus == SSRBlockType.BLK) {
				this.setSsrBlockStatusDate(LocalDateTime.now());
			}
		}
		this.setGcsLastCheck(LocalDateTime.now());
		this.setPrintEir(printEir);
		this.setUserRemarks(CommonUtil.changeCase(this.userRemarks, CommonUtil.UPPER_CASE));
		this.setKpaApproval(CommonUtil.changeCase(this.kpaApproval, CommonUtil.LOWER_CASE));
		this.setHdlGoodsCode(CommonUtil.changeCase(this.hdlGoodsCode, CommonUtil.LOWER_CASE));
		this.setDgDescription(CommonUtil.changeCase(this.dgDescription, CommonUtil.LOWER_CASE));
		this.setHdlGoodsDescription(CommonUtil.changeCase(this.hdlGoodsDescription, CommonUtil.LOWER_CASE));
		this.getBaseCommonGateInOutAttribute().setHpabBooking(hpabBooking);
		calculateWeightPercentages();
		this.setHpabISOCode(CommonUtil.changeCase(this.hpabISOCode, CommonUtil.UPPER_CASE));
		this.setCosmosISOCode(CommonUtil.changeCase(this.cosmosISOCode, CommonUtil.UPPER_CASE));
		this.setFuelWeight(CommonUtil.changeCase(this.fuelWeight, CommonUtil.UPPER_CASE));
		this.setTireWeight(CommonUtil.changeCase(this.tireWeight, CommonUtil.UPPER_CASE));
		this.setVariance(CommonUtil.changeCase(this.variance, CommonUtil.UPPER_CASE));
		this.setTrailerPlateNo(CommonUtil.changeCase(this.trailerPlateNo, CommonUtil.UPPER_CASE));
		this.setTrailerWeight(CommonUtil.changeCase(this.trailerWeight, CommonUtil.UPPER_CASE));
		this.setPmWeight(CommonUtil.changeCase(this.pmWeight, CommonUtil.UPPER_CASE));
		if (this.solas == null) {
			this.setSolas(new CommonSolasAttribute());
		}
		this.getSolas().setShipperVGM(CommonUtil.changeCase(this.getSolas().getShipperVGM(), CommonUtil.UPPER_CASE));
		this.setCalculatedVariance(calculatedVariance);
		this.getSolas()
				.setSolasDetailID(CommonUtil.changeCase(this.getSolas().getSolasDetailID(), CommonUtil.LOWER_CASE));
		this.getSolas()
				.setFaLedgerCode(CommonUtil.changeCase(this.getSolas().getFaLedgerCode(), CommonUtil.UPPER_CASE));
		this.getSolas()
				.setSolasRefNumber(CommonUtil.changeCase(this.getSolas().getSolasRefNumber(), CommonUtil.LOWER_CASE));
	}

	private void calculateWeightPercentages() {
		double weightDifferentPercentage = 0;
		double weightDifferent = 0;
		double grossWeightCosmos = 0;
		double netWeight = 0;
		if (!(this.grossWeight == null)) {
			grossWeightCosmos = new Double(this.grossWeight.doubleValue());
		} else {
			double comsosTareWeight = 0;
			double cosmosNetWeight = 0;
			if (!(this.tareWeight == null)) {
				comsosTareWeight = new Double(this.tareWeight.doubleValue());
			}
			if (!(this.cosmosNetWeight == null)) {
				cosmosNetWeight = new Double(this.cosmosNetWeight.doubleValue());
			}
			grossWeightCosmos = comsosTareWeight + cosmosNetWeight;
		}
		if (!(this.expNetWeight == null)) {
			netWeight = new Double(this.expNetWeight.doubleValue());
		}
		if (netWeight > 0) {
			weightDifferentPercentage = Math.round(100 - ((grossWeightCosmos / netWeight) * 100));
		} else {
			weightDifferentPercentage = 100;
		}
		weightDifferent = grossWeightCosmos - netWeight;
		this.setWeightDifference(weightDifferent);
		this.setWeightDiffPercentage(weightDifferentPercentage);
	}

}
