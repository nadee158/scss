package com.privasia.scss.gatein.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.gatein.service.GateInService;




@RestController
@RequestMapping("**/gatein")
public class GateInController {

  private static Logger logger = Logger.getLogger(GateInController.class.getName());

  @Autowired
  private GateInService gateInService;


  @RequestMapping(value = "/allowgatein", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public  CustomResponseEntity<ApiResponseObject<?>> gateInImpNormal(@RequestBody GateInfo gateInfo) {

	  gateInfo = gateInService.checkGateInAllow(gateInfo);
	  return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateInfo>(HttpStatus.OK, gateInfo),
		        HttpStatus.OK);
  }

  

}
