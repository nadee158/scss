/**
 * 
 */
package com.privasia.scss.core.util.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.util.CommonUtil;



/**
 * @author Janaka
 *
 */
@Component("currentDateTimeService")
public class CurrentDateTimeService {

  public ZonedDateTime getCurrentDateAndTime() {
    return ZonedDateTime.now();
  }

  public String getFormattedCurrentDateAndTime() {
    return CommonUtil.getFormatteDate(LocalDateTime.now());
  }

}
