package com.privasia.scss.core.repository;

import com.privasia.scss.core.model.HPATBookingDetail;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.HpatReferStatus;

public interface HPATBookingDetailRepository extends BaseRepository<HPATBookingDetail, Long> {

  HPATBookingDetail findByContainerNumberAndBookingTypeAndHpatBooking_StatusAndHpatBooking_CardNo(String containerNo,
      BookingType import1, HpatReferStatus active, String cardNo);

  HPATBookingDetail findByContainerNumberAndBookingTypeAndHpatBooking_StatusAndHpatBooking_CardNoAndHpatBooking_PmNumber(
      String containerNo, BookingType type, HpatReferStatus hpatReferStatus, String cardNo, String truckHeadNo);

}
