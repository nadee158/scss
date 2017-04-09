package com.privasia.scss.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

  public static final String GLOBAL_DATE_PATTERN = "dd-MM-yyyy";
  public static final String GLOBAL_DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

  // List of all date formats that we want to parse.
  // Add your own format here.
  private static List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>() {
    private static final long serialVersionUID = 1L;

    {
      add(new SimpleDateFormat("yyyy-M-dd"));
      add(new SimpleDateFormat("M/dd/yyyy"));
      add(new SimpleDateFormat("dd.M.yyyy"));
      add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));
      add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a"));
      add(new SimpleDateFormat("dd.MMM.yyyy"));
      add(new SimpleDateFormat("dd-MMM-yyyy"));
      add(new SimpleDateFormat("dd-MM-yyyy"));
      add(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z"));
    }
  };

  public static Date addHours(Date date, int hour) {
    Calendar cal = setTime(date);
    cal.add(Calendar.HOUR, hour);
    return cal.getTime();
  }

  /**
   * Convert String with various formats into java.util.Date
   * 
   * @param input Date as a string
   * @return java.util.Date object if input string is parsed successfully else returns null
   */
  public static Date convertToDate(String input) {
    Date date = null;
    if (null == input) {
      return null;
    }
    for (SimpleDateFormat format : dateFormats) {
      try {
        format.setLenient(false);
        date = format.parse(input);
      } catch (ParseException e) {
        // Shhh.. try other formats
      }
      if (date != null) {
        break;
      }
    }

    return date;
  }

  public static Date convertStringToDate(String date, String pattern) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    return simpleDateFormat.parse(date);
  }

  public static Date addDate(Date date, int noOfDays) {
    Calendar cal = setTime(date);
    cal.add(Calendar.DATE, noOfDays);
    return cal.getTime();
  }

  public static Calendar setTime(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  public static long getDaysBetween2Dates(Date dateFrom, Date dateTo) {
    long msDateFrom = DateUtil.truncateTime(dateFrom).getTime();
    long msDateTo = DateUtil.truncateTime(dateTo).getTime();

    return (msDateTo - msDateFrom) / (24 * 3600 * 1000) + 1;
  }

  /**
   * erase all time. set all time fields to 0.
   * 
   * @param date
   * @return
   */
  public static Date truncateTime(Date date) {
    return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
  }

  public static LocalDateTime getLocalDateFromString(String gateInDateTime) {
    try {
      if (StringUtils.isNotEmpty(gateInDateTime)) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(gateInDateTime, dateFormat);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getJsonDateFromDate(LocalDateTime localDateTime) {
    try {
      if (localDateTime != null) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return localDateTime.format(dateFormat);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // public static String getJsonDateFromUtilDate(Date localDate) {
  // try {
  // if (localDate != null) {
  // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
  // return dateFormat.format(localDate);
  // }
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // return null;
  // }

  public static LocalDateTime getLocalDategFromString(String gateINDateTime) {
    try {
      if (StringUtils.isNotEmpty(gateINDateTime)) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(gateINDateTime, dateFormat);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
