/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author Janaka
 *
 */
public class HPABBookingDTO implements Serializable {
	
	
	  private String bookingID;

	  
	  private LocalDateTime appointmentStartDate;

	  
	  private String buffer;

	 
	  private String cardNo;

	
	  private LocalDateTime hpatCreationDate;


	  private String driverName;

	
	  private String driverICNumber;


	  private LocalDateTime hpatLastModifiedDate;


	  private String pmNumber;

	 
	  private String status;

	  
	  private String trailerNo;

	 
	  private String trailerType;

	 
	  private String haulierCode;

	  
	  private LocalDateTime appointmentEndDate;

	 
	  private String pmWeight;

	  
	  private String axleWeight;

	 
	  private String trailerPlate;
	  
	 
	  private Boolean axleVerified;

	  private Boolean pmVerified;

}
