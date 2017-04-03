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

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CardUsageDTO;
import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.CommonSolasDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SolasInfo;
import com.privasia.scss.common.enums.BookingType;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "ETP_BOOKING_HPAT_DETAIL")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY") ),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY") ),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD") ),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE") )})
public class HPABBookingDetail extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ETP_BOOKING_HPAT_DETAIL")
  @SequenceGenerator(name = "SEQ_ETP_BOOKING_HPAT_DETAIL", sequenceName = "SEQ_ETP_BOOKING_HPAT_DETAIL")
  @Column(name = "DETAIL_ID")
  private Long hpatBookingDetailID;

  @Column(name = "BOOKING_TYPE", nullable = true)
  @Type(type = "com.privasia.scss.common.enumusertype.BookingTypeEnumUserType")
  private BookingType bookingType;

  @Column(name = "CLOSING_TIME")
  private LocalDateTime closingTime;

  @Column(name = "CONTAINER_ISO")
  private String containerISO;

  @Column(name = "CONTAINER_LENGTH")
  private String containerLength;

  @Column(name = "CONTAINER_NUMBER")
  private String containerNumber;

  @Column(name = "CONTAINER_SIZE")
  private String containerSize;

  @Column(name = "CONTAINER_TYPE")
  private String containerType;

  @Column(name = "COSMOS_STATUS")
  private String cosmosStatus;

  @Column(name = "EXP_SEAL_NO1")
  private String expSealNo01;

  @Column(name = "EXP_SEAL_NO2")
  private String expSealNo02;

  @Column(name = "IMP_GATEPASS_NUMBER")
  private String impGatePassNumber;

  @Column(name = "ODD_LOCATION")
  private String oddLocation;

  @Column(name = "ODD_PICK_DROP")
  private String oddPickOrDrop;

  @Column(name = "YARD_OPENING_TIME")
  private LocalDateTime yardOpeningTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOKING_ID", nullable = false)
  private HPABBooking hpatBooking;

  @Column(name = "EXP_BOOKING_NO")
  private String expBookingNo;

  @Embedded
  private CommonSolasAttribute solas;


  public Long getHpatBookingDetailID() {
    return hpatBookingDetailID;
  }


  public void setHpatBookingDetailID(Long hpatBookingDetailID) {
    this.hpatBookingDetailID = hpatBookingDetailID;
  }


  public CommonSolasAttribute getSolas() {
    return solas;
  }


  public void setSolas(CommonSolasAttribute solas) {
    this.solas = solas;
  }


  public BookingType getBookingType() {
    return bookingType;
  }


  public void setBookingType(BookingType bookingType) {
    this.bookingType = bookingType;
  }


  public LocalDateTime getClosingTime() {
    return closingTime;
  }


  public void setClosingTime(LocalDateTime closingTime) {
    this.closingTime = closingTime;
  }


  public String getContainerISO() {
    return containerISO;
  }


  public void setContainerISO(String containerISO) {
    this.containerISO = containerISO;
  }


  public String getContainerLength() {
    return containerLength;
  }


  public void setContainerLength(String containerLength) {
    this.containerLength = containerLength;
  }


  public String getContainerNumber() {
    return containerNumber;
  }


  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }


  public String getContainerSize() {
    return containerSize;
  }


  public void setContainerSize(String containerSize) {
    this.containerSize = containerSize;
  }


  public String getContainerType() {
    return containerType;
  }


  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }


  public String getCosmosStatus() {
    return cosmosStatus;
  }


  public void setCosmosStatus(String cosmosStatus) {
    this.cosmosStatus = cosmosStatus;
  }


  public String getExpSealNo01() {
    return expSealNo01;
  }


  public void setExpSealNo01(String expSealNo01) {
    this.expSealNo01 = expSealNo01;
  }


  public String getExpSealNo02() {
    return expSealNo02;
  }


  public void setExpSealNo02(String expSealNo02) {
    this.expSealNo02 = expSealNo02;
  }


  public String getImpGatePassNumber() {
    return impGatePassNumber;
  }


  public void setImpGatePassNumber(String impGatePassNumber) {
    this.impGatePassNumber = impGatePassNumber;
  }


  public String getOddLocation() {
    return oddLocation;
  }


  public void setOddLocation(String oddLocation) {
    this.oddLocation = oddLocation;
  }


  public String getOddPickOrDrop() {
    return oddPickOrDrop;
  }


  public void setOddPickOrDrop(String oddPickOrDrop) {
    this.oddPickOrDrop = oddPickOrDrop;
  }


  public LocalDateTime getYardOpeningTime() {
    return yardOpeningTime;
  }


  public void setYardOpeningTime(LocalDateTime yardOpeningTime) {
    this.yardOpeningTime = yardOpeningTime;
  }


  public HPABBooking getHpatBooking() {
    return hpatBooking;
  }


  public void setHpatBooking(HPABBooking hpatBooking) {
    this.hpatBooking = hpatBooking;
  }

  public String getExpBookingNo() {
    return expBookingNo;
  }


  public void setExpBookingNo(String expBookingNo) {
    this.expBookingNo = expBookingNo;
  }

  public ExportContainer constructExportContainer(ExportContainer exportContainer) {

    if (exportContainer == null) {
      exportContainer = new ExportContainer();
    }

    if (exportContainer.getContainer() == null) {
      exportContainer.setContainer(new CommonContainerDTO());
    }


    if (!(this.getSolas() == null)) {
      if (exportContainer.getSolas() == null) {
        exportContainer.setSolas(new CommonSolasDTO());
      }
      SolasInfo solasInfo = this.getSolas().constructSolasInfo();
      exportContainer.getSolas().setFaLedgerCode(solasInfo.getFaLedgerCode());
      exportContainer.getSolas().setMgw(solasInfo.getMgw());
      exportContainer.getSolas().setShipperVGM(solasInfo.getShipperVGM());
      exportContainer.getSolas().setSolasDetailID(solasInfo.getSolasDetailID());
      exportContainer.getSolas().setSolasInstruction(solasInfo.getSolasInstruction());
      exportContainer.getSolas().setSolasRefNumber(solasInfo.getSolasRefNumber());

    }
    exportContainer.getContainer().setContainerNumber(containerNumber);
    exportContainer.setSealAttribute(new CommonSealDTO());
    exportContainer.getSealAttribute().setSeal01Number(expSealNo01);
    exportContainer.getSealAttribute().setSeal02Number(expSealNo02);
    exportContainer.getContainer().setContainerISOCode(containerISO);
    exportContainer.setBookingNo(expBookingNo);
    return exportContainer;
  }

  public ImportContainer constructImportContainer(ImportContainer importContainer) {

    if (importContainer == null) {
      importContainer = new ImportContainer();
    }

    if (importContainer.getBaseCommonGateInOutAttribute() == null) {
      importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
    }
    importContainer.getBaseCommonGateInOutAttribute().setHpatBooking(this.hpatBooking.getBookingID());

    if (importContainer.getContainer() == null) {
      importContainer.setContainer(new CommonContainerDTO());
    }
    importContainer.getContainer().setContainerNumber(this.containerNumber);
    importContainer.getContainer().setContainerISOCode(this.containerISO);
    importContainer.getContainer().setContainerHeight(Integer.parseInt(this.containerSize));

    if (importContainer.getCardUsage() == null) {
      importContainer.setCardUsage(new CardUsageDTO());
    }
    importContainer.setContainerType(this.containerType);
    importContainer.setSealAttribute(new CommonSealDTO());
    importContainer.getSealAttribute().setSeal01Number(this.expSealNo01);
    importContainer.getSealAttribute().setSeal02Number(this.expSealNo02);
    importContainer.setGatePassNo(Long.parseLong(this.impGatePassNumber));
    importContainer.setContainerLength(Integer.parseInt(this.containerLength));

    return importContainer;
  }

}
