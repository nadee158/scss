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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.GatePassStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.util.CommonUtil;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_GATE_PASS")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "GTP_ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "GTP_UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "GTP_DATECREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "GTP_DATEUPDATE")) })
public class GatePass extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GTP_PASSNO")
	private Long gatePassID;

	@Column(name = "GTP_GATEORDERNO")
	private Long gateOrderNo;

	@Column(name = "GTP_GATEPASSNO")
	private Long gatePassNo;

	@Column(name = "CONT_LENGTH")
	@Type(type = "com.privasia.scss.common.enumusertype.ContainerSizeEnumUserType")
	private ContainerSize containerLength = ContainerSize.SIZE_0;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "GTP_CONTAINERNO")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "GTP_CONT_ISO_CODE")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "GTP_FULLEMPTYFLAG")) })
	private CommonContainerAttribute container;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GTP_HCID", nullable = false, referencedColumnName = "COM_ID_SEQ")
	private Company company;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "eirNumber", column = @Column(name = "GTP_EIRNO")),
			@AttributeOverride(name = "impExpFlag", column = @Column(name = "GTP_IMPEXPFLAG", nullable = true)),
			@AttributeOverride(name = "rejectReason", column = @Column(name = "GTP_REJECTREASON")),
			@AttributeOverride(name = "kioskConfirmed", column = @Column(name = "KIOSK_CONFIRMED")),
			@AttributeOverride(name = "kioskCancelPickUp", column = @Column(name = "KIOSK_CANCEL_PICKUP")),
			@AttributeOverride(name = "gateInStatus", column = @Column(name = "GTP_GATEIN_STATUS")),
			@AttributeOverride(name = "zipFileNo", column = @Column(name = "ZIP_FILE_NO")),
			@AttributeOverride(name = "trxSlipNo", column = @Column(name = "TRX_SLIP_NO")) })
	private CommonGateInOutAttribute commonGateInOut;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "pmHeadNo", column = @Column(name = "GTP_TRUCK_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "GTP_TRUCK_PLATE_NO")),
			@AttributeOverride(name = "hpabBooking", column = @Column(name = "BOOKING_ID")),
			@AttributeOverride(name = "eirStatus", column = @Column(name = "GTP_EIRSTATUS", nullable = true)),
			@AttributeOverride(name = "transactionSlipPrinted", column = @Column(name = "TRANSACTION_SLIP_PRINTED")),
			@AttributeOverride(name = "gateOutBoothNo", column = @Column(name = "GTP_GATE_OUT_BOOTH_NO")),
			@AttributeOverride(name = "timeGateIn", column = @Column(name = "GTP_TIMEGATEIN")),
			@AttributeOverride(name = "timeGateInOk", column = @Column(name = "GTP_TIMEGATEINOK")),
			@AttributeOverride(name = "timeGateOut", column = @Column(name = "GTP_TIMEGATEOUT")),
			@AttributeOverride(name = "timeGateOutOk", column = @Column(name = "GTP_TIMEGATEOUTOK")),
			@AttributeOverride(name = "timeGateOutBooth", column = @Column(name = "GTP_TIMEGATEOUT_BOOTH")) })
	@AssociationOverrides({
			@AssociationOverride(name = "gateOutBoothClerk", joinColumns = @JoinColumn(name = "GTP_GATEOUT_BOOTH_CLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "card", joinColumns = @JoinColumn(name = "GTP_HCTDID", referencedColumnName = "CRD_CARDID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateInClerk", joinColumns = @JoinColumn(name = "GTP_GATEINCLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateOutClerk", joinColumns = @JoinColumn(name = "GTP_GATEOUTCLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateInClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEIN", referencedColumnName = "CLI_CLIENTID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateOutClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEOUT", referencedColumnName = "CLI_CLIENTID_SEQ", nullable = true)) })
	private BaseCommonGateInOutAttribute baseCommonGateInOutAttribute;

	@Column(name = "GTP_GATEPASSSTATUS")
	@Type(type = "com.privasia.scss.common.enumusertype.GatePassStatusEnumUserType")
	private GatePassStatus gatePassStatus;

	@Column(name = "GTP_HANDLINGID")
	private Long handlingID;

	@Column(name = "GTP_ORDER_NO")
	private String orderNo;

	@Column(name = "GTP_IN_OUT")
	@Type(type = "com.privasia.scss.common.enumusertype.GateInOutStatusEnumUserType")
	private GateInOutStatus gateInOut;

	@Column(name = "GTP_LINE")
	private String shippingLine;

	@Column(name = "GTP_CUR_POS")
	private String currentPosition;

	@Column(name = "GTP_TRUK_POS")
	@Type(type = "com.privasia.scss.common.enumusertype.ContainerPositionEnumUserType")
	private ContainerPosition containerPosition;

	@Column(name = "GTP_GATE_IN_LANE_NO")
	private String gateInLaneNo;

	@Column(name = "GTP_GATE_OUT_LANE_NO")
	private String gateOutLaneNo;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "seal01Origin", column = @Column(name = "GTP_SEAL_1_ORIGIN")),
			@AttributeOverride(name = "seal01Type", column = @Column(name = "GTP_SEAL_1_TYPE")),
			@AttributeOverride(name = "seal01Number", column = @Column(name = "GTP_SEAL_1_NO")),
			@AttributeOverride(name = "seal02Origin", column = @Column(name = "GTP_SEAL_2_ORIGIN")),
			@AttributeOverride(name = "seal02Type", column = @Column(name = "GTP_SEAL_2_TYPE")),
			@AttributeOverride(name = "seal02Number", column = @Column(name = "GTP_SEAL_2_NO")) })
	private CommonSealAttribute sealAttribute;

	@Column(name = "GTP_GOUT_REMARKS")
	private String gateOutRemarks;

	@Column(name = "GTP_YARD_POSITION")
	private String yardPosition;

	@Column(name = "GTP_BAY_CODE")
	private String bayCode;

	@Column(name = "GTP_CALL_CARD")
	private Long callCard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GTP_PRINT_EIR", nullable = true, referencedColumnName = "PRINT_NO")
	private PrintEir printEir;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUG_ID_SEQ", nullable = true, referencedColumnName = "CUG_ID_SEQ")
	private CardUsage cardUsage;

	@Column(name = "COSMOS_GTP_SEAL_1_ORIGIN")
	private String cosmosSeal01Origin = StringUtils.EMPTY;

	@Column(name = "COSMOS_GTP_SEAL_1_TYPE")
	private String cosmosSeal01Type = StringUtils.EMPTY;

	@Column(name = "COSMOS_GTP_SEAL_1_NO")
	private String cosmosSeal01Number = StringUtils.EMPTY;

	@Column(name = "COSMOS_GTP_SEAL_2_ORIGIN")
	private String cosmosSeal02Origin = StringUtils.EMPTY;

	@Column(name = "COSMOS_GTP_SEAL_2_TYPE")
	private String cosmosSeal02Type = StringUtils.EMPTY;

	@Column(name = "COSMOS_GTP_SEAL_2_NO")
	private String cosmosSeal02Number = StringUtils.EMPTY;

	@Column(name = "DATE_GATEPASS_VALID")
	private LocalDateTime gatePassValidDate;

	@Column(name = "IS_RETRIEVED_COSMOS", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean retrievedCosmos;

	@Column(name = "IS_CHANGE_SEAL", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean sealChange = false;

	@Column(name = "FORCED_SEAL")
	@Type(type = "yes_no")
	private Boolean forcedSeal;

	public Long getGatePassID() {
		return gatePassID;
	}

	public void setGatePassID(Long gatePassID) {
		this.gatePassID = gatePassID;
	}

	public Long getGateOrderNo() {
		return gateOrderNo;
	}

	public void setGateOrderNo(Long gateOrderNo) {
		this.gateOrderNo = gateOrderNo;
	}

	public Long getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(Long gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public CommonContainerAttribute getContainer() {
		return container;
	}

	public void setContainer(CommonContainerAttribute container) {
		this.container = container;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CommonGateInOutAttribute getCommonGateInOut() {
		return commonGateInOut;
	}

	public void setCommonGateInOut(CommonGateInOutAttribute commonGateInOut) {
		this.commonGateInOut = commonGateInOut;
	}

	public GatePassStatus getGatePassStatus() {
		return gatePassStatus;
	}

	public void setGatePassStatus(GatePassStatus gatePassStatus) {
		this.gatePassStatus = gatePassStatus;
	}

	public Long getHandlingID() {
		return handlingID;
	}

	public void setHandlingID(Long handlingID) {
		this.handlingID = handlingID;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public ContainerPosition getContainerPosition() {
		return containerPosition;
	}

	public void setContainerPosition(ContainerPosition containerPosition) {
		this.containerPosition = containerPosition;
	}

	public String getGateInLaneNo() {
		return gateInLaneNo;
	}

	public void setGateInLaneNo(String gateInLaneNo) {
		this.gateInLaneNo = gateInLaneNo;
	}

	public String getGateOutLaneNo() {
		return gateOutLaneNo;
	}

	public void setGateOutLaneNo(String gateOutLaneNo) {
		this.gateOutLaneNo = gateOutLaneNo;
	}

	public CommonSealAttribute getSealAttribute() {
		return sealAttribute;
	}

	public void setSealAttribute(CommonSealAttribute sealAttribute) {
		this.sealAttribute = sealAttribute;
	}

	public String getGateOutRemarks() {
		return gateOutRemarks;
	}

	public void setGateOutRemarks(String gateOutRemarks) {
		if (StringUtils.isNotEmpty(gateOutRemarks)) {
			gateOutRemarks = gateOutRemarks.toUpperCase();
		}
		this.gateOutRemarks = gateOutRemarks;
	}

	public String getYardPosition() {
		return yardPosition;
	}

	public void setYardPosition(String yardPosition) {
		this.yardPosition = yardPosition;
	}

	public String getBayCode() {
		return bayCode;
	}

	public void setBayCode(String bayCode) {
		this.bayCode = bayCode;
	}

	public Long getCallCard() {
		return callCard;
	}

	public void setCallCard(Long callCard) {
		this.callCard = callCard;
	}

	public PrintEir getPrintEir() {
		return printEir;
	}

	public void setPrintEir(PrintEir printEir) {
		this.printEir = printEir;
	}

	public CardUsage getCardUsage() {
		return cardUsage;
	}

	public void setCardUsage(CardUsage cardUsage) {
		this.cardUsage = cardUsage;
	}

	public String getCosmosSeal01Origin() {
		return cosmosSeal01Origin;
	}

	public void setCosmosSeal01Origin(String cosmosSeal01Origin) {
		if (StringUtils.isNotEmpty(cosmosSeal01Origin)) {
			cosmosSeal01Origin = cosmosSeal01Origin.toUpperCase();
		}
		this.cosmosSeal01Origin = cosmosSeal01Origin;
	}

	public String getCosmosSeal01Type() {
		return cosmosSeal01Type;
	}

	public void setCosmosSeal01Type(String cosmosSeal01Type) {
		if (StringUtils.isNotEmpty(cosmosSeal01Type)) {
			cosmosSeal01Type = cosmosSeal01Type.toUpperCase();
		}
		this.cosmosSeal01Type = cosmosSeal01Type;
	}

	public String getCosmosSeal01Number() {
		return cosmosSeal01Number;
	}

	public void setCosmosSeal01Number(String cosmosSeal01Number) {
		if (StringUtils.isNotEmpty(cosmosSeal01Number)) {
			cosmosSeal01Number = cosmosSeal01Number.toUpperCase();
		}
		this.cosmosSeal01Number = cosmosSeal01Number;
	}

	public String getCosmosSeal02Origin() {
		return cosmosSeal02Origin;
	}

	public void setCosmosSeal02Origin(String cosmosSeal02Origin) {
		if (StringUtils.isNotEmpty(cosmosSeal02Origin)) {
			cosmosSeal02Origin = cosmosSeal02Origin.toUpperCase();
		}
		this.cosmosSeal02Origin = cosmosSeal02Origin;
	}

	public String getCosmosSeal02Type() {
		return cosmosSeal02Type;
	}

	public void setCosmosSeal02Type(String cosmosSeal02Type) {
		if (StringUtils.isNotEmpty(cosmosSeal02Type)) {
			cosmosSeal02Type = cosmosSeal02Type.toUpperCase();
		}
		this.cosmosSeal02Type = cosmosSeal02Type;
	}

	public String getCosmosSeal02Number() {
		return cosmosSeal02Number;
	}

	public void setCosmosSeal02Number(String cosmosSeal02Number) {
		if (StringUtils.isNotEmpty(cosmosSeal02Type)) {
			cosmosSeal02Type = cosmosSeal02Type.toUpperCase();
		}
		this.cosmosSeal02Number = cosmosSeal02Number;
	}

	public Boolean getRetrievedCosmos() {
		return retrievedCosmos;
	}

	public void setRetrievedCosmos(Boolean retrievedCosmos) {
		this.retrievedCosmos = retrievedCosmos;
	}

	public Boolean getSealChange() {
		return sealChange;
	}

	public void setSealChange(Boolean sealChange) {
		this.sealChange = sealChange;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getForcedSeal() {
		return forcedSeal;
	}

	public void setForcedSeal(Boolean forcedSeal) {
		this.forcedSeal = forcedSeal;
	}

	public LocalDateTime getGatePassValidDate() {
		return gatePassValidDate;
	}

	public void setGatePassValidDate(LocalDateTime gatePassValidDate) {
		this.gatePassValidDate = gatePassValidDate;
	}

	public BaseCommonGateInOutAttribute getBaseCommonGateInOutAttribute() {
		return baseCommonGateInOutAttribute;
	}

	public void setBaseCommonGateInOutAttribute(BaseCommonGateInOutAttribute baseCommonGateInOutAttribute) {
		this.baseCommonGateInOutAttribute = baseCommonGateInOutAttribute;
	}

	public ContainerSize getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(ContainerSize containerLength) {
		this.containerLength = containerLength;
	}

	@Override
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean checkChangeSeal() {

		String seal1C1 = StringUtils.trim(getSealAttribute().getSeal01Number());
		String seal2C1 = StringUtils.trim(getSealAttribute().getSeal01Number());

		String cosmosSeal1C1 = StringUtils.trim(this.getCosmosSeal01Number());
		String cosmosSeal2C1 = StringUtils.trim(this.getCosmosSeal02Number());
		sealChange = false;

		if (StringUtils.isNotBlank(cosmosSeal1C1) && StringUtils.isNotBlank(cosmosSeal2C1)) {
			if (seal1C1.equalsIgnoreCase(cosmosSeal1C1) || seal1C1.equalsIgnoreCase(cosmosSeal2C1)) {
			} else {
				if (seal2C1.equalsIgnoreCase(cosmosSeal1C1) || seal2C1.equalsIgnoreCase(cosmosSeal2C1)) {
				} else {
					sealChange = true;
				}
			}
			if (seal2C1.equalsIgnoreCase(cosmosSeal1C1) || seal2C1.equalsIgnoreCase(cosmosSeal2C1)) {
			} else {
				if (seal1C1.equalsIgnoreCase(cosmosSeal1C1) || seal1C1.equalsIgnoreCase(cosmosSeal2C1)) {
				} else {
					sealChange = true;
				}
			}

		} else if (StringUtils.isNotBlank(cosmosSeal1C1)) {
			if (seal1C1.equalsIgnoreCase(cosmosSeal1C1) || seal2C1.equalsIgnoreCase(cosmosSeal1C1)) {
			} else {
				sealChange = true;
			}
		} else if (StringUtils.isNotBlank(cosmosSeal2C1)) {
			if (seal1C1.equalsIgnoreCase(cosmosSeal2C1) || seal2C1.equalsIgnoreCase(cosmosSeal2C1)) {
			} else {
				sealChange = true;
			}
		}

		return sealChange;
	}

	public void prepareForInsertFromOpus(Card card, SystemUser gateInClerk, Client gateInClient,
			HPABBooking hpabBooking) {
		if (this.baseCommonGateInOutAttribute == null) {
			this.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutAttribute());
		}
		this.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS);
		this.getBaseCommonGateInOutAttribute().setCard(card);
		this.getBaseCommonGateInOutAttribute().setGateInClerk(gateInClerk);
		this.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
		this.getBaseCommonGateInOutAttribute().setGateInClient(gateInClient);

		if (this.commonGateInOut == null) {
			this.setCommonGateInOut(new CommonGateInOutAttribute());
		}
		this.getCommonGateInOut().setImpExpFlag(ImpExpFlagStatus.IMPORT);
		this.setOrderNo(CommonUtil.changeCase(this.orderNo, CommonUtil.UPPER_CASE));
		this.setCurrentPosition(CommonUtil.changeCase(this.currentPosition, CommonUtil.UPPER_CASE));

		if (this.getContainer() == null) {
			this.setContainer(new CommonContainerAttribute());
		}
		this.setShippingLine(CommonUtil.changeCase(this.shippingLine, CommonUtil.UPPER_CASE));
		this.getContainer().setContainerISOCode(
				CommonUtil.changeCase(this.getContainer().getContainerISOCode(), CommonUtil.UPPER_CASE));
		this.setGateInLaneNo(CommonUtil.changeCase(gateInClient.getLaneNo(), CommonUtil.UPPER_CASE));
		this.getBaseCommonGateInOutAttribute().setPmHeadNo(
				CommonUtil.changeCase(this.getBaseCommonGateInOutAttribute().getPmHeadNo(), CommonUtil.UPPER_CASE));
		this.getBaseCommonGateInOutAttribute().setPmPlateNo(
				CommonUtil.changeCase(this.getBaseCommonGateInOutAttribute().getPmPlateNo(), CommonUtil.UPPER_CASE));
		this.setYardPosition(CommonUtil.changeCase(this.yardPosition, CommonUtil.UPPER_CASE));
		this.setBayCode(CommonUtil.changeCase(this.bayCode, CommonUtil.UPPER_CASE));
		this.setPrintEir(printEir);
		this.getBaseCommonGateInOutAttribute().setHpabBooking(hpabBooking);
		this.getCommonGateInOut().setRejectReason(
				CommonUtil.changeCase(this.getCommonGateInOut().getRejectReason(), CommonUtil.UPPER_CASE));

	}

}
