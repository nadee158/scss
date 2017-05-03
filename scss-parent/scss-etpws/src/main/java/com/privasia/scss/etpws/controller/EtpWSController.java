package com.privasia.scss.etpws.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.etpws.dto.SolasETPDTO;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;

@RestController
@RequestMapping("**/etpws")
public class EtpWSController {

  private static Logger logger = Logger.getLogger(EtpWSController.class.getName());

  private ETPWebserviceClient etpWebserviceClient;

  @Autowired
  public void setEtpWebserviceClient(ETPWebserviceClient etpWebserviceClient) {
    this.etpWebserviceClient = etpWebserviceClient;
  }

  @RequestMapping(value = "/updatesolastoetp", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<SolasETPDTO>> updateSolasToEtp(@Valid @RequestBody List<SolasETPDTO> solasETPDTOs,
      BindingResult bindingResult) throws BindException { 
 
    solasETPDTOs = etpWebserviceClient.updateSolasToEtp(solasETPDTOs);
    
    return new ResponseEntity<List<SolasETPDTO>>(solasETPDTOs, HttpStatus.OK);
  }



}
