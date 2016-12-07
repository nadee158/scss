package com.privasia.scss.kioskbooth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.ClientInfo;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.kioskbooth.dto.KioskBoothRightInfo;
import com.privasia.scss.kioskbooth.service.KioskBoothService;

@RestController
@RequestMapping("**/kioskbooth")
public class KioskBoothController {

  @Autowired
  private KioskBoothService kioskBoothService;


  @RequestMapping(value = "/activateboothsbybiosk", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> activateBoothsByKioskId(
      @RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    System.out.println("kioskBoothRightInfo :" + kioskBoothRightInfo);
    String status = kioskBoothService.activateBoothsByKioskId(kioskBoothRightInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/lockboothforkiosk", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> lockBoothForKiosk(
      @RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    System.out.println("kioskID :" + kioskBoothRightInfo.getKioskID());
    System.out.println("boothID :" + kioskBoothRightInfo.getBoothID());
    String status = kioskBoothService.lockBoothForKiosk(kioskBoothRightInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/completekioskbooth", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> completekioskbooth(
      @RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    System.out.println("kioskID :" + kioskBoothRightInfo.getKioskID());
    System.out.println("boothID :" + kioskBoothRightInfo.getBoothID());
    String status = kioskBoothService.completekioskbooth(kioskBoothRightInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/lockedkioskboothinfo/{kioskID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getLockedKioskBoothInfo(@PathVariable String kioskID) {
    System.out.println("kioskID :" + kioskID);
    KioskBoothRightInfo kioskBoothRightInfo = kioskBoothService.getLockedKioskBoothInfo(kioskID);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<KioskBoothRightInfo>(HttpStatus.OK, kioskBoothRightInfo), HttpStatus.OK);
  }

  @RequestMapping(value = "/getboothaccessright/{boothID}/{kioskID}/{cardNumber}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getBoothAccessRight(@PathVariable String boothID,
      @PathVariable String kioskID, @PathVariable String cardNumber) {
    System.out.println("boothID :" + boothID);
    System.out.println("kioskID :" + kioskID);
    System.out.println("cardNumber :" + cardNumber);
    List<KioskBoothRightInfo> kioskBoothRightInfos =
        kioskBoothService.getBoothAccessRight(boothID, kioskID, cardNumber);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<KioskBoothRightInfo>>(HttpStatus.OK, kioskBoothRightInfos), HttpStatus.OK);
  }

  @RequestMapping(value = "/kioskinfo/{boothID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ApiResponseObject<?>> getKioskInfoByBooth(@PathVariable String boothID) {
    System.out.println("boothID :" + boothID);
    List<ClientInfo> clientInfo = kioskBoothService.getKioskInfoByBooth(boothID);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ClientInfo>>(HttpStatus.OK, clientInfo), HttpStatus.OK);
  }

}
