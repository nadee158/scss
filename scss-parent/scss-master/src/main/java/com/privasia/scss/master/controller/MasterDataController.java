/**
 * 
 */
package com.privasia.scss.master.controller;


import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.util.service.CurrentDateTimeService;
import com.privasia.scss.master.service.GlobalSettingService;
import com.privasia.scss.master.service.ReferReasonService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("**/masterdata")
public class MasterDataController {


  @Autowired
  private CurrentDateTimeService currentDateTimeService;

  @Autowired
  private GlobalSettingService globalSettingService;

  @Autowired
  private ReferReasonService referReasonService;

  @RequestMapping(value = "serverdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> getServerDateTime() {

    String date = currentDateTimeService.getFormattedCurrentDateAndTime();

    System.out.println("date : " + date);

    return new ResponseEntity<String>(date, HttpStatus.OK);
  }


  @RequestMapping(value = "customcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Boolean> isCustomCheckAfterTransaction() {

    boolean check = globalSettingService.isCustomCheckBeforeTransaction();

    System.out.println("check : " + check);

    return new ResponseEntity<Boolean>(check, HttpStatus.OK);
  }

  @RequestMapping(value = "/referreason", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Map<Boolean, Set<ReferReason>>> getReferReasonList() {

    Map<Boolean, Set<ReferReason>> map = referReasonService.findAllReferReason();

    return new ResponseEntity<Map<Boolean, Set<ReferReason>>>(map, HttpStatus.OK);
  }

}
