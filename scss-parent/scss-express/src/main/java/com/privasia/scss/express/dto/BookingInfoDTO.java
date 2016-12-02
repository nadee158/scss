package com.privasia.scss.express.dto;

import java.io.Serializable;
import java.util.List;

public class BookingInfoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String OK = "OK";
  public static final String NOK = "NOK";
  public static final String CUS_CHECKING = "CUSTOM_CHECKING";

  private String messageCode; // OK, NOK
  private String messageDesc;

  private String driverName;
  private String companyName;
  private byte[] driverPhoto;
  private List<BookingDTO> bookings;

  public String getMessageCode() {
    return messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  public String getMessageDesc() {
    return messageDesc;
  }

  public void setMessageDesc(String messageDesc) {
    this.messageDesc = messageDesc;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public byte[] getDriverPhoto() {
    return driverPhoto;
  }

  public void setDriverPhoto(byte[] driverPhoto) {
    this.driverPhoto = driverPhoto;
  }

  public List<BookingDTO> getBookings() {
    return bookings;
  }

  public void setBookings(List<BookingDTO> bookings) {
    this.bookings = bookings;
  }



}
