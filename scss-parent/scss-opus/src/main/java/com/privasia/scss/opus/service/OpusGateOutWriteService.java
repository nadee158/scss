/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.OpusExporterContainer;
import com.privasia.scss.opus.dto.OpusGateOutWriteRequest;
import com.privasia.scss.opus.dto.OpusGateOutWriteResponse;
import com.privasia.scss.opus.dto.OpusImportContainer;

/**
 * @author Janaka
 *
 */

@Service("opusGateOutWriteService")
public class OpusGateOutWriteService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateOutWriteService.class);

  @Value("${gate_out.write.response.url}")
  private String gateOutWriteResponseURL;

  public OpusGateOutWriteResponse getGateOutWriteResponse(OpusGateOutWriteRequest opusGateOutWriteRequest) {
    System.out.println("gateOutWriteResponseURL " + gateOutWriteResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateOutWriteRequest> request =
        new HttpEntity<OpusGateOutWriteRequest>(opusGateOutWriteRequest, headers);

    ResponseEntity<OpusGateOutWriteResponse> response =
        restTemplate.postForEntity(gateOutWriteResponseURL, request, OpusGateOutWriteResponse.class);

    log.info(response.toString());
    return response.getBody();
  }


  public GateOutReponse constructGateOutReponse(OpusGateOutWriteResponse opusGateOutWriteResponse,
      GateOutReponse gateOutReponse) {
    LocalDateTime localDateTime = OpusService.getLocalDategFromString(opusGateOutWriteResponse.getGateOUTDateTime());
    gateOutReponse.setGateOUTDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateOutReponse.setHaulageCode(opusGateOutWriteResponse.getHaulageCode());
    gateOutReponse.setLaneNo(opusGateOutWriteResponse.getLaneNo());
    gateOutReponse.setTruckHeadNo(opusGateOutWriteResponse.getTruckHeadNo());
    gateOutReponse.setTruckPlateNo(opusGateOutWriteResponse.getTruckPlateNo());
    gateOutReponse.setExportContainers(OpusService
        .constructExportContainersFromOpusExportContainers(opusGateOutWriteResponse.getExportContainerListCY()));
    gateOutReponse.setImportContainers(OpusService
        .constructImportContainersFromOpusImportContainers(opusGateOutWriteResponse.getImportContainerListCY()));
    gateOutReponse.setCallCardNo(opusGateOutWriteResponse.getCallCardNo());
    return gateOutReponse;
  }



  public OpusGateOutWriteRequest constructOpusGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest) {
    OpusGateOutWriteRequest opusGateOutWriteRequest = new OpusGateOutWriteRequest();

    List<OpusExporterContainer> exportContainerListCY =
        OpusService.constructOpusExporterContainersFromExporterContainers(gateOutWriteRequest.getExportContainers());
    List<OpusImportContainer> importContainerListCY =
        OpusService.constructOpusImportContainersFromImportContainers(gateOutWriteRequest.getImportContainers());

    LocalDateTime gateOutDateTime = CommonUtil.getParsedDate(gateOutWriteRequest.getGateOUTDateTime());
    opusGateOutWriteRequest.setGateOUTDateTime(DateUtil.getJsonDateFromDate(gateOutDateTime));
    opusGateOutWriteRequest.setHaulageCode("HAUCD");
    opusGateOutWriteRequest.setLaneNo(gateOutWriteRequest.getLaneNo());
    opusGateOutWriteRequest.setTruckHeadNo(gateOutWriteRequest.getTruckHeadNo());
    opusGateOutWriteRequest.setUserID(gateOutWriteRequest.getUserName());
    opusGateOutWriteRequest.setExportContainerListCY(exportContainerListCY);
    opusGateOutWriteRequest.setImportContainerListCY(importContainerListCY);
    return opusGateOutWriteRequest;
  }



}
