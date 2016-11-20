/**
 * 
 */
package com.privasia.scss.hpat.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.predicate.HPATBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.hpat.dto.HpatDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
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

  // @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<HpatDto> getEtpHpat4ImpAndExp(Long cardId, LocalDateTime date, List<BookingType> bookingTypes) {
    List<HpatDto> dtoList = new ArrayList<HpatDto>();
    Optional<Card> card = cardRepository.findOne(cardId); // need to handle optional
    if (card.isPresent()) {
      Predicate byCardNo = HPATBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
      Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
      Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(date);
      Predicate byBookingTypes = HPATBookingPredicates.byBookingDetailTypes(bookingTypes);
      Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
      OrderSpecifier<LocalDateTime> sortSpec = HPATBookingPredicates.orderByAppointmentStartDateAsc();

      Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition, sortSpec);

      bookingList.forEach((HPATBooking b) -> {
        dtoList.add(new HpatDto(b));
      });
      return dtoList;
    } else {
      throw new ResultsNotFoundException("No HPAT Bookings Founds !");
    }

  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<HpatDto> findEtpHpat4ImpAndExp(Long cardId, LocalDateTime systemDateTime, List<String> bookingTypes)
      throws ResultsNotFoundException {

    List<HpatDto> hpats = null;

    List<BookingType> convertedBookingTypes = new ArrayList<>();
    System.out.println("bookingTypes :" + bookingTypes);
    bookingTypes.forEach(bookingType -> {
      BookingType bk = BookingType.fromValue(bookingType);
      if (!(bk == null)) {
        convertedBookingTypes.add(bk);
      } else {
        throw new BusinessException("Invalid Booking Type!");
      }
    });
    System.out.println("convertedBookingTypes :" + convertedBookingTypes);
    hpats = getEtpHpat4ImpAndExp(cardId, systemDateTime, convertedBookingTypes);
    if (!(hpats == null || hpats.isEmpty())) {

      for (HpatDto hpat : hpats) {
        if (StringUtils.isBlank(hpat.getApptStart()))
          continue;
        if (systemDateTime.isAfter(hpat.getApptEndDate()))
          hpat.setStatus(HpatDto.LATE);
        if (systemDateTime.isAfter(hpat.getApptStartDate()) && systemDateTime.isBefore(hpat.getApptEndDate())) {
          hpat.setStatus(HpatDto.ACTIVE);
          hpat.setOnTimeFlag("Y");
        } else if (systemDateTime.isBefore(hpat.getApptEndDate())) {
          hpat.setStatus(HpatDto.EARLY);
          hpat.setOnTimeFlag("N");
        }

      }
      Comparator<HpatDto> byApptStartDate = (o1, o2) -> o1.getApptStartDate().compareTo(o2.getApptStartDate());
      List<HpatDto> sortedHpats = new ArrayList<HpatDto>();
      hpats.stream().sorted(byApptStartDate).forEach(e -> sortedHpats.add(e));

      return sortedHpats;

    } else {
      // need to discuss with etp team to manage web services between etp and scss
      throw new ResultsNotFoundException("No HPAT Bookings Founds !");
    }
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public TransactionDTO getEtpHpat4ImpAndExp(String bookingID) {

    Optional<HPATBooking> hpatBooking =
        hpatBookingRepository.findByBookingIDAndStatus(bookingID, HpatReferStatus.ACTIVE);

    if (hpatBooking.isPresent()) {

      HPATBooking booking = hpatBooking.get();

      TransactionDTO transactionDTO = booking.constructTransactionDTO();

      if (!(booking.getHpatBookingDetails() == null || booking.getHpatBookingDetails().isEmpty())) {

        booking.getHpatBookingDetails().forEach(bookingDetail -> {

          BookingType bookingType = bookingDetail.getBookingType();
          bookingDetail.getImpGatePassNumber();
          switch (bookingType) {
            case IMPORT:

              if (transactionDTO.getImportContainer01() == null) {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer01(bookingDetail.constructImportContainer());
                }
              } else {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer02(bookingDetail.constructImportContainer());
                }
              }

              break;
            case EXPORT:

              if (transactionDTO.getExportContainer01() == null) {
                if (!(bookingDetail == null)) {
                  transactionDTO.setExportContainer01(bookingDetail.constructExportContainer());
                }
              } else {
                transactionDTO.setExportContainer02(bookingDetail.constructExportContainer());
              }

              break;
            case IMPORT_ITT:

              if (transactionDTO.getImportContainer01() == null) {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer01(bookingDetail.constructImportContainer());
                }
              } else {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer02(bookingDetail.constructImportContainer());
                }
              }

              break;
            case EMPTY_PICKUP:

              break;
            case EMPTY_RETURN:

              break;

            default:
              break;
          }

        });
      }

      return transactionDTO;

    } else {
      // need to discuss with etp team to manage web services between etp and scss
      throw new ResultsNotFoundException("No HPAT Booking was Found !");
    }
  }

}
