package com.privasia.scss.cosmos.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeString {

  public static void main(String[] args) throws Exception {
    System.out.println(TimeString.format(59));
    System.out.println(TimeString.format(2 * HOUR + 61 * MINUTE + 62 * SECOND));
    System.out.println(TimeString.format(-3663));
  }

  public static String format(Date date) {
    SimpleDateFormat df = new SimpleDateFormat(FORMAT_PATTERN);
    return df.format(date);
  }

  public static String format(long second) {
    DecimalFormat df = new DecimalFormat("00");
    int HH = 0, mm = 0, ss = 0;
    String sign = "";

    if (second < 0) {
      sign = "-";
    }

    second = Math.abs(second);

    while (second >= HOUR) {
      second -= HOUR;
      HH++;
    }
    while (second >= MINUTE) {
      second -= MINUTE;
      mm++;
    }
    while (second >= SECOND) {
      second -= SECOND;
      ss++;
    }

    return sign + df.format(HH) + ":" + df.format(mm) + ":" + df.format(ss);
  }

  private static final long SECOND = 1;
  private static final long MINUTE = 60 * SECOND;
  private static final long HOUR = 60 * MINUTE;
  private static final long DAY = 24 * HOUR;

  private static String FORMAT_PATTERN = "HH:mm:ss";
}

