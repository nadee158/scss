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

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.HpatDto;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.predicate.HPABBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hpabService")
public class HPABService {

  @Autowired
  private HPATBookingRepository hpatBookingRepository;

  @Autowired
  private CardRepository cardRepository;

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<HpatDto> createPredicatesAndFindHpab4ImpAndExp(Long cardId, LocalDateTime date,
      List<BookingType> bookingTypes) {
    List<HpatDto> dtoList = new ArrayList<HpatDto>();
    Optional<Card> card = cardRepository.findOne(cardId); // need to handle optional
    if (card.isPresent()) {
      Predicate byCardNo = HPABBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
      Predicate byBookingStatus = HPABBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
      Predicate byAppointmentEndDate = HPABBookingPredicates.byAppointmentEndDate(date);
      Predicate byBookingTypes = HPABBookingPredicates.byBookingDetailTypes(bookingTypes);
      Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
      OrderSpecifier<LocalDateTime> sortSpec = HPABBookingPredicates.orderByAppointmentStartDateAsc();

      Iterable<HPABBooking> bookingList = hpatBookingRepository.findAll(condition, sortSpec);

      bookingList.forEach((HPABBooking b) -> {
        dtoList.add(b.constructHpatDto());
      });
      return dtoList;
    } else {
      throw new ResultsNotFoundException("No HPAB Bookings Founds !");
    }

  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<HpatDto> findEtpHpab4ImpAndExp(Long cardId, LocalDateTime systemDateTime, List<String> bookingTypes)
      throws ResultsNotFoundException {

    List<HpatDto> hpats = null;

    List<BookingType> convertedBookingTypes = new ArrayList<>();
    System.out.println("bookingTypes :" + bookingTypes);
    bookingTypes.forEach(bookingType -> {
      BookingType bk = BookingType.fromValue(bookingType);
      if (bk != null) {
        convertedBookingTypes.add(bk);
      } else {
        throw new BusinessException("Invalid Booking Type!");
      }
    });
    System.out.println("convertedBookingTypes :" + convertedBookingTypes);
    hpats = createPredicatesAndFindHpab4ImpAndExp(cardId, systemDateTime, convertedBookingTypes);
    if (hpats != null && !hpats.isEmpty()) {

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
      throw new ResultsNotFoundException("No HPAT Bookings were found!");
    }
  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public TransactionDTO getEtpHpab4ImpAndExp(String bookingID) {

    Optional<HPABBooking> hpatBooking =
        hpatBookingRepository.findByBookingIDAndStatus(bookingID, HpatReferStatus.ACTIVE);

    if (hpatBooking.isPresent()) {

      HPABBooking booking = hpatBooking.get();

      TransactionDTO transactionDTO = booking.constructTransactionDTO();

      if (!(booking.getHpatBookingDetails() == null || booking.getHpatBookingDetails().isEmpty())) {

        booking.getHpatBookingDetails().forEach(bookingDetail -> {

          BookingType bookingType = bookingDetail.getBookingType();
          bookingDetail.getImpGatePassNumber();
          switch (bookingType) {
            case IMPORT:

              if (transactionDTO.getImportContainer01() == null) {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer01(bookingDetail.constructImportContainer(null));
                }
              } else {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer02(bookingDetail.constructImportContainer(null));
                }
              }

              break;
            case EXPORT:

              if (transactionDTO.getExportContainer01() == null) {
                if (!(bookingDetail == null)) {
                  transactionDTO.setExportContainer01(bookingDetail.constructExportContainer(null));
                }
              } else {
                transactionDTO.setExportContainer02(bookingDetail.constructExportContainer(null));
              }

              break;
            case IMPORT_ITT:

              if (transactionDTO.getImportContainer01() == null) {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer01(bookingDetail.constructImportContainer(null));
                }
              } else {
                if (!(bookingDetail == null)) {
                  transactionDTO.setImportContainer02(bookingDetail.constructImportContainer(null));
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

  // rename method to populateHpabForImpExp
  // return GateOutReponse
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateHpabForImpExp(GateInReponse gateInReponse, String hpabSeqId) {

    Optional<HPABBooking> hpatBookingOpt =
        hpatBookingRepository.findByBookingIDAndStatus(hpabSeqId, HpatReferStatus.ACTIVE);

    HPABBooking booking =
        hpatBookingOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid HPAB Bookibg ID ! " + hpabSeqId));

    List<ImportContainer> updatedImportContainers = new ArrayList<ImportContainer>();
    List<ExportContainer> updatedExportContainers = new ArrayList<ExportContainer>();

    if (!(booking.getHpatBookingDetails() == null || booking.getHpatBookingDetails().isEmpty())) {

      // construct DTO from domain
      booking.getHpatBookingDetails().forEach(bookingDetail -> {

        BookingType bookingType = bookingDetail.getBookingType();
        switch (bookingType) {
          case IMPORT:
          case IMPORT_ITT:
            /// create a import conatiner
            // fetch details from hpat
            // add to a list
            ImportContainer importContainer = null;
            if (!(gateInReponse.getImportContainers() == null || gateInReponse.getImportContainers().isEmpty())) {
              importContainer = gateInReponse.getImportContainers().stream().filter(e -> (e.getContainer() != null)
                  && (StringUtils.equals(e.getContainer().getContainerNumber(), bookingDetail.getContainerNumber())))
                  .findFirst().get();
            }
            updatedImportContainers.add(bookingDetail.constructImportContainer(importContainer));
            break;
          case EXPORT:
            /// create a export conatiner
            // fetch details from hpat
            // add to a list

            ExportContainer exportContainer = null;
            if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {
              exportContainer = gateInReponse.getExportContainers().stream().filter(e -> (e.getContainer() != null)
                  && (StringUtils.equals(e.getContainer().getContainerNumber(), bookingDetail.getContainerNumber())))
                  .findFirst().get();
            }

            ExportContainer exportContainerCons = bookingDetail.constructExportContainer(exportContainer);
            gateInReponse.setSolasInstruction(exportContainerCons.getSolas().getSolasInstruction());
            updatedExportContainers.add(exportContainerCons);
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

    if (!(updatedImportContainers == null || updatedImportContainers.isEmpty())) {
      gateInReponse.setImportContainers(updatedImportContainers);
    }

    if (!(updatedExportContainers == null || updatedExportContainers.isEmpty())) {
      gateInReponse.setExportContainers(updatedExportContainers);
    }

    return gateInReponse;

  }

}
