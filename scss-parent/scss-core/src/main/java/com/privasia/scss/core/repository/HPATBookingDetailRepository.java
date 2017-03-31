package com.privasia.scss.core.repository;

import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.model.HPABBookingDetail;

public interface HPATBookingDetailRepository extends BaseRepository<HPABBookingDetail, Long> {

  HPABBookingDetail findByContainerNumberAndBookingTypeAndHpatBooking_StatusAndHpatBooking_CardNo(String containerNo,
      BookingType import1, HpatReferStatus active, String cardNo);

  HPABBookingDetail findByContainerNumberAndBookingTypeAndHpatBooking_StatusAndHpatBooking_CardNoAndHpatBooking_PmNumber(
      String containerNo, BookingType type, HpatReferStatus hpatReferStatus, String cardNo, String truckHeadNo);

}
