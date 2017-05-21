package com.privasia.scss.gatein.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.gatein.service.ExportGateInService;


@RestController
@RequestMapping("**/test")
public class TestController {

  @Autowired
  private ExportGateInService exportService;

  @RequestMapping(value = "/dg", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> checkSolas(@RequestBody GateInWriteRequest gateInWriteRequest) {

    exportService.testDGValidationLog(gateInWriteRequest);

    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }

  @RequestMapping(value = "/testlog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> testlog() {

    System.out.println("came to test log");
    exportService.testLogging("isuru", 29);

    return new ResponseEntity<String>("OK", HttpStatus.OK);

  }

  @RequestMapping(value = "/testreferreject", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> testReferReject(@RequestBody GateInWriteRequest gateInWriteRequest) {

    ExportContainer exportContainer = gateInWriteRequest.getExportContainers().get(0);

    exportService.updateReferReject(gateInWriteRequest, exportContainer);

    return new ResponseEntity<String>("OK", HttpStatus.OK);

  }

}
