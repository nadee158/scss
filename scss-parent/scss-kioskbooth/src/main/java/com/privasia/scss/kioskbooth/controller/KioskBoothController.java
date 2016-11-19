package com.privasia.scss.kioskbooth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.kioskbooth.dto.KioskBoothRightInfo;
import com.privasia.scss.kioskbooth.service.KioskBoothService;

@RestController
@RequestMapping("api")
public class KioskBoothController {

  @Autowired
  private KioskBoothService kioskBoothService;


  @RequestMapping(value = "/activateBoothsByKioskId", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Map<String, Object>> activateBoothsByKioskId(
      @RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    System.out.println("kioskBoothRightInfo :" + kioskBoothRightInfo);
    String status = kioskBoothService.activateBoothsByKioskId(kioskBoothRightInfo);
    resultMap.put("STATUS", status);
    return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
  }

  @RequestMapping(value = "/lockboothforkiosk", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Map<String, Object>> lockBoothForKiosk(@RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    System.out.println("kioskID :" + kioskBoothRightInfo.getKioskID());
    System.out.println("boothID :" + kioskBoothRightInfo.getBoothID());
    String status = kioskBoothService.lockBoothForKiosk(kioskBoothRightInfo);
    resultMap.put("STATUS", status);
    return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
  }

  @RequestMapping(value = "/completekioskbooth", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Map<String, Object>> completekioskbooth(@RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    System.out.println("kioskID :" + kioskBoothRightInfo.getKioskID());
    System.out.println("boothID :" + kioskBoothRightInfo.getBoothID());
    String status = kioskBoothService.completekioskbooth(kioskBoothRightInfo);
    resultMap.put("STATUS", status);
    return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
  }


}
