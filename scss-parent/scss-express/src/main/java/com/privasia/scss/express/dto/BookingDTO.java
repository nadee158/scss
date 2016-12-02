package com.privasia.scss.express.dto;

import java.io.Serializable;

public class BookingDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String bookingId;
  private String truckHeadNo;
  private String gatePass1;
  private String gatePass2;
  private String container1;
  private String container2;
  private String length1;
  private String length2;
  private String bookingStatus; // EARLY, ON TIME, LATE
  private String bookingStartTime; // DD/MM/YYYY HH24:MI:SS
  private String bookingEndTime; // DD/MM/YYYY HH24:MI:SS

  private String location;
  private String bookingType;// "CY" or "ODDPICKUP" OR "ODDDROP"

  public String getBookingId() {
    return bookingId;
  }

  public void setBookingId(String bookingId) {
    this.bookingId = bookingId;
  }

  public String getTruckHeadNo() {
    return truckHeadNo;
  }

  public void setTruckHeadNo(String truckHeadNo) {
    this.truckHeadNo = truckHeadNo;
  }

  public String getGatePass1() {
    return gatePass1;
  }

  public void setGatePass1(String gatePass1) {
    this.gatePass1 = gatePass1;
  }

  public String getGatePass2() {
    return gatePass2;
  }

  public void setGatePass2(String gatePass2) {
    this.gatePass2 = gatePass2;
  }

  public String getContainer1() {
    return container1;
  }

  public void setContainer1(String container1) {
    this.container1 = container1;
  }

  public String getContainer2() {
    return container2;
  }

  public void setContainer2(String container2) {
    this.container2 = container2;
  }

  public String getLength1() {
    return length1;
  }

  public void setLength1(String length1) {
    this.length1 = length1;
  }

  public String getLength2() {
    return length2;
  }

  public void setLength2(String length2) {
    this.length2 = length2;
  }

  public String getBookingStatus() {
    return bookingStatus;
  }

  public void setBookingStatus(String bookingStatus) {
    this.bookingStatus = bookingStatus;
  }

  public String getBookingStartTime() {
    return bookingStartTime;
  }

  public void setBookingStartTime(String bookingStartTime) {
    this.bookingStartTime = bookingStartTime;
  }

  public String getBookingEndTime() {
    return bookingEndTime;
  }

  public void setBookingEndTime(String bookingEndTime) {
    this.bookingEndTime = bookingEndTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getBookingType() {
    return bookingType;
  }

  public void setBookingType(String bookingType) {
    this.bookingType = bookingType;
  }



}
