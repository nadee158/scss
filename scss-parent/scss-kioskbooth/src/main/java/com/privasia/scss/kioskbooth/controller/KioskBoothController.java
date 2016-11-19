package com.privasia.scss.kioskbooth.controller;

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


  @RequestMapping(value = "/activateBoothsByKioskId", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> activateBoothsByKioskId(@RequestBody KioskBoothRightInfo kioskBoothRightInfo) {
    String status = kioskBoothService.activateBoothsByKioskId(kioskBoothRightInfo);
    return new ResponseEntity<String>(status, HttpStatus.OK);
  }

}
