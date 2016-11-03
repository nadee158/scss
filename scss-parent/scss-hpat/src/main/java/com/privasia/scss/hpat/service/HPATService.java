/**
 * 
 */
package com.privasia.scss.hpat.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.predicate.HPATBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.CompanyType;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.privasia.scss.hpat.dto.CardDto;
import com.privasia.scss.hpat.dto.HpatDetailsResponseType;
import com.privasia.scss.hpat.dto.HpatDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hpatService")
public class HPATService {

  @Autowired
  private HPATBookingRepository hpatBookingRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private EtpService etpService;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public void findEtpHpat4ImpAndExp(Long cardID, LocalDateTime date) {
    /*
     * String sql =
     * "SELECT bookHpat.BOOKING_ID, bookHpat.APPT_DATE_START, bookHpat.BUFFER, bookHpat.CRD_SCARDNO, bookHpat.CREATION_DATE \n"
     * // +
     * " , bookHpat.DRIVER_IC_PP, bookHpat.PM_NO, bookHpat.STATUS_CODE, bookHpat.TRAILER_NO , bookHpat.TRAILER_TYPE, bookHpat.HAULIER_CODE \n"
     * // +
     * " , bookHpat.PM_HEAD_WEIGHT, bookHpat.AXLE_WEIGHT, bookHpat.TRAILER_PLATE , bookHpat.APPT_DATE_END, bookHpat.AXLE_VERIFIED, bookHpat.PM_VERIFIED \n"
     * // +
     * " , det.MGW, det.BOOKING_TYPE, det.CONTAINER_LENGTH, det.CONTAINER_NUMBER, det.CONTAINER_TYPE, det.IMP_GATEPASS_NUMBER, det.SOLAS_INSTRUCTION \n"
     * // +
     * " , det.EXP_SEAL_NO1, det.EXP_SEAL_NO2, det.ODD_LOCATION, det.ODD_PICK_DROP, det.SHIPPER_VGM, det.SOLAS_DET_ID, det.CONTAINER_ISO, det.EXP_BOOKING_NO \n"
     * // + " , det.FA_LEDGER_CODE, det.SOLAS_REF_NO, card.CRD_CARDID_SEQ \n" // +
     * " FROM ETP_BOOKING_HPAT bookHpat \n" // +
     * " INNER JOIN ETP_BOOKING_HPAT_DETAIL det on det.BOOKING_ID = bookHpat.BOOKING_ID \n" // +
     * " INNER JOIN SCSS_CARD card ON bookHpat.CRD_SCARDNO = card.CRD_SCARDNO \n" // +
     * " WHERE bookHpat.BOOKING_ID = ? " + " AND bookHpat.STATUS_CODE = 'ACTV' ";
     */
    Optional<Card> card = cardRepository.findOne(cardID);
    Predicate byCardNo = HPATBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
    System.out.println("ACTV : " + HpatReferStatus.ACTIVE.getValue());
    Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
    Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(date);
    Predicate byBookingTypes = HPATBookingPredicates
        .byBookingDetailTypes(Arrays.asList(BookingType.EXPORT, BookingType.IMPORT, BookingType.IMPORT_ITT));
    Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
    Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition);

