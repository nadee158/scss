/**
 * 
 */
package com.privasia.scss.core.util.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.util.DateUtil;



/**
 * @author Janaka
 *
 */
@Component("currentDateTimeService")
public class CurrentDateTimeService {

  public LocalDateTime getCurrentDateAndTime() {
    return LocalDateTime.now();
  }

  public String getFormattedCurrentDateAndTime() {
    return DateUtil.getFormatteDateTime(LocalDateTime.now());
  }

}
