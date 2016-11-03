/**
 * 
 */
package com.privasia.scss.hpat.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.predicate.HPATBookingDetailPredicates;
import com.privasia.scss.core.predicate.HPATBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.HpatReferStatus;
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
    System.out.println("ACTV : "+HpatReferStatus.ACTIVE.getValue());
    Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
    Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(date);
    Predicate byBookingTypes = HPATBookingPredicates
        .byBookingDetailTypes(Arrays.asList(BookingType.EXPORT, BookingType.IMPORT, BookingType.IMPORT_ITT));
    Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
    Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition);

    bookingList.forEach((HPATBooking b) -> System.out.print(b.getBookingID()));

  }

  public List<HpatDto> findHpats(String cardIdSeq, Date systemDateTime, boolean isImpExp) throws Exception {

    List<HpatDto> hpats = null;

    /**
     * Eterminal Flag
     */
    /* TODO:Janaka Sir */
//    String etpFlag = gateInDao.getWDCGlobalSeeting("ETP_HPAT");
//
//    if (isImpExp) {
//      hpats = gateInDao.findEtpHpat4ImpAndExp(cardIdSeq, systemDateTime, HpatDto.IMPORT, HpatDto.EXPORT,
//          HpatDto.IMPORT_ITT);
//      if (CollectionUtil.isNotEmpty(hpats)) {
//        return hpats;
//      } else {
//        if ("Y".equalsIgnoreCase(etpFlag)) {
//          ScssCardDto scssCardDto = gateInDao.getSCardInfo(cardIdSeq);
//          if (scssCardDto != null) {
//            String icNo = "";
//
//            if (StringUtils.isNotBlank(scssCardDto.getNewICNo())) {
//              icNo = scssCardDto.getNewICNo();
//            } else if (StringUtils.isNotBlank(scssCardDto.getOldICNo())) {
//              icNo = scssCardDto.getOldICNo();
//            } else if (StringUtils.isNotBlank(scssCardDto.getPassportNo())) {
//              icNo = scssCardDto.getPassportNo();
//            }
//
//            if (StringUtils.isNotBlank(icNo)) {
//              EtpPlusWebService etpWebService = EtpPlusWebService.getInstance();
//              etpWebService.getHpatDetails(scssCardDto.getCrdScardNo(), icNo, scssCardDto.getCompCode(), new Date());
//            }
//
//            start = System.currentTimeMillis();
//            hpats = gateInDao.findEtpHpat4ImpAndExp(cardIdSeq, systemDateTime, HpatDto.IMPORT, HpatDto.EXPORT,
//                HpatDto.IMPORT_ITT);
//          }
//        } else {
//          hpats = new ArrayList<HpatDto>();
//        }
//      }
//    } else {
//      hpats =
//          gateInDao.findEtpHpat4ImpAndExp(cardIdSeq, systemDateTime, HpatDto.EMPTY_PICKUP, HpatDto.EMPTY_RETURN, null);
//      if (CollectionUtil.isNotEmpty(hpats)) {
//        return hpats;
//      } else {
//        ScssCardDto scssCardDto = gateInDao.getSCardInfo(cardIdSeq);
//        if (scssCardDto != null) {
//          String icNo = "";
//
//          if (StringUtils.isNotBlank(scssCardDto.getNewICNo())) {
//            icNo = scssCardDto.getNewICNo();
//          } else if (StringUtils.isNotBlank(scssCardDto.getOldICNo())) {
//            icNo = scssCardDto.getOldICNo();
//          } else if (StringUtils.isNotBlank(scssCardDto.getPassportNo())) {
//            icNo = scssCardDto.getPassportNo();
//          }
//
//          EtpPlusWebService etpWebService = EtpPlusWebService.getInstance();
//          etpWebService.getHpatDetails(scssCardDto.getCrdScardNo(), icNo, scssCardDto.getCompCode(), new Date());
//          hpats = gateInDao.findEtpHpat4ImpAndExp(cardIdSeq, systemDateTime, HpatDto.EMPTY_PICKUP, HpatDto.EMPTY_RETURN,
//              null);
//        }
//      }
//    }
    return hpats;
  }



}
