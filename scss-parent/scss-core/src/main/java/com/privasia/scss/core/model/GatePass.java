/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.ContainerPosition;
import com.privasia.scss.core.util.constant.GateInOutStatus;
import com.privasia.scss.core.util.constant.GatePassStatus;


/**
 * @author Janaka
 *
 */
//@Entity
//@Table(name = "SCSS_GATE_PASS")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "GTP_ADD_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "GTP_UPDATE_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "GTP_DATECREATE")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "GTP_DATEUPDATE"))})
public class GatePass extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "GTP_PASSNO")
  private Long gatePassID;

  @Id
  @Column(name = "GTP_GATEORDERNO")
  private Long gateOrderNo;

  @Id
  @Column(name = "GTP_GATEPASSNO")
  private Long gatePassNo;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "GTP_CONTAINERNO")),
      @AttributeOverride(name = "containerISOCode", column = @Column(name = "GTP_CONT_ISO_CODE")),
      @AttributeOverride(name = "containerLength", column = @Column(name = "CONT_LENGTH")),
      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "GTP_FULLEMPTYFLAG"))})
  private CommonContainerAttribute container;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GTP_HCID", nullable = false, referencedColumnName = "COM_ID_SEQ")
  private Company company;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "card", column = @Column(name = "GTP_HCTDID")),
      @AttributeOverride(name = "gateInClerk", column = @Column(name = "GTP_GATEINCLERKID")),
      @AttributeOverride(name = "timeGateIn", column = @Column(name = "GTP_TIMEGATEIN")),
      @AttributeOverride(name = "timeGateInOk", column = @Column(name = "GTP_TIMEGATEINOK")),
      @AttributeOverride(name = "gateOutClerk", column = @Column(name = "GTP_GATEOUTCLERKID")),
      @AttributeOverride(name = "timeGateOut", column = @Column(name = "GTP_TIMEGATEOUT")),
      @AttributeOverride(name = "timeGateOutOk", column = @Column(name = "GTP_TIMEGATEOUTOK")),
      @AttributeOverride(name = "eirNumber", column = @Column(name = "GTP_EIRNO")),
      @AttributeOverride(name = "eirStatus", column = @Column(name = "GTP_EIRSTATUS")),
      @AttributeOverride(name = "gateInClient", column = @Column(name = "CLI_CLIENTID_GATEIN")),
      @AttributeOverride(name = "gateOutClerk", column = @Column(name = "CLI_CLIENTID_GATEOUT")),
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

  })
  private CommonGateInOutAttribute commonGateInOut;

  @Column(name = "GTP_GATEPASSSTATUS")
  @Enumerated(EnumType.STRING)
  private GatePassStatus gatePassStatus;

  @Column(name = "GTP_HANDLINGID")
  private Long handlingID;

  @Column(name = "GTP_ORDER_NO")
  private String orderNo;

  @Column(name = "GTP_IN_OUT")
  @Enumerated(EnumType.STRING)
  private GateInOutStatus gateInOut;

  @Column(name = "GTP_LINE")
  private String line;

  @Column(name = "GTP_CUR_POS")
  private String currentPosition;

  @Enumerated(EnumType.STRING)
  @Column(name = "GTP_TRUK_POS")
  private ContainerPosition containerPosition;

  @Column(name = "GTP_GATE_IN_LANE_NO")
  private String gateInLaneNo;

  @Column(name = "GTP_GATE_OUT_LANE_NO")
  private String gateOutLaneNo;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "seal01Origin", column = @Column(name = "GTP_SEAL_1_ORIGIN")),
      @AttributeOverride(name = "seal01Type", column = @Column(name = "GTP_SEAL_1_TYPE")),
      @AttributeOverride(name = "seal01Number", column = @Column(name = "GTP_SEAL_1_NO")),
      @AttributeOverride(name = "seal02Origin", column = @Column(name = "GTP_SEAL_2_ORIGIN")),
      @AttributeOverride(name = "seal02Type", column = @Column(name = "GTP_SEAL_2_TYPE")),
      @AttributeOverride(name = "seal02Number", column = @Column(name = "GTP_SEAL_2_NO"))})
  private CommonSealAttribute sealAttribute;

  @Column(name = "GTP_GOUT_REMARKS")
  private String gateOutRemarks;

  @Column(name = "GTP_YARD_POSITION")
  private String yardPosition;

  @Column(name = "GTP_BAY_CODE")
  private String bayCode;

  @Column(name = "GTP_CALL_CARD")
  private Long callCard;

  // modified
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GTP_HCID_M", nullable = true, referencedColumnName = "PRINT_NO")
  private PrintEir printEir;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUG_ID_SEQ", nullable = true, referencedColumnName = "CUG_ID_SEQ")
  private CardUsage cardUsage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOKING_ID", nullable = true, referencedColumnName = "BOOKING_ID")
  private HPATBooking hpatBooking;

  @Column(name = "COSMOS_GTP_SEAL_1_ORIGIN")
  private String cosmosSeal01Origin;

  @Column(name = "COSMOS_GTP_SEAL_1_TYPE")
  private String cosmosSeal01Type;

  @Column(name = "COSMOS_GTP_SEAL_1_NO")
  private String cosmosSeal01Number;

  @Column(name = "COSMOS_GTP_SEAL_2_ORIGIN")
  private String cosmosSeal02Origin;

  @Column(name = "COSMOS_GTP_SEAL_2_TYPE")
  private String cosmosSeal02Type;

  @Column(name = "COSMOS_GTP_SEAL_2_NO")
  private String cosmosSeal02Number;

  @Column(name = "IS_RETRIEVED_COSMOS", columnDefinition = "TINYINT")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean retrievedCosmos;

  @Column(name = "IS_CHANGE_SEAL", columnDefinition = "TINYINT")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean sealChange;

  @Column(name = "FORCED_SEAL")
  @Type(type = "yes_no")
  private boolean forcedSeal;

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

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
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

  public HPATBooking getHpatBooking() {
    return hpatBooking;
  }

  public void setHpatBooking(HPATBooking hpatBooking) {
    this.hpatBooking = hpatBooking;
  }

  public String getCosmosSeal01Origin() {
    return cosmosSeal01Origin;
  }

  public void setCosmosSeal01Origin(String cosmosSeal01Origin) {
    this.cosmosSeal01Origin = cosmosSeal01Origin;
  }

  public String getCosmosSeal01Type() {
    return cosmosSeal01Type;
  }

  public void setCosmosSeal01Type(String cosmosSeal01Type) {
    this.cosmosSeal01Type = cosmosSeal01Type;
  }

  public String getCosmosSeal01Number() {
    return cosmosSeal01Number;
  }

  public void setCosmosSeal01Number(String cosmosSeal01Number) {
    this.cosmosSeal01Number = cosmosSeal01Number;
  }

  public String getCosmosSeal02Origin() {
    return cosmosSeal02Origin;
  }

  public void setCosmosSeal02Origin(String cosmosSeal02Origin) {
    this.cosmosSeal02Origin = cosmosSeal02Origin;
  }

  public String getCosmosSeal02Type() {
    return cosmosSeal02Type;
  }

  public void setCosmosSeal02Type(String cosmosSeal02Type) {
    this.cosmosSeal02Type = cosmosSeal02Type;
  }

  public String getCosmosSeal02Number() {
    return cosmosSeal02Number;
  }

  public void setCosmosSeal02Number(String cosmosSeal02Number) {
    this.cosmosSeal02Number = cosmosSeal02Number;
  }

  public boolean isRetrievedCosmos() {
    return retrievedCosmos;
  }

  public void setRetrievedCosmos(boolean retrievedCosmos) {
    this.retrievedCosmos = retrievedCosmos;
  }

  public boolean isSealChange() {
    return sealChange;
  }

  public void setSealChange(boolean sealChange) {
    this.sealChange = sealChange;
  }

  public boolean isForcedSeal() {
    return forcedSeal;
  }

  public void setForcedSeal(boolean forcedSeal) {
    this.forcedSeal = forcedSeal;
  }



}
