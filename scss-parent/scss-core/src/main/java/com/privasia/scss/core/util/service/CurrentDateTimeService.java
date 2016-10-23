/**
 * 
 */
package com.privasia.scss.core.util.service;

import java.time.ZonedDateTime;


/**
 * @author Janaka
 *
 */

public class CurrentDateTimeService {
	
	public ZonedDateTime  getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }

}
