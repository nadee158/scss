/**
 * 
 */
package com.privasia.scss.core.util.service;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.data.auditing.DateTimeProvider;


/**
 * @author Janaka
 *
 */

public class AuditingDateTimeProvider implements DateTimeProvider {


  private final CurrentDateTimeService currentDateTimeService;

  public AuditingDateTimeProvider(CurrentDateTimeService currentDateTimeService) {
    this.currentDateTimeService = currentDateTimeService;
  }

  @Override
  public Calendar getNow() {
    ZonedDateTime dateTime = ZonedDateTime.now();// .parse(currentDateTimeService.getFormattedCurrentDateAndTime());
    return GregorianCalendar.from(dateTime);
  }

}
