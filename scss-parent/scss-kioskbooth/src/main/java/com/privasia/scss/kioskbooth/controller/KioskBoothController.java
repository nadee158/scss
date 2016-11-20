package com.privasia.scss.kioskbooth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ClientInfo;
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

  @RequestMapping(value = "/getLockedKioskBoothInfo/{kioskID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<KioskBoothRightInfo> getLockedKioskBoothInfo(@PathVariable String kioskID) {
    System.out.println("kioskID :" + kioskID);
    KioskBoothRightInfo kioskBoothRightInfo = kioskBoothService.getLockedKioskBoothInfo(kioskID);
    return new ResponseEntity<KioskBoothRightInfo>(kioskBoothRightInfo, HttpStatus.OK);
  }

  @RequestMapping(value = "/getBoothAccessRight/{boothID}/{kioskID}/{cardNumber}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<KioskBoothRightInfo>> getBoothAccessRight(@PathVariable String boothID,
      @PathVariable String kioskID, @PathVariable String cardNumber) {
    System.out.println("boothID :" + boothID);
    System.out.println("kioskID :" + kioskID);
    System.out.println("cardNumber :" + cardNumber);
    List<KioskBoothRightInfo> kioskBoothRightInfos =
        kioskBoothService.getBoothAccessRight(boothID, kioskID, cardNumber);
    return new ResponseEntity<List<KioskBoothRightInfo>>(kioskBoothRightInfos, HttpStatus.OK);
  }

  @RequestMapping(value = "/getKioskInfoByBooth/{boothID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<ClientInfo>> getKioskInfoByBooth(@PathVariable String boothID) {
    System.out.println("boothID :" + boothID);
    List<ClientInfo> clientInfo = kioskBoothService.getKioskInfoByBooth(boothID);
    return new ResponseEntity<List<ClientInfo>>(clientInfo, HttpStatus.OK);
  }

  @RequestMapping(value = "/getKioskLoginInfo/{ipAddress}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ClientInfo> getKioskLoginInfo(@PathVariable String ipAddress) {
    System.out.println("ipAddress :" + ipAddress);
    ClientInfo clientInfo = kioskBoothService.getKioskLoginInfo(ipAddress);
    return new ResponseEntity<ClientInfo>(clientInfo, HttpStatus.OK);
  }



}
