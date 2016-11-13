/**
 * 
 */
package com.privasia.scss.hpat.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.hpat.dto.HpatDto;
import com.privasia.scss.hpat.etp.service.EdoExpiryForLineResponseType;
import com.privasia.scss.hpat.etp.service.client.ETPWebserviceClient;
import com.privasia.scss.hpat.service.HPATService;


/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("api/hpat")
public class HPATController {

  @Autowired
  private HPATService hpatService;

  @Autowired
  private ETPWebserviceClient etpWebserviceClient;


  @RequestMapping(value = "{cardID}/{bookingTypes}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<HpatDto>> findEtpHpat4ImpAndExp(@PathVariable Long cardID,
      @PathVariable List<String> bookingTypes) {

    System.out.println(cardID + "cardID");
    System.out.println(bookingTypes + "bookingTypes");

    List<HpatDto> dtos = hpatService.findEtpHpat4ImpAndExp(cardID, LocalDateTime.now(), bookingTypes);

    return new ResponseEntity<List<HpatDto>>(dtos, HttpStatus.OK);
  }

  @RequestMapping(value = "/test/{lineNo}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Boolean> findEtpHpat4ImpAndExp(@PathVariable String lineNo) {

    System.out.println(lineNo + "lineNo");

    EdoExpiryForLineResponseType response = etpWebserviceClient.getEdoExpiryForLine(lineNo);

    return new ResponseEntity<Boolean>(response.isEdoExpiryEnabled(), HttpStatus.OK);
  }



}
