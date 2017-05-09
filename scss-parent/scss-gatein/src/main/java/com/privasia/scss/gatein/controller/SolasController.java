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
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.gatein.exports.business.service.SolasService;



@RestController
@RequestMapping("**/gatein")
public class SolasController {

  private static Logger logger = Logger.getLogger(SolasController.class.getName());

  @Autowired
  private SolasService solasService;


  @RequestMapping(value = "/calculatevgm", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CustomResponseEntity<ApiResponseObject<?>> gateInImpNormal(
      @RequestBody List<ExportContainer> exportContainers) {
    logger.info("exportContainers.size() " + exportContainers.size());
    exportContainers = solasService.calculateTerminalVGM(exportContainers);
    return new CustomResponseEntity<ApiResponseObject<?>>(
        new ApiResponseObject<List<ExportContainer>>(HttpStatus.OK, exportContainers), HttpStatus.OK);
  }



}
