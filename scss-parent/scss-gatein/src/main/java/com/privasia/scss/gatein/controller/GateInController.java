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
import com.privasia.scss.opus.dto.GIR01Request;
import com.privasia.scss.opus.dto.GIR01Response;
import com.privasia.scss.opus.dto.GIW01Request;
import com.privasia.scss.opus.dto.GIW01Response;
import com.privasia.scss.opus.service.OpusService;



@RestController
@RequestMapping("**/gatein")
public class GateInController {

  private static Logger logger = Logger.getLogger(GateInController.class.getName());

  @Autowired
  private GateInService gateInService;


  @RequestMapping(value = "/allow", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> gateInImpNormal(@RequestBody GateInfo gateInfo) {

    gateInfo = gateInService.checkGateInAllow(gateInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateInfo>(HttpStatus.OK, gateInfo),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> testOpus() {

    // gateInfo = gateInService.checkGateInAllow(gateInfo);
    // return new CustomResponseEntity<ApiResponseObject<?>>(new
    // ApiResponseObject<GateInfo>(HttpStatus.OK, gateInfo),
    // HttpStatus.OK);
    return null;
  }

  @Autowired
  private OpusService opusService;


  @RequestMapping(value = "gir01", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> gir01() {

    GIR01Request gir01Request = GIR01Request.constructObjectWithTestValues();

    GIR01Response gir01Response = opusService.getGIR01Response(gir01Request);

    System.out.println("gir01Response : " + gir01Response);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<GIR01Response>(HttpStatus.OK, gir01Response), HttpStatus.OK);
  }

  @RequestMapping(value = "giw01", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> giw01() {

    GIW01Request giW01Request = GIW01Request.constructObjectWithTestValues();

    GIW01Response giW01Response = opusService.getGIW01Response(giW01Request);

    System.out.println("giW01Response : " + giW01Response);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<GIW01Response>(HttpStatus.OK, giW01Response), HttpStatus.OK);
  }

}
