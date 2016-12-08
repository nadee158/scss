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
import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.ClientInfo;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.kioskbooth.dto.KioskBoothRightInfo;
import com.privasia.scss.kioskbooth.service.KioskBoothService;

@RestController
@RequestMapping("**/kioskbooth")
public class KioskBoothController {

  @Autowired
  private KioskBoothService kioskBoothService;


  @RequestMapping(value = "/activatetransaction", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> activateBoothsByKioskId(
      @RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    System.out.println("kioskBoothRightInfo :" + kioskBoothRightInfo);
    String status = kioskBoothService.activateBoothsByKioskId(kioskBoothRightInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/locktransaction", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> lockBoothForKiosk(
      @RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    System.out.println("kioskID :" + kioskBoothRightInfo.getKioskID());
    System.out.println("boothID :" + kioskBoothRightInfo.getBoothID());
    String status = kioskBoothService.lockBoothForKiosk(kioskBoothRightInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/completetransaction", method = RequestMethod.PUT,
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
  public CustomResponseEntity<ApiResponseObject<?>> getLockedKioskBoothInfo(@PathVariable Long kioskID) {
    System.out.println("kioskID :" + kioskID);
    KioskBoothRightInfo kioskBoothRightInfo = kioskBoothService.getLockedKioskBoothInfo(kioskID); 
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<KioskBoothRightInfo>(HttpStatus.OK, kioskBoothRightInfo), HttpStatus.OK);
  }

  @RequestMapping(value = "/boothaccessright", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getBoothAccessRight(@RequestBody KioskBoothRightsDTO kioskBoothRightInfo) {
	
	Long boothID = kioskBoothRightInfo.getBoothClientID();
	Long kioskID = kioskBoothRightInfo.getKioskClientID();
	Integer cardNumber= kioskBoothRightInfo.getCardNumber(); 
	
    System.out.println("boothID :" + kioskBoothRightInfo.getBoothClientID());  
    System.out.println("kioskID :" + kioskBoothRightInfo.getKioskClientID());
    System.out.println("cardNumber :" + kioskBoothRightInfo.getCardNumber());
    List<KioskBoothRightsDTO> kioskBoothRightInfos = kioskBoothService.getBoothAccessRight(boothID, kioskID, cardNumber); 
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<KioskBoothRightsDTO>>(HttpStatus.OK, kioskBoothRightInfos), HttpStatus.OK);
  }

  @RequestMapping(value = "/kiosklistbybooth/{boothID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ApiResponseObject<?>> getKioskListByBooth(@PathVariable long boothID) {
    System.out.println("boothID :" + boothID);
    List<ClientDTO> clientDTO = kioskBoothService.getKioskListByBooth(boothID);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ClientDTO>>(HttpStatus.OK, clientDTO), HttpStatus.OK);
  }

}
