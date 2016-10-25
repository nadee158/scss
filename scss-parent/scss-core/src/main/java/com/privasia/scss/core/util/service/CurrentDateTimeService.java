/**
 * 
 */
package com.privasia.scss.core.util.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;




/**
 * @author Janaka
 *
 */
@Service
public class CurrentDateTimeService {

  public ZonedDateTime getCurrentDateAndTime() {
    return ZonedDateTime.now();
  }
  
  public String getFormattedCurrentDateAndTime() {
	  DateTimeFormatter  dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
	  return LocalDateTime.now().format(dateFormat);
	 
  }

}
