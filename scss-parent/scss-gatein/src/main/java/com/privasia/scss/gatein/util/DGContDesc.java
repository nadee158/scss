package com.privasia.scss.gatein.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class DGContDesc {
  public int parseHoursToGateInForDG(String desc) {
    int hours = 72;

    LinkedHashMap<String, Integer> dgHours = new LinkedHashMap<String, Integer>();
    dgHours.put("120 HOURS", 120);
    dgHours.put("96 HOURS", 96);
    dgHours.put("72 HOURS", 72);
    dgHours.put("48 HOURS", 48);
    dgHours.put("36 HOURS", 36);
    dgHours.put("24 HOURS", 24);
    dgHours.put("12 HOURS", 12);
    dgHours.put("04 HOURS", 4);

    for (Map.Entry entry : dgHours.entrySet()) {
      if (StringUtils.contains(desc, entry.getKey().toString())) {
        hours = dgHours.get(entry.getKey());
        break;
      }
    }

    return hours;
  }

}
