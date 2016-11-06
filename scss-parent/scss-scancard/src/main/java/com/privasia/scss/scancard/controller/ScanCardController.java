/**
 * 
 */
package com.privasia.scss.scancard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.scancard.dto.CardValidationDto;
import com.privasia.scss.scancard.dto.SCUInfoDto;
import com.privasia.scss.scancard.service.CardService;
import com.privasia.scss.scancard.service.CardValidationService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("/scss/scancard")
public class ScanCardController {

  @Autowired
  private CardService cardService;

  @Autowired
  private CardValidationService cardValidationService;

  @RequestMapping(value = "/{cardNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CardValidationDto> scanCardByCardNo(@PathVariable("cardNo") String cardNo) {
    CardValidationDto cardValidation = cardValidationService.validateCard(cardNo);
    return new ResponseEntity<CardValidationDto>(cardValidation, HttpStatus.OK);
  }

  @RequestMapping(value = "/bycardid/{cardId}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CardValidationDto> validateCardByCardId(@PathVariable("cardID") Long cardID) {
    CardValidationDto cardValidation = cardValidationService.validateCard(cardID);
    return new ResponseEntity<CardValidationDto>(cardValidation, HttpStatus.OK);
  }

  @RequestMapping(value = "/{cardNo}/scuinfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SCUInfoDto> selectSCUInfo(@PathVariable("cardNo") String cardNo, HttpServletRequest request) {
    String webIPAddress = UserIpAddressUtil.getUserIp(request);
    String baseUrl = getBaseUrl(request);
    SCUInfoDto dto = cardService.selectSCUInfo(cardNo, webIPAddress, baseUrl);
    return new ResponseEntity<SCUInfoDto>(dto, HttpStatus.OK);
  }

  private String getBaseUrl(HttpServletRequest request) {
    // http://localhost:8080/
    // String baseUrl = String.format("%s://%s:%d/",request.getScheme(), request.getServerName(),
    // request.getServerPort());
    // http://localhost:
    return String.format("%s://%s:", request.getScheme(), request.getServerName());
  }

  @RequestMapping(value = "/{cardID}/scuinfo/bycardid", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SCUInfoDto> selectSCUInfoByCardId(@PathVariable("cardID") Long cardID,
      HttpServletRequest request) {
    String webIPAddress = UserIpAddressUtil.getUserIp(request);
    String baseUrl = getBaseUrl(request);
    SCUInfoDto dto = cardService.selectSCUInfo(cardID, webIPAddress, baseUrl);
    return new ResponseEntity<SCUInfoDto>(dto, HttpStatus.OK);
  }

  @RequestMapping(value = "/{cardNo}/compnay/code", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> selectCompanyCode(@PathVariable("cardNo") String cardNo) {
    String companyCode = cardService.selectCompanyCode(cardNo);
    return new ResponseEntity<String>(companyCode, HttpStatus.OK);
  }

  @RequestMapping(value = "/{cardID}/compnay/code/bycardid", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> selectCompanyCodeByCardId(@PathVariable("cardID") Long cardID) {
    String companyCode = cardService.selectCompanyCode(cardID);
    return new ResponseEntity<String>(companyCode, HttpStatus.OK);
  }


}
