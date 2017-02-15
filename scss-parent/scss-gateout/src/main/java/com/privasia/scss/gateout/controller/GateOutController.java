package com.privasia.scss.gateout.controller;


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
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.gateout.service.ImportExportGateOutService;



@RestController
@RequestMapping("**/gateout")
public class GateOutController {

  private static Logger logger = Logger.getLogger(GateOutController.class.getName());

  @Autowired
  private ImportExportGateOutService importExportGateOutService;


  @RequestMapping(value = "/populategateout", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> populateGateOut(@RequestBody GateOutRequest gateOutRequest) {

    GateOutReponse gateOutReponse = importExportGateOutService.populateGateOut(gateOutRequest);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<GateOutReponse>(HttpStatus.OK, gateOutReponse), HttpStatus.OK);
  }


  @RequestMapping(value = "/savegateoutinfo", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> saveGateOutOutfo(
      @RequestBody GateOutWriteRequest gateOutWriteRequest) {

    GateOutReponse gateOutWriteReponse = importExportGateOutService.saveGateOutInfo(gateOutWriteRequest);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<GateOutReponse>(HttpStatus.OK, gateOutWriteReponse), HttpStatus.OK);
  }


}