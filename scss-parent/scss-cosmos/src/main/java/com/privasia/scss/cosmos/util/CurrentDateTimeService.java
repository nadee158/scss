/**
 * 
 */
package com.privasia.scss.cosmos.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.util.DateUtil;



/**
 * @author Janaka
 *
 */
@Component("cosmosCurrentDateTimeService")
public class CurrentDateTimeService {

  public LocalDateTime getCurrentDateAndTime() {
    return LocalDateTime.now();
  }

  public String getFormattedCurrentDateAndTime() {
    return DateUtil.getFormatteDateTime(LocalDateTime.now());
  }

}
