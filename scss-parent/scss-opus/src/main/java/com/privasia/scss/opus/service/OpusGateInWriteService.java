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

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.OpusExporterContainer;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusImportContainer;

/**
 * @author Janaka
 *
 */

@Service("opusGateInWriteService")
public class OpusGateInWriteService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateInWriteService.class);

  @Value("${gate_in.write.response.url}")
  private String gateInWriteResponseURL;

  public OpusGateInWriteResponse getGateInWriteResponse(OpusGateInWriteRequest opusGateInWriteRequest) {
    System.out.println("gateInWriteResponseURL " + gateInWriteResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInWriteRequest> request =
        new HttpEntity<OpusGateInWriteRequest>(opusGateInWriteRequest, headers);

    ResponseEntity<OpusGateInWriteResponse> response =
        restTemplate.postForEntity(gateInWriteResponseURL, request, OpusGateInWriteResponse.class);

    log.info(response.toString());
    return response.getBody();
  }


  public GateInReponse constructGateInReponse(OpusGateInWriteResponse opusGateInWriteResponse,
      GateInReponse gateInReponse) {
    LocalDateTime localDateTime = OpusService.getLocalDategFromString(opusGateInWriteResponse.getGateINDateTime());
    gateInReponse.setGateINDateTime(CommonUtil.getFormatteDate(localDateTime));
    gateInReponse.setHaulageCode(opusGateInWriteResponse.getHaulageCode());
    gateInReponse.setLaneNo(opusGateInWriteResponse.getLaneNo());
    gateInReponse.setTruckHeadNo(opusGateInWriteResponse.getTruckHeadNo());
    gateInReponse.setTruckPlateNo(opusGateInWriteResponse.getTruckPlateNo());
    gateInReponse.setExportContainers(OpusService
        .constructExportContainersFromOpusExportContainers(opusGateInWriteResponse.getExportContainerListCY()));
    gateInReponse.setImportContainers(OpusService
        .constructImportContainersFromOpusImportContainers(opusGateInWriteResponse.getImportContainerListCY()));
    gateInReponse.setCallCardNo(opusGateInWriteResponse.getCallCardNo());
    return gateInReponse;
  }



  public OpusGateInWriteRequest constructOpusGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
    OpusGateInWriteRequest opusGateInWriteRequest = new OpusGateInWriteRequest();

    List<OpusExporterContainer> exportContainerListCY =
        OpusService.constructOpusExporterContainersFromExporterContainers(gateInWriteRequest.getExportContainers());
    List<OpusImportContainer> importContainerListCY =
        OpusService.constructOpusImportContainersFromImportContainers(gateInWriteRequest.getImportContainers());

    LocalDateTime gateInDateTime = CommonUtil.getParsedDate(gateInWriteRequest.getGateInDateTime());
    opusGateInWriteRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInDateTime));
    opusGateInWriteRequest.setHaulageCode("HAUCD");
    opusGateInWriteRequest.setLaneNo(gateInWriteRequest.getLaneNo());
    opusGateInWriteRequest.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
    opusGateInWriteRequest.setUserID(gateInWriteRequest.getUserName());
    opusGateInWriteRequest.setExportContainerListCY(exportContainerListCY);
    opusGateInWriteRequest.setImportContainerListCY(importContainerListCY);
    return opusGateInWriteRequest;
  }



}
