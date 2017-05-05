package com.privasia.scss.core.repository;

import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.core.model.HPABBookingDetail;

public interface HPABBookingDetailRepository extends BaseRepository<HPABBookingDetail, Long> {

  HPABBookingDetail findByContainerNumberAndBookingTypeAndHpabBooking_StatusAndHpabBooking_CardNo(String containerNo,
      BookingType import1, HpabReferStatus active, String cardNo);

  HPABBookingDetail findByContainerNumberAndBookingTypeAndHpabBooking_StatusAndHpabBooking_CardNoAndHpabBooking_PmNumber(
      String containerNo, BookingType type, HpabReferStatus hpabReferStatus, String cardNo, String truckHeadNo);

}
