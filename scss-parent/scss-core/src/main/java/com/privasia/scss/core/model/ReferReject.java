/**
 * 
 */
package com.privasia.scss.core.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_REFER_REJECT")
public class ReferReject implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_REFER_ID")
  @SequenceGenerator(name = "SEQ_SCSS_REFER_ID", sequenceName = "SEQUENCE_REFER_REJECT")
  private Long referRejectID;
  /*private Company company;
  private int expWeightBridge;
  private int expNetWeight;
  private HPATBooking bookingID;
  private String statusCode;
  
  @Embedded
  private CommonGateInOutAttribute commonGateInOutAttribute;

  private String expTruckPlateNo;

  @Column(name = "REFER_DATE_TIME")
  private LocalDateTime referDateTime;


  private CardUsage cardUsage;

  private int pmWeight;

  private int trailerWeight;

  private String trailerPlateNo;



  @Column(name = "AXLE_VERIFIED")
  @Type(type = "yes_no")
  private boolean axleVerified;

  @Column(name = "PM_VERIFIED")
  @Type(type = "yes_no")
  private boolean pmVerified;*/



}
