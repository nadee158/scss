/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


import com.privasia.scss.common.enums.BookingType;


/**
 * @author Janaka
 *
 */
public class HPABBookingDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	  private Long hpatBookingDetailID;

	  
	  private BookingType bookingType;

	  
	  private LocalDateTime closingTime;

	  
	  private String containerISO;

	  
	  private String containerLength;

	 
	  private String containerNumber;

	  
	  private String containerSize;

	  
	  private String containerType;

	  
	  private String cosmosStatus;

	  
	  private String expSealNo01;

	  
	  private String expSealNo02;

	 
	  private String impGatePassNumber;

	  
	  private String oddLocation;

	 
	  private String oddPickOrDrop;

	  
	  private LocalDateTime yardOpeningTime;

	  
	  private HPABBookingDTO hpatBooking;

	  
	  private String expBookingNo;
	
	

}
