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
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.opus.dto.GIWriteRequestExportContainer;
import com.privasia.scss.opus.dto.GIWriteRequestImportContainer;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateInWriteService")
public class OpusGateInWriteService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateInWriteService.class);

  @Value("${async.wait.time}")
  private long asyncWaitTime;

  @Value("${gate_in.write.response.url}")
  private String gateInWriteResponseURL;

  private OpusDTOConstructService opusDTOConstructService;

  private OpusRequestResponseService opusRequestResponseService;

  private Gson gson;

  @Autowired
  public void setOpusRequestResponseService(OpusRequestResponseService opusRequestResponseService) {
    this.opusRequestResponseService = opusRequestResponseService;
  }

  @Autowired
  public void setGson(Gson gson) {
    this.gson = gson;
  }

  @Autowired
  public void setOpusDTOConstructService(OpusDTOConstructService opusDTOConstructService) {
    this.opusDTOConstructService = opusDTOConstructService;
  }

  public OpusGateInWriteResponse getGateInWriteResponse(OpusGateInWriteRequest opusGateInWriteRequest,
      OpusRequestResponseDTO opusRequestResponseDTO) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInWriteRequest> request =
        new HttpEntity<OpusGateInWriteRequest>(opusGateInWriteRequest, headers);

    // save in to db
    Future<Long> future = opusRequestResponseService.saveOpusRequest(opusRequestResponseDTO);

    log.info("OpusGateInWriteResponse : -" + (new Gson()).toJson(opusGateInWriteRequest));

    ResponseEntity<OpusGateInWriteResponse> response =
        restTemplate.postForEntity(gateInWriteResponseURL, request, OpusGateInWriteResponse.class);

    log.info("RESPONSE FROM OPUS: " + response.toString());

    opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));

    // update to db
    while (true) {
      if (future.isDone()) {
        try {
          opusRequestResponseService.updateOpusResponse(opusRequestResponseDTO, future);
        } catch (InterruptedException | ExecutionException e) {
          log.error("Error Occured when update Opus Response getGateInWriteResponse "
              + opusRequestResponseDTO.getGateinTime().toString());
          log.error(e.getMessage());
        }
        break;
      }

      try {
        Thread.sleep(asyncWaitTime);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        break;
      }
    }

    return response.getBody();
  }

  public GateInReponse constructGateInReponse(OpusGateInWriteResponse opusGateInWriteResponse,
      GateInReponse gateInReponse) {

    //LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateInWriteResponse.getGateINDateTime());
    //gateInReponse.setGateINDateTime(localDateTime);
    gateInReponse.setHaulageCode(opusGateInWriteResponse.getHaulageCode());
    gateInReponse.setLaneNo(opusGateInWriteResponse.getLaneNo());
    gateInReponse.setTruckHeadNo(opusGateInWriteResponse.getTruckHeadNo());
    gateInReponse.setTruckPlateNo(opusGateInWriteResponse.getTruckPlateNo());
    gateInReponse.setExportContainers(opusDTOConstructService.giWriteResponseExportContainerListToExportContainerList(
        opusGateInWriteResponse, gateInReponse.getExportContainers()));
    gateInReponse.setImportContainers(opusDTOConstructService.giWriteResponseImportContainerListToImportContainerList(
        opusGateInWriteResponse, gateInReponse.getImportContainers()));
    gateInReponse.setCallCardNo(opusGateInWriteResponse.getCallCardNo());
    return gateInReponse;
  }

  public OpusGateInWriteRequest constructOpusGateInWriteRequest(GateInWriteRequest gateInWriteRequest) {
    OpusGateInWriteRequest opusGateInWriteRequest = new OpusGateInWriteRequest();

    List<GIWriteRequestExportContainer> exportContainerListCY =
        opusDTOConstructService.exportContainerListToGIWriteRequestExportContainerList(gateInWriteRequest);
    List<GIWriteRequestImportContainer> importContainerListCY =
        opusDTOConstructService.importContainerListToGIWriteRequestImportContainerList(gateInWriteRequest);

    opusGateInWriteRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInWriteRequest.getGateInDateTime()));
    opusGateInWriteRequest.setHaulageCode(gateInWriteRequest.getHaulageCode());
    opusGateInWriteRequest.setLaneNo(gateInWriteRequest.getLaneNo());
    opusGateInWriteRequest.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
    opusGateInWriteRequest.setUserID(gateInWriteRequest.getUserName());
    opusGateInWriteRequest.setExportContainerListCY(exportContainerListCY);
    opusGateInWriteRequest.setImportContainerListCY(importContainerListCY);
    opusGateInWriteRequest.setTrailerNo(gateInWriteRequest.getTrailerNo());
    opusGateInWriteRequest.setTrailerWeight(Integer.toString(gateInWriteRequest.getTrailerWeight()));
    opusGateInWriteRequest.setTruckPlateNo(gateInWriteRequest.getTruckPlateNo());
    opusGateInWriteRequest.setTruckWeight(Integer.toString(gateInWriteRequest.getTruckWeight()));
    return opusGateInWriteRequest;
  }

}
