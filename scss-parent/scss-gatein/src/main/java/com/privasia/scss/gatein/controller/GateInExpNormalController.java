package com.privasia.scss.gatein.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.security.AuditContext;
import com.privasia.scss.common.security.SecurityContext;
import com.privasia.scss.common.security.SecurityHelper;
import com.privasia.scss.gatein.service.CardUsageService;


@RestController
@RequestMapping("/in")
public class GateInExpNormalController {

  private static Logger logger = Logger.getLogger(GateInExpNormalController.class.getName());

  @Autowired
  private CardUsageService cardUsageService;

  @RequestMapping(value = "/gateInImpNormal/{cardNo}", method = RequestMethod.GET)
  public ResponseEntity<String> scanCard(@PathVariable String cardNo) {

    SecurityContext securityContext = SecurityHelper.getSecurityContext();
    AuditContext auditContext = SecurityHelper.getAuditContext();

    System.out.println("cardNo : " + cardNo);



    return new ResponseEntity<String>(cardNo, HttpStatus.OK);
  }

}
