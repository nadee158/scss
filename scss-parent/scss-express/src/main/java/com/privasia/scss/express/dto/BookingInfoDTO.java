package com.privasia.scss.express.dto;

import java.io.Serializable;
import java.util.List;

public class BookingInfoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String OK = "OK";
  public static final String NOK = "NOK";
  public static final String CUS_CHECKING = "CUSTOM_CHECKING";

  public static final String BLACKLISTED_CARD_MESSAGE =
      "Maaf, Port Pass anda sudah di senarai hitam.\nSila letakkan kenderaan anda di kawasan yang disediakan dan dapatkan bantuan kerani gate di booth 1.";

  public static final String INVALID_CARD_MESSAGE =
      "Maaf, Port Pass anda sudah tamat tempoh.\nSila letakkan kenderaan anda di kawasan yang disediakan dan dapatkan bantuan kerani gate di booth 1.";

  public static final String CARD_NOT_FOUND_MESSAGE =
      "Maaf, Port Pass anda tidak sah (tiada di dalam sistem / tidak dapat dibaca).\nSila letakkan kenderaan anda di kawasan yang disediakan dan dapatkan bantuan kerani gate di booth 1.";

  public static final String IMPEXP_NOT_COMPLETED_MESSAGE =
      "Maaf, anda tidak melengkapkan proses transaksi.\nSila letakkan kenderaan anda di kawasan yang disediakan dan dapatkan bantuan kerani gate di booth 1.";

  public static final String NO_BOOKING_MESSAGE =
      "Maaf, tiada sebarang tempahan untuk anda di dalam system.\nSila letakkan kenderaan anda di kawasan yang disediakan dan dapatkan bantuan kerani gate di booth 1.";

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
