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
import com.privasia.scss.common.annotation.LogOpusData;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.util.DateUtil;
// import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

/**
 * @author Janaka
 *
 */

@Service("opusGateInReadService")
public class OpusGateInReadService {

  private static final Logger log = LoggerFactory.getLogger(OpusGateInReadService.class);

  @Value("${async.wait.time}")
  private long asyncWaitTime;

  @Value("${gate_in.read.response.url}")
  private String gateInReadResponseURL;

  private OpusService opusService;

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
  public void setOpusService(OpusService opusService) {
    this.opusService = opusService;
  }

  public OpusGateInReadRequest constructOpenGateInRequest(GateInRequest gateInRequest) {

    OpusGateInReadRequest opusGateInRequest = new OpusGateInReadRequest();

    opusGateInRequest.setContainerNo1ImportCY(gateInRequest.getImpContainer1());
    opusGateInRequest.setContainerNo2ImportCY(gateInRequest.getImpContainer2());
    // add export containers also
    opusGateInRequest.setContainerNo1ExportCY(gateInRequest.getExpContainer1());
    opusGateInRequest.setContainerNo2ExportCY(gateInRequest.getExpContainer2());

    // 20161130112233 - yyyyMMddHHmmss
    System.out.println("gateInRequest.getGateInDateTime() " + gateInRequest.getGateInDateTime());
    opusGateInRequest.setGateINDateTime(DateUtil.getJsonDateFromDate(gateInRequest.getGateInDateTime()));
    System.out.println("opusGateInRequest.getGateINDateTime() " + opusGateInRequest.getGateINDateTime());
    opusGateInRequest.setHaulageCode(gateInRequest.getHaulageCode());
    opusGateInRequest.setLaneNo(gateInRequest.getLaneNo());
    opusGateInRequest.setTruckHeadNo(gateInRequest.getTruckHeadNo());
    opusGateInRequest.setUserID(gateInRequest.getUserName());
    return opusGateInRequest;
  }

  @LogOpusData
  public OpusGateInReadResponse getGateInReadResponse(OpusGateInReadRequest opusGateInReadRequest,
      OpusRequestResponseDTO opusRequestResponseDTO) {
    System.out.println("gateInReadResponseURL " + gateInReadResponseURL);
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<OpusGateInReadRequest> request = new HttpEntity<OpusGateInReadRequest>(opusGateInReadRequest, headers);

    log.info("OpusGateInReadRequest : -" + (new Gson()).toJson(opusGateInReadRequest));

    // save in to db
    Future<Long> future = opusRequestResponseService.saveOpusRequest(opusRequestResponseDTO);

    ResponseEntity<OpusGateInReadResponse> response =
        restTemplate.postForEntity(gateInReadResponseURL, request, OpusGateInReadResponse.class);

    log.info("RESPONSE FROM OPUS: " + response.toString());

    opusRequestResponseDTO.setResponse(gson.toJson(response.getBody()));

    // update to db
    while (true) {
      if (future.isDone()) {
        try {
          System.out.println("Result from asynchronous process getGateInReadResponse - " + future.get());
          opusRequestResponseService.updateOpusResponse(opusRequestResponseDTO, future);
        } catch (InterruptedException | ExecutionException e) {
          log.error("Error Occured when update Opus Response getGateInReadResponse "
              + opusRequestResponseDTO.getGateinTime().toString());
          log.error(e.getMessage());
        }
        System.out.println("WHILE LOOP BROKEN getGateInReadResponse!!!!. ");
        break;
      }
      System.out.println("Continue doing something else getGateInReadResponse. ");

      try {
        Thread.sleep(asyncWaitTime);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        System.out.println("WHILE LOOP BROKEN ON THREAD EXCEPTION getGateInReadResponse!!!!. ");
        break;
      }
    }

    return response.getBody();
  }

  public GateInReponse constructGateInReponse(OpusGateInReadResponse opusGateInReadResponse,
      GateInReponse gateInReponse) {

    gateInReponse.setUserId(opusGateInReadResponse.getUserID());
    LocalDateTime localDateTime = DateUtil.getLocalDategFromString(opusGateInReadResponse.getGateInDateTime());

    gateInReponse.setGateINDateTime(DateUtil.getFormatteDateTime(localDateTime));
    gateInReponse.setHaulageCode(opusGateInReadResponse.getHaulageCode());
    gateInReponse.setLaneNo(opusGateInReadResponse.getLaneNo());
    gateInReponse.setTruckHeadNo(opusGateInReadResponse.getTruckHeadNo());
    // already hav import containers and export containers, update them
    List<ExportContainer> updatedExportContainerList =
        opusService.giReadResponseExporterContainerListToExportContainerList(
            opusGateInReadResponse.getExportContainerListCY(), gateInReponse.getExportContainers());

    List<ImportContainer> updatedImportContainerList =
        opusService.giReadResponseImportContainerListToImportContainerList(
            opusGateInReadResponse.getImportContainerListCY(), gateInReponse.getImportContainers());

    gateInReponse.setExportContainers(updatedExportContainerList);
    gateInReponse.setImportContainers(updatedImportContainerList);
    return gateInReponse;
  }



}
