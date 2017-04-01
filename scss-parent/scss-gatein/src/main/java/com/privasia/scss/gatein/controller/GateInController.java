package com.privasia.scss.gatein.controller;


import java.util.List;

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
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.gatein.service.GateInService;
import com.privasia.scss.gatein.service.ImportExportGateInService;
import com.privasia.scss.gatein.service.ImportGateInService;



@RestController
@RequestMapping("**/gatein")
public class GateInController {

  private static Logger logger = Logger.getLogger(GateInController.class.getName());

  @Autowired
  private GateInService gateInService;

  @Autowired
  private ImportExportGateInService importExportGateInService;

  @Autowired
  private ImportGateInService importGateInService;


  @RequestMapping(value = "/allow", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> gateInImpNormal(@RequestBody GateInfo gateInfo) {

    gateInfo = gateInService.checkGateInAllow(gateInfo);
    return new CustomResponseEntity<ApiResponseObject<?>>(new ApiResponseObject<GateInfo>(HttpStatus.OK, gateInfo),
        HttpStatus.OK);
  }


  @RequestMapping(value = "/populategatein", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> populateGateIn(@RequestBody GateInRequest gateInRequest) {

    GateInReponse gateInReponse = importExportGateInService.populateGateIn(gateInRequest);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<GateInReponse>(HttpStatus.OK, gateInReponse), HttpStatus.OK);
  }


  @RequestMapping(value = "/savegateininfo", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> saveGateInInfo(@RequestBody GateInWriteRequest gateInWriteRequest) {

    GateInReponse gateInWriteReponse = importExportGateInService.saveGateInInfo(gateInWriteRequest);

    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<GateInReponse>(HttpStatus.OK, gateInWriteReponse), HttpStatus.OK);
  }

  @RequestMapping(value = "/tetssaveimportcontainer", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> testSaveImportContainer(
      @RequestBody GateInWriteRequest gateInWriteRequest) {

    List<ImportContainer> reponse = null;

    try {
      reponse = importGateInService.saveGateInInfo(gateInWriteRequest);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("reponse" + reponse);


    CustomResponseEntity<ApiResponseObject<?>> obj = new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<String>(HttpStatus.OK, "fdifs dhfhsdhf isdhfihsdifhidus"), HttpStatus.OK);

    System.out.println("obj  +" + obj);

    return obj;
  }


}
