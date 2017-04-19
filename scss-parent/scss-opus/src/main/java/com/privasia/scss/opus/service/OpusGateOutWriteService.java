/**
 * 
 */
package com.privasia.scss.opus.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GOWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GOWriteRequestImportContainer;
import com.privasia.scss.opus.dto.OpusGateOutWriteRequest;
import com.privasia.scss.opus.dto.OpusGateOutWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateOutWriteService")
public class OpusGateOutWriteService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateOutWriteService.class);

  @Value("${async.wait.time}")
  private long asyncWaitTime;

  @Value("${gate_out.write.response.url}")
  private String gateOutWriteResponseURL;

  private OpusRequestResponseService opusRequestResponseService;

  private Gson gson;

  private OpusService opusService;

  @Autowired
  public void setOpusService(OpusService opusService) {
    this.opusService = opusService;
  }

  @Autowired
  public void setOpusRequestResponseService(OpusRequestResponseService opusRequestResponseService) {
    this.opusRequestResponseService = opusRequestResponseService;
  }

  @Autowired
  public void setGson(Gson gson) {
    this.gson = gson;
  }

  public OpusGateOutWriteResponse getGateOutWriteResponse(OpusGateOutWriteRequest opusGateOutWriteRequest,
      OpusRequestResponseDTO opusRequestResponseDTO) {
    log.info("gateOutWriteResponseURL " + gateOutWriteResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    log.info("OpusGateOutWriteResponse : -" + (new Gson()).toJson(opusGateOutWriteRequest));

    HttpEntity<OpusGateOutWriteRequest> request =
        new HttpEntity<OpusGateOutWriteRequest>(opusGateOutWriteRequest, headers);

    // save in to db
    Future<Long> future = opusRequestResponseService.saveOpusRequest(opusRequestResponseDTO);

    ResponseEntity<OpusGateOutWriteResponse> response =
        restTemplate.postForEntity(gateOutWriteResponseURL, request, OpusGateOutWriteResponse.class);

    System.out.println(response.toString());
    log.info("RESPONSE FROM OPUS: " + response.toString());

    opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));


    // update to db
    while (true) {
      if (future.isDone()) {
        try {
          System.out.println("Result from asynchronous process getGateOutWriteResponse - " + future.get());
          opusRequestResponseService.updateOpusResponse(opusRequestResponseDTO, future);
        } catch (InterruptedException | ExecutionException e) {
          log.error("Error Occured when update Opus Response getGateOutWriteResponse "
              + opusRequestResponseDTO.getGateinTime().toString());
          log.error(e.getMessage());
        }
        System.out.println("WHILE LOOP BROKEN getGateOutWriteResponse!!!!. ");
        break;
      }
      System.out.println("Continue doing something else getGateOutWriteResponse. ");

      try {
        Thread.sleep(asyncWaitTime);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        System.out.println("WHILE LOOP BROKEN ON THREAD EXCEPTION getGateOutWriteResponse!!!!. ");
        break;
      }
    }


    return response.getBody();
  }

  public GateOutReponse constructGateOutReponse(OpusGateOutWriteResponse opusGateOutWriteResponse,
      GateOutReponse gateOutReponse) {
    LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateOutWriteResponse.getGateINDateTime());
    gateOutReponse.setGateOUTDateTime(DateUtil.getFormatteDateTime(localDateTime));
    gateOutReponse.setHaulageCode(opusGateOutWriteResponse.getHaulageCode());
    gateOutReponse.setLaneNo(opusGateOutWriteResponse.getLaneNo());
    gateOutReponse.setTruckHeadNo(opusGateOutWriteResponse.getTruckHeadNo());
    gateOutReponse.setTruckPlateNo(opusGateOutWriteResponse.getTruckPlateNo());
    gateOutReponse.setExportContainers(opusService
        .goWriteRequestExportContainerListToExportContainerList(opusGateOutWriteResponse.getExportContainerListCY()));
    gateOutReponse.setImportContainers(opusService
        .goWriteRequestImportContainerListToImportContainerList(opusGateOutWriteResponse.getImportContainerListCY()));
    return gateOutReponse;
  }

  public OpusGateOutWriteRequest constructOpusGateOutWriteRequest(GateOutWriteRequest gateOutWriteRequest) {
    OpusGateOutWriteRequest opusGateOutWriteRequest = new OpusGateOutWriteRequest();

    List<GOWriteRequestExportContainer> exportContainerListCY =
        opusService.exportContainerListToGOWriteRequestExportContainerList(gateOutWriteRequest.getExportContainers());
    List<GOWriteRequestImportContainer> importContainerListCY =
        opusService.importContainerListToGOWriteRequestImportContainerList(gateOutWriteRequest.getImportContainers());

    opusGateOutWriteRequest.setGateOUTDateTime(DateUtil.getJsonDateFromDate(gateOutWriteRequest.getGateOUTDateTime()));
    opusGateOutWriteRequest.setHaulageCode(gateOutWriteRequest.getHaulageCode());
    opusGateOutWriteRequest.setLaneNo(gateOutWriteRequest.getLaneNo());
    opusGateOutWriteRequest.setTruckHeadNo(gateOutWriteRequest.getTruckHeadNo());
    opusGateOutWriteRequest.setTruckPlateNo(gateOutWriteRequest.getTruckPlateNo());
    opusGateOutWriteRequest.setUserID(gateOutWriteRequest.getUserName());
    opusGateOutWriteRequest.setExportContainerListCY(exportContainerListCY);
    opusGateOutWriteRequest.setImportContainerListCY(importContainerListCY);
    return opusGateOutWriteRequest;
  }

}
