package com.privasia.scss.kioskbooth.controller;

import java.util.Arrays;
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
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.kioskbooth.service.KioskBoothService;

@RestController
@RequestMapping("**/kioskbooth")
public class KioskBoothController {

  @Autowired
  private KioskBoothService kioskBoothService;


  @RequestMapping(value = "/activatetrx", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> activateBoothsByKioskId(
      @RequestBody KioskBoothRightsDTO kioskBoothRightsDTO) {
    String status = kioskBoothService.activateBoothsForTransaction(kioskBoothRightsDTO);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/locktrx", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> lockBoothForKiosk(
      @RequestBody KioskBoothRightsDTO kioskBoothRightsDTO) {
    String status = kioskBoothService.lockBoothTransaction(kioskBoothRightsDTO);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/completetrx", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> completekioskbooth(
      @RequestBody KioskBoothRightsDTO kioskBoothRightsDTO) {
    String status = kioskBoothService.completeBoothTransaction(kioskBoothRightsDTO);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<String>(HttpStatus.OK, status),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/lockedkioskboothinfo/{kioskID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getLockedKioskBoothInfo(@PathVariable Long kioskID) {
    KioskBoothRightsDTO kioskBoothRightDTO = kioskBoothService.getLockedKioskBoothInfo(kioskID);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<KioskBoothRightsDTO>(HttpStatus.OK, kioskBoothRightDTO), HttpStatus.OK);
  }

  @RequestMapping(value = "/boothaccessright", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> getBoothAccessRight(
      @RequestBody KioskBoothRightsDTO kioskBoothRightsDTO) {

    Long boothID = kioskBoothRightsDTO.getBoothClientID();
    Long kioskID = kioskBoothRightsDTO.getKioskClientID();
    Long cardNumber = kioskBoothRightsDTO.getCardNumber();

    List<KioskBoothRightsDTO> kioskBoothRightInfos =
        kioskBoothService.getBoothAccessRight(boothID, kioskID, cardNumber);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<KioskBoothRightsDTO>>(HttpStatus.OK, kioskBoothRightInfos), HttpStatus.OK);
  }

  @RequestMapping(value = "/kiosklistbybooth/{boothID}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ApiResponseObject<?>> getKioskListByBooth(@PathVariable long boothID) {
    List<ClientDTO> clientDTO = kioskBoothService.getKioskListByBooth(boothID);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ClientDTO>>(HttpStatus.OK, clientDTO), HttpStatus.OK);
  }

  @RequestMapping(
      value = {"/searchkioskbooth/{cardNumber}/[{kioskLockStatusList}]", "/searchkioskbooth/{cardNumber}",
          "/searchkioskbooth/[{kioskLockStatusList}]"},
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ApiResponseObject<?>> searchKioskBooth(
      @PathVariable(value = "cardNumber", required = false) Long cardNumber,
      @PathVariable(value = "kioskLockStatusList", required = false) String[] kioskLockStatusArray) {
    List<String> kioskLockStatusList = null;
    if (!(kioskLockStatusArray == null)) {
      kioskLockStatusList = Arrays.asList(kioskLockStatusArray);
    }
    List<KioskBoothRightsDTO> kioskBoothRightsDTOs =
        kioskBoothService.searchKioskBooth(cardNumber, kioskLockStatusList);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<KioskBoothRightsDTO>>(HttpStatus.OK, kioskBoothRightsDTOs), HttpStatus.OK);
  }

}
