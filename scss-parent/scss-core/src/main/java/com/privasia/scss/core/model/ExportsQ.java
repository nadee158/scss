/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.ExportOPTFlagType;
import com.privasia.scss.common.enums.SSRBlockType;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.ReferTempType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.VesselStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_EXPORTS_Q")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "EXP_ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "EXP_UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "EXP_DATECREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "EXP_DATEUPDATE")) })
public class ExportsQ extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EXP_EXPORTNO_SEQ")
	private Long exportID;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "EXP_CONTAINERNO")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "EXP_CONT_ISO_CODE")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "EXP_FULL_EMPTY_FLAG", nullable = true)) })
	private CommonContainerAttribute container;

	@Column(name = "EXP_EIRNO")
	private Long eirNumber;

	@Column(name = "EXP_IMPEXPFLAG")
	@Type(type = "com.privasia.scss.common.enumusertype.ImpExpFlagEnumUserType")
	private ImpExpFlagStatus impExpFlag;

	@Column(name = "EXP_REJECTREASON")
	private String rejectReason;

	@Column(name = "EXP_GATEIN_STATUS")
	@Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus gateInStatus;

	@Column(name = "ZIP_FILE_NO")
	private String zipFileNo;

	@Column(name = "TRX_SLIP_NO")
	private String trxSlipNo;
	/* COMMON GATE IN ATTRIBUTE STARTS */

	/* BASE COMMON GATE IN ATTRIBUTE STARTS */
	@Column(name = "EXP_TRUCK_HEAD_NO")
	private String pmHeadNo;

	@Column(name = "EXP_TRUCK_PLATE_NO")
	private String pmPlateNo;

	@Column(name = "EXP_EIRSTATUS")
	@Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus eirStatus;

	@Column(name = "EXP_TIMEGATEIN")
	private LocalDateTime timeGateIn;

	@Column(name = "EXP_TIMEGATEINOK")
	private LocalDateTime timeGateInOk;

	@Column(name = "EXP_TIMEGATEOUT")
	private LocalDateTime timeGateOut;

	@Column(name = "EXP_TIMEGATEOUTOK")
	private LocalDateTime timeGateOutOk;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "EXP_HCTDID", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_GATEINCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateInClerk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXP_GATEOUTCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateOutClerk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEIN", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateInClient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEOUT", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateOutClient;
	/* BASE COMMON GATE IN ATTRIBUTE ENDS */

	@Column(name = "EXP_MANUALOPTFLAG", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.ExportOPTFlagEnumUserType")
	private ExportOPTFlagType manualPlanIndicator;

	@Column(name = "EXP_BOOKINGNO")
	private String bookingNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCN_SEQ", nullable = true, referencedColumnName = "SCN_SEQ")
	private ShipSCN scn;

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
	private Integer referTemp;

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
	private String shipID;

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

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public ShipSCN getScn() {
		return scn;
	}

	public void setScn(ShipSCN scn) {
		this.scn = scn;
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

	public Integer getCallCard() {
		return callCard;
	}

	public void setCallCard(Integer callCard) {
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

	public String getShipID() {
		return shipID;
	}

	public void setShipID(String shipID) {
		this.shipID = shipID;
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

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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

	public ExportOPTFlagType getManualPlanIndicator() {
		return manualPlanIndicator;
	}

	public void setManualPlanIndicator(ExportOPTFlagType manualPlanIndicator) {
		this.manualPlanIndicator = manualPlanIndicator;
	}

	public String getYardBayCode() {
		return yardBayCode;
	}

	public void setYardBayCode(String yardBayCode) {
		this.yardBayCode = yardBayCode;
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

	public Integer getReferTemp() {
		return referTemp;
	}

	public void setReferTemp(Integer referTemp) {
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

	public Long getEirNumber() {
		return eirNumber;
	}

	public void setEirNumber(Long eirNumber) {
		this.eirNumber = eirNumber;
	}

	public ImpExpFlagStatus getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(ImpExpFlagStatus impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public TransactionStatus getGateInStatus() {
		return gateInStatus;
	}

	public void setGateInStatus(TransactionStatus gateInStatus) {
		this.gateInStatus = gateInStatus;
	}

	public String getZipFileNo() {
		return zipFileNo;
	}

	public void setZipFileNo(String zipFileNo) {
		this.zipFileNo = zipFileNo;
	}

	public String getTrxSlipNo() {
		return trxSlipNo;
	}

	public void setTrxSlipNo(String trxSlipNo) {
		this.trxSlipNo = trxSlipNo;
	}

	public String getPmHeadNo() {
		return pmHeadNo;
	}

	public void setPmHeadNo(String pmHeadNo) {
		this.pmHeadNo = pmHeadNo;
	}

	public String getPmPlateNo() {
		return pmPlateNo;
	}

	public void setPmPlateNo(String pmPlateNo) {
		this.pmPlateNo = pmPlateNo;
	}

	public TransactionStatus getEirStatus() {
		return eirStatus;
	}

	public void setEirStatus(TransactionStatus eirStatus) {
		this.eirStatus = eirStatus;
	}

	public LocalDateTime getTimeGateIn() {
		return timeGateIn;
	}

	public void setTimeGateIn(LocalDateTime timeGateIn) {
		this.timeGateIn = timeGateIn;
	}

	public LocalDateTime getTimeGateInOk() {
		return timeGateInOk;
	}

	public void setTimeGateInOk(LocalDateTime timeGateInOk) {
		this.timeGateInOk = timeGateInOk;
	}

	public LocalDateTime getTimeGateOut() {
		return timeGateOut;
	}

	public void setTimeGateOut(LocalDateTime timeGateOut) {
		this.timeGateOut = timeGateOut;
	}

	public LocalDateTime getTimeGateOutOk() {
		return timeGateOutOk;
	}

	public void setTimeGateOutOk(LocalDateTime timeGateOutOk) {
		this.timeGateOutOk = timeGateOutOk;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public SystemUser getGateInClerk() {
		return gateInClerk;
	}

	public void setGateInClerk(SystemUser gateInClerk) {
		this.gateInClerk = gateInClerk;
	}

	public SystemUser getGateOutClerk() {
		return gateOutClerk;
	}

	public void setGateOutClerk(SystemUser gateOutClerk) {
		this.gateOutClerk = gateOutClerk;
	}

	public Client getGateInClient() {
		return gateInClient;
	}

	public void setGateInClient(Client gateInClient) {
		this.gateInClient = gateInClient;
	}

	public Client getGateOutClient() {
		return gateOutClient;
	}

	public void setGateOutClient(Client gateOutClient) {
		this.gateOutClient = gateOutClient;
	}

	public boolean isDirectEntry() {
		return directEntry;
	}

	public void setDirectEntry(boolean directEntry) {
		this.directEntry = directEntry;
	}
	
	

}
