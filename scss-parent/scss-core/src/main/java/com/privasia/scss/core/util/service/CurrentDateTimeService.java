/**
 * 
 */
package com.privasia.scss.core.util.service;

import java.time.ZonedDateTime;

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

}