    bookingList.forEach((HPATBooking b) -> System.out.print(b.getBookingID()));

  }

  @javax.transaction.Transactional
  // overloaded method of above to match the following method's requirements
  public List<HpatDto> findEtpHpat4ImpAndExp(String cardIdSeq, LocalDateTime date, BookingType bookingType1,
      BookingType bookingType2, BookingType bookingType3) {
    List<HpatDto> dtoList = new ArrayList<HpatDto>();
    Long cardID = Long.parseLong(cardIdSeq);
    Optional<Card> card = cardRepository.findOne(cardID);
    Predicate byCardNo = HPATBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
    System.out.println("ACTV : " + HpatReferStatus.ACTIVE.getValue());
    Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
    Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(date);
    Predicate byBookingTypes =
        HPATBookingPredicates.byBookingDetailTypes(Arrays.asList(bookingType1, bookingType2, bookingType3));
    Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
    Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition);

    bookingList.forEach((HPATBooking b) -> {
      dtoList.add(new HpatDto(b));
    });
    return dtoList;
  }

  public List<HpatDto> findHpats(String cardIdSeq, LocalDateTime systemDateTime, boolean isImpExp) throws Exception {

    List<HpatDto> hpats = null;

    /**
     * Eterminal Flag
     */
    /* TODO:Janaka Sir */
    // String etpFlag = gateInDao.getWDCGlobalSeeting("ETP_HPAT");
    String etpFlag = "";

    hpats = findHpatsLocally(cardIdSeq, systemDateTime, isImpExp);
    if (!(hpats == null || hpats.isEmpty())) {
      return hpats;
    } else {
      if (StringUtils.equalsIgnoreCase(etpFlag, "Y")) {
        String hpatFetchStatus = fetchHpatsFromWebService(cardIdSeq);
        if (StringUtils.equals(hpatFetchStatus, "SUCCESS")) {
          hpats = findHpatsLocally(cardIdSeq, systemDateTime, isImpExp);
        }
      }
    }
    return hpats;
  }

  private String fetchHpatsFromWebService(String cardIdSeq) throws Exception {
    String finalStatus = "SUCCESS";
    Long cardNumber = Long.parseLong(cardIdSeq);
    Optional<Card> card = cardRepository.findByCardIDAndCompany_CompanyType(cardNumber, CompanyType.HAULAGE);
    if (card.isPresent()) {
      CardDto cardDto = new CardDto(card.orElse(null));
      if (cardDto != null) {
        String icNo = "";

        if (StringUtils.isNotBlank(cardDto.getNewICNo())) {
          icNo = cardDto.getNewICNo();
        } else if (StringUtils.isNotBlank(cardDto.getOldICNo())) {
          icNo = cardDto.getOldICNo();
        } else if (StringUtils.isNotBlank(cardDto.getPassportNo())) {
          icNo = cardDto.getPassportNo();
        }

        if (StringUtils.isNotBlank(icNo)) {
          HpatDetailsResponseType[] responses =
              etpService.getHpatDetails(cardDto.getCrdScardNo(), icNo, cardDto.getCompCode(), LocalDateTime.now());
          if (!(responses == null)) {
            for (HpatDetailsResponseType hpatDetailsResponseType : responses) {
              finalStatus = doEterminalPlusHpat(hpatDetailsResponseType, cardIdSeq, "");
              if (StringUtils.equals("SUCCESS", finalStatus)) {
                break;
              } ;
            }
          }
        }
      }
    }
    return finalStatus;
  }

  private List<HpatDto> findHpatsLocally(String cardIdSeq, LocalDateTime date, boolean isImpExp) {
    List<HpatDto> hpats = null;
    if (isImpExp) {
      hpats = findEtpHpat4ImpAndExp(cardIdSeq, date, BookingType.IMPORT, BookingType.EXPORT, BookingType.IMPORT_ITT);
    } else {
      hpats = findEtpHpat4ImpAndExp(cardIdSeq, date, BookingType.EP, BookingType.ER, null);
    }
    return hpats;
  }

  public boolean isExistEtpBookingHpat(String smartCardNo) {
    boolean isExist = false;
    // String sql = " SELECT * FROM ETP_BOOKING_HPAT WHERE BOOKING_ID = ? ";
    Optional<HPATBooking> booking = hpatBookingRepository.findOne(smartCardNo);
    if (booking.isPresent()) {
      isExist = true;
    }
    return isExist;
  }

  public String doEterminalPlusHpat(HpatDetailsResponseType hpatDetailsResponseType, String smartCardNo,
      String driverName) throws Exception {
    String finalStatus = "SUCESS";
    String sql = "";
    String sqlDetail = "";
//@formatter:off
//    Date systemDate = new Date();
//    long t = systemDate.getTime();
//    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
//
//    sql = "INSERT INTO ETP_BOOKING_HPAT(BOOKING_ID, APPT_DATE_START, BUFFER, CRD_SCARDNO, CREATION_DATE \n" //
//        + " , DRIVER_IC_PP, DRIVER_NAME, PM_NO, STATUS_CODE, TRAILER_NO ,TRAILER_TYPE, HAULIER_CODE \n" //
//        + " , ADD_BY, DATETIME_ADD, UPDATE_BY, DATETIME_UPDATE , VERSION, APPT_DATE_END ) \n" //
//        + " VALUES (" //
//        + SQL.format(StringUtils.trim(hpatDetailsResponseType.getRefId()), true)//
//        + ", " //
//        + SQL.TO_DATETIME(this.convertStrToDate(StringUtils.trim(hpatDetailsResponseType.getAppointmentDateTime()))) //
//        + ", " //
//        + SQL.format(StringUtils.trim("" + hpatDetailsResponseType.getBuffer())) //
//        + ", " //
//        + SQL.format(smartCardNo) //
//        + ", " //
//        + SQL.TO_DATETIME(this.convertStrToDate(StringUtils.trim(hpatDetailsResponseType.getCreatedDateTime()))) //
//        + ", " //
//        + SQL.format(StringUtils.trim(hpatDetailsResponseType.getDriverIc())) //
//        + ", " //
//        + SQL.format(driverName) //
//        + ", " //
//        + SQL.format(StringUtils.trim(hpatDetailsResponseType.getPmNo()))//
//        + ", " //
//        + SQL.format("ACTV") //
//        + ", " //
//        + SQL.format(StringUtils.trim(hpatDetailsResponseType.getTrailerNo())) //
//        + ", " //
//        + SQL.format(StringUtils.trim(hpatDetailsResponseType.getTrailerType())) //
//        + ", " //
//        + SQL.format(StringUtils.trim(hpatDetailsResponseType.getHaulageCompanyCode())) //
//        + ", " //
//        + SQL.format("1") //
//        + ", " //
//        + SQL.TO_DATETIME(new Date()) //
//        + ", " //
//        + SQL.format("1") //
//        + ", " //
//        + SQL.TO_DATETIME(new Date()) //
//        + ", " //
//        + SQL.format("0") //
//        + ", " //
//        + SQL.TO_DATETIME(new Date()) //
//        + ") ";
//
//
//    int count = 0;
//    /**
//     * Export
//     */
//    if (hpatDetailsResponseType.getExportDetails() != null) {
//      if (hpatDetailsResponseType.getExportDetails().length > 0) {
//        ExportDetailsType[] export = hpatDetailsResponseType.getExportDetails();
//        for (int i = 0; i < export.length; i++) {
//          ExportDetailsType exportDetailsType = export[i];
//
//          stmt = conn.createStatement();
//          int detailId = SQL.NEXTVAL(conn, "SEQ_ETP_BOOKING_HPAT_DETAIL");
//          String sqlDetails =
//              this.getEtpHpatDetails("" + detailId, "E", "", StringUtils.trim(exportDetailsType.getContainerNo()), "",
//                  StringUtils.trim(exportDetailsType.getSealOne()), StringUtils.trim(exportDetailsType.getSealTwo()),
//                  "", "", "", StringUtils.trim(hpatDetailsResponseType.getRefId()));
//          stmt.execute(sqlDetails);
//        }
//      }
//    }
//
//    /**
//     * Import Container
//     */
//    if (hpatDetailsResponseType.getImportDetails() != null) {
//      if (hpatDetailsResponseType.getImportDetails().length > 0) {
//        ImportDetailsType[] importCont = hpatDetailsResponseType.getImportDetails();
//        for (int i = 0; i < importCont.length; i++) {
//          ImportDetailsType importDetailsType = importCont[i];
//
//          int detailId = SQL.NEXTVAL(conn, "SEQ_ETP_BOOKING_HPAT_DETAIL");
//          stmt = conn.createStatement();
//          String sqlDetails =
//              this.getEtpHpatDetails("" + detailId, "I", "", StringUtils.trim(importDetailsType.getContainerNo()), "",
//                  "", "", StringUtils.trim(importDetailsType.getGatepassNo()), "", "",
//                  StringUtils.trim(hpatDetailsResponseType.getRefId()));
//          stmt.execute(sqlDetails);
//        }
//      }
//    }
//
//    /**
//     * Empty Pickup
//     */
//    if (hpatDetailsResponseType.getEmptyPickupDetails() != null) {
//      if (hpatDetailsResponseType.getEmptyPickupDetails().length > 0) {
//        PickupReturnDetailsType[] emptyPickupCont = hpatDetailsResponseType.getEmptyPickupDetails();
//        for (int i = 0; i < emptyPickupCont.length; i++) {
//          PickupReturnDetailsType pickupReturnDetailsType = emptyPickupCont[i];
//
//          int detailId = SQL.NEXTVAL(conn, "SEQ_ETP_BOOKING_HPAT_DETAIL");
//          stmt = conn.createStatement();
//          String sqlDetails = this.getEtpHpatDetails("" + detailId, "EP",
//              StringUtils.trim(pickupReturnDetailsType.getContainerLength()),
//              StringUtils.trim(pickupReturnDetailsType.getContainerNo()),
//              StringUtils.trim(pickupReturnDetailsType.getContainerType()), "", "", "",
//              StringUtils.trim(pickupReturnDetailsType.getOddLocation()), "",
//              StringUtils.trim(hpatDetailsResponseType.getRefId()));
//          stmt.execute(sqlDetails);
//        }
//      }
//    }
//
//    /**
//     * Empty Return
//     */
//    if (hpatDetailsResponseType.getEmptyReturnDetails() != null) {
//      if (hpatDetailsResponseType.getEmptyReturnDetails().length > 0) {
//        PickupReturnDetailsType[] emptyReturnCont = hpatDetailsResponseType.getEmptyReturnDetails();
//        for (int i = 0; i < emptyReturnCont.length; i++) {
//          PickupReturnDetailsType pickupReturnDetailsType = emptyReturnCont[i];
//
//          ps = conn.prepareStatement(sqlDetail);
//          count = 0;
//          int detailId = SQL.NEXTVAL(conn, "SEQ_ETP_BOOKING_HPAT_DETAIL");
//          stmt = conn.createStatement();
//          String sqlDetails = this.getEtpHpatDetails("" + detailId, "ER",
//              StringUtils.trim(pickupReturnDetailsType.getContainerLength()),
//              StringUtils.trim(pickupReturnDetailsType.getContainerNo()),
//              StringUtils.trim(pickupReturnDetailsType.getContainerType()), "", "", "",
//              StringUtils.trim(pickupReturnDetailsType.getOddLocation()), "",
//              StringUtils.trim(hpatDetailsResponseType.getRefId()));
//          stmt.execute(sqlDetails);
//        }
//      }
//    }
//
//    /**
//     * Import ITT Container
//     */
//    if (hpatDetailsResponseType.getImportIttDetails() != null) {
//      if (hpatDetailsResponseType.getImportIttDetails().length > 0) {
//        ImportIttDetailsType[] importIttCont = hpatDetailsResponseType.getImportIttDetails();
//        for (int i = 0; i < importIttCont.length; i++) {
//          ImportIttDetailsType importIttDetailsType = importIttCont[i];
//
//          int detailId = SQL.NEXTVAL(conn, "SEQ_ETP_BOOKING_HPAT_DETAIL");
//          stmt = conn.createStatement();
//          String sqlDetails =
//              this.getEtpHpatDetails("" + detailId, "II", "", StringUtils.trim(importIttDetailsType.getContainerNo()),
//                  "", "", "", StringUtils.trim(importIttDetailsType.getGatepassNo()), "", "",
//                  StringUtils.trim(hpatDetailsResponseType.getRefId()));
//          stmt.execute(sqlDetails);
//        }
//      }
//    }
  //@formatter:on
    return finalStatus;
  }



}
