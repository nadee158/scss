/**
 * 
 */
package com.privasia.scss.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.client.service.ClientService;

/**
 * @author Janaka
 *
 */

@RestController
@RequestMapping("api")
public class ClientController {

  @Autowired
  private ClientService clientService;


  @RequestMapping(value = "client/{webIp}/unitNo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> getClientUnitNoByIp(@PathVariable String webIPAddress) {
    String unitNo = clientService.getClientUnitNoByIp(webIPAddress);
    return new ResponseEntity<String>(unitNo, HttpStatus.OK);
  }



}
