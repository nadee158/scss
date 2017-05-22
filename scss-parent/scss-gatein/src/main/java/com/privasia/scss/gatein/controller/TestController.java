package com.privasia.scss.gatein.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.annotation.HasAuthority;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.security.util.SecurityHelper;
import com.privasia.scss.gatein.service.ExportGateInService;


@RestController
@RequestMapping("**/test")
public class TestController {

  @Autowired
  private ExportGateInService exportService;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private SystemUserRepository systemUserRepository;

  @RequestMapping(value = "/dg", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> checkSolas(@RequestBody GateInWriteRequest gateInWriteRequest) {

    exportService.testDGValidationLog(gateInWriteRequest);

    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }

  @RequestMapping(value = "/testlog", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> testlog() {

    System.out.println("came to test log");
    exportService.testLogging("isuru", 29);

    return new ResponseEntity<String>("OK", HttpStatus.OK);

  }

  @HasAuthority(functions = {"12345"})
  @RequestMapping(value = "/testreferreject", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> testReferReject(
      @RequestBody GateInWriteRequest gateInWriteRequest) {

    ExportContainer exportContainer = gateInWriteRequest.getExportContainers().get(0);

    Client gateInClient = clientRepository.findOne(gateInWriteRequest.getGateInClient())
        .orElseThrow(() -> new ResultsNotFoundException(
            "Invalid Client Id : " + gateInWriteRequest.getGateInClient()));

    Card card = cardRepository.findOne(gateInWriteRequest.getCardID()).orElseThrow(
        () -> new ResultsNotFoundException("Invalid Card : " + gateInWriteRequest.getCardID()));

    SystemUser gateInClerk = systemUserRepository.findOne(SecurityHelper.getCurrentUserId())
        .orElseThrow(() -> new AuthenticationServiceException(
            "Log in User Not Found : " + SecurityHelper.getCurrentUserId()));

    exportService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);

    // exportService.updateReferReject(gateInWriteRequest, exportContainer);

    return new ResponseEntity<String>("OK", HttpStatus.OK);

  }

}
